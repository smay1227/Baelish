package com.darna.wmxfx.atys;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.bean.Order;
import com.darna.wmxfx.bean.OrderAmount;
import com.darna.wmxfx.bean.OrderConfirm;
import com.darna.wmxfx.bean.OrderJson;
import com.darna.wmxfx.bean.OrderShop;
import com.darna.wmxfx.bean.OrderVouchers;
import com.darna.wmxfx.bean.UserAddress;
import com.darna.wmxfx.dialog.DialogDeliveryTime;
import com.darna.wmxfx.dialog.DialogEditText;
import com.darna.wmxfx.dialog.DialogIntergral;
import com.darna.wmxfx.dialog.DialogVoucher;
import com.darna.wmxfx.net.NetOrderCheck;
import com.darna.wmxfx.net.NetOrderConfirm;
import com.darna.wmxfx.net.NetUserAddress;
import com.darna.wmxfx.utils.ListViewUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Aty_Order extends Activity {
	Order mOrder;
	List<OrderJson> orderJsons = new ArrayList<OrderJson>();
	ListView mListView;
	OrderAdapter orderAdapter;
	ListViewUtil listViewUtil;
	OrderConfirm orderConfirm;
	TextView tv_onlinepay, tv_onpay, tv_voucher, tv_vouchernum, tv_totalnum, tv_intergralnum,
	         tv_username, tv_userphone, tv_useraddress;
	ImageView iv_onlinepay, iv_onpay, iv_rleft;
	RelativeLayout rl_onlinepay, rl_onpay, rl_voucher, rl_intergral, rl_address;
	String[] voucherList, Integralist;
	Map<String, String> integraMap;
	List<UserAddress> mAddresses;
	Button btn_pay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_order);
		
		listViewUtil = new ListViewUtil();
		orderAdapter = new OrderAdapter();
		orderConfirm = new OrderConfirm();
		
		mListView = (ListView) findViewById(R.id.lv_shop);
		tv_onlinepay = (TextView) findViewById(R.id.tv_onlinepay);
		tv_onpay = (TextView) findViewById(R.id.tv_onpay);
		iv_onlinepay = (ImageView) findViewById(R.id.iv_onlinepay);
		iv_onpay = (ImageView) findViewById(R.id.iv_onpay);
		rl_onlinepay = (RelativeLayout) findViewById(R.id.rl_onlinepay);
		rl_onpay = (RelativeLayout) findViewById(R.id.rl_onpay);
		rl_voucher = (RelativeLayout) findViewById(R.id.rl_voucher);
		tv_totalnum = (TextView) findViewById(R.id.tv_totalnum);
		tv_intergralnum = (TextView) findViewById(R.id.tv_intergralnum);
		rl_intergral = (RelativeLayout) findViewById(R.id.rl_intergral);
		rl_address = (RelativeLayout) findViewById(R.id.rl_address);
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_userphone = (TextView) findViewById(R.id.tv_userphone);
		tv_useraddress = (TextView) findViewById(R.id.tv_useraddress);
		iv_rleft = (ImageView) findViewById(R.id.iv_rleft);
		btn_pay = (Button) findViewById(R.id.btn_pay);
		
		mListView.setAdapter(orderAdapter);
		
		getAddressData();
		getData();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		getAddressData();
	}

	private void getAddressData() {
		new NetUserAddress(this, Config.getCachedToken(this), new NetUserAddress.SuccessCallback() {
			@Override
			public void onSuccess(List<UserAddress> addresses) {
				mAddresses = addresses;
				
				setAddressOnclick();
				
				setAddressText();
			}
		}, new NetUserAddress.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)) {
					System.out.println("获取数据失败");
				}else if (errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)) {
					//进行登录和定位操作，然后再调用这个方法
					
				}
			}
		});
	}

	private void init() {
		orderConfirm.setPay_radio("cash_on");
		
		iv_rleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		rl_onlinepay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				orderConfirm.setPay_radio("cash_on");
				setPayRadio();
			}
		});
		
		rl_onpay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				orderConfirm.setPay_radio("cash_on_delivery");
				setPayRadio();
			}
		});
		
		setPayRadio();
		
		List<OrderVouchers> vouchers = mOrder.getVouchers();
		if (vouchers.size() > 0) {
			rl_voucher.setVisibility(View.VISIBLE);
			tv_vouchernum = (TextView) findViewById(R.id.tv_vouchernum);
			tv_vouchernum.setText("共"+ vouchers.size() +"张优惠劵");
			setVoucherOnClick();
			voucherList = new String[vouchers.size() + 1];
			voucherList[0] = "不使用代金券";
			for (int i = 1; i < voucherList.length; i++) {
				voucherList[i] = vouchers.get(i-1).getVouchers_name(); 
			}
			orderConfirm.setOrderVouchers("不使用代金券");
		}else {
			rl_voucher.setVisibility(View.GONE);
		}
		
		if (mOrder.getIntegralist() > 100) {
			rl_intergral.setVisibility(View.VISIBLE);
			int integralist = mOrder.getIntegralist();
			tv_intergralnum.setText("使用000积分（共" + integralist + "积分）");
        	int vaild = (int) Math.floor(integralist / 100);
        	integraMap = new HashMap<String, String>();
        	for (int i = 1; i <= vaild; i++) {
        		integraMap.put(i+"", i*100 + "积分");
    		}
        	Integralist = new String[vaild + 1];
        	Integralist[0] = "不使用积分";
        	orderConfirm.setUse_integral("不使用积分");
        	for(int i =1; i <= integraMap.size(); i++){
        		Integralist[i] =  integraMap.get(i+"");
        	}
        	setIntegralistOnClick();
		}else {
			rl_intergral.setVisibility(View.GONE);
		}
		
		setPrice();
		
		btn_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setPayOnClick();
			}
		});
	}

	private void setPayOnClick() {
		String full_voucher_id = "";
		if (mOrder.getVouchers().size() > 0 && !orderConfirm.getOrderVouchers().equals("不使用代金券")) {
			OrderVouchers voucher = getVoucher();
			full_voucher_id = "full" + "_" +voucher.getVouchers_id() + "_" + voucher.getVouchers_money();
		}
		String use_integral = "0";
		if(mOrder.getIntegralist() > 100 && !orderConfirm.getUse_integral().equals("不使用积分")){
			use_integral = Config.getKeyInMap(orderConfirm.getUse_integral(), integraMap);
		}
		String sub_post = ChangeOrderShopToJson(orderJsons);
		System.out.println(sub_post);
		new NetOrderConfirm(this, Config.getCachedToken(this), orderConfirm.getPay_radio(), orderConfirm.getD02011_v(), full_voucher_id, use_integral, sub_post, new NetOrderConfirm.SuccessCallback() {
			@Override
			public void onSuccess(OrderAmount orderAmount) {
				if (!orderConfirm.getPay_radio().equals("cash_on_delivery")) {
					Intent intent = new Intent(Aty_Order.this, Aty_Pay.class);
					intent.putExtra(Config.KEY_AMOUNT, 1);//orderAmount.getAmount() * 100
					intent.putExtra(Config.KEY_ORDERSN, orderAmount.getSn());
					startActivity(intent);
					finish();
				}else {
					Intent intent = new Intent(Aty_Order.this, Aty_UnfinishedOrder.class);
					intent.putExtra(Config.KEY_ORDERSN, orderAmount.getSn());
					startActivity(intent);
	                finish();
				}
			}
		}, new NetOrderConfirm.FailCallback() {
			
			@Override
			public void onFail(String errorCode) {
				Toast.makeText(Aty_Order.this, "提交订单失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void setAddressText() {
		//Boolean flag = false;
		for (int i = 0; i < mAddresses.size(); i++) {
			UserAddress address = mAddresses.get(i);
			if (address.getDefaultFlg().equals("1")) {
				//flag = true;
				tv_useraddress.setText(address.getArea_address()+address.getDetailed_address());
				tv_username.setText(address.getConsignee());
				tv_userphone.setText(address.getTel());
				orderConfirm.setD02011_v(address.getD02011());
			}
		}
		/*if (!flag) {
			tv_useraddress.setText(mAddresses.get(0).getArea_address()+mAddresses.get(0).getDetailed_address());
			tv_username.setText(mAddresses.get(0).getConsignee());
			tv_userphone.setText(mAddresses.get(0).getTel());
			orderConfirm.setD02011_v(mAddresses.get(0).getD02011());
		}*/
	}

	private void setAddressOnclick() {
		rl_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Aty_Order.this, Aty_CommonAddress.class));
			}
		});
	}

	private void setIntegralistOnClick() {
		tv_intergralnum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Aty_Order.this, DialogIntergral.class);
				i.putExtra(Config.KEY_DICT, Integralist);
				i.putExtra(Config.KEY_CODE, orderConfirm.getUse_integral());
				startActivityForResult(i, Config.KEY_INTEGRREQUESTCODE);
			}
		});
	}

	private void setVoucherOnClick() {
		tv_vouchernum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Aty_Order.this, DialogVoucher.class);
				i.putExtra(Config.KEY_DICT, voucherList);
				i.putExtra(Config.KEY_CODE, orderConfirm.getOrderVouchers());
				startActivityForResult(i, Config.KEY_VOUCHEREQUESTCODE);
			}
		});
	}

	private void setPayRadio() {
		if (orderConfirm.getPay_radio().equals("cash_on_delivery")) {
			iv_onlinepay.setVisibility(View.GONE);
			iv_onpay.setVisibility(View.VISIBLE);
		}else {
			iv_onlinepay.setVisibility(View.VISIBLE);
			iv_onpay.setVisibility(View.GONE);
		}
	}

	private void getData() {
		new NetOrderCheck(this, Config.getCachedToken(this), new NetOrderCheck.SuccessCallback() {
			@Override
			public void onSuccess(Order order) {
				mOrder = order;
				OrderJson orderJson;
				List<OrderShop> orderShops = order.getShops();
				OrderShop orderShop;
				for (int i = 0; i < orderShops.size(); i++) {
					orderShop = orderShops.get(i);
					orderJson = new OrderJson();
					orderJson.setShop_id(orderShop.getShop_id());
					orderJson.setShop_name(orderShop.getShop_name());
					orderJson.setIs_book("0");
					orderJson.setIs_need_invoice("0");
					orderJson.setNote("");
					orderJsons.add(orderJson);
				}
				orderAdapter.notifyDataSetChanged();
				listViewUtil.setListViewHeightBasedOnChildren(mListView);
				init();
			}
		}, new NetOrderCheck.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)) {
					System.out.println("获取数据失败");
				}else if (errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)) {
					//进行登录和定位操作，然后再调用这个方法
					
				}
			}
		});
	}
	
	public class OrderAdapter extends BaseAdapter{
		
		OrderShopCell osc;

		@Override
		public int getCount() {
			return orderJsons.size();
		}

		@Override
		public OrderJson getItem(int position) {
			return orderJsons.get(position);
		}
		
		public OrderShop getOrderShop(int position){
			return mOrder.getShops().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_ordershop, parent, false);
				osc = new OrderShopCell();
				osc.tv_shopName = (TextView) convertView.findViewById(R.id.tv_shopName);
				osc.tv_rightnow = (TextView) convertView.findViewById(R.id.tv_rightnow);
				osc.tv_invoice = (TextView) convertView.findViewById(R.id.tv_invoice);
				osc.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
				convertView.setTag(osc);
			}else {
				osc = (OrderShopCell) convertView.getTag();
			}
			final OrderJson orderJson = getItem(position);
			final OrderShop orderShop = getOrderShop(position);
			
			osc.tv_shopName.setText(orderJson.getShop_name());
			if (orderJson.getIs_book().equals("0")) {
				osc.tv_rightnow.setText("立即送餐");
			}else {
				String day = null;
				if (orderJson.getBook_day().equals("today")) {
					day = "今天";
				}else if (orderJson.getBook_day().equals("tomorrow")) {
					day = "明天";
				}else if (orderJson.getBook_day().equals("after_tomorrow")) {
					day = "后天";
				}
				osc.tv_rightnow.setText(day + " " + orderJson.getBook_time());
			}
			if (orderJson.getIs_need_invoice().equals("0")) {
				osc.tv_invoice.setText("不需要发票");
			}else {
				osc.tv_invoice.setText(orderJson.getInvoice_title());
			}
			if (orderJson.getNote().equals("")) {
				osc.tv_comment.setText("填写备注");
			}else {
				osc.tv_comment.setText(orderJson.getNote());
			}
			osc.tv_rightnow.setOnClickListener(new OnClickListener() {
				@Override  
				public void onClick(View v) {
					Intent intent = new Intent(Aty_Order.this, DialogDeliveryTime.class);
					intent.putExtra(Config.KEY_SHOWBOOKWAY, orderShop.getShow_book_way());
					if (orderShop.getShow_book_way().equals("1") || orderShop.getShow_book_way().equals("3")) {
						intent.putStringArrayListExtra(Config.KEY_TODAYBOOK, (ArrayList<String>) orderShop.getTodayBook());
					}
					intent.putStringArrayListExtra(Config.KEY_OTHERBOOK, (ArrayList<String>) orderShop.getOtherBook());
					if (orderJson.getIs_book().equals("0")) {
						intent.putExtra(Config.KEY_DAY, "立即送餐");
						intent.putExtra(Config.KEY_TIME, "立即送餐");
					}else {
						if (orderJson.getBook_day().equals("today")) {
							intent.putExtra(Config.KEY_DAY, "今天");
						}else if (orderJson.getBook_day().equals("tomorrow")) {
							intent.putExtra(Config.KEY_DAY, "明天");
						}else if (orderJson.getBook_day().equals("after_tomorrow")) {
							intent.putExtra(Config.KEY_DAY, "后天");
						}
						intent.putExtra(Config.KEY_TIME, orderJson.getBook_time());
					}
					startActivityForResult(intent, position);
				}
			});
			
			osc.tv_invoice.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//startActivity(new Intent(Aty_Order.this, DialogEditText.class));
					Intent i = new Intent(Aty_Order.this, DialogEditText.class);
					i.putExtra(Config.KEY_FLAG, "invoice");
					i.putExtra(Config.KEY_NEEDINVOICE, orderJson.getIs_need_invoice());
					if (!orderJson.getIs_need_invoice().equals("0")) {
						i.putExtra(Config.KEY_INVOICE, orderJson.getInvoice_title());
					}
					startActivityForResult(i, position);
				}
			});
			
			osc.tv_comment.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Aty_Order.this, DialogEditText.class);
					i.putExtra(Config.KEY_FLAG, "note");
					i.putExtra(Config.KEY_NOTE, orderJson.getNote());
					startActivityForResult(i, position);
				}
			});
			
			return convertView;
		}
		
		public class OrderShopCell{
			TextView tv_shopName, tv_rightnow, tv_invoice, tv_comment;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			OrderJson orderJson;
			switch (resultCode) {
			case Config.DIALOG_DELIVERYTIME:
				orderJson = orderAdapter.getItem(requestCode);
				String day = data.getStringExtra(Config.KEY_DAY);
				String time = data.getStringExtra(Config.KEY_TIME);
				if (day.equals("立即送餐")) {
					orderJson.setIs_book("0");
				}else {
					orderJson.setIs_book("1");
					if (day.equals("今天")) {
						orderJson.setBook_day("today");
					}else if (day.equals("明天")) {
						orderJson.setBook_day("tomorrow");
					}else if (day.equals("后天")) {
						orderJson.setBook_day("after_tomorrow");
					}
					orderJson.setBook_time(time);
				}
				break;
	        case Config.DIALOG_INVOICE:
	        	orderJson = orderAdapter.getItem(requestCode);
	        	if (data.getStringExtra(Config.KEY_TEXT).equals("")) {
	        		orderJson.setIs_need_invoice("0");
				}else {
					orderJson.setIs_need_invoice("1");
					orderJson.setInvoice_title(data.getStringExtra(Config.KEY_TEXT));
				}
	        	break;
			case Config.DIALOG_NOTE:
				orderJson = orderAdapter.getItem(requestCode);
				orderJson.setNote(data.getStringExtra(Config.KEY_TEXT));
				break;
			case Config.DIALOG_VOUCHER:
				orderConfirm.setOrderVouchers(data.getStringExtra(Config.KEY_RETURNCODE));
				if (orderConfirm.getOrderVouchers().equals("不使用代金券")) {
					tv_vouchernum.setText("共"+ mOrder.getVouchers().size() +"张优惠劵");
				}else {
					tv_vouchernum.setText(orderConfirm.getOrderVouchers());
				}
				setPrice();
				break;
			case Config.DIALOG_INTERGRAl:
				orderConfirm.setUse_integral(data.getStringExtra(Config.KEY_RETURNCODE));
				if (!orderConfirm.getUse_integral().equals("不使用积分")) {
					tv_intergralnum.setText("使用" + orderConfirm.getUse_integral() + "（共" + mOrder.getIntegralist() + "积分）");
				}else {
					tv_intergralnum.setText("使用000积分（共" + mOrder.getIntegralist() + "积分）");
				}
				setPrice();
				break;
			default:
				break;
			}
			orderAdapter.notifyDataSetChanged();
			listViewUtil.setListViewHeightBasedOnChildren(mListView);
		}
	}
	
	public void setPrice(){
		BigDecimal price = mOrder.getSub_money();
		if (mOrder.getVouchers().size() > 0 && !orderConfirm.getOrderVouchers().equals("不使用代金券")) {
			OrderVouchers orderVoucher = getVoucher();
			price = price.subtract(orderVoucher.getVouchers_money());
		}
		
		if(mOrder.getIntegralist() > 100 && !orderConfirm.getUse_integral().equals("不使用积分")){
			String integ = Config.getKeyInMap(orderConfirm.getUse_integral(), integraMap);
			BigDecimal inteDecimal = new BigDecimal(integ);
			price = price.subtract(inteDecimal);
		}
		
		if (price.compareTo(new BigDecimal(0)) >= 0) {
			tv_totalnum.setText(price+"元");
		}else {
			tv_totalnum.setText("0元");
		}
		
		
	}
	
	public OrderVouchers getVoucher(){
		OrderVouchers orderVoucher = new OrderVouchers();
		List<OrderVouchers> vouchers = mOrder.getVouchers();
		for (int i = 0; i < vouchers.size(); i++) {
			if (vouchers.get(i).getVouchers_name().equals(orderConfirm.getOrderVouchers())) {
				orderVoucher = vouchers.get(i);
				break;
			}
		}
		return orderVoucher;
	}
	
	 public String ChangeOrderShopToJson(List<OrderJson> orderShops){
	    	String jsonString;
	    	JSONArray orderjsonArray = new JSONArray();
	    	//JSONObject jsonObject = new JSONObject();
	    	JSONObject orderJsonObject;
	    	OrderJson orderShop;
	    	for(int i = 0; i < orderShops.size(); i++){
	    		orderJsonObject = new JSONObject();
	    		orderShop = orderShops.get(i);
	    		try {
					orderJsonObject.put(Config.KEY_SHOPID, orderShop.getShop_id());
					orderJsonObject.put(Config.KEY_SHOPNAME, orderShop.getShop_name());
					orderJsonObject.put(Config.KEY_ISBOOK, orderShop.getIs_book());
					orderJsonObject.put(Config.KEY_ISNEEDINVOICE, orderShop.getIs_need_invoice());
					orderJsonObject.put(Config.KEY_NOTE, orderShop.getNote());
					orderJsonObject.put(Config.KEY_INVOICETITLE, orderShop.getInvoice_title());
					orderJsonObject.put(Config.KEY_BOOKDAY, orderShop.getBook_day());
					orderJsonObject.put(Config.KEY_BOOKTIME, orderShop.getBook_time());
					orderjsonArray.put(orderJsonObject);
				} catch (JSONException e) {
					e.printStackTrace();
				}
	    	}
	    	
	    	jsonString = orderjsonArray.toString();
	    	return jsonString;
	    }
	
}

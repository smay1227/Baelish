package com.darna.wmxfx.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.atys.Aty_CommonAddressAdd;
import com.darna.wmxfx.atys.Aty_LoginCode;
import com.darna.wmxfx.atys.Aty_Order;
import com.darna.wmxfx.bean.API_Cart;
import com.darna.wmxfx.bean.API_CartDish;
import com.darna.wmxfx.bean.API_CartShop;
import com.darna.wmxfx.bean.UserAddress;
import com.darna.wmxfx.net.NetCartClear;
import com.darna.wmxfx.net.NetCartDishDelete;
import com.darna.wmxfx.net.NetCartInfo;
import com.darna.wmxfx.net.NetCartModify;
import com.darna.wmxfx.net.NetLocate;
import com.darna.wmxfx.net.NetLogin;
import com.darna.wmxfx.net.NetLoginStatus;
import com.darna.wmxfx.net.NetUserAddress;
import com.darna.wmxfx.utils.PinnedHeaderListView;
import com.darna.wmxfx.utils.SectionedBaseAdapter;

public class Layout_Cart extends FrameLayout {
	
	Context mContext;
	TextView tv_poi, tv_pricenum, tv_nocart;
	CartAdapter cartAdapter;
	PinnedHeaderListView mlist;
	Button tv_clearcart, tv_check;
	RelativeLayout rl_bottom;

	public Layout_Cart(Context context) {
		this(context, null);
	}
	
	public Layout_Cart(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public Layout_Cart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initUI();
	}

	private void initUI() {
		LayoutInflater.from(mContext).inflate(R.layout.layout_cart, this, true);
		mlist = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
		tv_pricenum = (TextView) findViewById(R.id.tv_pricenum);
		tv_clearcart = (Button) findViewById(R.id.tv_clearcart);
		tv_check = (Button) findViewById(R.id.tv_check);
		rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
		tv_nocart = (TextView) findViewById(R.id.tv_nocart);
		
		cartAdapter = new CartAdapter(mContext);
		mlist.setAdapter(cartAdapter);
		getData();
		
		tv_check.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loginStatus();
			}
		});
		
		tv_clearcart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cartClear();
			}
		});
		
	}
	
	public void loginStatus(){
		new NetLoginStatus(mContext, Config.getCachedToken(mContext), new NetLoginStatus.SuccessCallback() {
			@Override
			public void onSuccess() {
				getAddressData();
			}

		}, new NetLoginStatus.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("获取信息失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					login();
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession("login","","",0,"");
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOGIN)) {
					//去登陆
					login();
				}
			}
		});
	}
	
	private void getAddressData() {
		new NetUserAddress(mContext, Config.getCachedToken(mContext), new NetUserAddress.SuccessCallback() {
			@Override
			public void onSuccess(List<UserAddress> addresses) {
				if (addresses.size() > 0) {
					mContext.startActivity(new Intent(mContext, Aty_Order.class));
				}else {
					mContext.startActivity(new Intent(mContext, Aty_CommonAddressAdd.class));
				}
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
	
	public void login(){
		Map<String, String> loginMap = Config.getcacheLogin(mContext);
		if (loginMap.get(Config.KEY_MOBILE_PHONE) == null) {
			//跳转验证码登陆页面
			mContext.startActivity(new Intent(mContext, Aty_LoginCode.class));
		}else {
			//后台登陆
			new NetLogin(mContext, Config.getCachedToken(mContext), loginMap.get(Config.KEY_MOBILE_PHONE), loginMap.get(Config.KEY_PASSWORD), new NetLogin.SuccessCallback() {
				@Override
				public void onSuccess() {
					getAddressData();
				}
			}, new NetLogin.FailCallback() {
				@Override
				public void onFail(String errorCode) {
					if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
						System.out.println("登陆失败");
					}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
						login();
					}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
						setAddressInSession("login","","",0,"");
					}else if (errorCode.equals(Config.RESULT_STATUS_UNLOGIN)) {
						login();
					}
				}
			});
		}
	}

	public void getData() {
		new NetCartInfo(mContext, Config.getCachedToken(mContext), new NetCartInfo.SuccessCallback() {
			@Override
			public void onSuccess(API_Cart CartInfo) {
				cartAdapter.add(CartInfo.getShop_list());
				if (CartInfo.getShop_list().size() <= 0) {
					rl_bottom.setVisibility(View.GONE);
					tv_nocart.setVisibility(View.VISIBLE);
				}else {
					tv_nocart.setVisibility(View.GONE);
					rl_bottom.setVisibility(View.VISIBLE);
					tv_pricenum.setText(Config.priceFormat(CartInfo.getCart_total()));
				}
			}
		}, new NetCartInfo.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("获取购物车信息失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession("getdata","","",0,"");
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession("getdata","","",0,"");
				}
			}
		});
	}
	
	public void ModifyCart(final String shopId, final String dishId, final int num, final String attrCode){
		new NetCartModify(mContext, shopId, dishId, num, attrCode, Config.getCachedToken(mContext), new NetCartModify.SuccessCallback() {
			@Override
			public void onSuccess(String success) {
				//layout_Cart.getData();
				getData();
			}
		}, new NetCartModify.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("修改购物车失败");
				}else {
					if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
						System.out.println("获取购物车信息失败");
					}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
						setAddressInSession("modify",shopId,dishId,num,attrCode);
					}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
						setAddressInSession("modify",shopId,dishId,num,attrCode);
					}
				}
			}
		});
	}
	
	private void cartClear(){
		new NetCartClear(mContext, Config.getCachedToken(mContext), new NetCartClear.SuccessCallback() {
			@Override
			public void onSuccess() {
				getData();
			}
		}, new NetCartClear.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("获取购物车信息失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession("clear","" ,"" ,0,"");
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession("clear","" ,"" ,0,"");
				}
			}
		});
	}
	
	private void deleteDish(final String shopId, final String dishId, final String attrCode){
		new NetCartDishDelete(mContext, Config.getCachedToken(mContext), shopId, dishId, attrCode, new NetCartDishDelete.SuccessCallback() {
			@Override
			public void onSuccess() {
				getData();
			}
		}, new NetCartDishDelete.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("删除购物车菜品失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){  }else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession("delete", shopId, dishId, 0, attrCode);
				}
			}
		});
	}
	
	private void setAddressInSession(final String flag, final String shopId, final String dishId, final int num, final String attrCode) {
		Map<String, String> poiMap = Config.getCachePOI(mContext);
		new NetLocate(Config.getCachedToken(mContext), poiMap.get(Config.KEY_POSTITLE), 
				poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
					@Override
					public void onSuccess() {
						if (flag.equals("getdata")) {
							getData();
						}else if (flag.equals("modify")) {
							ModifyCart(shopId, dishId, num, attrCode);
						}else if (flag.equals("delete")) {
							deleteDish(shopId, dishId, attrCode);
						}else if (flag.equals("clear")) {
							cartClear();
						}else if (flag.equals("login")) {
							login();
						}
					}
				}, new NetLocate.FailCallback() {
					@Override
					public void onFail() {
						System.out.println("上传缓存地址到服务器失败");
					}
				});
	}
	
	public class CartAdapter extends SectionedBaseAdapter {
		List<API_CartShop> shoplist = new ArrayList<API_CartShop>();
		LayoutInflater mLayoutInflater;
		CartGroupCell cgc;
		CartItemCell cic;
		Layout_Cart layout_Cart;
		Context mContext;
		
		public CartAdapter(Context mContext) {
			this.mContext = mContext;
			mLayoutInflater = LayoutInflater.from(mContext);
			//layout_Cart = new Layout_Cart(mContext);
		}
		
		public void add(List<API_CartShop> shoplist){
			clear();
			this.shoplist = shoplist;
			notifyDataSetChanged();
		}
		
		public void clear(){
			this.shoplist.clear();
		}

		@Override
		public API_CartDish getItem(int section, int position) {
			return shoplist.get(section).getDish_list().get(position);
		}
		
		public API_CartShop getSection(int section){
			return shoplist.get(section);
		}

		@Override
		public long getItemId(int section, int position) {
			return position;
		}

		@Override
		public int getSectionCount() {
			return shoplist.size();
		}

		@Override
		public int getCountForSection(int section) {
			return shoplist.get(section).getDish_list().size();
		}

		@Override
		public View getItemView(int section, int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(R.layout.item_cartitem, parent, false);
				cic = new CartItemCell();
				cic.tv_dishname = (TextView) convertView.findViewById(R.id.tv_dishname);
				cic.tv_dishatrrName = (TextView) convertView.findViewById(R.id.tv_dishatrrName);
				cic.tv_dishatrrPrice = (TextView) convertView.findViewById(R.id.tv_dishatrrPrice);
				cic.tv_dishnum = (TextView) convertView.findViewById(R.id.tv_dishnum);
				cic.tv_dishpricetotaltxt = (TextView) convertView.findViewById(R.id.tv_dishpricetotaltxt);
				cic.tv_pricenum = (TextView) convertView.findViewById(R.id.tv_pricenum);
				cic.ll_dishdelete = (LinearLayout) convertView.findViewById(R.id.ll_dishdelete);
				cic.iv_dishadd = (ImageView) convertView.findViewById(R.id.iv_dishadd);
				cic.iv_dishdiv = (ImageView) convertView.findViewById(R.id.iv_dishdiv);
				convertView.setTag(cic);
			}else {
				cic = (CartItemCell) convertView.getTag();
			}
			final API_CartDish dish = getItem(section, position);
			cic.tv_dishname.setText(dish.getDish_main_name());
			if (dish.getAttr_code().equals("0,0,0")) {
				cic.tv_dishatrrName.setText("单价");
			}else {
				cic.tv_dishatrrName.setText(dish.getAttr_name());
			}
			cic.tv_dishatrrPrice.setText(dish.getDish_price() + "元");
			cic.tv_dishnum.setText(dish.getNumber() + "");
			cic.tv_dishpricetotaltxt.setText("小计 （含打包费" + dish.getAll_pack_price() + "元）");
			cic.tv_pricenum.setText(dish.getDish_price_subtotal()+ "");
			cic.ll_dishdelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteDish(dish.getShop_id(), dish.getDish_id(), dish.getAttr_code());
				}
			});
			
			cic.iv_dishadd.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ModifyCart(dish.getShop_id(), dish.getDish_id(), 1, dish.getAttr_code());
				}
			});
			
			cic.iv_dishdiv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(dish.getNumber() > 1){
						ModifyCart(dish.getShop_id(), dish.getDish_id(), -1, dish.getAttr_code());
					}
				}
			});
			
			return convertView;
		}
		
		@Override
		public View getSectionHeaderView(int section, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(R.layout.item_carthead, parent, false);
				cgc = new CartGroupCell();
				cgc.tv_shopname = (TextView) convertView.findViewById(R.id.tv_shopname);
				cgc.tv_deliverycost = (TextView) convertView.findViewById(R.id.tv_deliverycost);
				convertView.setTag(cgc);
			}else {
				cgc = (CartGroupCell) convertView.getTag();
			}
			API_CartShop shop = getSection(section);
			cgc.tv_shopname.setText(shop.getShop_name());
			cgc.tv_deliverycost.setText("配送费￥" + shop.getDelivery_price());
			return convertView;
		}
		
		public class CartGroupCell{
			TextView tv_shopname, tv_deliverycost;
		}
		
		public class CartItemCell{
			TextView tv_dishname, tv_dishatrrName, tv_dishatrrPrice, tv_dishnum, tv_dishpricetotaltxt, tv_pricenum;
			LinearLayout ll_dishdelete;
			ImageView iv_dishadd, iv_dishdiv;
		}

	}

}

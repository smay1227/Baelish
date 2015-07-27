package com.darna.wmxfx.atys;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.bean.DishDetail;
import com.darna.wmxfx.bean.DishDetailInfo;
import com.darna.wmxfx.bean.DishDetailInfoClassify;
import com.darna.wmxfx.dialog.DishPop;
import com.darna.wmxfx.net.NetCartModify;
import com.darna.wmxfx.net.NetDishDetail;
import com.darna.wmxfx.net.NetDishpop;
import com.darna.wmxfx.net.NetLocate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Aty_DishIndex extends Activity{

	LinearLayout tv_sizechoose, tv_tastechoose, tv_specchoose;
	DishDetailInfo dishInfo;
	String shopId, dishId, sizeCode = "0", specCode = "0", tasteCode = "0";
	TextView tv_dishname, tv_dishdesc, tv_dishsize, tv_dishtaste, tv_dishspec, tv_dishnum, tv_dishpricetotaltxt,
				tv_pricenum, tv_dishprice;
	ImageView iv_dishimage, iv_dishadd, iv_dishdiv, iv_rleft;
	RelativeLayout rl_dishsize, rl_dishtaste, rl_dishspec, rl_addtocart;
	int num = 1;
	Boolean detailPop;
	DishDetail dishDetail;
	String[] dishSize, dishTaste, dishSpec;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_dishindex);
		
		shopId = getIntent().getStringExtra(Config.KEY_SHOPID);
		dishId = getIntent().getStringExtra(Config.KEY_DISH_ID);
		detailPop = getIntent().getBooleanExtra(Config.KEY_DETAIL_POPUP, false);
		
		tv_dishname = (TextView) findViewById(R.id.tv_dishname);
		tv_dishdesc = (TextView) findViewById(R.id.tv_dishdesc);
		iv_dishimage = (ImageView) findViewById(R.id.iv_dishimage);
		rl_dishsize = (RelativeLayout) findViewById(R.id.rl_dishsize);
		rl_dishtaste = (RelativeLayout) findViewById(R.id.rl_dishtaste);
		rl_dishspec = (RelativeLayout) findViewById(R.id.rl_dishspec);
		tv_dishnum = (TextView) findViewById(R.id.tv_dishnum);
		tv_dishpricetotaltxt = (TextView) findViewById(R.id.tv_dishpricetotaltxt);
		tv_pricenum = (TextView) findViewById(R.id.tv_pricenum);
		tv_dishprice = (TextView) findViewById(R.id.tv_dishprice);
		iv_dishadd = (ImageView) findViewById(R.id.iv_dishadd);
		iv_dishdiv = (ImageView) findViewById(R.id.iv_dishdiv);
		rl_addtocart = (RelativeLayout) findViewById(R.id.rl_addtocart);
		iv_rleft = (ImageView) findViewById(R.id.iv_rleft);
		
		iv_rleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		rl_addtocart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addToCart();
			}
		});
		
		iv_dishadd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				num++;
				setPriceAndNum();
			}
		});
		
		iv_dishdiv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (num > 1) {
					num--;
					setPriceAndNum();
				}
			}
		});
		
		getData();
	}
	

	public void init(){
		
		tv_dishname.setText(dishInfo.getDish_name());
		tv_dishdesc.setText("菜品描述：" + dishInfo.getDish_name());
		
		if (detailPop) {
			if (!dishInfo.getSize_dict().isEmpty()) {
				sizeCode = "1";
				rl_dishsize.setVisibility(View.VISIBLE);
				tv_sizechoose = (LinearLayout) findViewById(R.id.tv_sizechoose);
				tv_dishsize = (TextView) findViewById(R.id.tv_dishsize);
				
				dishSize= new String[dishInfo.getSize_dict().size()];
				Iterator<String> it = dishInfo.getSize_dict().keySet().iterator();
				int count = 0;
				while (it.hasNext()) {
					String key = (String) it.next();
					dishSize[count] = dishInfo.getSize_dict().get(key);
					count ++;
				}
			}
			
			if(!dishInfo.getTaste_dict().isEmpty()){
				tasteCode = "1";
				rl_dishtaste.setVisibility(View.VISIBLE);
				tv_tastechoose = (LinearLayout) findViewById(R.id.tv_tastechoose);
				tv_dishtaste = (TextView) findViewById(R.id.tv_dishtaste);
				
				dishTaste= new String[dishInfo.getTaste_dict().size()];
				Iterator<String> it = dishInfo.getTaste_dict().keySet().iterator();
				int count = 0;
				while (it.hasNext()) {
					String key = (String) it.next();
					dishTaste[count] = dishInfo.getTaste_dict().get(key);
					count ++;
				}
			}
			if(!dishInfo.getSpec_dict().isEmpty()){
				specCode = "1";
				rl_dishspec.setVisibility(View.VISIBLE);
				tv_specchoose = (LinearLayout) findViewById(R.id.tv_specchoose);
				tv_dishspec = (TextView) findViewById(R.id.tv_dishspec);
				
				dishSpec= new String[dishInfo.getSpec_dict().size()];
				Iterator<String> it = dishInfo.getSpec_dict().keySet().iterator();
				int count = 0;
				while (it.hasNext()) {
					String key = (String) it.next();
					dishSpec[count] = dishInfo.getSpec_dict().get(key);
					count ++;
				}
			}
			initAttr();
		}
		
		setPriceAndNum();
		
	}
	
	public void initAttr(){
		if (!dishInfo.getSize_dict().isEmpty()) {
			tv_dishsize.setText(dishInfo.getSize_dict().get(sizeCode));
			tv_sizechoose.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Aty_DishIndex.this, DishPop.class);
					i.putExtra(Config.KEY_CODE, dishInfo.getSize_dict().get(sizeCode));
					i.putExtra(Config.KEY_DICT, dishSize);
					startActivityForResult(i, Config.CODE_SIZE);
				}
			});
		} 
		
		if(!dishInfo.getTaste_dict().isEmpty()){
			tv_dishtaste.setText(dishInfo.getTaste_dict().get(tasteCode));
			tv_tastechoose.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Aty_DishIndex.this, DishPop.class);
					i.putExtra(Config.KEY_CODE, dishInfo.getTaste_dict().get(tasteCode));
					i.putExtra(Config.KEY_DICT, dishTaste);
					startActivityForResult(i, Config.CODE_TASTE);
					
				}
			});
		}
		
		if(!dishInfo.getSpec_dict().isEmpty()){
			tv_dishspec.setText(dishInfo.getSpec_dict().get(specCode));
			tv_specchoose.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Aty_DishIndex.this, DishPop.class);
					i.putExtra(Config.KEY_CODE, dishInfo.getSpec_dict().get(specCode));
					i.putExtra(Config.KEY_DICT, dishSpec);
					startActivityForResult(i, Config.CODE_SPEC);
				}
			});
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			String attrName = data.getStringExtra(Config.KEY_RETURNCODE);
			switch (requestCode) {
			case Config.CODE_SIZE:
				sizeCode = Config.getKeyInMap(attrName, dishInfo.getSize_dict());
				initAttr();
				break;
			case Config.CODE_SPEC:
				specCode = Config.getKeyInMap(attrName, dishInfo.getSpec_dict());
				initAttr();
				break;
			case Config.CODE_TASTE:
				tasteCode = Config.getKeyInMap(attrName, dishInfo.getTaste_dict());
				initAttr();
				break;
			default:
				break;
			}
			setPriceAndNum();
		}
	}
	
	public void initSingleDish(){
		tv_dishname.setText(dishDetail.getDishName());
		tv_dishdesc.setText("菜品描述：" + dishDetail.getDishName());
		
		setPriceAndNum();
	}
	
	public void setPriceAndNum(){
		tv_dishnum.setText(num + "");
		if (detailPop) {
			tv_dishprice.setText(getSinglePrice(sizeCode, specCode, tasteCode).get(Config.KEY_DISH_PRICE) + "元");
			
			Map<String, BigDecimal> priceMap = getPrice(num, sizeCode, specCode, tasteCode);
			tv_dishpricetotaltxt.setText("小计（含打包费" + priceMap.get(Config.KEY_PACK_PRICE) + "元）");
			tv_pricenum.setText(priceMap.get(Config.KEY_DISH_PRICE) + "");
		}else {
			tv_dishprice.setText(dishDetail.getPrice_disp() + "元");
			tv_dishpricetotaltxt.setText("小计（含打包费" + dishDetail.getPack_price() + "元）");
			tv_pricenum.setText(dishDetail.getPrice_disp().multiply(new BigDecimal(num)) + "");
		}
		
	}
	
	private Map<String, BigDecimal> getSinglePrice(String sizeCode, String specCode, String tasteCode){
		Map<String, BigDecimal> priceMap = new HashMap<String, BigDecimal>();
		Iterator<DishDetailInfoClassify> it = dishInfo.getClassify_info().iterator();
		DishDetailInfoClassify dishDetailClassify;
		while(it.hasNext()){
			dishDetailClassify = it.next();
			if(sizeCode.equals(dishDetailClassify.getSize_code()) && specCode.equals(dishDetailClassify.getSpec_code()) && tasteCode.equals(dishDetailClassify.getTaste_code())){
				priceMap.put(Config.KEY_DISH_PRICE, dishDetailClassify.getPrice_new());
				priceMap.put(Config.KEY_PACK_PRICE, dishDetailClassify.getPack_price());
			}
		}
		return priceMap;
	} 
	
	private Map<String, BigDecimal> getPrice(int num, String sizeCode, String specCode, String tasteCode){
		Map<String, BigDecimal> priceMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> singlePrice = getSinglePrice(sizeCode, specCode, tasteCode);
		priceMap.put(Config.KEY_DISH_PRICE, singlePrice.get(Config.KEY_DISH_PRICE).multiply(new BigDecimal(num)));
		priceMap.put(Config.KEY_PACK_PRICE, singlePrice.get(Config.KEY_PACK_PRICE).multiply(new BigDecimal(num)));
		return priceMap;
	}
	
	private void getData() {
		if(detailPop){
			new NetDishpop(this, Config.getCachedToken(this), shopId, dishId, new NetDishpop.SuccessCallback() {
				@Override
				public void onSuccess(DishDetailInfo dish) {
					dishInfo = dish;
					init();
				}
			}, new NetDishpop.FailCallback() {
				@Override
				public void onFail(String errorCode) {
					if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
						System.out.println("获取菜品详细信息失败");
						
					}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
						setAddressInSession("data");
					}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
						setAddressInSession("data");
					}
				}
			});
		}else {
			new NetDishDetail(this, Config.getCachedToken(this), shopId, dishId, new NetDishDetail.SuccessCallback() {
				@Override
				public void onSuccess(DishDetail dish) {
					dishDetail = dish;
					initSingleDish();
				}
			}, new NetDishDetail.FailCallback() {
				@Override
				public void onFail(String errorCode) {
					if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
						System.out.println("获取单个菜品详细信息失败");
						
					}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
						setAddressInSession("data");
					}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
						setAddressInSession("data");
					}
				}
			});
		}
	}
	
	protected void addToCart() {
	    String attr_code = sizeCode+","+specCode+","+tasteCode;
	    System.out.println("num" + num);
		new NetCartModify(this, shopId, dishId, num, attr_code, Config.getCachedToken(this), new NetCartModify.SuccessCallback() {
			@Override
			public void onSuccess(String success) {
				finish();
			}
		}, new NetCartModify.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("添加购物车失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession("cart");
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession("cart");
				}
			}
		});
	}
	
	private void setAddressInSession(final String flag) {
		Map<String, String> poiMap = Config.getCachePOI(this);
		new NetLocate(Config.getCachedToken(this), poiMap.get(Config.KEY_POSTITLE), 
				poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
					@Override
					public void onSuccess() {
						if (flag.equals("cart")) {
							addToCart();
						}else {
							getData();
						}
					}
				}, new NetLocate.FailCallback() {
					@Override
					public void onFail() {
						System.out.println("上传缓存地址到服务器失败");
					}
				});
	}
	
}

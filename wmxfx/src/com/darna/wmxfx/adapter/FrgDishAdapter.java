package com.darna.wmxfx.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.atys.Aty_DishIndex;
import com.darna.wmxfx.bean.API_Cart;
import com.darna.wmxfx.bean.Dish;
import com.darna.wmxfx.bean.Dishlist;
import com.darna.wmxfx.layout.RoundImageView;
import com.darna.wmxfx.net.NetCartDishDelete;
import com.darna.wmxfx.net.NetCartInfo;
import com.darna.wmxfx.net.NetCartModify;
import com.darna.wmxfx.net.NetLocate;
import com.darna.wmxfx.utils.CartUtil;
import com.darna.wmxfx.utils.SectionedBaseAdapter;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class FrgDishAdapter extends SectionedBaseAdapter {
	
	private List<Dish> data = new ArrayList<Dish>();
	private LayoutInflater mLayoutInflater;
	SectionHeadItem sectionHeadItem;
	SectionItem sectionItem;
	private ImageView buyImg;
	Context mContext;
	private ViewGroup anim_mask_layout;
	//private int buyNum = 0;//购买数量
	TextView tv_cartdishnum, tv_cartprice;
	public CartUtil cartUtil;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	public FrgDishAdapter(Context mContext, View v) {
		cartUtil = new CartUtil();
		mLayoutInflater = LayoutInflater.from(mContext);
		this.mContext = mContext;
		tv_cartdishnum = (TextView) v.findViewById(R.id.tv_cartdishnum);
		tv_cartprice = (TextView) v.findViewById(R.id.tv_cartprice);
		initImageLoader(mContext);
		getCartData();
	}
	
	private void getCartData() {
		new NetCartInfo(mContext, Config.getCachedToken(mContext), new NetCartInfo.SuccessCallback() {
			@Override
			public void onSuccess(API_Cart CartInfo) {
				cartUtil.cartInfo = CartInfo;
				notifyDataSetChanged();
				initView();
			}
		}, new NetCartInfo.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("获取购物车信息失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession("", "", 0, "", "cartinfo");
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession("", "", 0, "", "cartinfo");
				}
			}
		});
	}
	
	private void deleteDish(String shopId, String dishId, String attrCode){
		new NetCartDishDelete(mContext, Config.getCachedToken(mContext), shopId, dishId, attrCode, new NetCartDishDelete.SuccessCallback() {
			@Override
			public void onSuccess() {
				getCartData();
			}
		}, new NetCartDishDelete.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("删除购物车菜品失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession("", "", 0, "", "delete");
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession("", "", 0, "", "delete");
				}
			}
		});
	}

	public void initView() {
		this.notifyDataSetChanged();
		tv_cartdishnum.setText(cartUtil.getDishTotalNum(Config.getCachedShopId(mContext)) + "");
		tv_cartprice.setText("￥" + cartUtil.getDishPriceInShop(Config.getCachedShopId(mContext)));
		
	}

	public void addAll(List<Dish> data) {
		clear();
		this.data = data;
		notifyDataSetChanged();
	}
	
	public void clear(){
		this.data.clear();
	}

	@Override
	public Dishlist getItem(int section, int position) {
		return data.get(section).getDishes().get(position);
	}
	
	public String getSection(int section){
		return data.get(section).getType();
	}

	@Override
	public long getItemId(int section, int position) {
		return position;
	}

	@Override
	public int getSectionCount() {
		return data.size();
	}

	@Override
	public int getCountForSection(int section) {
		return data.get(section).getDishes().size();
	}

	@Override
	public View getItemView(int section, int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_dishright, parent, false);
			sectionItem = new SectionItem();
			sectionItem.tv_shopname = (TextView) convertView.findViewById(R.id.tv_shopname);
			sectionItem.tv_dishprice = (TextView) convertView.findViewById(R.id.tv_dishprice);
			sectionItem.iv_dishadd = (ImageView) convertView.findViewById(R.id.iv_dishadd);
			sectionItem.tv_dishnum = (TextView) convertView.findViewById(R.id.tv_dishnum);
			sectionItem.iv_dishdiv = (ImageView) convertView.findViewById(R.id.iv_dishdiv);
			sectionItem.iv_dishimage =  (RoundImageView) convertView.findViewById(R.id.iv_dishimage);
			sectionItem.rl_dishright = (RelativeLayout) convertView.findViewById(R.id.rl_dishright);
			convertView.setTag(sectionItem);
		}else {
			sectionItem = (SectionItem) convertView.getTag();
		}
		final Dishlist dishlist = getItem(section, position);
		sectionItem.tv_shopname.setText(dishlist.getDish_name());
		sectionItem.tv_dishprice.setText(Config.priceFormat(dishlist.getPrice_new()));
		
		String image = "http://www.wmxfx.com/" + dishlist.getThumb();
		//imageLoader.displayImage(image + "", sectionItem.iv_dishimage);
		
		final int num = cartUtil.getDishNum(dishlist.getShop_id(), dishlist.getDish_id());
		sectionItem.tv_dishnum.setText(num + "");
		if (num > 0) {
			sectionItem.tv_dishnum.setVisibility(View.VISIBLE);
		}else {
			sectionItem.tv_dishnum.setVisibility(View.GONE);
		}
		
		if (!dishlist.getDetail_popup()) {
			sectionItem.iv_dishadd.setVisibility(View.VISIBLE);
			if (num > 0) {
				sectionItem.iv_dishdiv.setVisibility(View.VISIBLE);
				
				sectionItem.iv_dishdiv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (num == 1) {
							deleteDish(dishlist.getShop_id(), dishlist.getDish_id(), "0,0,0");
						}else {
							ModifyCart(mContext, dishlist.getShop_id(), dishlist.getDish_id(), -1, "0,0,0");
						}
					}
				});
			}else {
				sectionItem.iv_dishdiv.setVisibility(View.GONE);
				sectionItem.tv_dishnum.setVisibility(View.GONE);
			}
		}
		
		sectionItem.iv_dishadd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dishlist.getDetail_popup()) {
					Intent i = new Intent(mContext, Aty_DishIndex.class);
					i.putExtra(Config.KEY_SHOPID, dishlist.getShop_id());
					i.putExtra(Config.KEY_DISH_ID, dishlist.getDish_id());
					i.putExtra(Config.KEY_DETAIL_POPUP, dishlist.getDetail_popup());
					i.putExtra(Config.KEY_NUM, num);
					mContext.startActivity(i);
				}else {
					int startPosition[] = new int[2];
					v.getLocationInWindow(startPosition);
					buyImg = new ImageView(mContext);
					buyImg.setImageResource(R.drawable.buyimg);
					buyImg.setLayoutParams(new LayoutParams(10, 10));
					buyImg.setScaleType(ImageView.ScaleType.CENTER);
					setUpAnim(buyImg, startPosition);
					ModifyCart(mContext, dishlist.getShop_id(), dishlist.getDish_id(), 1, "0,0,0");
				}
			}
		});
		
		sectionItem.rl_dishright.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, Aty_DishIndex.class);
				i.putExtra(Config.KEY_SHOPID, dishlist.getShop_id());
				i.putExtra(Config.KEY_DISH_ID, dishlist.getDish_id());
				i.putExtra(Config.KEY_DETAIL_POPUP, dishlist.getDetail_popup());
				mContext.startActivity(i);
			}
		});
		
		return convertView;
	}
	
	@Override
	public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_dishrighthead, parent, false);
			sectionHeadItem = new SectionHeadItem();
			sectionHeadItem.tv_shoptype = (TextView) convertView.findViewById(R.id.tv_shoptype);
			convertView.setTag(sectionHeadItem);
		}else {
			sectionHeadItem = (SectionHeadItem) convertView.getTag();
		}
		String type = getSection(section);
		sectionHeadItem.tv_shoptype.setText(type);
		return convertView;
	}
	
	public class SectionHeadItem{
		TextView tv_shoptype;
	}
	
	public class SectionItem{
		TextView tv_shopname, tv_dishprice, tv_dishnum;
		ImageView iv_dishadd, iv_dishdiv;
		RoundImageView iv_dishimage;
		RelativeLayout rl_dishright;
	}
	
	public void ModifyCart(Context context, final String shopId, final String dishId, final int num, final String attrCode){
		new NetCartModify(context, shopId, dishId, num, attrCode, Config.getCachedToken(mContext), new NetCartModify.SuccessCallback() {
			@Override
			public void onSuccess(String success) {
				getCartData();
			}
		}, new NetCartModify.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("修改购物车失败");
				}else {
					setAddressInSession(shopId, dishId, num, attrCode, "modify");
				}
			}
		});
	}
	
	private void setAddressInSession(final String shopId, final String dishId, final int num, final String attrCode, String flag) {
		Map<String, String> poiMap = Config.getCachePOI(mContext);
		if (flag.equals("cartinfo")) {
			new NetLocate(Config.getCachedToken(mContext), poiMap.get(Config.KEY_POSTITLE), 
					poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
						@Override
						public void onSuccess() {
							getCartData();
						}
					}, new NetLocate.FailCallback() {
						@Override
						public void onFail() {
							System.out.println("上传缓存地址到服务器失败");
						}
					});
		}else if(flag.equals("modify")){
			new NetLocate(Config.getCachedToken(mContext), poiMap.get(Config.KEY_POSTITLE), 
					poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
						@Override
						public void onSuccess() {
							ModifyCart(mContext, shopId, dishId, num, attrCode);
						}
					}, new NetLocate.FailCallback() {
						@Override
						public void onFail() {
							System.out.println("上传缓存地址到服务器失败");
						}
					});
		}else if (flag.equals("delete")) {
			new NetLocate(Config.getCachedToken(mContext), poiMap.get(Config.KEY_POSTITLE), 
					poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
						@Override
						public void onSuccess() {
							deleteDish(shopId, dishId, attrCode);
						}
					}, new NetLocate.FailCallback() {
						@Override
						public void onFail() {
							System.out.println("上传缓存地址到服务器失败");
						}
					});
		}
	}
	
	/**
	 * @Description: 创建动画层
	 * @param
	 * @return void
	 * @throws
	 */
	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) ((Activity) mContext).getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}
	
	private View addViewToAnimLayout(final ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}
	
	private void setUpAnim(final View v, int[] start_location) {
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout();
		anim_mask_layout.addView(v);//把动画小球添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout, v, start_location);
		final int[] end_location = new int[2];
		end_location[0] = start_location[0] - 200;
		end_location[1] = start_location[1] - 100;
		
		int endX = end_location[0] - start_location[0];
		int endY = end_location[1] - start_location[1];
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);
		
		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new DecelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationY.setFillAfter(true);
		
		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(100);// 动画的执行时间
		view.startAnimation(set);
		
		// 动画监听事件
				set.setAnimationListener(new AnimationListener() {
					// 动画的开始
					@Override
					public void onAnimationStart(Animation animation) {
						v.setVisibility(View.VISIBLE);
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
					}

					// 动画的结束
					@Override
					public void onAnimationEnd(Animation animation) {
						setAnim(v, end_location);
					}
				});
		
	}
	
	private void setAnim(final View v, int[] start_location) {
		final View view = addViewToAnimLayout(anim_mask_layout, v,
				start_location);
		int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
		tv_cartdishnum.getLocationInWindow(end_location);// shopCart是那个购物车

		// 计算位移
		int endX = 0 - start_location[0] + 40;// 动画位移的X坐标
		int endY = end_location[1] - start_location[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationY.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(300);// 动画的执行时间
		view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
				//buyNum++;//让购买数量加1
				//initView();
			}
		});
	
	}
	
	
	public static void initImageLoader(Context context) {  
        // This configuration tuning is custom. You can tune every option, you  
        // may tune some of them,  
        // or you can create default configuration by  
        // ImageLoaderConfiguration.createDefault(this);  
        // method.  
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove  
                                                                                                                                                                                                                                                                                                // for  
                                                                                                                                                                                                                                                                                                // release  
                                                                                                                                                                                                                                                                                                // app  
                .build();  
        // Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(config);  
    }
	
}

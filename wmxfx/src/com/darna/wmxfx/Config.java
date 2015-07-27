package com.darna.wmxfx;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * @author xiashuai
 *
 */
public class Config {
	
	public static String APP_ID = "com.darna.wmxfx";
	public static String CHARSET = "utf-8";
	
	public static final String SERVER_URL = "http://www.wmxfx.com/api/client/api.php";
	//public static final String SERVER_URL = "http://192.168.1.156/darna/api/client/api.php";
	public static final String KEY_ACTION = "Action";
	public static final String KEY_STATUS = "status";
	public static final String KEY_DATA = "data";
	public static final String KEY_TOKEN = "token";
	
	public static final String RESULT_STATUS_SUCCESS = "1";
	public static final String RESULT_STATUS_FAIL = "0";
	public static final String RESULT_STATUS_INVALID_TOKEN = "2";
	public static final String RESULT_STATUS_UNLOCATE = "3";
	public static final String RESULT_STATUS_UNLOGIN = "4";
	public static final String RESULT_STATUS_CODE = "5";
	public static final String RESULT_DATA_NULL = "6";
 	
	public static final int DIALOG_DELIVERYTIME = 0;
	public static final int DIALOG_INVOICE = 1;
	public static final int DIALOG_NOTE = 2;
	public static final int DIALOG_VOUCHER = 3;
	public static final int DIALOG_INTERGRAl = 4;
	
	public static final int CODE_SIZE = 1;
	public static final int CODE_TASTE = 2;
	public static final int CODE_SPEC = 3;
	
	public static final int REQUESTCODE = 0;
	public static final int RESULTCODE = 1;
	public static final int KEY_VOUCHEREQUESTCODE = 10000;
	public static final int KEY_INTEGRREQUESTCODE = 9999;
	
	public static final String KEY_UID = "uid";
	public static final String KEY_ADMIN_UID = "admin";
	public static final String KEY_PASS = "pwd";
	public static final String KEY_PWD = "123456";
	public static final String KEY_INDEX = "index";
	public static final String KEY_SREACH = "search";
	public static final String KEY_CART = "cart";
	public static final String KEY_HistoryOrder = "historyorder";
	public static final String KEY_UnfinishOrder = "unfinishorder";
	public static final String KEY_UnCommentOrder = "uncommentorder";
	public static final String KEY_USERCENTER = "usercenter";
	public static final String KEY_TASTECODE = "tastecode";
	public static final String KEY_SEQUENCRCODE = "sequencecode";
	public static final String KEY_POSTITLE = "posTitle";
	public static final String KEY_POSX = "posX";
	public static final String KEY_POSY = "posY";
	public static final String KEY_POSADDRESS = "posAddress";
	public static final String KEY_SHOPID = "shop_id";
	public static final String KEY_SHOPNAME = "shop_name";
	public static final String KEY_SHOPTHUMB = "shop_thumb";
	public static final String KEY_DISTANCE = "distance";
	public static final String KEY_DELITIME = "deli_time";
	public static final String KEY_SEARCHKEY = "search_key";
	public static final String KEY_SHOPTYPESUB = "shop_type_sub";
	public static final String KEY_SORT = "sort";
	public static final String KEY_NAME = "name";
	public static final String KEY_SUBCODE = "sub_code";
	public static final String KEY_DISH_LIST = "dish_list";
	public static final String KEY_DISH_ID = "dish_id";
	public static final String KEY_DISH_NAME = "dish_name";
	public static final String KEY_PRICE_OLD = "price_old";
	public static final String KEY_PRICE_NEW = "price_new";
	public static final String KEY_PRICE_DISP = "price_disp";
	public static final String KEY_PACK_PRICE = "pack_price";
	public static final String KEY_DISH_THUMB = "thumb";
	public static final String KEY_HAS_ACTIVITY = "has_activity";
	public static final String KEY_DETAIL_POPUP = "detail_popup";
	public static final String KEY_IN_SHORT_SUPPLY = "in_short_supply";
	public static final String KEY_TYPE = "type";
	public static final String KEY_NUM = "num";
	public static final String KEY_ATTRCODE = "attr_code";
	public static final String KEY_ATTRNAME = "attr_name";
	public static final String KEY_ISNEWUSER = "is_new_user";
	public static final String KEY_CART_TOTAL = "cart_total";
	public static final String KEY_SHOP_LIST = "shop_list";
	public static final String KEY_DIS_RANGE = "dis_range";
	public static final String KEY_SHOP_ITEM_MONEY = "shop_item_money";
	public static final String KEY_SHOPALLPACKPRICE = "shop_all_pack_price";
	public static final String KEY_DELIVERYPRICE = "delivery_price";
	public static final String KEY_ACTIVITYFLG = "activity_flg";
	public static final String KEY_MYSELFPARAM = "myself_param";
	public static final String KEY_SHOPREALPRICE = "shop_real_price";
	public static final String KEY_INTRODUCE = "introduce";
	public static final String KEY_DISHMAINNAME = "dish_main_name";
	public static final String KEY_DISH_PRICE = "dish_price";
	public static final String KEY_THUMB = "thumb";
	public static final String KEY_NUMBER = "number";
	public static final String KEY_DISHPRICESUBTOTAL = "dish_price_subtotal";
	public static final String KEY_ALLPACKPRICE = "all_pack_price";
	public static final String KEY_ACTIVITYTYPE = "activity_type";
	public static final String KEY_NOTICE = "notice";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_AVEREGECOST = "average_cost";
	public static final String KEY_BUSINESSHOUR = "businesshour";
	public static final String KEY_ACTIVITYDESC = "activitydesc";
	public static final String KEY_DELIVERYCOST = "deliverycost";
	public static final String KEY_AVERAGE_POINT = "average_point";
	public static final String KEY_LOCATE = "locate";
	public static final String KEY_LOGIN = "login";
	public static final String KEY_POINT_TOTAL = "pointTotal";
	public static final String KEY_TOTAL = "total";
	public static final String KEY_POINT_LIST = "pointList";
	public static final String KEY_MOBILE_PHONE = "mobile_phone";
	public static final String KEY_ALIAS = "alias";
	public static final String KEY_POINT = "point";
	public static final String KEY_EVALUATE_TIME = "evaluate_time";
	public static final String KEY_EXPERIENCE = "experience";
	public static final String KEY_CLASSIFY_INFO = "classify_info";
	public static final String KEY_SIZE_CODE = "size_code";
	public static final String KEY_SPEC_CODE = "spec_code";
	public static final String KEY_TASTE_CODE = "taste_code";
	public static final String KEY_SIZE_DICT = "size_dict";
	public static final String KEY_SPEC_DICT = "spec_dict";
	public static final String KEY_TASTE_DICT = "taste_dict";
	public static final String KEY_HAS_CLASSIFY = "has_classify";
	public static final String KEY_PACKPRICE = "pack_price";
	public static final String KEY_CODE = "code";
	public static final String KEY_DICT = "dict";
	public static final String KEY_RETURNCODE = "returncode";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_MOBILE = "mobile";
	public static final String KEY_SHOPLIST = "shoplist";
	public static final String KEY_SHOWBOOKWAY = "show_book_way";
	public static final String KEY_TODAYBOOK = "today_book";
	public static final String KEY_OTHERBOOK = "other_book";
	public static final String KEY_VOUCHERS = "vouchers";
	public static final String KEY_VOUCHERSID = "vouchers_id";
	public static final String KEY_VOUCHERSTYPE = "vouchers_type";
	public static final String KEY_VOUCHERSNAME = "vouchers_name";
	public static final String KEY_VOUCHERSMONEY = "vouchers_money";
	public static final String KEY_INTEGRAL = "integralist";
	public static final String KEY_USERFLG = "userflg";
	public static final String KEY_ALLDISHMONEY = "all_dish_money";
	public static final String KEYALLDELIVERYCOST = "all_delivery_cost";
	public static final String KEY_SUBMONEY = "sub_money";
	public static final String KEY_PHONENUM = "phoneNum";
	public static final String KEY_DAY = "day";
	public static final String KEY_TIME = "time";
	public static final String KEY_NEEDINVOICE = "needinvoice";
	public static final String KEY_INVOICE = "invoice";
	public static final String KEY_TEXT = "text";
	public static final String KEY_FLAG = "flag";
	public static final String KEY_NOTE = "note";
	public static final String KEY_USERNAME = "user_name";
	public static final String KEY_D02011 = "d02011";
	public static final String KEY_CONSIGNEE = "consignee";
	public static final String KEY_AREAADDRESS = "area_address";
	public static final String KEY_DETAILEDADDRESS = "detailed_address";
	public static final String KEY_TEL = "tel";
	public static final String KEY_DEFAULTFLG = "defaultFlg";
	public static final String KEY_STREETADDRESS = "street_address";
	public static final String KEY_PAYRADIO = "pay_radio";
	public static final String KEY_D02011_V = "d02011_v";
	public static final String KEY_FULLVOUCHERID = "full_voucher_id";
	public static final String KEY_USEINTEGRAL = "use_integral";
	public static final String KEY_SUBPOST = "sub_post";
	public static final String KEY_ISBOOK = "is_book";
	public static final String KEY_ISNEEDINVOICE = "is_need_invoice";
	public static final String KEY_INVOICETITLE = "invoice_title";
	public static final String KEY_BOOKDAY = "book_day";
	public static final String KEY_BOOKTIME = "book_time";
	public static final String KEY_RECIPIENT_PHONE = "recipient_phone";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_ORDERSN = "sn";
	public static final String ACTION_HISTORYORDER = "HistoryOrder";
	public static final String KEY_ORDERSTATUS = "order_status";
	public static final String KEY_DISH = "dish";
	public static final String KEY_ORDERAMOUNT = "order_amount";
	public static final String KEY_ADDTIME = "add_time";
	public static final String KEY_JUSTSHOP = "shop";
	public static final String KEY_CUSTOMREALPRICE = "customer_real_price";
	public static final String KEY_PAYID = "pay_id";
	public static final String KEY_SN = "order_sn";
	public static final String KEY_SHIPPINGTIME = "shipping_time";
	public static final String KEY_STA = "order_info_status";
	
	public static final String KEY_ALLTASTE = "全部";
	public static final String KEY_FASTFOOD = "简餐";
	public static final String KEY_CHINAFOOD = "中餐";
	public static final String KEY_WESTFOOD = "西式";
	public static final String KEY_TOKYOFOOD = "日韩";
	public static final String KEY_FOREIGNFOOD = "异域";
	public static final String KEY_DRINK = "饮品";
	public static final String KEY_SNACKFOOD = "小食";
	public static final String KEY_CAKE = "蛋糕";
	public static final String KEY_DONUT = "甜品 ";
	public static final String KEY_ALLSEQUENCE = "综合排序";
	public static final String KEY_LOWDELIVER = "配送费最低";
	public static final String KEY_HIGHGRADE = "评分最高";
	public static final String KEY_HIGHSALE = "销量最高";
	
	public static final String ACTION_GETAD = "GETAD";
	public static final String ACTION_GETLOCATION = "GetLocation";
	public static final String ACTION_LOCATE = "Locate";
	public static final String ACTION_SHOPLIST = "ShopList";
	public static final String ACTION_SHOPTYPESUB = "ShopTypeSub";
	public static final String ACTION_DISH = "DishList";
	public static final String ACTION_ADDSUBTRACTCART = "AddSubtractCart";
	public static final String ACTION_CARTINFO = "CartInfo";
	public static final String ACTION_DELETECARTDISH = "DeleteCartDish";
	public static final String ACTION_SHOPINFO = "ShopInfo";
	public static final String ACTION_SHOPCOMMENT = "ShopComment";
	public static final String ACTION_DISHINFO = "DishInfo";
	public static final String ACTION_DISHDETAIL = "DishDetail";
	public static final String ACTION_CARTCLEAR = "ClearCart";
	public static final String ACTION_LOGINSTATUS = "SessionStatus";
	public static final String ACTION_LOGIN = "Login";
	public static final String ACTION_VERIFYSMS = "VerifySMS";
	public static final String ACTION_CODELOGIN = "CodeLogin";
	public static final String ACTION_ORDERCHECKOUT = "OrderCheckOut";
	public static final String ACTION_USERADDRESS = "GetUserAddress";
	public static final String ACTION_CREATEORDER = "CreateOrder";
	public static final String ACTION_ADDUSERADDRESS = "AddUserAddress";
	public static final String ACTION_SETUSERADDRESSDEFAULT = "SetUserDefaultAddress";
	public static final String ACTION_ORDERINFO = "OrderInfo";
	public static final String ACTION_CANCELORER = "CancelOrder";
	public static final String ACTION_USERPHONENAME = "GetUserPhoneName";
	
	public static BigDecimal getBigDecimal(Double d){
		return new BigDecimal(d).setScale(1, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 缓存兴趣点
	 * @param context
	 * @param poi
	 */
	public static void cachePOI(Context context, String posTitle, String posAddress, String posX, String posY){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		e.putString(KEY_POSTITLE, posTitle);
		e.putString(KEY_POSADDRESS, posAddress);
		e.putString(KEY_POSX, posX);
		e.putString(KEY_POSY, posY);
		e.commit();
	}
	
	/**
	 * 得到缓存的兴趣点
	 * @param context
	 * @return
	 */
	public static Map<String, String> getCachePOI(Context context){
		Map<String, String> poiMap = new HashMap<String, String>();
		String poi = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_POSTITLE, null);
		String street = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_POSADDRESS, null);
		String lat = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_POSX, null);
		String lng = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_POSY, null);
		poiMap.put(KEY_POSTITLE, poi);
		poiMap.put(KEY_POSADDRESS, street);
		poiMap.put(KEY_POSX, lat);
		poiMap.put(KEY_POSY, lng);
		return poiMap;
	}
	
	public static Map<String, String> getcacheLogin(Context context){
		Map<String, String> poiMap = new HashMap<String, String>();
		String phone = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_MOBILE_PHONE, null);
		String password = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PASSWORD, null);
		poiMap.put(KEY_MOBILE_PHONE, phone);
		poiMap.put(KEY_PASSWORD, password);
		return poiMap;
	}
	
	public static void cacheLogin(Context context, String phone, String password){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		e.putString(Config.KEY_MOBILE_PHONE, phone);
		e.putString(Config.KEY_PASSWORD, password);
		e.commit();
	}
	
	/**
	 * 得到缓存的token
	 * @param context
	 * @return
	 */
	public static String getCachedToken(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, null);
	}
	/**
	 * 缓存token
	 * @param context
	 * @param token
	 */
	public static void cacheToken(Context context, String token ){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		e.putString(KEY_TOKEN, token);
		e.commit();
	}
	
	public static String getCachedShopId(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_SHOPID, null);
	}
	
	public static void cacheShopId(Context context, String shopId){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		e.putString(KEY_SHOPID, shopId);
		e.commit();
	}
	
	/**
	 * 将JSONObject对象转化为HashMap对象
	 * @param jsonObject
	 * @return HashMap<String, String>
	 */
	public static HashMap<String, String> toHashMap(JSONObject jsonObject) {
		HashMap<String, String> data = new HashMap<String, String>();
		Iterator<String> it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		try {
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value;
				value = (String) jsonObject.get(key);
				data.put(key, value);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 格式化价格用来显示
	 * @param price
	 * @return
	 */
	public static String priceFormat(BigDecimal price){
		DecimalFormat df = new DecimalFormat("0.#");
		return df.format(price);
	}
	
	/**
	 * 获取hashMap里的key值
	 * @param value
	 * @param hashMap
	 * @return
	 */
	public static String getKeyInMap(String value, Map<String, String> hashMap){
		 String str = null; 
		 Iterator<String> it = hashMap.keySet().iterator();
		 while(it.hasNext()){
			 String key = (String)it.next();
			 if (value.equals(hashMap.get(key))) {
				str = key;
				break;
			}
		 }
		 return str;
	}
	
}

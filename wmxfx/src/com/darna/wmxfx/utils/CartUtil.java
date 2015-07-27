package com.darna.wmxfx.utils;

import java.util.List;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.API_Cart;
import com.darna.wmxfx.bean.API_CartDish;
import com.darna.wmxfx.bean.API_CartShop;

public class CartUtil {
	public API_Cart cartInfo;
	
	public int getDishNum(String shopId, String dishId){
		int num = 0;
		List<API_CartShop> shoplist = cartInfo.getShop_list();
		List<API_CartDish> dishs;
		for(API_CartShop shop : shoplist){
			if (shop.getShop_id().equals(shopId)) {
				dishs = shop.getDish_list();
				for(API_CartDish dish : dishs){
					if (dish.getDish_id().equals(dishId)) {
						num += dish.getNumber();
					}
				}
			}
		}
		return num;
	}
	
	public String getDishPriceInShop(String shopId){
		String price = "0";
		List<API_CartShop> shoplist = cartInfo.getShop_list();
		for(API_CartShop shop : shoplist){
			if (shop.getShop_id().equals(shopId)) {
				price = Config.priceFormat(shop.getShop_item_money());
			}
		}
		return price;
	}
	
	public int getDishTotalNum(String shopId){
		int num = 0;
		List<API_CartShop> shoplist = cartInfo.getShop_list();
		List<API_CartDish> dishs;
		for(API_CartShop shop : shoplist){
			if (shop.getShop_id().equals(shopId)) {
				dishs = shop.getDish_list();
				for(API_CartDish dish : dishs){
					num += dish.getNumber(); 
				}
			}
		}
		return num;
	}
}

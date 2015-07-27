package com.darna.wmxfx.bean;

import java.math.BigDecimal;

public class DishDetailInfoClassify {
	
	public DishDetailInfoClassify(String shop_id, String dish_id, String size_code, String spec_code, 
			                      String taste_code, BigDecimal dish_price, BigDecimal pack_price, BigDecimal price_new) {
		this.shop_id = shop_id;
		this.dish_id = dish_id;
		this.size_code = size_code;
		this.spec_code = spec_code;
		this.taste_code = taste_code;
		this.dish_price = dish_price;
		this.pack_price = pack_price;
		this.price_new = price_new;
	}
	
	
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getDish_id() {
		return dish_id;
	}
	public void setDish_id(String dish_id) {
		this.dish_id = dish_id;
	}
	public String getSize_code() {
		return size_code;
	}
	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}
	public String getSpec_code() {
		return spec_code;
	}
	public void setSpec_code(String spec_code) {
		this.spec_code = spec_code;
	}
	public String getTaste_code() {
		return taste_code;
	}
	public void setTaste_code(String taste_code) {
		this.taste_code = taste_code;
	}
	public BigDecimal getDish_price() {
		return dish_price;
	}
	public void setDish_price(BigDecimal dish_price) {
		this.dish_price = dish_price;
	}
	public BigDecimal getPack_price() {
		return pack_price;
	}
	public void setPack_price(BigDecimal pack_price) {
		this.pack_price = pack_price;
	}
	public BigDecimal getPrice_new() {
		return price_new;
	}
	public void setPrice_new(BigDecimal price_new) {
		this.price_new = price_new;
	}


	private String shop_id, dish_id;
	private String size_code, spec_code, taste_code;
	private BigDecimal dish_price, pack_price, price_new;

}

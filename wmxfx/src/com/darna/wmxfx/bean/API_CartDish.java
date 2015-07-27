package com.darna.wmxfx.bean;

import java.math.BigDecimal;

public class API_CartDish {
	
	public String getDish_id() {
		return dish_id;
	}
	public void setDish_id(String dish_id) {
		this.dish_id = dish_id;
	}
	public String getDish_name() {
		return dish_name;
	}
	public void setDish_name(String dish_name) {
		this.dish_name = dish_name;
	}
	public String getDish_main_name() {
		return dish_main_name;
	}
	public void setDish_main_name(String dish_main_name) {
		this.dish_main_name = dish_main_name;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getActivity_type() {
		return activity_type;
	}
	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}
	public BigDecimal getDish_price() {
		return dish_price;
	}
	public void setDish_price(BigDecimal dish_price) {
		this.dish_price = dish_price;
	}
	public BigDecimal getDish_price_subtotal() {
		return dish_price_subtotal;
	}
	public void setDish_price_subtotal(BigDecimal dish_price_subtotal) {
		this.dish_price_subtotal = dish_price_subtotal;
	}
	public BigDecimal getPack_price() {
		return pack_price;
	}
	public void setPack_price(BigDecimal pack_price) {
		this.pack_price = pack_price;
	}
	public BigDecimal getAll_pack_price() {
		return all_pack_price;
	}
	public void setAll_pack_price(BigDecimal all_pack_price) {
		this.all_pack_price = all_pack_price;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	private String dish_id, dish_name, dish_main_name, thumb, shop_id, shop_name, attr_code, attr_name, activity_type;
	private BigDecimal dish_price, dish_price_subtotal, pack_price, all_pack_price;
	private int number;
	private Double distance;
}

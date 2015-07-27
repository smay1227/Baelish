package com.darna.wmxfx.bean;

import java.math.BigDecimal;
import java.util.List;

public class API_Cart {
	
	public List<API_CartShop> getShop_list() {
		return shop_list;
	}
	public void setShop_list(List<API_CartShop> shop_list) {
		this.shop_list = shop_list;
	}
	public Boolean getIs_new_user() {
		return is_new_user;
	}
	public void setIs_new_user(Boolean is_new_user) {
		this.is_new_user = is_new_user;
	}
	public BigDecimal getCart_total() {
		return cart_total;
	}
	public void setCart_total(BigDecimal cart_total) {
		this.cart_total = cart_total;
	}
	private List<API_CartShop> shop_list;
	private Boolean is_new_user;
	private BigDecimal cart_total;
}

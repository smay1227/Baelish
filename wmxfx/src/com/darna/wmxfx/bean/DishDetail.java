package com.darna.wmxfx.bean;

import java.math.BigDecimal;

public class DishDetail {
	String shopId, dishId,dishName,dishThumb;
	BigDecimal price_disp, pack_price;
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getDishId() {
		return dishId;
	}
	public void setDishId(String dishId) {
		this.dishId = dishId;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public String getDishThumb() {
		return dishThumb;
	}
	public void setDishThumb(String dishThumb) {
		this.dishThumb = dishThumb;
	}
	public BigDecimal getPrice_disp() {
		return price_disp;
	}
	public void setPrice_disp(BigDecimal price_disp) {
		this.price_disp = price_disp;
	}
	public BigDecimal getPack_price() {
		return pack_price;
	}
	public void setPack_price(BigDecimal pack_price) {
		this.pack_price = pack_price;
	}
	
}

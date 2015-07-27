package com.darna.wmxfx.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Order {
	
	public List<OrderVouchers> getVouchers() {
		return vouchers;
	}
	public void setVouchers(List<OrderVouchers> vouchers) {
		this.vouchers = vouchers;
	}
	public int getIntegralist() {
		return integralist;
	}
	public void setIntegralist(int integralist) {
		this.integralist = integralist;
	}
	public String getUserflg() {
		return userflg;
	}
	public void setUserflg(String userflg) {
		this.userflg = userflg;
	}
	public BigDecimal getAll_dish_money() {
		return all_dish_money;
	}
	public void setAll_dish_money(BigDecimal all_dish_money) {
		this.all_dish_money = all_dish_money;
	}
	public BigDecimal getAll_delivery_cost() {
		return all_delivery_cost;
	}
	public void setAll_delivery_cost(BigDecimal all_delivery_cost) {
		this.all_delivery_cost = all_delivery_cost;
	}
	public BigDecimal getAll_pack_price() {
		return all_pack_price;
	}
	public void setAll_pack_price(BigDecimal all_pack_price) {
		this.all_pack_price = all_pack_price;
	}
	public BigDecimal getSub_money() {
		return sub_money;
	}
	public void setSub_money(BigDecimal sub_money) {
		this.sub_money = sub_money;
	}
	public List<OrderShop> getShops() {
		return shops;
	}
	public void setShops(List<OrderShop> shops) {
		this.shops = shops;
	}
	private List<OrderShop> shops;
	private List<OrderVouchers> vouchers;
	private int integralist;
	private String userflg;
	private BigDecimal all_dish_money, all_delivery_cost, all_pack_price, sub_money;
}

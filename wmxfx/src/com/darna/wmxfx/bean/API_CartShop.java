package com.darna.wmxfx.bean;

import java.math.BigDecimal;
import java.util.List;

public class API_CartShop {
	
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
	public String getActivity_flg() {
		return activity_flg;
	}
	public void setActivity_flg(String activity_flg) {
		this.activity_flg = activity_flg;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public Double getDis_range() {
		return dis_range;
	}
	public void setDis_range(Double dis_range) {
		this.dis_range = dis_range;
	}
	public BigDecimal getShop_item_money() {
		return shop_item_money;
	}
	public void setShop_item_money(BigDecimal shop_item_money) {
		this.shop_item_money = shop_item_money;
	}
	public BigDecimal getShop_all_pack_price() {
		return shop_all_pack_price;
	}
	public void setShop_all_pack_price(BigDecimal shop_all_pack_price) {
		this.shop_all_pack_price = shop_all_pack_price;
	}
	public BigDecimal getDelivery_price() {
		return delivery_price;
	}
	public void setDelivery_price(BigDecimal delivery_price) {
		this.delivery_price = delivery_price;
	}
	public BigDecimal getMyself_param() {
		return myself_param;
	}
	public void setMyself_param(BigDecimal myself_param) {
		this.myself_param = myself_param;
	}
	public BigDecimal getShop_real_price() {
		return shop_real_price;
	}
	public void setShop_real_price(BigDecimal shop_real_price) {
		this.shop_real_price = shop_real_price;
	}
	public List<API_CartDish> getDish_list() {
		return dish_list;
	}
	public void setDish_list(List<API_CartDish> dish_list) {
		this.dish_list = dish_list;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPay_id() {
		return pay_id;
	}
	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}
	public String getRecipient_phone() {
		return recipient_phone;
	}
	public void setRecipient_phone(String recipient_phone) {
		this.recipient_phone = recipient_phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getShipping_time() {
		return shipping_time;
	}
	public void setShipping_time(String shipping_time) {
		this.shipping_time = shipping_time;
	}
	
	public int getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(int orderamount) {
		this.orderamount = orderamount;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	private String shop_id, shop_name, activity_flg, introduce, consignee, pay_id, recipient_phone, address, order_sn,
					shipping_time, order_status;
	private Double dis_range;
	private BigDecimal shop_item_money, shop_all_pack_price, delivery_price, myself_param, shop_real_price;
	private int orderamount;
	private List<API_CartDish> dish_list;
}

package com.darna.wmxfx.bean;

import java.math.BigDecimal;

public class OrderVouchers {
	
	public String getVouchers_id() {
		return vouchers_id;
	}
	public void setVouchers_id(String vouchers_id) {
		this.vouchers_id = vouchers_id;
	}
	public String getVouchers_type() {
		return vouchers_type;
	}
	public void setVouchers_type(String vouchers_type) {
		this.vouchers_type = vouchers_type;
	}
	public String getVouchers_name() {
		return vouchers_name;
	}
	public void setVouchers_name(String vouchers_name) {
		this.vouchers_name = vouchers_name;
	}
	public BigDecimal getVouchers_money() {
		return vouchers_money;
	}
	public void setVouchers_money(BigDecimal vouchers_money) {
		this.vouchers_money = vouchers_money;
	}
	private String vouchers_id, vouchers_type, vouchers_name;
	private BigDecimal vouchers_money;
}

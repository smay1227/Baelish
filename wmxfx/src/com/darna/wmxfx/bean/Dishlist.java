package com.darna.wmxfx.bean;

import java.math.BigDecimal;

public class Dishlist {
	public Dishlist(String shop_id, String dish_id, String dish_name, BigDecimal price_old, BigDecimal price_new, 
			        BigDecimal pack_price,String thumb,Boolean has_activity,Boolean detail_popup,Boolean in_short_supply) {
		this.shop_id = shop_id;
		this.dish_id = dish_id;
		this.dish_name = dish_name;
		this.price_old = price_old;
		this.price_new = price_new;
		this.pack_price = pack_price;
		this.thumb = thumb;
		this.has_activity = has_activity;
		this.detail_popup = detail_popup;
		this.in_short_supply = in_short_supply;
	}
	public String getShop_id() {
		return shop_id;
	}
	
	public String getDish_id() {
		return dish_id;
	}
	public String getDish_name() {
		return dish_name;
	}
	public BigDecimal getPrice_old() {
		return price_old;
	}
	public BigDecimal getPrice_new() {
		return price_new;
	}
	public BigDecimal getPack_price() {
		return pack_price;
	}
	public String getThumb() {
		return thumb;
	}
	public Boolean getHas_activity() {
		return has_activity;
	}
	public Boolean getDetail_popup() {
		return detail_popup;
	}
	public Boolean getIn_short_supply() {
		return in_short_supply;
	}
	private String shop_id, dish_id, dish_name, thumb;
	private BigDecimal price_new, price_old, pack_price;
	private Boolean has_activity, detail_popup,in_short_supply;
	
}

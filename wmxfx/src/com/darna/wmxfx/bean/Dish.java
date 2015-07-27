package com.darna.wmxfx.bean;

import java.util.List;

public class Dish {
	public Dish(String type, List<Dishlist> dishes) {
		this.type = type;
		this.dishes = dishes;
	}
	public String getType() {
		return type;
	}
	public List<Dishlist> getDishes() {
		return dishes;
	}
	private String type;
	private List<Dishlist> dishes;
}

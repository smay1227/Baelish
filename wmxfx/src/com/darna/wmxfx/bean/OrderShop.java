package com.darna.wmxfx.bean;

import java.util.List;

public class OrderShop {
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
	public String getShow_book_way() {
		return show_book_way;
	}
	public void setShow_book_way(String show_book_way) {
		this.show_book_way = show_book_way;
	}
	public List<String> getTodayBook() {
		return todayBook;
	}
	public void setTodayBook(List<String> todayBook) {
		this.todayBook = todayBook;
	}
	public List<String> getOtherBook() {
		return otherBook;
	}
	public void setOtherBook(List<String> otherBook) {
		this.otherBook = otherBook;
	}
	String shop_id, shop_name, show_book_way;
	List<String> todayBook, otherBook;
}

package com.darna.wmxfx.bean;

import java.util.List;

public class ShopPoint {
	ShopPointTotal pointTotal;
	List<ShopPointList> pointList;
	public ShopPointTotal getPointTotal() {
		return pointTotal;
	}
	public void setPointTotal(ShopPointTotal pointTotal) {
		this.pointTotal = pointTotal;
	}
	public List<ShopPointList> getPointList() {
		return pointList;
	}
	public void setPointList(List<ShopPointList> pointList) {
		this.pointList = pointList;
	}
	
}

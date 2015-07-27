package com.darna.wmxfx.bean;

public class ShopPointList {
	String mobile_phone, alias, experience, evaluate_time, thumb;
	float  point;

	public String getMobile_phone() {
		return mobile_phone;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public float getPoint() {
		return point;
	}

	public void setPoint(float point) {
		this.point = point;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getEvaluate_time() {
		return evaluate_time;
	}

	public void setEvaluate_time(String evaluate_time) {
		this.evaluate_time = evaluate_time;
	}
}

package com.darna.wmxfx.bean;

import java.util.List;

public class DeliveryTime {
   String dayType;
   List<String> time;
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public List<String> getTime() {
		return time;
	}
	public void setTime(List<String> time) {
		this.time = time;
	}
}

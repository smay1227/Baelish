package com.darna.wmxfx.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DishDetailInfo {
	
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getDish_id() {
		return dish_id;
	}
	public void setDish_id(String dish_id) {
		this.dish_id = dish_id;
	}
	public String getDish_name() {
		return dish_name;
	}
	public void setDish_name(String dish_name) {
		this.dish_name = dish_name;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getActivity_flg() {
		return activity_flg;
	}
	public void setActivity_flg(String activity_flg) {
		this.activity_flg = activity_flg;
	}
	public String getActivity_type() {
		return activity_type;
	}
	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}
	public String getPrice_down() {
		return price_down;
	}
	public void setPrice_down(String price_down) {
		this.price_down = price_down;
	}
	public String getSize_code() {
		return size_code;
	}
	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}
	public String getSpec_code() {
		return spec_code;
	}
	public void setSpec_code(String spec_code) {
		this.spec_code = spec_code;
	}
	public String getTaste_code() {
		return taste_code;
	}
	public void setTaste_code(String taste_code) {
		this.taste_code = taste_code;
	}
	public String getDish_type() {
		return dish_type;
	}
	public void setDish_type(String dish_type) {
		this.dish_type = dish_type;
	}
	public String getDish_details() {
		return dish_details;
	}
	public void setDish_details(String dish_details) {
		this.dish_details = dish_details;
	}
	public BigDecimal getDish_price() {
		return dish_price;
	}
	public void setDish_price(BigDecimal dish_price) {
		this.dish_price = dish_price;
	}
	public BigDecimal getPack_price() {
		return pack_price;
	}
	public void setPack_price(BigDecimal pack_price) {
		this.pack_price = pack_price;
	}
	public BigDecimal getOriginal_price() {
		return original_price;
	}
	public void setOriginal_price(BigDecimal original_price) {
		this.original_price = original_price;
	}
	public BigDecimal getPrice_disp() {
		return price_disp;
	}
	public void setPrice_disp(BigDecimal price_disp) {
		this.price_disp = price_disp;
	}
	public BigDecimal getPrice_disp_format() {
		return price_disp_format;
	}
	public void setPrice_disp_format(BigDecimal price_disp_format) {
		this.price_disp_format = price_disp_format;
	}
	public Boolean getHas_limit_activity() {
		return has_limit_activity;
	}
	public void setHas_limit_activity(Boolean has_limit_activity) {
		this.has_limit_activity = has_limit_activity;
	}
	public Boolean getHas_classify() {
		return has_classify;
	}
	public void setHas_classify(Boolean has_classify) {
		this.has_classify = has_classify;
	}
	public List<String> getActivity_desc() {
		return activity_desc;
	}
	public void setActivity_desc(List<String> activity_desc) {
		this.activity_desc = activity_desc;
	}
	public List<DishDetailInfoClassify> getClassify_info() {
		return classify_info;
	}
	public void setClassify_info(List<DishDetailInfoClassify> classify_info) {
		this.classify_info = classify_info;
	}
	public Map<String, String> getSize_dict() {
		return size_dict;
	}
	public void setSize_dict(Map<String, String> size_dict) {
		this.size_dict = size_dict;
	}
	public Map<String, String> getSpec_dict() {
		return spec_dict;
	}
	public void setSpec_dict(Map<String, String> spec_dict) {
		this.spec_dict = spec_dict;
	}
	public Map<String, String> getTaste_dict() {
		return taste_dict;
	}
	public void setTaste_dict(Map<String, String> taste_dict) {
		this.taste_dict = taste_dict;
	}
	private String shop_id, dish_id, dish_name, thumb, activity_flg, activity_type, price_down, size_code
	               ,spec_code, taste_code, dish_type, dish_details;
	private BigDecimal dish_price, pack_price, original_price, price_disp, price_disp_format;
	private Boolean has_limit_activity, has_classify;
	private List<String> activity_desc;
	private List<DishDetailInfoClassify> classify_info;
	private Map<String, String> size_dict;
	private Map<String, String> spec_dict;
	private Map<String, String> taste_dict;
	
}

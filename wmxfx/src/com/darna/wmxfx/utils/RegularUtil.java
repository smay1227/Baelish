package com.darna.wmxfx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
	
	public Boolean checkCode(String code){
		Boolean flag = false;
		try{
			Pattern p = Pattern.compile("^\\d{6}$");
		    Matcher m = p.matcher(code);
		    flag = m.matches();
		}catch(Exception e){
		    flag = false;
	    }
		return flag;
	}
	
	/**
	 * @param phone
	 * @return return true if phone correct else return false
	 */
	public Boolean checkPhone(String phone){
		Boolean flag = false;
		try{
			Pattern p = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8}$");
		    Matcher m = p.matcher(phone);
		    flag = m.matches();
		}catch(Exception e){
		    flag = false;
	    }
		return flag;
	}
}

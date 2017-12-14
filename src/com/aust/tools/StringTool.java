package com.aust.tools;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

/**
 * 字符串工具类
 * 
 * @author bojiangzhou
 *
 */
public class StringTool {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return true: 空 | false: 不为空
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	// 打印测试
	public static void print(String str) {
		System.out.println(str);
	}

	/**
	 * 将get方式传来的中文转为UTF-8格式
	 * 
	 * @param str
	 * @return
	 */
	public static String messyCode(String str) {
		String code = null;
		try {
			byte[] by = str.getBytes("ISO-8859-1");
			code = new String(by, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * 根据长度生成占位符：即'?,'
	 * 
	 * @param length
	 * @return
	 */
	public static String getMark(int length) {
		StringBuffer mark = new StringBuffer("");
		for (int i = 0; i < length; i++) {
			mark.append("?,");
		}
		// 删除最后一个逗号
		mark.deleteCharAt(mark.length() - 1);
		// 返回
		return mark.toString();
	}

	public static java.sql.Date StrTosqlDate(String strDate) {
		String d = strDate;

		String p = d.substring(2, 3);
		if (p.equals("/")) {
			String dd = d.substring(3, 5);
			String mm = d.substring(0, 2);
			String yy = d.substring(6, 10);
			d = yy + "-" + mm + "-" + dd;
		}
	
		
		return java.sql.Date.valueOf(d);
	}
	
	public static java.sql.Timestamp StrTosqlDateTime(String strDate) {
		String d = ""; // 日期部分，例如：2017-12-08
		String t = ""; // 时间部分，例如：16:40:42:00
		if (strDate.length() > 10) {
			d = strDate.substring(0, 10);
			t = strDate.substring(11);
		} else
			d = strDate;

		String p = d.substring(2, 3);
		if (p.equals("/")) {
			String dd = d.substring(3, 5);
			String mm = d.substring(0, 2);
			String yy = d.substring(6, 10);
			d = yy + "-" + mm + "-" + dd;
		}
		if (t.length() > 0)
		{
			d = d + " " + t;
			
		}
		//TODO date
//		 System.out.println("date:"+d);
		
		 return java.sql.Timestamp.valueOf(d);
	}

}

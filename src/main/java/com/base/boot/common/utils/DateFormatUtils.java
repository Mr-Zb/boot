package com.base.boot.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {
	private final static DateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
	private final static DateFormat df2 = new SimpleDateFormat("yyyyMMdd");
	private final static DateFormat df3 = new SimpleDateFormat("HHmmss");

	/**
	 * 解析时间日期
	 *
	 * @param text
	 * @return
	 */
	public static Date parse(String text) {
		Date date = null;
		try {
			if (StringUtils.isNotBlank(text)) {
				text = StringUtils.replacePattern(text, "\\D", "");
				if (org.apache.commons.lang.StringUtils.length(text) == 14) {
					date = df1.parse(text);
				} else if (org.apache.commons.lang.StringUtils.length(text) == 8) {
					date = df2.parse(text);
				} else if (org.apache.commons.lang.StringUtils.length(text) == 6) {
					date = df3.parse(text);
				}
			}
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 解析时间日期
	 *
	 * @param text
	 * @param def
	 * @return
	 */
	public static Date parse(String text, Date def) {
		Date date = null;
		try {
			if (StringUtils.isNotBlank(text)) {
				text = StringUtils.replacePattern(text, "\\D", "");
				if (org.apache.commons.lang.StringUtils.length(text) == 14) {
					date = df1.parse(text);
				} else if (org.apache.commons.lang.StringUtils.length(text) == 8) {
					date = df2.parse(text);
				} else if (org.apache.commons.lang.StringUtils.length(text) == 6) {
					date = df3.parse(text);
				}
			}
		} catch (Exception e) {
		}
		return date == null ? def : date;
	}

	/**
	 * 解析时间日期
	 *
	 * @param text
	 * @return
	 */
	public static Date parse(String text, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(text);
		} catch (Exception e) {
		}
		return date;
	}
}

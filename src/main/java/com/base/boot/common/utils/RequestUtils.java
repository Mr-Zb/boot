package com.base.boot.common.utils;

import com.base.boot.common.utils.ip2region.DataBlock;
import com.base.boot.common.utils.ip2region.DbConfig;
import com.base.boot.common.utils.ip2region.DbSearcher;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class RequestUtils {
	private static DbSearcher dbSearcher = null;

	/**
	 * 获取真实IP
	 *
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	public static String getAgent(HttpServletRequest request) {
		String loginAgent = request.getHeader("User-Agent");
		if (StringUtils.contains(loginAgent, "IE")) {
			loginAgent = "IE 浏览器";
		} else if (StringUtils.contains(loginAgent, "Chrome")) {
			loginAgent = "Chrome 浏览器";
		} else if (StringUtils.contains(loginAgent, "Firefox")) {
			loginAgent = "Firefox 浏览器";
		} else if (StringUtils.contains(loginAgent, "360")) {
			loginAgent = "360 浏览器";
		} else if (StringUtils.contains(loginAgent, "iPhone")) {
			loginAgent = "iPhone";
		} else if (StringUtils.contains(loginAgent, "iPad")) {
			loginAgent = "iPad";
		} else if (StringUtils.contains(loginAgent, "Android")) {
			loginAgent = "Android手机";
		} else {
			loginAgent = "其它浏览器";
		}
		return loginAgent;
	}

	/**
	 * ip地址转网络地址
	 *
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public static String getIpAddress(String ip) throws Exception {
		if (dbSearcher == null) {
			DbConfig dbConfig = new DbConfig();
			dbSearcher = new DbSearcher(dbConfig, new ClassPathResource("data/ip2region.db").getFile());
		}
		DataBlock dataBlock = dbSearcher.btreeSearch(ip);
		if (dataBlock != null) {
			String replace = dataBlock.getRegion().replace("|", "");
			if (replace.contains("0000")) {
				return replace.replace("0000", "");
			} else {
				return replace;
			}
		}
		return StringUtils.EMPTY;
	}

	// ======================================================================================================
	public static String getParameter(HttpServletRequest request, String parameter, String defVal) {
		String val = request.getParameter(parameter);
		return StringUtils.isBlank(val) ? defVal : StringUtils.trim(val);
	}

	public static Integer getInteger(HttpServletRequest request, String parameter, Integer defVal) {
		return NumberUtils.toInt(getParameter(request, parameter, StringUtils.EMPTY), defVal);
	}

	public static Double getDouble(HttpServletRequest request, String parameter, Double defVal) {
		return NumberUtils.toDouble(getParameter(request, parameter, StringUtils.EMPTY), defVal);
	}

	public static Date getDate(HttpServletRequest request, String parameter, Date defVal) {
		return DateFormatUtils.parse(getParameter(request, parameter, StringUtils.EMPTY), defVal);
	}

	public static String getParameter(HttpServletRequest request, String parameter) {
		return getParameter(request, parameter, StringUtils.EMPTY);
	}

	public static Integer getInteger(HttpServletRequest request, String parameter) {
		return getInteger(request, parameter, 0);
	}

	public static Double getDouble(HttpServletRequest request, String parameter) {
		return getDouble(request, parameter, 0d);
	}

	public static Date getDate(HttpServletRequest request, String parameter) {
		return getDate(request, parameter, null);
	}

	// ======================================================================================================
	public static String getParameter(WebRequest request, String parameter, String defVal) {
		String val = request.getParameter(parameter);
		return StringUtils.isBlank(val) ? defVal : StringUtils.trim(val);
	}

	public static Integer getInteger(WebRequest request, String parameter, Integer defVal) {
		return NumberUtils.toInt(getParameter(request, parameter, StringUtils.EMPTY), defVal);
	}

	public static Double getDouble(WebRequest request, String parameter, Double defVal) {
		return NumberUtils.toDouble(getParameter(request, parameter, StringUtils.EMPTY), defVal);
	}

	public static Date getDate(WebRequest request, String parameter, Date defVal) {
		return DateFormatUtils.parse(getParameter(request, parameter, StringUtils.EMPTY), defVal);
	}

	public static String getParameter(WebRequest request, String parameter) {
		return getParameter(request, parameter, StringUtils.EMPTY);
	}

	public static Integer getInteger(WebRequest request, String parameter) {
		return getInteger(request, parameter, 0);
	}

	public static Double getDouble(WebRequest request, String parameter) {
		return getDouble(request, parameter, 0d);
	}

	public static Date getDate(WebRequest request, String parameter) {
		return getDate(request, parameter, null);
	}
}

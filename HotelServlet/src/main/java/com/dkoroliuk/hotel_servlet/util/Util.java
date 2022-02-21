package com.dkoroliuk.hotel_servlet.util;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class for different methods
 */
public class Util {
	private static final String ORDER = "order";
	private Util() {

	}

	public static Locale defineLocale(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		String language = null;
		String country = null;
		Locale locale = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("language")) {
					language = c.getValue();
				}
				if (c.getName().equals("country")) {
					country = c.getValue();
				}
			}
		}
		if (country != null && language != null) {
			locale = new Locale(language, country);
		} else {
			locale = new Locale("en", "US");
		}
		return locale;
	}
	
	public static String defineSortAndStoreInCookie(HttpServletRequest request, HttpServletResponse response) {
		String result = request.getParameter("sort");
		if (result == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals("sort")) {
						result = c.getValue();
					}
				}
			}
		}
		if (result == null) {
			result = "r.seats_amount";
		}
		Cookie cookie = new Cookie("sort", result);
		response.addCookie(cookie);
		
		return result;
	}
	
	public static String defineOrderAndStoreInCookie(HttpServletRequest request, HttpServletResponse response) {
		String result = request.getParameter(ORDER);
		if (result == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals(ORDER)) {
						result = c.getValue();
					}
				}
			}
		}
		if (result == null) {
			result = "desc";
		}
		Cookie cookie = new Cookie(ORDER, result);
		response.addCookie(cookie);
		
		return result;
	}

	public static String buildPaginationNavForRooms(String command, int page, int pagesCount, String seatsAmount, String perdayCost,
			String roomStatus, String roomType) {
		if (pagesCount == 1) {
			return "";
		}
		StringBuilder path = new StringBuilder();
		path
			.append(command)
			.append("&seatsAmount=")
			.append(seatsAmount)
			.append("&perdayCost=")
			.append(perdayCost)
			.append("&roomStatus=")
			.append(roomStatus)
			.append("&roomType=")
			.append(roomType)
			.append("&page=");
		StringBuilder result = new StringBuilder();
		result.append("<nav>")
			  .append("<ul class=\"pagination pagination-lg\">");
		for (int i = 0; i < pagesCount; i++) {
			if (i == page) {
				result.append("<li class=\"page-item active\"><span class=\"page-link\">")
						.append(i+1)
						.append("</span></li>");
			} else {
				result.append("<li class=\"page-item\"><a class=\"page-link\" href=\"")
						.append(path)
						.append(i)
						.append("\">")
						.append(i+1)
						.append("</a></li>");
			}
		}
		result.append("</ul></nav>");
		return result.toString();
	}
	
}

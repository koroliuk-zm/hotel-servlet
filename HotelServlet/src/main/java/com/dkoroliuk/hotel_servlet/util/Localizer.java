package com.dkoroliuk.hotel_servlet.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for localization of application messages
 */
public class Localizer {
	private static final String RESOURSE_NAME = "messages";

	private Localizer() {
	}

	public static String getString(HttpServletRequest request, String key) {
		Locale locale = (Locale) request.getSession().getAttribute("locale");

		ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURSE_NAME, locale);

		return resourceBundle.getString(key);
	}

}

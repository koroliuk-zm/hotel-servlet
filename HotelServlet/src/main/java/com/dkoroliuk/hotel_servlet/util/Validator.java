package com.dkoroliuk.hotel_servlet.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dkoroliuk.hotel_servlet.model.entity.Order;
import com.dkoroliuk.hotel_servlet.model.entity.Request;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.User;

/**
 * Utility class for validation entities
 */
public class Validator {

	private static final String LOGIN_REGEX = "^[A-Za-z0-9]{1,20}$";
	private static final String PASSWORD_REGEX = "^[A-Za-z0-9]{6,}$";
	private static final String EMAIL_REGEX = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	private Validator() {
	}

	public static boolean validatePassword(HttpServletRequest request, String password1, String password2,
			Map<String, String> errors) {
		boolean result = true;
		StringBuilder message = new StringBuilder();

		if (password1 == null || password1.isEmpty()) {
			result = false;
			message.append(Localizer.getString(request, "validation.password.empty"));
		} else {
			if (!password1.equals(password2)) {
				result = false;
				message.append(Localizer.getString(request, "validation.password.equal"));
			}
			if (!password1.matches(PASSWORD_REGEX)) {
				result = false;
				message.append(Localizer.getString(request, "validation.password.regex"));
			}
		}
		if (!result) {
			errors.put("password", message.toString());
		}

		return result;
	}

	public static boolean validateUser(HttpServletRequest request, User user, Map<String, String> errors) {
		boolean result;

		result = validateLogin(request, user.getLogin(), errors);
		result = validateName(request, user.getName(), errors) && result;
		result = validateSurname(request, user.getSurname(), errors) && result;
		result = validateEmail(request, user.getEmail(), errors) && result;

		return result;
	}

	private static boolean validateLogin(HttpServletRequest request, String login, Map<String, String> errors) {
		boolean result = true;

		if (login == null || login.isEmpty()) {
			result = false;
			errors.put("login", Localizer.getString(request, "validation.login.empty"));
		} else if (!login.matches(LOGIN_REGEX)) {
			result = false;
			errors.put("login", Localizer.getString(request, "validation.login.regex"));
		}

		return result;
	}

	private static boolean validateEmail(HttpServletRequest request, String email, Map<String, String> errors) {
		boolean result = true;

		if (email == null || email.isEmpty()) {
			result = false;
			errors.put("email", Localizer.getString(request, "validation.email.empty"));
		} else if (!email.matches(EMAIL_REGEX)) {
			result = false;
			errors.put("email", Localizer.getString(request, "validation.email.regex"));
		}

		return result;
	}

	private static boolean validateName(HttpServletRequest request, String name, Map<String, String> errors) {
		boolean result = true;

		if (name == null || name.isEmpty()) {
			result = false;
			errors.put("name", Localizer.getString(request, "validation.name.empty"));
		} else if (name.length() > 30) {
			result = false;
			errors.put("name", Localizer.getString(request, "validation.name.length"));
		}

		return result;
	}

	private static boolean validateSurname(HttpServletRequest request, String surname, Map<String, String> errors) {
		boolean result = true;

		if (surname == null || surname.isEmpty()) {
			result = false;
			errors.put("surname", Localizer.getString(request, "validation.surname.empty"));
		} else if (surname.length() > 30) {
			result = false;
			errors.put("surname", Localizer.getString(request, "validation.surname.length"));
		}

		return result;
	}

	public static boolean validateRoom(HttpServletRequest request, Room room, Map<String, String> errors) {
		boolean result;

		result = validateNumber(request, room.getNumber(), errors);
		result = validateSeatsAmount(request, room.getSeatsAmount(), errors) && result;
		result = validatePerdayCost(request, room.getPerdayCost(), errors) && result;
		result = validateDescription(request, room.getDescription(), errors) && result;

		return result;
	}

	private static boolean validateDescription(HttpServletRequest request, String description,
			Map<String, String> errors) {
		boolean result = true;

		if (description.isEmpty()) {
			result = false;
			errors.put("description", Localizer.getString(request, "validation.description.empty"));
		} else if (description.length() > 300) {
			result = false;
			errors.put("description", Localizer.getString(request, "validation.description.length"));
		}
		return result;
	}

	private static boolean validatePerdayCost(HttpServletRequest request, int perdayCost, Map<String, String> errors) {
		boolean result = true;
		if (perdayCost < 1) {
			result = false;
			errors.put("perdayCost", Localizer.getString(request, "validation.perdayCost.add"));
		}

		return result;
	}

	private static boolean validateSeatsAmount(HttpServletRequest request, int seatsAmount,
			Map<String, String> errors) {
		boolean result = true;

		if (seatsAmount < 1) {
			result = false;
			errors.put("seatsAmount", Localizer.getString(request, "validation.seatsAmount.add"));
		}

		return result;
	}

	public static boolean validateNumber(HttpServletRequest request, int number, Map<String, String> errors) {
		boolean result = true;

		if (number < 1) {
			result = false;
			errors.put("number", Localizer.getString(request, "validation.roomNumber.add"));
		}

		return result;
	}

	public static boolean validateOrder(HttpServletRequest request, Order order, Map<String, String> errors) {
		boolean result;

		result = validateTotalCost(request, order.getTotalCost(), errors);
		result = validateDates(request, order.getCheckInDate(), order.getCheckOutDate(), order.getOrderDate(), errors)
				&& result;

		return result;
	}

	private static boolean validateDates(HttpServletRequest request, LocalDate checkInDate, LocalDate checkOutDate,
			LocalDateTime orderDate, Map<String, String> errors) {
		boolean result = true;

		if (orderDate.toLocalDate().isAfter(checkInDate)) {
			result = false;
			errors.put("checkInDate", Localizer.getString(request, "checkInDate.earlier.orderDate"));
		}
		if (orderDate.toLocalDate().isAfter(checkOutDate)) {
			result = false;
			errors.put("checkOutDate", Localizer.getString(request, "checkOutDate.earlier.orderDate"));
		}
		if (checkInDate.isAfter(checkOutDate)) {
			result = false;
			errors.put("checkOutDate", Localizer.getString(request, "checkOutDate.earlier.checkInDate"));
		}

		return result;
	}

	private static boolean validateTotalCost(HttpServletRequest request, int totalCost, Map<String, String> errors) {
		boolean result = true;

		if (totalCost < 0) {
			result = false;
			errors.put("totalCost", Localizer.getString(request, "validation.totalCost.add"));
		}

		return result;
	}

	public static boolean validateRequest(HttpServletRequest request, Request req, Map<String, String> errors) {
		boolean result;

		result = validateSeatsNumber(request, req.getSeatsNumber(), errors);
		result = validateDates(request, req.getCheckInDate(), req.getCheckOutDate(), req.getRequestDate(), errors)
				&& result;

		return result;
	}

	private static boolean validateSeatsNumber(HttpServletRequest request, int seatsNumber,
			Map<String, String> errors) {
		boolean result = true;

		if (seatsNumber < 1) {
			result = false;
			errors.put("seatsNumber", Localizer.getString(request, "validation.seatsNumber"));
		}

		return result;
	}

}

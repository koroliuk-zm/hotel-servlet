package com.dkoroliuk.hotel_servlet.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Class that builds and returns as {@link List} pagination parameters "Sort by"
 * and "Sort order"
 */
public class RoomComporators {

	private RoomComporators() {
	}

	public static class SortBy {
		private String title;
		private String value;

		public SortBy(String title, String value) {
			this.title = title;
			this.value = value;
		}

		public String getTitle() {
			return title;
		}

		public String getValue() {
			return value;
		}

	}

	public static class OrderBy {
		private String title;
		private String value;

		public OrderBy(String title, String value) {
			this.title = title;
			this.value = value;
		}

		public String getTitle() {
			return title;
		}

		public String getValue() {
			return value;
		}

	}

	public static List<SortBy> getAllSortBy(HttpServletRequest request) {
		List<SortBy> result = new ArrayList<>();
		result.add(new SortBy(Localizer.getString(request, "seatsAmount"), "r.seats_amount"));
		result.add(new SortBy(Localizer.getString(request, "perdayCost"), "r.perday_cost"));
		result.add(new SortBy(Localizer.getString(request, "roomStatus"), "rs.status"));
		result.add(new SortBy(Localizer.getString(request, "roomType"), "rt.type"));
		return result;
	}

	public static List<OrderBy> getAllOrderBy(HttpServletRequest request) {
		List<OrderBy> result = new ArrayList<>();
		result.add(new OrderBy(Localizer.getString(request, "ascending"), "asc"));
		result.add(new OrderBy(Localizer.getString(request, "descending"), "desc"));
		return result;
	}

}

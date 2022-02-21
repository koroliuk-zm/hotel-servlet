package com.dkoroliuk.hotel_servlet.controller.servlet;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Order;

/**
 * Scheduler to process 
 */

@WebListener
public class Scheduler implements ServletContextListener {
	private ScheduledExecutorService scheduled;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		scheduled = Executors.newSingleThreadScheduledExecutor();
		scheduled.scheduleAtFixedRate(new CheckOrderPayment(), 0, 2, TimeUnit.DAYS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduled.shutdownNow();
	}

	public class CheckOrderPayment implements Runnable {

		@Override
		public void run() {
			OrderDAO orderDAO = OrderDAOImpl.getInstance();
			List<Order> orders = orderDAO.getAllOrders();
			List<Order> unpaidOrders = orders.stream()
					.filter(o -> ChronoUnit.MINUTES.between(o.getOrderDate(), LocalDateTime.now()) > 1)
					.filter(o -> o.getOrderStatus().getId() < 3).toList();
			for (Order order : unpaidOrders) {
				orderDAO.setExpiredIfOrderUnpaidMoreThanTwoDays(order);
			}
		}

	}

}

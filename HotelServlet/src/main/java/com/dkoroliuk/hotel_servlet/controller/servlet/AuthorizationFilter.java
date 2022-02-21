package com.dkoroliuk.hotel_servlet.controller.servlet;

import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.ADDROOM;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.ADDROOMLINK;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.ADDUSER;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.ADDUSERLINK;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.CANCELORDER;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.CHANGELOCALE;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.DEFAULT;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.DELETEROOM;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.DELETEUSER;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.GETALLROOMS;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.GETALLUSERS;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.GETPRIVATECABINET;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.LOGIN;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.LOGOUT;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.MAKEREQUEST;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.ORDERPAGE;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.ORDERROOM;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.PAYROOMORDER;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.PROCESSREQUEST;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.PROCESSREQUESTPAGE;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.REGISTER;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.REGISTERUSER;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.REQUESTPAGE;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.UPDATEROOM;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.UPDATEROOMPAGE;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.UPDATEUSER;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.UPDATEUSERPAGE;
import static com.dkoroliuk.hotel_servlet.controller.command.CommandEnum.WAITERPAGE;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dkoroliuk.hotel_servlet.controller.command.CommandEnum;

/**
 * Authorization filter
 */
@WebFilter("/*")
public class AuthorizationFilter implements Filter {
	private EnumSet<CommandEnum> guestCommands;
	private EnumSet<CommandEnum> userCommands;
	private EnumSet<CommandEnum> waiterCommands;
	private EnumSet<CommandEnum> adminCommands;
	private static final String DEFAULT_COMMAND = "main?action=default";

	public AuthorizationFilter() {
		guestCommands = EnumSet.of(LOGIN, GETALLROOMS, REGISTERUSER, REGISTER, DEFAULT, CHANGELOCALE);
		userCommands = EnumSet.of(LOGOUT, DEFAULT, CHANGELOCALE, GETALLROOMS, GETPRIVATECABINET, MAKEREQUEST, ORDERPAGE,
				ORDERROOM, REQUESTPAGE, PAYROOMORDER, CANCELORDER);
		waiterCommands = EnumSet.of(LOGOUT, DEFAULT, CHANGELOCALE, WAITERPAGE, PROCESSREQUEST, PROCESSREQUESTPAGE);
		adminCommands = EnumSet.of(LOGOUT, DEFAULT, CHANGELOCALE, ADDROOM, ADDUSER, ADDUSERLINK, ADDROOMLINK,
				UPDATEROOM, UPDATEROOMPAGE, UPDATEUSER, UPDATEUSERPAGE, DELETEROOM, DELETEUSER, GETALLUSERS,
				GETALLROOMS);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getServletPath();
		String action = req.getParameter("action");
		if (action == null) {
			if ("/login.jsp".equals(path) || "/register.jsp".equals(path)) {
				req.getRequestDispatcher(path).forward(request, response);
				return;
			}
			req.getRequestDispatcher(DEFAULT_COMMAND).forward(request, response);
			return;
		}

		HttpSession session = req.getSession();
		String userRole = (String) session.getAttribute("currentUserRole");
		CommandEnum command = CommandEnum.valueOf(action.toUpperCase());

		if ("guest".equals(userRole) && !guestCommands.contains(command)) {
			req.getRequestDispatcher(DEFAULT_COMMAND).forward(request, response);
			return;
		}

		if ("client".equals(userRole) && !userCommands.contains(command)) {
			req.getRequestDispatcher(DEFAULT_COMMAND).forward(request, response);
			return;
		}

		if ("waiter".equals(userRole) && !waiterCommands.contains(command)) {
			req.getRequestDispatcher(DEFAULT_COMMAND).forward(request, response);
			return;
		}

		if ("admin".equals(userRole) && !adminCommands.contains(command)) {
			req.getRequestDispatcher(DEFAULT_COMMAND).forward(request, response);
			return;
		}

		chain.doFilter(request, response);
	}

}

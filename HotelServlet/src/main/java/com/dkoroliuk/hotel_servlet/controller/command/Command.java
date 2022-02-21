package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;

/**
 * Interface for command
 */
public interface Command {
	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DBException, ParseException;
}

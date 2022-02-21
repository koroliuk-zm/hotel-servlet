package com.dkoroliuk.hotel_servlet.controller.command;

/**
 * Factory method to retrieve particular command by command name
 */
public class CommandFactory {

	private CommandFactory() {
	}

	public static Command getCommand(String action) {
		Command command;
		CommandEnum com = null;
		try {
			com = CommandEnum.valueOf(action.toUpperCase());
			command = com.getCommand();
		} catch (IllegalArgumentException e) {
			command = CommandEnum.DEFAULT.getCommand();
		}
		return command;
	}

}

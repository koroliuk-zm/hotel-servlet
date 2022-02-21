package com.dkoroliuk.hotel_servlet.controller.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandFactoryTest {

	@Test
	public void whenWrongActionShouldReturnDefaultCommand() {
		assertEquals(new DefaultCommand().getClass(), CommandFactory.getCommand("command").getClass());
	}
}

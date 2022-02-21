package com.dkoroliuk.hotel_servlet.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * utility class for password hashing
 */
public class Password {
	private static final Logger logger = LogManager.getLogger(Password.class.getName());
	private static final SecureRandom RANDOM = new SecureRandom();

	private Password() {
	}

	public static byte[] generateSalt() {
		final byte[] salt = new byte[32];
		RANDOM.nextBytes(salt);
		return salt;
	}

	public static byte[] hash(String password, byte[] salt) {
		MessageDigest digest;
		byte[] hash = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			digest.update(salt);
			hash = digest.digest(password.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("Unsuccesful encoding operation", e);
		}
		return hash;
	}

	public static boolean isExpectedPassword(byte[] password, byte[] expectedPassword) {
		if (password.length != expectedPassword.length) {
			return false;
		}
		for (int i = 0; i < password.length; i++) {
			if (password[i] != expectedPassword[i]) {
				return false;
			}
		}
		return true;
	}
}

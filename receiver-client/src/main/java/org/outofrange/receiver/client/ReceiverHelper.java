package org.outofrange.receiver.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Collect some util methods for receiver - package private!
 * @author outofrange
 */
class ReceiverHelper {
	private static final char[] hexArray = "0123456789abcdef".toCharArray();

	private ReceiverHelper() {
		// no instantiation allowed
	}

	static URL appendToUrl(URL url, String... paths) throws MalformedURLException {
		StringBuilder s = new StringBuilder(url.toString());

		for (String path : paths) {
			s.append(path);
		}

		return new URL(s.toString());
	}

	static String convertStreamToString(InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	static String md5(File file) throws IOException {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			try(InputStream is = Files.newInputStream(file.toPath())) {
				DigestInputStream dis = new DigestInputStream(is, md);

				while(dis.read() != -1); // just read everything and discard.

				return bytesToHex(md.digest());
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("You don't have MD5?!", e);
		}
	}

	static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}

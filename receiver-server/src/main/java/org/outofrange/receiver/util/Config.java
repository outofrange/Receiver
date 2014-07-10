package org.outofrange.receiver.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author extmoesl
 *         Created on 07.07.2014.
 */
public enum Config {
	CONFIG;

	private Properties properties;

	Config() {
		properties = new Properties();
		BufferedInputStream stream = null;
		try {
			stream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("default.properties"));
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stream != null) {
                    stream.close();
                }
			} catch (IOException e) {
				// ignore
			}
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}

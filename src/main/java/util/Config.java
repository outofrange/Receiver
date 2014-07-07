package util;

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

	private String configPath = "";

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
				stream.close();
			} catch (IOException | NullPointerException e) {
				// ignore
			}
		}
	}

	public String getServerPath() {
		return "http://" + CONFIG.getProperty("host") + ":" + CONFIG.getProperty("port") + "/";
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}

package org.outofrange.receiver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author outofrange
 */
public enum Config {
	CONFIG;

    private final Logger logger;
	private Properties properties;

	Config() {
        logger = LoggerFactory.getLogger(Config.class);

		properties = new Properties();

		try( BufferedInputStream stream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("default.properties"))) {
			properties.load(stream);
		} catch (IOException e) {
			logger.error("Couldn't load config file /o\\", e);
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}

package org.outofrange.receiver.service.dto;

import java.net.URL;

/**
 * @author extmoesl
 *         Created on 07.07.2014.
 */
public class ConfigDto {
	private String host;
	private String port;
	private URL url;

	public ConfigDto(String host, String port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	@Override
	public String toString() {
		return "http://" + host + ":" + port;
	}
}

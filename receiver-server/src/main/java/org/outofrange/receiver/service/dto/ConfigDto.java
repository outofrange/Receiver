package org.outofrange.receiver.service.dto;

import java.net.URI;

/**
 * @author outofrange
 */
public class ConfigDto {
    private final URI uri;

    public ConfigDto(URI uri) {
        this.uri = uri;
	}

	public URI getUri() {
		return uri;
	}

	@Override
	public String toString() {
		return uri.toString();
	}
}

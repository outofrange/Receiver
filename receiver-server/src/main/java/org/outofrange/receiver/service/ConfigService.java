package org.outofrange.receiver.service;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.outofrange.receiver.service.dto.ConfigDto;
import org.outofrange.receiver.util.Config;

import java.io.File;
import java.net.URI;

/**
 * @author outofrange
 */
public class ConfigService {
	private final Config config = Config.CONFIG;

	public File getConfigAsQr() {
		return QRCode.from(getConfigDto().toString()).to(ImageType.GIF).file();
	}

	public ConfigDto getConfigDto() {
		return new ConfigDto(URI.create(config.getProperty("address")));
	}
}

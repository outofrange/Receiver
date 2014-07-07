package org.outofrange.receiver.service;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.outofrange.receiver.service.dto.ConfigDto;
import org.outofrange.receiver.util.Config;

import java.io.File;

/**
 * @author extmoesl
 *         Created on 07.07.2014.
 */
public class ConfigService {
	private final Config CONFIG = Config.CONFIG;

	public File getConfigAsQr() {
		return QRCode.from(getConfigDto().toString()).to(ImageType.GIF).file();
	}

	public ConfigDto getConfigDto() {
		return new ConfigDto(CONFIG.getProperty("host"), CONFIG.getProperty("port"));
	}
}

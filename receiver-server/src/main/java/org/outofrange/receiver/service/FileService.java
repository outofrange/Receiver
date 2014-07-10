package org.outofrange.receiver.service;

import org.outofrange.receiver.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author outofrange
 */
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final Config config = Config.CONFIG;

	public void saveFile(InputStream uploadedInputStream,  String fileName) throws IOException {
        logger.debug("Saving file " + fileName);

		try(OutputStream outputStream = new FileOutputStream(new File(fileName))) {
			int read;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			outputStream.flush();
		}
	}

	public File getFile(String path) {
		return new File(config.getProperty("upload") + "/" + path);
	}
}

package org.outofrange.receiver.service;

import org.outofrange.receiver.exceptions.ServerException;
import org.outofrange.receiver.util.Config;
import org.outofrange.receiver.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author outofrange
 */
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final Config config = Config.CONFIG;

	public void saveFile(InputStream uploadedInputStream,  String fileName) {
        logger.debug("Saving file " + fileName);
        Validator.noSpecialCharacters(fileName);
		Validator.notLongerThan(fileName, 255);

        try (OutputStream outputStream = new FileOutputStream(new File(config.getProperty("upload") + "/" + fileName))) {

            int read;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			outputStream.flush();
		} catch (IOException e) {
            throw new ServerException("Couldn't save file", e);
        }
	}

	public File getFile(String fileName) {
        logger.debug("Retrieving file " + fileName);
        Validator.noSpecialCharacters(fileName);

		return new File(config.getProperty("upload") + "/" + fileName);
	}
}

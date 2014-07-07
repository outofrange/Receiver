package org.outofrange.receiver.service;

import java.io.*;

/**
 * @author extmoesl
 *         Created on 07.07.2014.
 */
public class FileService {
	public void saveFile(InputStream uploadedInputStream,  String serverLocation) throws IOException {
		try(OutputStream outputStream = new FileOutputStream(new File(serverLocation))) {
			int read;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			outputStream.flush();
		}
	}

	public File getFile(String path) {
		return new File(path);
	}
}

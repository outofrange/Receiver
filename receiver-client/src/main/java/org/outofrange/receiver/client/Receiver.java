package org.outofrange.receiver.client;

import org.outofrange.receiver.RestPaths;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * @author outofrange
 */
public class Receiver {
    private static final String CRLF = "\r\n";
    private static final String BOUNDARY_START = "--";
    private static final String BOUNDARY = Long.toHexString(System.currentTimeMillis());
    private static final String CHARSET = "UTF-8";

    private final URL url;

    public Receiver(URL url) {
        this.url = url;
    }

	/**
	 * Send a file to the remote server. Receiver will choose a filename by itself.
	 * @param file A reference to the existing, local file
	 * @return The name of the remote file
	 * @throws IOException An IOException is thrown either if the file could not be read locally,
	 * something was wrong with the URL/connection or the server didn't respond with 200
	 */
    public String sendFile(File file) throws IOException {
		// TODO use something like toBase64(SHA1(file))
		final String fileName = file.getName();
		sendFile(file, fileName);

		return fileName;
    }

	/**
	 * Send a file to the remote server. Be careful when specifying your filename - other users are able to download
	 * your file or overwrite them with their own files if they know or guess the right filename.
	 *
	 * @param file A reference to the existing, local file
	 * @param fileName The filename which should be used by the server
	 * @throws IOException An IOException is thrown either if the file could not be read locally,
	 * something was wrong with the URL/connection or the server didn't respond with 200
	 */
    public void sendFile(File file, String fileName) throws IOException {
        String encodedFileId = URLEncoder.encode(fileName, CHARSET);

        URLConnection connection = appendToUrl(url, RestPaths.FILE, RestPaths.DELIMITER,
                encodedFileId).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        try (OutputStream output = connection.getOutputStream()) {
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, CHARSET), true)) {
                writer.append(BOUNDARY_START).append(BOUNDARY).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"").append(RestPaths.FILE_PARAM_NAME).append
                        ("\"; filename=\"").append(encodedFileId).append("\"").append(CRLF);
                writer.append(CRLF).flush();
                Files.copy(file.toPath(), output);
                output.flush();
                writer.append(CRLF).flush();

                writer.append(BOUNDARY_START).append(BOUNDARY).append(BOUNDARY_START).append(CRLF);
            }
        }

        if (((HttpURLConnection) connection).getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Server couldn't save file.");
        }
    }

    private static URL appendToUrl(URL url, String... paths) throws MalformedURLException {
        StringBuilder s = new StringBuilder(url.toString());

        for (String path : paths) {
            s.append(path);
        }

        return new URL(s.toString());
    }

	/**
	 * Query the server for a remote file
	 * @param fileName The name of the remote file
	 * @return The content of the file as a String
	 * @throws IOException
	 */
    public String getFileContent(String fileName) throws IOException {
        String encodedFileId = URLEncoder.encode(fileName, CHARSET);
        URLConnection connection = appendToUrl(url, RestPaths.FILE, RestPaths.DELIMITER,
                encodedFileId).openConnection();

        return convertStreamToString(connection.getInputStream());
    }

    private static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}

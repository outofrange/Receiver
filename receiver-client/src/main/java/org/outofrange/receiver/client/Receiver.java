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

    public void sendFile(File file) throws IOException {
        sendFile(file, file.getName());
    }

    public void sendFile(File file, String fileId) throws IOException {
        String encodedFileId = URLEncoder.encode(fileId, CHARSET);

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

    public String getFileContent(String fileId) throws IOException {
        String encodedFileId = URLEncoder.encode(fileId, CHARSET);
        URLConnection connection = appendToUrl(url, RestPaths.FILE, RestPaths.DELIMITER,
                encodedFileId).openConnection();

        return convertStreamToString(connection.getInputStream());
    }

    private static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}

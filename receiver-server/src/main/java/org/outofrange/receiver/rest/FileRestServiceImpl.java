package org.outofrange.receiver.rest;


import org.outofrange.receiver.service.FileService;

import javax.ws.rs.core.Response;
import java.io.InputStream;

public class FileRestServiceImpl implements FileRestService {
    private final FileService service = new FileService();

    @Override
    public Response getFile(String fileName) {
        return Response.ok(service.getFile(fileName)).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
    }

    @Override
    public Response postFile(InputStream fileInputStream, String fileName) {
        service.saveFile(fileInputStream, fileName);

        return Response.ok("File successfully saved").build();
    }


}
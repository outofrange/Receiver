package org.outofrange.receiver.rest;


import org.outofrange.receiver.service.FileService;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

public class FileRestServiceImpl implements FileRestService {
    private FileService service = new FileService();

    @Override
    public Response getFile(String fileName) {
        return Response.ok(service.getFile(fileName)).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
    }

    @Override
    public Response postFile(String fileId, InputStream fileInputStream) throws IOException {
        service.saveFile(fileInputStream, fileId);

        return Response.ok("File successfully saved").build();
    }


}
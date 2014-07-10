package org.outofrange.receiver.rest;


import org.glassfish.jersey.media.multipart.FormDataParam;
import org.outofrange.receiver.service.FileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/file/{fileName}")
public class FileRestServiceImpl implements FileRestService {
    private FileService service = new FileService();

    @Override
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@PathParam("fileName") String fileName) {
        return Response.ok(service.getFile(fileName)).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
    }

    @Override
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response postFile(@PathParam("fileName") String fileId, @FormDataParam("file") InputStream fileInputStream) throws IOException {
        service.saveFile(fileInputStream, fileId);

        return Response.ok("File successfully saved").build();
    }


}
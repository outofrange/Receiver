package org.outofrange.receiver.rest;


import org.glassfish.jersey.media.multipart.FormDataParam;
import org.outofrange.receiver.service.FileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/file/{fileId}")
public class FileRestServiceImpl implements FileRestService {
    private FileService service = new FileService();

    @Override
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@PathParam("fileId") String fileId) {
        return Response.ok(service.getFile(fileId)).header("Content-Disposition", "attachment; filename=\"" + fileId + "\"").build();
    }

    @Override
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response postFile(@PathParam("fileId") String fileId, @FormDataParam("file") InputStream fileInputStream) throws IOException {
        service.saveFile(fileInputStream, fileId);

        return Response.ok("File successfully saved").build();
    }


}
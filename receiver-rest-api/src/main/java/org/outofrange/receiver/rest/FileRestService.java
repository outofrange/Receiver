package org.outofrange.receiver.rest;

import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by morg on 07.07.14.
 */
@Path("/file/{fileId}")
public interface FileRestService {
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response getFile(@PathParam("fileId") String fileId);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response postFile(@PathParam("fileId") String fileId, @FormDataParam("file") InputStream fileInputStream) throws IOException;
}

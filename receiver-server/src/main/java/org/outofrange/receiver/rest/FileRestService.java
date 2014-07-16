package org.outofrange.receiver.rest;


import org.glassfish.jersey.media.multipart.FormDataParam;
import org.outofrange.receiver.RestPaths;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * @author outofrange
 */
@Path(RestPaths.FILE + "/{fileId}")
public interface FileRestService {
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response getFile(@PathParam("fileId") String fileId);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response postFile(@FormDataParam(RestPaths.FILE_PARAM_NAME) InputStream fileInputStream, @PathParam("fileName") String fileName);
}

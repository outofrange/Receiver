package org.outofrange.receiver;

import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/file")
public class FileRestService {
    @PUT
    @Path("/{fileName}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response postFile(@PathParam("fileName") String fileId, @FormDataParam("file") InputStream fileInputStream) {
        try {
            saveFile(fileInputStream, fileId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok("File successfully saved").build();
    }

    private void saveFile(InputStream uploadedInputStream,  String serverLocation) throws IOException {
        try(OutputStream outputStream = new FileOutputStream(new File(serverLocation))) {
            int read;
            byte[] bytes = new byte[1024];

            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            outputStream.flush();
        }
    }


}
package org.outofrange.receiver.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by morg on 07.07.14.
 */
@Path("/config")
public interface ConfigRestService {
    @GET
    @Path("/qr")
    @Produces("image/gif")
    Response getConfigAsQr();

    @GET
    @Produces("application/json")
    Response getConfig();
}

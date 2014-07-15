package org.outofrange.receiver.rest;

import org.outofrange.receiver.RestPaths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by morg on 07.07.14.
 */
@Path(RestPaths.CONFIG)
public interface ConfigRestService {
    @GET
    @Path(RestPaths.QR)
    @Produces("image/gif")
    Response getConfigAsQr();

    @GET
    @Produces("application/json")
    Response getConfig();
}

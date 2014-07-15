package org.outofrange.receiver.rest;

import org.outofrange.receiver.RestPaths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author outofrange
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

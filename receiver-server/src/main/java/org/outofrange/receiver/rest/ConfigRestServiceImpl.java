package org.outofrange.receiver.rest;

import com.google.gson.Gson;
import org.outofrange.receiver.service.ConfigService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/config")
public class ConfigRestServiceImpl implements org.outofrange.receiver.rest.ConfigRestService {
	private ConfigService service = new ConfigService();

    @Override
    @GET
	@Path("/qr")
	@Produces("image/gif")
	public Response getConfigAsQr() {
		return Response.ok(service.getConfigAsQr()).build();
	}

    @Override
    @GET
	@Produces("application/json")
	public Response getConfig() {
		return Response.ok().entity(new Gson().toJson(service.getConfigDto())).build();
	}
}

package org.outofrange.receiver.rest;

import com.google.gson.Gson;
import org.outofrange.receiver.service.ConfigService;

import javax.ws.rs.core.Response;


public class ConfigRestServiceImpl implements org.outofrange.receiver.rest.ConfigRestService {
	private ConfigService service = new ConfigService();

    @Override
	public Response getConfigAsQr() {
		return Response.ok(service.getConfigAsQr()).build();
	}

    @Override
	public Response getConfig() {
		return Response.ok().entity(new Gson().toJson(service.getConfigDto())).build();
	}
}

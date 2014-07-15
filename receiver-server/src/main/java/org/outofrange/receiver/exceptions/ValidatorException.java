package org.outofrange.receiver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author outofrange
 */
@SuppressWarnings("SameParameterValue")
public class ValidatorException extends WebApplicationException {
    public ValidatorException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(message).build());
    }
}
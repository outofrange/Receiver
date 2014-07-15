package org.outofrange.receiver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author outofrange
 */
@SuppressWarnings("SameParameterValue")
public class ServerException extends WebApplicationException {
    public ServerException(String message, Throwable e) {
        super(message, e, Response.Status.INTERNAL_SERVER_ERROR);
    }
}

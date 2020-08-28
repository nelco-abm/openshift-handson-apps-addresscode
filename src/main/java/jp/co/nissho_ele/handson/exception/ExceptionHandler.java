package jp.co.nissho_ele.handson.exception;

import javax.json.Json;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(ExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {
        int code = 500;
        if (exception instanceof WebApplicationException) {
            code = ((WebApplicationException) exception).getResponse().getStatus();
        }
        LOG.error("exception happened");
        return Response.status(code)
                .entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
                .build();
    }
}
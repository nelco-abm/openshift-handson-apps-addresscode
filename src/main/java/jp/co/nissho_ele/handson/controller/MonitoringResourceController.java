package jp.co.nissho_ele.handson.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/probe")
public class MonitoringResourceController {

    @ApplicationScoped
    private boolean error = false;

    @GET
    public Response uptime() {
        String msg;
        if (error) {
            msg = "error";
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(msg).build();
        }
        msg = "success";
        return Response.status(Response.Status.OK).entity(msg).build();
    }

    @GET
    @Path("/distruct/{flag}")
    public Response distruct(@PathParam("flag") @DefaultValue("false") String flag) {
        error = Boolean.parseBoolean(flag);
        return Response.status(Response.Status.OK).build();
    }

}
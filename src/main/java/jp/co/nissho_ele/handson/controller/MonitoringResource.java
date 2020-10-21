package jp.co.nissho_ele.handson.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/probe")
public class MonitoringResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String uptime() {
        return "success";
    }
}
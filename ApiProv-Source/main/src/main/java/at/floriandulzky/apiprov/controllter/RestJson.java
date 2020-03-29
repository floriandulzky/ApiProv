package at.floriandulzky.apiprov.controllter;

import at.floriandulzky.apiprov.service.AppControllerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/")
public class RestJson {

    @Inject
    AppControllerService appControllerService;

    @Context
    HttpHeaders httpHeaders;
    @Context
    UriInfo uriInfo;
    @Context
    HttpHeaders headers;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{path: (.*)}")
    public Response get(@PathParam("path") String path, String body) {
        return appControllerService.handleRequest(path, this.uriInfo, this.headers, "GET", body);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{path: (.*)}")
    public Response post(@PathParam("path") String path, @Context UriInfo uriInfo, String body) {
        return appControllerService.handleRequest(path, this.uriInfo, this.headers, "POST", body);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{path: (.*)}")
    public Response delete(@PathParam("path") String path, @Context UriInfo uriInfo, String body) {
        return appControllerService.handleRequest(path, this.uriInfo, this.headers, "DELETE", body);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{path: (.*)}")
    public Response put(@PathParam("path") String path, @Context UriInfo uriInfo, String body) {
        return appControllerService.handleRequest(path, this.uriInfo, this.headers, "PUT", body);
    }

}

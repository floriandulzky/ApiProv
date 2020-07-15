package at.floriandulzky.apiprov.controllter;

import at.floriandulzky.apiprov.dto.Api;
import at.floriandulzky.apiprov.json.ApiDeserializer;
import at.floriandulzky.apiprov.service.ConfigurationService;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/config/")
public class RestConfiguration {

    @Inject
    ConfigurationService configurationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("apis")
    public Response getApis() {
        return new ResponseBuilderImpl().status(200).entity(configurationService.getConfiguredApis()).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("api/{path: (.*)}")
    public Response putApi(@PathParam("path") String path, String jsonString) {
        Jsonb jsonb = JsonbBuilder.newBuilder().withConfig(new JsonbConfig().withDeserializers(new ApiDeserializer())).build();
        configurationService.saveApi(jsonb.fromJson(jsonString, Api.class));
        return new ResponseBuilderImpl().status(200).entity(configurationService.getConfiguredApis()).build();
    }

}

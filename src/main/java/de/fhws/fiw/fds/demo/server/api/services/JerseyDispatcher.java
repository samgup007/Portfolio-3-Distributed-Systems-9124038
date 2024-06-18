package de.fhws.fiw.fds.demo.server.api.services;

import de.fhws.fiw.fds.demo.server.api.states.dispatcher.GetDispatcher;
import de.fhws.fiw.fds.demo.server.database.utils.DatabaseInitialization;
import de.fhws.fiw.fds.demo.server.database.utils.ResetDatabase;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
public class JerseyDispatcher extends AbstractJerseyService {


    @GET
    @Path("resetdatabase")
    @Produces({MediaType.APPLICATION_JSON})
    public Response resetDatabase() {
        System.out.println("RESET DATABASE");

        ResetDatabase.resetAll();

        return Response.ok().build();
    }

    @GET
    @Path("filldatabase")
    @Produces({MediaType.APPLICATION_JSON})
    public Response fillDatabase() {
        System.out.println("FILL DATABASE");

        DatabaseInitialization.initializeDBWithRelations();

        return Response.ok().build();
    }

    @GET
    public Response getDispatcher() {
        try {
            return new GetDispatcher(this.serviceContext).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }
}

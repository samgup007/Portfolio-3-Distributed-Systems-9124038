package de.fhws.fiw.fds.demo.server.api.services;

import de.fhws.fiw.fds.demo.server.api.models.Module;
import de.fhws.fiw.fds.demo.server.api.models.Uni;
import de.fhws.fiw.fds.demo.server.api.queries.CountryAndNameQuery;
import de.fhws.fiw.fds.demo.server.api.queries.ModuleQuery;
import de.fhws.fiw.fds.demo.server.api.states.modules.DeleteUniModule;
import de.fhws.fiw.fds.demo.server.api.states.modules.GetAllUniModule;
import de.fhws.fiw.fds.demo.server.api.states.modules.GetSingleUniModule;
import de.fhws.fiw.fds.demo.server.api.states.modules.POSTUniModule;
import de.fhws.fiw.fds.demo.server.api.states.modules.PUTSingleUniModule;
import de.fhws.fiw.fds.demo.server.api.states.universities.DeleteUniversity;
import de.fhws.fiw.fds.demo.server.api.states.universities.GetAllUniversities;
import de.fhws.fiw.fds.demo.server.api.states.universities.GetSingleUniversity;
import de.fhws.fiw.fds.demo.server.api.states.universities.PostUniversity;
import de.fhws.fiw.fds.demo.server.api.states.universities.PutUniversity;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("universities")
public class JerseyUni extends AbstractJerseyService
{
    public JerseyUni() {
        super();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllUniversities(
            @DefaultValue("") @QueryParam("universityName") final String universityName,
            @DefaultValue("") @QueryParam("country") final String country,
            @DefaultValue("asc") @QueryParam("order") final String order,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("20") @QueryParam("size") int size) {
        try {
            return new GetAllUniversities(
                    this.serviceContext,
                    new CountryAndNameQuery<>(universityName, country, order, offset, size)
            ).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(e.getExceptionMessage(), e.getStatus().getCode());
        }
    }
    
    @GET
    @Path("{universityId: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleUniversity(@PathParam("universityId") final long id) {
        try {
            return new GetSingleUniversity(this.serviceContext, id).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response
                    .status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build()
            );
        }
    }

    @PUT
    @Path("{universityId: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateUniversity(@PathParam("universityId") final long id, final Uni universityModel) {
        try {

            Uni.validateUniversity(universityModel);

            return new PutUniversity(this.serviceContext, id, universityModel).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createUniversity(final Uni universityModel) {
        try {

            Uni.validateUniversity(universityModel);

            return new PostUniversity(this.serviceContext, universityModel).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }


    @DELETE
    @Path("{universityId: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteUniversity(@PathParam("universityId") final long id) {
        try {
            return new DeleteUniversity(this.serviceContext, id).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @POST
    @Path("{universityId: \\d+}/modules")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createModuleOfUniversity(@PathParam("universityId") final long universityId, final Module module) {

        try {
            Module.validateModule(module);
            
            return new POSTUniModule(this.serviceContext, universityId, module).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @GET
    @Path("{universityId: \\d+}/modules")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getModulesOfUniversity(@PathParam("universityId") final long personId,
    @DefaultValue("") @QueryParam("moduleName") final String moduleName,
    @DefaultValue("0") @QueryParam("offset") int offset,
    @DefaultValue("20") @QueryParam("size") int size) {
        try {
            return new GetAllUniModule(this.serviceContext, personId, new ModuleQuery<>(personId, moduleName, offset, size)).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @GET
    @Path("{universityId: \\d+}/modules/{moduleId: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getModuleByIdOfUniversity(@PathParam("universityId") final long universityId,
                                            @PathParam("moduleId") final long moduleId) {
        try {
            return new GetSingleUniModule(this.serviceContext, universityId, moduleId).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @PUT
    @Path("{universityId: \\d+}/modules/{moduleId: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateModuleOfUniversity(@PathParam("universityId") final long universityId,
    @PathParam("moduleId") final long moduleId, final Module module) {
        try {
            Module.validateModule(module);

            return new PUTSingleUniModule(this.serviceContext, universityId, moduleId, module).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @DELETE
    @Path("{universityId: \\d+}/modules/{moduleId: \\d+}")
    public Response deleteModuleOfUniversity(@PathParam("universityId") final long universityId,
    @PathParam("moduleId") final long moduleId) {
        try {
            return new DeleteUniModule(this.serviceContext, moduleId, universityId).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

}

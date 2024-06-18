package de.fhws.fiw.fds.demo.server.api.states.modules;

import de.fhws.fiw.fds.demo.server.api.models.Module;
import de.fhws.fiw.fds.demo.server.api.states.universities.UniversityRelTypes;
import de.fhws.fiw.fds.demo.server.api.states.universities.UniversityURI;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionRelationState;
import jakarta.ws.rs.core.Response;

public class GetAllUniModule extends AbstractGetCollectionRelationState<Response, Module> {

    public GetAllUniModule(ServiceContext serviceContext, long primaryId, AbstractRelationQuery<Response, Module> query) {
        super(serviceContext, primaryId, query);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UniversityURI.REL_PATH_ID, UniversityRelTypes.GET_SINGLE_UNIVERSITY, getAcceptRequestHeader(), this.primaryId);
        addLink(URLModulePath.REL_PATH, CRUDModuleType.CREATE_MODULE, getAcceptRequestHeader(), this.primaryId);
        addLink(URLModulePath.REL_PATH + "?moduleName={MODULENAME}", CRUDModuleType.FILTER_MODULES_BY_NAME, getAcceptRequestHeader(), this.primaryId);
    }
}

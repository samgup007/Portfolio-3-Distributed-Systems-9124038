package de.fhws.fiw.fds.demo.server.api.states.modules;

import de.fhws.fiw.fds.demo.server.DaoFactory;
import de.fhws.fiw.fds.demo.server.api.models.Module;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.ws.rs.core.Response;

public class POSTUniModule extends AbstractPostRelationState<Response, Module> {

    public POSTUniModule(ServiceContext serviceContext, long primaryId, Module modelToStore) {
        super(serviceContext, primaryId, modelToStore);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected NoContentResult saveModel() {
        return DaoFactory.getInstance().getModuleOfPartnerUniversityDao().create(this.primaryId, this.modelToStore);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(URLModulePath.REL_PATH_ID, CRUDModuleType.GET_SINGLE_MODULE, getAcceptRequestHeader(), this.primaryId, this.modelToStore.getId());
        addLink(URLModulePath.REL_PATH_ID, CRUDModuleType.UPDATE_SINGLE_MODULE, getAcceptRequestHeader(), this.primaryId, this.modelToStore.getId());
        addLink(URLModulePath.REL_PATH_ID, CRUDModuleType.DELETE_SINGLE_MODULE, getAcceptRequestHeader(), this.primaryId, this.modelToStore.getId());
    }
}

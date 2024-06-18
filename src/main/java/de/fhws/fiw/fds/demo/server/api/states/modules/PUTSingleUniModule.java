package de.fhws.fiw.fds.demo.server.api.states.modules;

import de.fhws.fiw.fds.demo.server.DaoFactory;
import de.fhws.fiw.fds.demo.server.api.models.Module;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.ws.rs.core.Response;

public class PUTSingleUniModule extends AbstractPutRelationState<Response, Module> {
    public PUTSingleUniModule(ServiceContext serviceContext, long primaryId, long requestedId, Module modelToUpdate) {
        super(serviceContext, primaryId, requestedId, modelToUpdate);
        this.suttonResponse = new JerseyResponse<>(); 
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(URLModulePath.REL_PATH_ID, CRUDModuleType.GET_SINGLE_MODULE, getAcceptRequestHeader(), this.primaryId, this.requestedId);
        addLink(URLModulePath.REL_PATH_ID, CRUDModuleType.DELETE_SINGLE_MODULE, getAcceptRequestHeader(), this.primaryId, this.requestedId);
    }

    @Override
    protected boolean clientDoesNotKnowCurrentModelState(AbstractModel modelFromDatabase) {
        return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
    }

    @Override
    protected NoContentResult updateModel() {
        return DaoFactory.getInstance().getModuleOfPartnerUniversityDao().update(this.primaryId, this.modelToUpdate);
    }

    @Override
    protected SingleModelResult<Module> loadModel() {
        return DaoFactory.getInstance().getModuleDao().readById(this.requestedId);
    }
}

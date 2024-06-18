package de.fhws.fiw.fds.demo.server.api.states.modules;

import de.fhws.fiw.fds.demo.server.DaoFactory;
import de.fhws.fiw.fds.demo.server.api.models.Module;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.ws.rs.core.Response;

public class GetSingleUniModule extends AbstractGetRelationState<Response, Module> {

    public GetSingleUniModule(ServiceContext serviceContext, long primaryId, long requestedId) {
        super(serviceContext, primaryId, requestedId);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Module> loadModel() {
        SingleModelResult<Module> location = DaoFactory.getInstance().getModuleDao().readById(this.requestedId);
        location.getResult().setPrimaryId(this.primaryId);
        return location;
    }

    @Override
    protected boolean clientKnowsCurrentModelState(AbstractModel modelFromDatabase) {
        return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(URLModulePath.REL_PATH, CRUDModuleType.GET_ALL_MODULES, getAcceptRequestHeader(), this.primaryId);
        addLink(URLModulePath.REL_PATH + "?moduleName={MODULENAME}", CRUDModuleType.FILTER_MODULES_BY_NAME, getAcceptRequestHeader(), this.primaryId);
        addLink(URLModulePath.REL_PATH_ID, CRUDModuleType.DELETE_SINGLE_MODULE, getAcceptRequestHeader(), this.primaryId, this.requestedId);
        addLink(URLModulePath.REL_PATH_ID, CRUDModuleType.UPDATE_SINGLE_MODULE, getAcceptRequestHeader(), this.primaryId, this.requestedId);
    }
}

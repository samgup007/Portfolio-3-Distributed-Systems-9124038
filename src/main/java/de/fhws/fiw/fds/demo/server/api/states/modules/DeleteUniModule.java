package de.fhws.fiw.fds.demo.server.api.states.modules;

import de.fhws.fiw.fds.demo.server.DaoFactory;
import de.fhws.fiw.fds.demo.server.api.models.Module;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.Response;

public class DeleteUniModule extends AbstractDeleteRelationState<Response, Module> {

    @Override
    protected SingleModelResult<Module> loadModel() {
        return DaoFactory.getInstance().getModuleOfPartnerUniversityDao().readById(this.primaryId, this.modelIdToDelete);
    }

    public DeleteUniModule(ServiceContext serviceContext, long modelIdToDelete, long primaryId) {
        super(serviceContext, modelIdToDelete, primaryId);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected NoContentResult deleteModel() {
        return DaoFactory.getInstance().getModuleOfPartnerUniversityDao().deleteRelation(this.primaryId, this.modelIdToDelete);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(URLModulePath.REL_PATH, CRUDModuleType.GET_ALL_MODULES, getAcceptRequestHeader(), this.primaryId);
    }
}

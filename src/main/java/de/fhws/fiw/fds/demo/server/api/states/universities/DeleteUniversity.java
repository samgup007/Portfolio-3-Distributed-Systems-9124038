package de.fhws.fiw.fds.demo.server.api.states.universities;

import de.fhws.fiw.fds.demo.server.DaoFactory;
import de.fhws.fiw.fds.demo.server.api.models.Uni;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import jakarta.ws.rs.core.Response;

public class DeleteUniversity extends AbstractDeleteState<Response, Uni> {

    public DeleteUniversity(ServiceContext serviceContext, long modelIdToDelete) {
        super(serviceContext, modelIdToDelete);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Uni> loadModel() {
        return DaoFactory.getInstance().getUniversityDao().readById(this.modelIdToDelete);
    }

    @Override
    protected NoContentResult deleteModel() {
        return DaoFactory.getInstance().getUniversityDao().delete(this.modelIdToDelete);
    }


    @Override
    protected void defineTransitionLinks() {
        addLink(UniversityURI.REL_PATH, UniversityRelTypes.GET_ALL_UNIVERSITIES, getAcceptRequestHeader());
    }

    
}

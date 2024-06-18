package de.fhws.fiw.fds.demo.server.api.states.universities;

import de.fhws.fiw.fds.demo.server.DaoFactory;
import de.fhws.fiw.fds.demo.server.api.models.Uni;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.ws.rs.core.Response;

public class GetSingleUniversity extends AbstractGetState<Response, Uni> {

    // Constructor initializes service context and the ID of the requested university
    public GetSingleUniversity(ServiceContext serviceContext, long requestedId) {
        super(serviceContext, requestedId);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Uni> loadModel() {
        return DaoFactory.getInstance().getUniversityDao().readById(this.requestedId);
    }

    @Override
    protected boolean clientKnowsCurrentModelState(AbstractModel modelFromDatabase) {
        return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink( UniversityURI.REL_PATH_ID, UniversityRelTypes.UPDATE_SINGLE_UNIVERSITY, getAcceptRequestHeader( ),
                this.requestedId );
        addLink( UniversityURI.REL_PATH_ID, UniversityRelTypes.DELETE_SINGLE_UNIVERSITY, getAcceptRequestHeader( ),
                this.requestedId );
        addLink( UniversityURI.REL_PATH, UniversityRelTypes.GET_ALL_UNIVERSITIES, getAcceptRequestHeader( ) );
    }

}

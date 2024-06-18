package de.fhws.fiw.fds.demo.server.api.states.universities;

import de.fhws.fiw.fds.demo.server.api.models.Uni;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import jakarta.ws.rs.core.Response;

public class GetAllUniversities extends AbstractGetCollectionState<Response, Uni> {

    public GetAllUniversities(ServiceContext serviceContext, AbstractQuery<Response, Uni> query) {
        super(serviceContext, query);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UniversityURI.REL_PATH, UniversityRelTypes.CREATE_UNIVERSITY, getAcceptRequestHeader());
        addLink(UniversityURI.REL_PATH + "?universityName={UNIVERSITYNAME}&country={COUNTRY}", UniversityRelTypes.FILTER_BY_NAME_AND_COUNTRY , getAcceptRequestHeader());
        addLink(UniversityURI.REL_PATH + "?universityName={UNIVERSITYNAME}&country={COUNTRY}&order=dsc", UniversityRelTypes.FILTER_BY_NAME_AND_COUNTRY_REVERSED, getAcceptRequestHeader());
        addLink(UniversityURI.REL_PATH + "?order=dsc", UniversityRelTypes.GET_ALL_UNIVERSITIES_REVERSED, getAcceptRequestHeader());
        addLink(UniversityURI.REL_PATH, UniversityRelTypes.GET_ALL_UNIVERSITIES, getAcceptRequestHeader());
    }

}

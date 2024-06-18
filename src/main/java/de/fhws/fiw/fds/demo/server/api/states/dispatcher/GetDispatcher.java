package de.fhws.fiw.fds.demo.server.api.states.dispatcher;

import de.fhws.fiw.fds.demo.server.api.states.universities.UniversityRelTypes;
import de.fhws.fiw.fds.demo.server.api.states.universities.UniversityURI;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetDispatcherState;
import jakarta.ws.rs.core.Response;

public class GetDispatcher extends AbstractGetDispatcherState<Response> {

    public GetDispatcher(ServiceContext serviceContext) {
        super(serviceContext);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UniversityURI.REL_PATH, UniversityRelTypes.CREATE_UNIVERSITY, getAcceptRequestHeader());
        addLink(UniversityURI.REL_PATH, UniversityRelTypes.GET_ALL_UNIVERSITIES, getAcceptRequestHeader());
        addLink(UniversityURI.REL_PATH + "?universityName={UNIVERSITYNAME}&country={COUNTRY}&order=dsc", UniversityRelTypes.FILTER_BY_NAME_AND_COUNTRY_REVERSED, getAcceptRequestHeader());
        addLink(UniversityURI.REL_PATH + "?universityName={UNIVERSITYNAME}&country={COUNTRY}", UniversityRelTypes.FILTER_BY_NAME_AND_COUNTRY , getAcceptRequestHeader());
        addLink(UniversityURI.REL_PATH + "?order=dsc", UniversityRelTypes.GET_ALL_UNIVERSITIES_REVERSED, getAcceptRequestHeader());
    }
}

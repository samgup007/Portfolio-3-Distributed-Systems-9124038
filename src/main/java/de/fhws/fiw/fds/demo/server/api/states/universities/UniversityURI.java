package de.fhws.fiw.fds.demo.server.api.states.universities;

import de.fhws.fiw.fds.demo.Start;

public interface UniversityURI
{
    String PATH_ELEMENT = "universities";
    String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";
}

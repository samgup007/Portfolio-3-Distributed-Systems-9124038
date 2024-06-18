package de.fhws.fiw.fds.demo.server;

import de.fhws.fiw.fds.demo.server.api.models.Uni;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public interface UniversityDao extends IDatabaseAccessObject<Uni> {
    void resetDatabase();

    void initializeDatabase();

    CollectionModelResult<Uni> readByUniversityNameCountry(String universityName, String country,SearchParameter searchParameter);
}

package de.fhws.fiw.fds.demo.server;

import de.fhws.fiw.fds.demo.server.api.models.Module;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseRelationAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public interface ModuleOfUniversityDao extends IDatabaseRelationAccessObject<Module> {

    CollectionModelResult<Module> readByModuleName(long primaryId, String moduleName, SearchParameter searchParameter);

    void initializeDatabase();

    void resetDatabase();

}

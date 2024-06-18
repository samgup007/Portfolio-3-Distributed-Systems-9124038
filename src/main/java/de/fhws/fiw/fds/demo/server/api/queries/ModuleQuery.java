package de.fhws.fiw.fds.demo.server.api.queries;


import de.fhws.fiw.fds.demo.server.DaoFactory;
import de.fhws.fiw.fds.demo.server.api.models.Module;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class ModuleQuery<R> extends AbstractRelationQuery<R, Module> {

    private String moduleName;

    public ModuleQuery(long primaryId, String moduleName, int offset, int size) {
        super(primaryId);
        this.moduleName = moduleName;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    protected CollectionModelResult<Module> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        return DaoFactory.getInstance().getModuleOfPartnerUniversityDao().readByModuleName(this.primaryId, this.moduleName, searchParameter);
    }
}

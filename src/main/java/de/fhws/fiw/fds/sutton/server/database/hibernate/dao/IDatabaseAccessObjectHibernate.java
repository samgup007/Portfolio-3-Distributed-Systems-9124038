package de.fhws.fiw.fds.sutton.server.database.hibernate.dao;

import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

public interface IDatabaseAccessObjectHibernate<T extends AbstractDBModel> {

    NoContentResult create(final T model);

    SingleModelHibernateResult<T> readById(final long id);

    default CollectionModelHibernateResult<T> readAll() {
        return readAll(SearchParameter.DEFAULT);
    }

    CollectionModelHibernateResult<T> readAll(SearchParameter searchParameter);

    NoContentResult update(final T model);

    NoContentResult delete(final long id);
}

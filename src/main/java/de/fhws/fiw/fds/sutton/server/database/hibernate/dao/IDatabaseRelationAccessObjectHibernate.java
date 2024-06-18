package de.fhws.fiw.fds.sutton.server.database.hibernate.dao;

import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

public interface IDatabaseRelationAccessObjectHibernate<B extends AbstractDBModel> {

    NoContentResult create(final long primaryId, final B secondaryModel);

    NoContentResult update(final long primaryId, final B secondaryModel);

    NoContentResult deleteRelation(final long primaryId, final long secondaryId);

    NoContentResult deleteRelationsFromPrimary(final long primaryId);

    NoContentResult deleteRelationsToSecondary(final long secondaryId);

    SingleModelHibernateResult<B> readById(final long primaryId, final long secondaryId);

    CollectionModelHibernateResult<B> readAll(final long primaryId, SearchParameter searchParameter);

    default CollectionModelHibernateResult<B> readAll(final long primaryId) {
        return readAll(primaryId, SearchParameter.DEFAULT);
    }

}

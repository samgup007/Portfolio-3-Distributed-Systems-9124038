package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * The AbstractRelationQuery extends the functionality of the {@link AbstractQuery} in order to fetch associated
 * data from the database.
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity encapsulated within the body of the HTTP response.
 */
public abstract class AbstractRelationQuery<R, T extends AbstractModel> extends AbstractQuery<R, T> {

    protected long primaryId;

    protected AbstractRelationQuery(final long primaryId) {
        this.primaryId = primaryId;
    }

    public AbstractRelationQuery<R, T> setPagingBehavior(final PagingBehavior<R, T> pagingBehavior) {
        super.setPagingBehavior(pagingBehavior);
        return this;
    }

}

package de.fhws.fiw.fds.sutton.server.api.states.get;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * <p>The AbstractGetCollectionRelationState extends the {@link AbstractGetCollectionState} with the required
 * functionality to request a collection of sub-resources related to a specific main resource from the database.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractGetCollectionRelationState.AbstractGetCollectionRelationStateBuilder}</p>
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity encapsulated within the body of the HTTP response.
 * @see AbstractGetCollectionState
 */
public abstract class AbstractGetCollectionRelationState<R, T extends AbstractModel> extends AbstractGetCollectionState<R, T> {

    /**
     * id {@link Long} of the main resource
     */
    protected long primaryId;

    /**
     * The query {@link AbstractRelationQuery} to be used to fetch sub-resources associated with a primary resource
     * from the database
     */
    protected AbstractRelationQuery<R, T> query;

    public AbstractGetCollectionRelationState(final AbstractGetCollectionRelationStateBuilder<R, T> builder) {
        super(builder);
        this.primaryId = builder.parentId;
        this.query = builder.query;
        super.query = this.query;
    }

    public AbstractGetCollectionRelationState(ServiceContext serviceContext, long primaryId, AbstractRelationQuery<R, T> query) {
        super(serviceContext, query);
        this.primaryId = primaryId;
        this.query = query;
        super.query = this.query;
    }

    public static abstract class AbstractGetCollectionRelationStateBuilder<R, T extends AbstractModel>
            extends AbstractGetCollectionStateBuilder<R, T> {
        protected long parentId;

        protected AbstractRelationQuery<R, T> query;

        public AbstractGetCollectionRelationStateBuilder<R, T> setParentId(final long parentId) {
            this.parentId = parentId;
            return this;
        }

        public AbstractGetCollectionRelationStateBuilder<R, T> setQuery(final AbstractRelationQuery<R, T> query) {
            this.query = query;
            return this;
        }
    }
}

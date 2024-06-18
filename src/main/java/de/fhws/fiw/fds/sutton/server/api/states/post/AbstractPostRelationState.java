package de.fhws.fiw.fds.sutton.server.api.states.post;

import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * <p>The AbstractPostRelationState extends the {@link AbstractPostState} with the required
 * functionality to create a sub-resource in the database and to create a relation between the created sub-resource
 * and its primary resource.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractPostRelationState.AbstractPostRelationStateBuilder}</p>
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity sent in the HTTP request to be created.
 * @see AbstractPostState
 */
public abstract class AbstractPostRelationState<R, T extends AbstractModel> extends AbstractPostState<R, T> {
    /**
     * id {@link Long} of the primary resource
     */
    protected long primaryId;

    public AbstractPostRelationState(final AbstractPostRelationStateBuilder<R, T> builder) {
        super(builder);
        this.primaryId = builder.parentId;
    }

    public AbstractPostRelationState(ServiceContext serviceContext, long primaryId, T modelToStore) {
        super(serviceContext, modelToStore);
        this.primaryId = primaryId;
        this.modelToStore = modelToStore;
    }

    public static abstract class AbstractPostRelationStateBuilder<R, T extends AbstractModel>
            extends AbstractPostStateBuilder<R, T> {
        protected long parentId;

        public AbstractPostRelationStateBuilder<R, T> setParentId(final long parentId) {
            this.parentId = parentId;
            return this;
        }
    }

}

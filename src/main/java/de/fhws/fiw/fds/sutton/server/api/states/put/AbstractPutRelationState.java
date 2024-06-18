package de.fhws.fiw.fds.sutton.server.api.states.put;

import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * <p>The AbstractPutRelationState extends the {@link AbstractPutState} with the required functionality to
 * update an existing sub-resource associated with a specific primary resource. The functionalities provided by this class
 * can be used also to relate an existing sub-resource to an existing primary resource.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractPutRelationState.AbstractPutRelationStateBuilder}</p>
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity sent in the HTTP request to perform an update process.
 * @see AbstractPutState
 */
public abstract class AbstractPutRelationState<R, T extends AbstractModel> extends AbstractPutState<R, T> {
    /**
     * The id {@link Long} of the primary resource
     */
    protected long primaryId;

    public AbstractPutRelationState(final AbstractPutRelationStateBuilder<R, T> builder) {
        super(builder);
        this.primaryId = builder.parentId;
    }

    public AbstractPutRelationState(ServiceContext serviceContext, long primaryId, long requestedId, T modelToUpdate) {
        super(serviceContext, requestedId, modelToUpdate);
        this.primaryId = primaryId;
        this.modelToUpdate = modelToUpdate;
        this.requestedId = requestedId;
    }

    public static abstract class AbstractPutRelationStateBuilder<R, T extends AbstractModel>
            extends AbstractPutStateBuilder<R, T> {
        protected long parentId;

        public AbstractPutRelationStateBuilder<R, T> setParentId(final long parentId) {
            this.parentId = parentId;
            return this;
        }
    }

}

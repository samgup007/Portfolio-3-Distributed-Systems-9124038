package de.fhws.fiw.fds.sutton.server.api.states.get;

import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * <p>The AbstractGetRelationState extends the {@link AbstractGetState} with the required functionality to request
 * a single sub-resource related to a specific primary resource from the database.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractGetRelationState.AbstractGetRelationStateBuilder}</p>
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity encapsulated within the body of the HTTP response.
 * @see AbstractGetState
 */
public abstract class AbstractGetRelationState<R, T extends AbstractModel> extends AbstractGetState<R, T> {

    /**
     * id {@link Long} of the primary resource
     */
    protected long primaryId;

    public AbstractGetRelationState(final AbstractGetRelationStateBuilder<R, T> builder) {
        super(builder);
        this.primaryId = builder.parentId;
    }

    public AbstractGetRelationState(ServiceContext serviceContext, long primaryId, long requestedId) {
        super(serviceContext, requestedId);
        this.primaryId = primaryId;
    }

    public static abstract class AbstractGetRelationStateBuilder<R, T extends AbstractModel> extends AbstractGetStateBuilder<R, T> {
        protected long parentId;

        public AbstractGetStateBuilder<R, T> setParentId(final long parentId) {
            this.parentId = parentId;
            return this;
        }
    }

}

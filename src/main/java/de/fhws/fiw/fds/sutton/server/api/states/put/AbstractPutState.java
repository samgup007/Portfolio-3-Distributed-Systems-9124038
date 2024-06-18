/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.api.states.put;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.net.URI;

/**
 * <p>The AbstractPutState extends the {@link AbstractState} and provides the required
 * functionality to update a model in the database.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractPutState.AbstractPutStateBuilder}</p>
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity sent in the HTTP request to perform an update process.
 */
public abstract class AbstractPutState<R, T extends AbstractModel> extends AbstractState<R, Void> {

    /**
     * The updated model {@link AbstractModel} to save in the database
     */
    protected T modelToUpdate;

    /**
     * The id {@link Long} from the request of the resource to be updated
     */
    protected long requestedId;

    /**
     * The result {@link SingleModelResult} of searching the model to be updated in the database
     */
    protected SingleModelResult<T> resultAfterGet;

    /**
     * The model {@link AbstractModel} from the result of searching the model to update in the database
     */
    protected T storedModel;

    /**
     * The result {@link NoContentResult} of updating the model in the database
     */
    protected NoContentResult resultAfterUpdate;

    protected AbstractPutState(final AbstractPutStateBuilder<R, T> builder) {
        super(builder);
        this.requestedId = builder.requestedId;
        this.modelToUpdate = builder.modelToUpdate;
    }

    public AbstractPutState(ServiceContext serviceContext, long requestedId, T modelToUpdate) {
        super(serviceContext);
        this.modelToUpdate = modelToUpdate;
        this.requestedId = requestedId;
    }

    @Override
    protected R buildInternal() {
        configureState();

        authorizeRequest();

        if (this.requestedId != this.modelToUpdate.getId()) {
            return this.suttonResponse.status(Status.BAD_REQUEST).build();
        }

        this.resultAfterGet = loadModel();

        if (this.resultAfterGet.isEmpty()) {
            return this.suttonResponse.status(Status.NOT_FOUND).build();
        }

        this.storedModel = this.resultAfterGet.getResult();

        if (clientDoesNotKnowCurrentModelState(this.storedModel)) {
            return this.suttonResponse.status(Status.PRECONDITION_FAILED).build();
        }

        this.resultAfterUpdate = updateModel();

        if (this.resultAfterUpdate.hasError()) {
            return this.suttonResponse.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return createResponse();
    }

    /**
     * This method should be used to prove if the user is allowed to update an existing model
     */
    protected void authorizeRequest(){}

    /**
     * Extending classes should use this method to load the model to update from the database
     */
    protected abstract SingleModelResult<T> loadModel();

    /**
     * Returns true if the user doesn't have the most recent version of the model
     *
     * @param modelFromDatabase the model from the database so that the user can compare it with
     *                          the model, the user knows about
     */
    protected boolean clientDoesNotKnowCurrentModelState(final AbstractModel modelFromDatabase) {
        return false;
    }

    /**
     * Extending classes should use this method to implement the updating mechanism of the model in the database
     *
     * @return the result {@link NoContentResult} of updating the model in the database
     */
    protected abstract NoContentResult updateModel();

    protected R createResponse() {
        defineResponseStatus();

        defineHttpCaching();

        defineHttpResponseBody();

        defineSelfLink();

        defineTransitionLinks();

        return this.suttonResponse.build();
    }

    private void defineResponseStatus() {
        this.suttonResponse.status(Status.NO_CONTENT);
    }

    /**
     * This method is used to configure the cashing behavior. It could be used to solve the lost update problem when
     * multiple clients attempt to update the same resource at the same time.
     */
    protected void defineHttpCaching() {

    }

    private void defineHttpResponseBody() {
        this.suttonResponse.entity(null);
    }

    /**
     * This method is used to define all transition links based on the idea of a REST system as
     * a finite state machine.
     */
    protected abstract void defineTransitionLinks();

    protected void defineSelfLink() {
        final URI self = this.uriInfo.getURI();

        this.suttonResponse.link(self, "self");
    }

    public static abstract class AbstractPutStateBuilder<R, T extends AbstractModel>
            extends AbstractState.AbstractStateBuilder<R, Void> {
        protected long requestedId;

        protected T modelToUpdate;

        public AbstractPutStateBuilder<R, T> setRequestedId(final long requestedId) {
            this.requestedId = requestedId;
            return this;
        }

        public AbstractPutStateBuilder<R, T> setModelToUpdate(final T modelToUpdate) {
            this.modelToUpdate = modelToUpdate;
            return this;
        }
    }

}

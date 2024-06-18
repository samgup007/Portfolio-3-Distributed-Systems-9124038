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

package de.fhws.fiw.fds.sutton.server.api.states.post;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.net.URI;

/**
 * <p>The AbstractPostState extends the {@link AbstractState} and provides the required
 * functionality to create a model in the database.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractPostState.AbstractPostStateBuilder}</p>
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity sent in the HTTP request to be created.
 */
public abstract class AbstractPostState<R, T extends AbstractModel> extends AbstractState<R, Void> {

    /**
     * The model {@link AbstractModel} sent in the request to be created
     */
    protected T modelToStore;

    /**
     * The result {@link NoContentResult} of creating the model in the database
     */
    protected NoContentResult resultAfterSave;

    protected AbstractPostState(final AbstractPostStateBuilder<R, T> builder) {
        super(builder);
        this.modelToStore = builder.modelToCreate;
    }

    public AbstractPostState(ServiceContext serviceContext, T modelToStore) {
        super(serviceContext);
        this.modelToStore = modelToStore;
    }

    @Override
    protected R buildInternal() {
        configureState();

        authorizeRequest();

        if (this.modelToStore.getId() != 0) {
            return this.suttonResponse.status(Status.BAD_REQUEST).build();
        }

        this.resultAfterSave = saveModel();

        if (this.resultAfterSave.hasError()) {
            return this.suttonResponse.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return createResponse();
    }

    /**
     * This method should be used to prove if the user is allowed to create a model
     */
    protected void authorizeRequest(){}

    /**
     * Extending classes should use this method to implement the creation of model in the database
     *
     * @return the result {@link NoContentResult} of creating the model in the database
     */
    protected abstract NoContentResult saveModel();

    protected R createResponse() {
        defineLocationLink();

        defineTransitionLinks();

        return this.suttonResponse.build();
    }

    /**
     * This method is used to define all transition links based on the idea of a REST system as
     * a finite state machine.
     */
    protected abstract void defineTransitionLinks();

    protected void defineLocationLink() {
        final URI location = this.uriInfo.appendIdToPath(this.modelToStore.getId());
        this.suttonResponse.status(Status.CREATED);
        this.suttonResponse.location(location);
    }

    public static abstract class AbstractPostStateBuilder<R, T extends AbstractModel>
            extends AbstractState.AbstractStateBuilder<R, Void> {
        protected T modelToCreate;

        public AbstractPostStateBuilder<R, T> setModelToCreate(final T modelToCreate) {
            this.modelToCreate = modelToCreate;
            return this;
        }
    }

}

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

package de.fhws.fiw.fds.sutton.server.api.states;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Hyperlinks;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.ServletRequestAdapter.SuttonServletRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.requestAdapter.SuttonRequest;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.SuttonResponse;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;

/**
 * <p>The AbstractState class defines the basic requirements each extending state class needs to define a proper workflow.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend {@link AbstractState.AbstractStateBuilder}.</p>
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity encapsulated within the body of the HTTP response.
 */
public abstract class AbstractState<R, T> {

    protected SuttonUriInfo uriInfo;

    protected SuttonServletRequest suttonServletRequest;

    protected SuttonRequest suttonRequest;

    protected SuttonResponse<R, T> suttonResponse;

    /**
     * This constructor instantiates an instance of the AbstractState class using the builder pattern
     */
    protected AbstractState(final AbstractStateBuilder<R, T> builder) {
        this.uriInfo = builder.uriInfo;
        this.suttonServletRequest = builder.suttonServletRequest;
        this.suttonRequest = builder.SuttonRequest;
        this.suttonResponse = builder.suttonResponse;
    }

    protected AbstractState(final ServiceContext serviceContext ) {
        this.uriInfo = serviceContext.getUriInfo();
        this.suttonServletRequest = serviceContext.getServletRequest();
        this.suttonRequest = serviceContext.getRequest();
    }

    /**
     * This is the main method to start execution of this state implementation.
     *
     * @return the response sent back to the client
     */
    public final R execute() throws SuttonWebAppException {
        try {
            return buildInternal();
        } catch (SuttonWebAppException e) {
            e.printStackTrace();
            throw e;
        } catch (final Exception e) {
            e.printStackTrace();

            return this.suttonResponse.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public AbstractState<R,T> setResponse( SuttonResponse<R, T> suttonResponse ) {
        this.suttonResponse = suttonResponse;
        return this;
    }

    /**
     * This reads the API-Key from the header of the {@link SuttonServletRequest}.
     *
     * @return the API-Key String of the header
     */
    private String getApiKeyFromRequest() {
        return suttonServletRequest.getHeader("API-Key");
    }

    /**
     * Extended states must implement this method which contains the main work flow depending on the state.
     *
     * @return the response sent back to the client
     */
    protected abstract R buildInternal();

    /**
     * This method can be used to configure the state. If extended classes implement the work flow
     * in different ways, this method would be the correct place to decide. For example, the PUT state
     * could be implemented so that it returns always status code 200 with the resource OR to return status
     * code 204 without the resource.
     */
    protected void configureState() {

    }

    /**
     * Add a link to the response builder. This method should be called by subclasses during
     * processing of the request, for example as part of method {@link #buildInternal()}.
     *
     * @param uriTemplate a template of an absolute URI
     * @param relType     the relation type of this link
     * @param params      parameters that are replaced in the given template
     */
    protected final void addLink(final String uriTemplate,
                                 final String relType,
                                 final String mediaType,
                                 final Object... params) {
        Hyperlinks.addLink(this.uriInfo, this.suttonResponse, uriTemplate, relType, mediaType, params);
    }


    /**
     * Creates hyperlinks as specified in the specifications of the REST architecture and adds them to the response as
     * headers
     *
     * @param uriTemplate {@link String} the uri template to be used to build the hyperlink in the response
     * @param relType     {@link String} describes what the hyperlink stands for
     * @param params      an ellipsis of {@link Object} to be built to the href part of the hyperlink
     */
    protected final void addLink(final String uriTemplate, final String relType, final Object... params) {
        Hyperlinks.addLink(this.uriInfo, this.suttonResponse, uriTemplate, relType, null, params);
    }

    /**
     * Returns the value of the Accept header from the request
     *
     * @return the value {@link String} of the Accept header from the request
     */
    protected final String getAcceptRequestHeader() {
        return getRequestHeader("Accept");
    }

    /**
     * @param headerName {@link String} name of the header in the request to return its value
     * @return the value {@link String} of the provided header from the request
     */
    protected final String getRequestHeader(final String headerName) {
        return this.suttonServletRequest.getHeader(headerName);
    }

    /**
     * this inner static abstract class is used in the context of the builder pattern in order to
     * instantiate state classes
     */

    public static abstract class AbstractStateBuilder<R, T> {
        protected SuttonUriInfo uriInfo;

        protected SuttonServletRequest suttonServletRequest;

        protected SuttonRequest SuttonRequest;

        protected SuttonResponse<R, T> suttonResponse;

        public AbstractStateBuilder<R, T> setUriInfo(final SuttonUriInfo uriInfo) {
            this.uriInfo = uriInfo;
            return this;
        }

        public AbstractStateBuilder<R, T>  setSuttonServletRequest(final SuttonServletRequest suttonServletRequest) {
            this.suttonServletRequest = suttonServletRequest;
            return this;
        }

        public AbstractStateBuilder<R, T>  setSuttonRequest(final SuttonRequest suttonRequest) {
            this.SuttonRequest = suttonRequest;
            return this;
        }

        public AbstractStateBuilder<R, T>  setSuttonResponse(final SuttonResponse<R, T> suttonResponse) {
            this.suttonResponse = suttonResponse;
            return this;
        }

        /**
         * This method is used to return a state class, which extends {@link AbstractState}
         */
        public abstract AbstractState<R, T> build();
    }

}

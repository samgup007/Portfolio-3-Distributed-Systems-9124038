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

package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.SuttonResponse;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;

/**
 * The PagingContext provides the essential requirements to create a paging context in the response to make it clear
 * for the client to navigate through the available pages according to the hyperlinks principle of the REST architecture.
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity encapsulated within the body of the HTTP response.
 */
public class PagingContext<R, T> {

    private final SuttonUriInfo uriInfo;

    private final SuttonResponse<R, T> responseBuilder;

    private final String mediaType;

    public PagingContext(final SuttonUriInfo uriInfo,
                         final SuttonResponse<R, T> responseBuilder,
                         final String mediaType) {
        this.uriInfo = uriInfo;
        this.responseBuilder = responseBuilder;
        this.mediaType = mediaType;
    }

    public SuttonUriInfo getUriInfo() {
        return this.uriInfo;
    }

    public SuttonResponse<R, T> getResponseBuilder() {
        return this.responseBuilder;
    }

    public String getMediaType() {
        return this.mediaType;
    }

}

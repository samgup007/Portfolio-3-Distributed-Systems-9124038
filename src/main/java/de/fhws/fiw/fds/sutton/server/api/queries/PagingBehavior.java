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

import java.net.URI;
import java.util.Collection;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Hyperlinks;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * The PagingBehavior provides the basic requirements to create different paging mechanisms.
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity encapsulated within the body of the HTTP response.
 */
public abstract class PagingBehavior<R, T extends AbstractModel> {

    /**
     * Returns the offset, where the result's page should start
     *
     * @return {@link Integer} the offset, from where the results page should start
     */
    public abstract int getOffset();

    /**
     * Returns the size of the result's page that should be delivered to the client in the response
     *
     * @return {@link Integer} the size results page
     */
    public abstract int getSize();

    /**
     * Adds a self link header to the response containing the number of the results in the page
     *
     * @param pagingContext the {@link PagingContext} containing the required information to create the self link
     */
    public final void addSelfLink(final PagingContext<R, Collection<T>> pagingContext) {
        Hyperlinks.addLink(pagingContext.getResponseBuilder(),
                getSelfUri(pagingContext.getUriInfo()),
                "self",
                pagingContext.getMediaType());
    }

    /**
     * Adds a link referring to the previous page to the headers in the response <strong>if there is a previous page</strong>
     *
     * @param pagingContext the {@link PagingContext} containing the required information to create the previous page link
     */
    public final void addPrevPageLink(final PagingContext<R, Collection<T>> pagingContext) {
        if (hasPrevLink()) {
            Hyperlinks.addLink(pagingContext.getResponseBuilder(),
                    getPrevUri(pagingContext.getUriInfo()),
                    "prev",
                    pagingContext.getMediaType());
        }
    }

    /**
     * Adds a link referring to the next page to the headers in the response <strong>if there is a next page</strong>
     *
     * @param pagingContext  the {@link PagingContext} containing the required information to create the next page link
     * @param databaseResult the {@link CollectionModelResult} of the full results fetched from the database to check if a next page could be created
     */
    public final void addNextPageLink(final PagingContext<R, Collection<T>> pagingContext,
                                      final CollectionModelResult<T> databaseResult) {
        if (hasNextLink(databaseResult)) {
            Hyperlinks.addLink(pagingContext.getResponseBuilder(),
                    getNextUri(pagingContext.getUriInfo(), databaseResult),
                    "next",
                    pagingContext.getMediaType());
        }
    }

    /**
     * This method returns true if there is a next page to return to the client
     *
     * @param result a {@link CollectionModelResult} with full results fetched from the database
     * @return true if there is a next page
     */
    protected abstract boolean hasNextLink(final CollectionModelResult<T> result);

    /**
     * This method returns true if there is a previous page to the current page
     */
    protected abstract boolean hasPrevLink();

    /**
     * Creates a self URI
     *
     * @param uriInfo a request {@link SuttonUriInfo} to use it to build a self URI
     * @return a self {@link URI}
     */
    protected abstract URI getSelfUri(final SuttonUriInfo uriInfo);

    /**
     * Creates a URI for the previous page
     *
     * @param uriInfo a request {@link SuttonUriInfo} to use it to build a URI for the previous page
     * @return a {@link URI} for the previous page
     */
    protected abstract URI getPrevUri(final SuttonUriInfo uriInfo);

    /**
     * Creates a URI for the next page
     *
     * @param uriInfo a request {@link SuttonUriInfo} to use it to build a URI for the next page
     * @param result  a {@link CollectionModelResult} of the full results fetched from the database
     * @return a {@link URI} for the next page
     */
    protected abstract URI getNextUri(final SuttonUriInfo uriInfo, final CollectionModelResult<T> result);

}

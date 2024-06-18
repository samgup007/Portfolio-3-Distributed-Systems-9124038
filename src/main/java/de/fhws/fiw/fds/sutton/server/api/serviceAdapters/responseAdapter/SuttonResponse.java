package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.cachingAdapter.SuttonCacheController;

import java.net.URI;

/**
 * The {@link SuttonResponse} interface defines the necessary functionality to create
 * and configure various aspects of an HTTP response in a Sutton application. This interface
 * is generic and framework-agnostic, allowing for flexibility in the underlying REST framework
 * used by Sutton.
 *
 * @param <R> The type of the HTTP response object specific to the REST framework in use.
 * @param <T> The type of the entity encapsulated within the body of the HTTP response.
 */
public interface SuttonResponse<R, T> {

    /**
     * Builds the HTTP response after all configurations have been applied.
     *
     * @return An instance of the HTTP response object specific to the REST framework in use.
     */
    R build();

    /**
     * Sets the Location header of the HTTP response.
     *
     * @param location The URI representing the location of the created entity.
     * @return The current instance of {@code SuttonResponse}.
     */
    SuttonResponse<R, T> location(final URI location);

    /**
     * Defines the Cache-Control header of the HTTP response.
     *
     * @param cacheController The {@link SuttonCacheController} object used to configure the Cache-Control header.
     * @return The current instance of {@code SuttonResponse}.
     */
    SuttonResponse<R, T> cacheControl(final SuttonCacheController cacheController);

    /**
     * Sets the ETag header of the HTTP response.
     *
     * @param entityTag The string representing the ETag value.
     * @return The current instance of {@code SuttonResponse}.
     */
    SuttonResponse<R, T> entityTag(final String entityTag);

    /**
     * Creates a hyperlink as a header in the HTTP response.
     *
     * @param uri The URI representing the href of the hyperlink.
     * @param rel The string representing the relation type of the hyperlink.
     * @return The current instance of {@code SuttonResponse}.
     */
    SuttonResponse<R, T> link(final URI uri, final String rel);

    /**
     * Sets the entity to be sent to the client within the body of the HTTP response.
     *
     * @param entity The entity to be sent in the response body.
     * @return The current instance of {@code SuttonResponse}.
     */
    SuttonResponse<R, T> entity(final T entity);

    /**
     * Sets the status code of the HTTP response.
     *
     * @param status The {@link Status} object representing the HTTP status code.
     * @return The current instance of {@code SuttonResponse}.
     */
    SuttonResponse<R, T> status(final Status status);

    /**
     * Creates a header and adds it to the HTTP response.
     *
     * @param headerName  The string representing the name of the header.
     * @param headerValue The object representing the value of the header.
     * @return The current instance of {@code SuttonResponse}.
     */
    SuttonResponse<R, T> header(final String headerName, final Object headerValue);
}

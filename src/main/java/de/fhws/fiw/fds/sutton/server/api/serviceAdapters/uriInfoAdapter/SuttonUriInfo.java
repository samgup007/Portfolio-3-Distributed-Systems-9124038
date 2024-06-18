package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter;

import java.net.URI;
import java.util.Map;

/**
 * The {@link SuttonUriInfo} interface provides functionality to access and manipulate
 * the HTTP request URI within the Sutton framework. It offers methods to create URI templates,
 * retrieve the current request URI, and manipulate URI paths and query parameters.
 */
public interface SuttonUriInfo {

    /**
     * Creates a URI template by replacing the path of the current HTTP request URI with
     * the specified path and optionally the query section if present in the given path string.
     *
     * @param path The string path used to create the URI template.
     * @return A string representing the newly formed URI.
     */
    String getUriTemplate(final String path);

    /**
     * Returns the URI of the current HTTP request.
     *
     * @return The {@link URI} object representing the request URI.
     */
    URI getURI();

    /**
     * Creates a URI string with query parameter placeholders for the specified query parameters.
     * This method is useful for generating URI templates with variable query parameters.
     *
     * @param queryParams The names of the query parameters to replace with template variables.
     * @return A string representing the URI with query parameter placeholders.
     */
    String createURIWithQueryParamTemplates(String... queryParams);

    /**
     * Creates a URI by replacing template variables within the query part of the given URI string
     * with their corresponding values provided in the {@code queryParams} map.
     *
     * @param URI         The string representing a URI.
     * @param queryParams A map containing template variables as keys and their values as values.
     * @return The updated URI with template variables replaced by their corresponding values.
     */
    URI getURI(final String URI, Map<String, ? extends Object> queryParams);

    /**
     * Appends the given ID to the request path and returns the updated URI object.
     *
     * @param id The ID to append to the request path.
     * @return The updated URI object with the ID appended to the path.
     */
    URI appendIdToPath(final long id);

    /**
     * Appends the given URI string to the base URI of the current request.
     * This method also replaces the query parameters of the current request URI
     * with the query parameters of the given URI string if it contains any.
     *
     * @param uri The URI string to append to the base URI.
     * @return A string representing the updated URI.
     */
    String appendToBaseUri(final String uri);

    /**
     * Appends the given URI string to the current HTTP request URI without including
     * the scheme, port, and host parts. This method also replaces the query parameters
     * of the current request URI with the query parameters of the given URI string if it contains any.
     *
     * @param uri The URI string to append.
     * @return A string representing the updated URI without scheme, port, and host.
     */
    String appendToBaseUriWithoutSchemePortHost(final String uri);

    /**
     * Creates a template variable from the given query parameter string.
     *
     * @param queryParam The query parameter string.
     * @return A string representing the template variable.
     */
    default String getQueryParamAsTemplate(final String queryParam) {
        return "{" + queryParam + "}";
    }

    /**
     * Returns the path part of the given path string, which is the part before the '?' mark.
     *
     * @param path The path string.
     * @return The path part before the '?' mark, or the entire path if '?' is not present.
     */
    default String beforeQuestionMark(final String path) {
        if (path.contains("?")) {
            return path.substring(0, path.indexOf("?"));
        } else {
            return path;
        }
    }

    /**
     * Returns the part of the given path string that comes after the '?' mark, if present.
     *
     * @param path The path string.
     * @return The part after the '?' mark, or an empty string if '?' is not present.
     */
    default String afterQuestionMark(final String path) {
        if (path.contains("?")) {
            return path.substring(path.indexOf("?") + 1);
        } else {
            return "";
        }
    }
}

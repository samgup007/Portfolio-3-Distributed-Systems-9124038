package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.ServletRequestAdapter;

/**
 * The {@link SuttonServletRequest} interface defines the required behavior for retrieving
 * information from an HTTP request within the Sutton framework. It provides methods to
 * access various details from a client's HTTP request, such as headers.
 */
public interface SuttonServletRequest {

    /**
     * Retrieves the value of a specified header from the client's HTTP request.
     *
     * @param headerName The name of the header whose value is to be retrieved.
     * @return A string representing the value of the specified header, or {@code null}
     *         if the header is not present in the request.
     */
    String getHeader(final String headerName);
}

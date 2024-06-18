package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.ServletRequestAdapter;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The {@link JerseyServletRequest} class is an implementation of the {@link SuttonServletRequest}
 * interface specifically designed to support the Jersey framework. It wraps an
 * {@link HttpServletRequest} object and utilizes it to provide the functionality required.
 */
public class JerseyServletRequest implements SuttonServletRequest {

    private final HttpServletRequest httpServletRequest;

    /**
     * Constructs a {@link JerseyServletRequest} with the specified {@link HttpServletRequest}.
     *
     * @param httpServletRequest The {@link HttpServletRequest} object that this class wraps.
     */
    public JerseyServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public String getHeader(final String headerName) {
        return this.httpServletRequest.getHeader(headerName);
    }
}

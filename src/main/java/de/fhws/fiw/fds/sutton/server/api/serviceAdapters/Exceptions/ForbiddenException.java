package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;

/**
 * The {@link ForbiddenException} class is a specialized runtime exception that
 * indicates a client is forbidden from accessing a certain endpoint. It extends
 * {@link SuttonWebAppException} with the status attribute set to {@code Status.FORBIDDEN}
 * (HTTP status code 403).
 *
 * This exception is typically used to signal that the server understands the request
 * but refuses to authorize it.
 */
public class ForbiddenException extends SuttonWebAppException {

    /**
     * Constructs a {@link ForbiddenException} with the specified exception message.
     *
     * @param exceptionMessage The detailed message for this exception.
     */
    public ForbiddenException(String exceptionMessage) {
        super(Status.FORBIDDEN, exceptionMessage);
    }

    /**
     * Constructs a {@link ForbiddenException} with a general message and a detailed exception message.
     *
     * @param message          The general message for this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public ForbiddenException(String message, String exceptionMessage) {
        super(message, Status.FORBIDDEN, exceptionMessage);
    }

    /**
     * Constructs a {@link ForbiddenException} with a general message, cause, and a detailed exception message.
     *
     * @param message          The general message for this exception.
     * @param cause            The cause of this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public ForbiddenException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause, Status.FORBIDDEN, exceptionMessage);
    }

    /**
     * Constructs a {@link ForbiddenException} with a cause and a detailed exception message.
     *
     * @param cause            The cause of this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public ForbiddenException(Throwable cause, String exceptionMessage) {
        super(cause, Status.FORBIDDEN, exceptionMessage);
    }
}

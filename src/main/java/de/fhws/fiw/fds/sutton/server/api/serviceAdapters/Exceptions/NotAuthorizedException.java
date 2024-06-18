package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;

/**
 * The {@link NotAuthorizedException} class is a specialized runtime exception that
 * indicates a client is not authorized to access a certain endpoint. It is an instance
 * of {@link SuttonWebAppException} with the status attribute set to {@code Status.UNAUTHORIZED}
 * (HTTP status code 401).
 */
public class NotAuthorizedException extends SuttonWebAppException {

    /**
     * Constructs a {@link NotAuthorizedException} with the specified exception message.
     *
     * @param exceptionMessage The detailed message for this exception.
     */
    public NotAuthorizedException(String exceptionMessage) {
        super(Status.UNAUTHORIZED, exceptionMessage);
    }

    /**
     * Constructs a {@link NotAuthorizedException} with a message and a detailed exception message.
     *
     * @param message          The message for this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public NotAuthorizedException(String message, String exceptionMessage) {
        super(message, Status.UNAUTHORIZED, exceptionMessage);
    }

    /**
     * Constructs a {@link NotAuthorizedException} with a message, cause, and a detailed exception message.
     *
     * @param message          The message for this exception.
     * @param cause            The cause of this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public NotAuthorizedException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause, Status.UNAUTHORIZED, exceptionMessage);
    }

    /**
     * Constructs a {@link NotAuthorizedException} with a cause and a detailed exception message.
     *
     * @param cause            The cause of this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public NotAuthorizedException(Throwable cause, String exceptionMessage) {
        super(cause, Status.UNAUTHORIZED, exceptionMessage);
    }
}

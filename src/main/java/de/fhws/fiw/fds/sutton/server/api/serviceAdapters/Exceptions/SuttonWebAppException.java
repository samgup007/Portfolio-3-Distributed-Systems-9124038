package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;

/**
 * The {@link SuttonWebAppException} class is a custom exception used in Sutton applications
 * to represent specific HTTP error responses. It extends {@link RuntimeException} and
 * encapsulates an HTTP status code along with a detailed exception message.
 */
public class SuttonWebAppException extends RuntimeException {

    private final Status status;

    private final String exceptionMessage;

    /**
     * Constructs a {@link SuttonWebAppException} with the specified HTTP status and exception message.
     *
     * @param status           The HTTP status code associated with this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public SuttonWebAppException(Status status, String exceptionMessage) {
        this.status = status;
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * Constructs a {@link SuttonWebAppException} with a message, specified HTTP status, and exception message.
     *
     * @param message          The message for this exception.
     * @param status           The HTTP status code associated with this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public SuttonWebAppException(String message, Status status, String exceptionMessage) {
        super(message);
        this.status = status;
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * Constructs a {@link SuttonWebAppException} with a message, cause, specified HTTP status, and exception message.
     *
     * @param message          The message for this exception.
     * @param cause            The cause of this exception.
     * @param status           The HTTP status code associated with this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public SuttonWebAppException(String message, Throwable cause, Status status, String exceptionMessage) {
        super(message, cause);
        this.status = status;
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * Constructs a {@link SuttonWebAppException} with a cause, specified HTTP status, and exception message.
     *
     * @param cause            The cause of this exception.
     * @param status           The HTTP status code associated with this exception.
     * @param exceptionMessage The detailed message for this exception.
     */
    public SuttonWebAppException(Throwable cause, Status status, String exceptionMessage) {
        super(cause);
        this.status = status;
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * Retrieves the HTTP status code associated with this exception.
     *
     * @return The HTTP status code.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Retrieves the detailed exception message.
     *
     * @return The exception message.
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }
}

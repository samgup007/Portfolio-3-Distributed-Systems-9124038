package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.requestAdapter;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * The {@link SuttonRequest} interface defines the behavior that the Sutton framework
 * relies on for processing HTTP requests, particularly for evaluating preconditions
 * in the context of performing conditional HTTP requests.
 *
 * This interface is intended to be implemented by classes that handle the logic of
 * determining whether the client's version of a resource matches the current version
 * on the server, which is essential for operations like conditional GET or PUT requests.
 */
public interface SuttonRequest {

    /**
     * Checks whether the client's version of the resource matches the server's version.
     * This method is typically used to implement conditional request processing, such as
     * validating ETags.
     *
     * @param model The resource model instance sent by the client.
     * @return {@code true} if the client knows the current version of the model;
     *         {@code false} otherwise.
     */
    boolean clientKnowsCurrentModel(final AbstractModel model);
}

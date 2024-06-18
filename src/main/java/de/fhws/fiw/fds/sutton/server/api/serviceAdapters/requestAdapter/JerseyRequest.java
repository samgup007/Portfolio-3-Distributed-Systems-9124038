package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.requestAdapter;

import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.ws.rs.core.EntityTag;
import jakarta.ws.rs.core.Request;

/**
 * The {@link JerseyRequest} class is an adapter that implements the {@link SuttonRequest}
 * interface, specifically designed to integrate with the Jersey framework for processing
 * conditional HTTP requests. It utilizes the {@link Request} interface from JAX-RS and its
 * implementation by Jersey to evaluate preconditions based on the client's version of a resource.
 */
public class JerseyRequest implements SuttonRequest {

    private final Request request;

    /**
     * Constructs a {@link JerseyRequest} with the specified JAX-RS {@link Request}.
     *
     * @param request The JAX-RS {@link Request} instance used for precondition evaluation.
     */
    public JerseyRequest(Request request) {
        this.request = request;
    }

    @Override
    public boolean clientKnowsCurrentModel(final AbstractModel model) {
        final String eTagOfModel = EtagGenerator.createEtag(model);
        return this.request.evaluatePreconditions(new EntityTag(eTagOfModel)) != null;
    }
}

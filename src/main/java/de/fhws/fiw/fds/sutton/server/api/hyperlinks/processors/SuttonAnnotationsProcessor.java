package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SecondarySelfLink;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SelfLink;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The {@link SuttonAnnotationsProcessor} class provides a centralized and extensible
 * mechanism for iterating over and processing Sutton link annotations within a resource
 * model class. It maintains a repository of annotation processors, each responsible for
 * processing a specific type of Sutton link annotation.
 */
public class SuttonAnnotationsProcessor {

    private final Map<String, Processor> suttonAnnotations;

    /**
     * Constructs a {@link SuttonAnnotationsProcessor} and initializes it with default
     * processors for known Sutton link annotations.
     *
     * @param uriInfo The {@link SuttonUriInfo} providing the context for hyperlink creation.
     */
    public SuttonAnnotationsProcessor(SuttonUriInfo uriInfo) {
        this.suttonAnnotations = new HashMap<>();
        suttonAnnotations.put(SuttonLink.class.getSimpleName(), new SuttonLinkProcessor(uriInfo));
        suttonAnnotations.put(SelfLink.class.getSimpleName(), new SelfLinkProcessor(uriInfo));
        suttonAnnotations.put(SecondarySelfLink.class.getSimpleName(), new SecondarySelfLinkProcessor(uriInfo));
    }

    /**
     * Adds a new Sutton link annotation processor to the repository.
     *
     * @param annotationName The name of the annotation to be processed.
     * @param processor      The processor responsible for processing the annotation.
     */
    public void addAnnotation(String annotationName, Processor processor) {
        this.suttonAnnotations.put(annotationName, processor);
    }

    /**
     * Processes all Sutton link annotations present on the "Link" fields of the given
     * resource model. It invokes the corresponding processors to inject hyperlinks into
     * these fields.
     *
     * @param model The resource model instance whose "Link" fields are to be processed.
     */
    public void processSuttonAnnotations(final AbstractModel model) {
        Field[] fields = model.getClass().getDeclaredFields();

        List<Field> annotatedFields = Arrays.stream(fields)
                .filter(f -> f.getType().equals(Link.class))
                .toList();

        for (Field link : annotatedFields) {
            suttonAnnotations.forEach((annotation, annotationProcessor) -> {
                annotationProcessor.processAnnotation(link, model);
            });
        }
    }
}

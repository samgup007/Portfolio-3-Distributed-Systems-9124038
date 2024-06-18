package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.Style;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;

/**
 * The {@link Processor} abstract class serves as the foundation for creating processors
 * for Sutton link annotations. It provides the necessary infrastructure to process annotations
 * and inject hyperlinks into resource model classes based on the contextual information
 * from the request URI.
 */
public abstract class Processor {

    /**
     * The {@link SuttonUriInfo} attribute used to retrieve the required contextual
     * information from the request URI to create the {@code href} attribute of the hyperlink.
     */
    protected final SuttonUriInfo uriInfo;

    /**
     * Constructs a new {@link Processor} with the specified {@link SuttonUriInfo}.
     *
     * @param uriInfo The {@link SuttonUriInfo} providing the context for hyperlink creation.
     */
    public Processor(SuttonUriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    /**
     * Processes the annotation on the given field within the specified model.
     * If the annotation is present, the field is further processed.
     *
     * @param field The field representing the "Link" attribute of a resource model class.
     * @param model The resource model instance of {@link AbstractModel} to be processed.
     */
    public void processAnnotation(final Field field, final AbstractModel model) {
        if(!isAnnotationPresent(field)) {
            return;
        }

        processField(field, model);
    }

    /**
     * Encapsulates the logic for processing the annotation on the given field that represents
     * the "Link" attribute within the specified resource model class .
     *
     * @param field The "Link" field annotated with a Sutton link annotation.
     * @param model The resource model instance of {@link AbstractModel} to be processed.
     */
    protected abstract void processField(final Field field, final AbstractModel model);

    /**
     * Checks whether the given field is annotated with a certain Sutton link annotation.
     *
     * @param field The "Link" field of a resource model to be inspected.
     * @return {@code true} if the annotation is present; {@code false} otherwise.
     */
    protected abstract boolean isAnnotationPresent(final Field field);

    /**
     * Creates the {@code href} attribute of the hyperlink based on the specified injection style.
     *
     * @param injectionStyle The injection style from the {@link Style} enum.
     * @param href           The partial {@code href} that should be used to create the final href attribute of the
     *                       hyperlink.
     * @return The complete {@code href} string after applying the injection style.
     */
    protected String createHref(final Style injectionStyle, final String href) {
        if (injectionStyle.equals(Style.ABSOLUTE)) {
            return uriInfo.appendToBaseUri(href);
        }

        if (injectionStyle.equals(Style.RELATIVE_PATH)) {
            return href;
        }

        return uriInfo.appendToBaseUriWithoutSchemePortHost(href);
    }
}

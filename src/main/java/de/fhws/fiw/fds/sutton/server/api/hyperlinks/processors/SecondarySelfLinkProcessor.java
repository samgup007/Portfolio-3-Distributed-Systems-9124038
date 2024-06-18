package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SecondarySelfLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;

/**
 * The {@link SecondarySelfLinkProcessor} class is a subclass of the {@link Processor} class
 * and is responsible for processing the {@link SecondarySelfLink} annotation.
 */
public class SecondarySelfLinkProcessor extends Processor {

    /**
     * Constructs a {@link SecondarySelfLinkProcessor} with the specified {@link SuttonUriInfo}.
     *
     * @param uriInfo The {@link SuttonUriInfo} providing the context for hyperlink creation.
     */
    public SecondarySelfLinkProcessor(SuttonUriInfo uriInfo) {
        super(uriInfo);
    }

    @Override
    protected void processField(Field field, final AbstractModel model) {
        field.setAccessible(true);

        SecondarySelfLink secondarySelfLink = field.getAnnotation(SecondarySelfLink.class);

        if (!hasPrimaryResource(model)) {
            return;
        }

        Link linkToInject = new Link();

        setHref(secondarySelfLink, linkToInject, model);

        linkToInject.setRel("self");

        linkToInject.setType(secondarySelfLink.type());

        try {
            field.set(model, linkToInject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Creates and sets the {@code href} attribute of the hyperlink based on the
     * {@link SecondarySelfLink} annotation and the resource model instance.
     *
     * @param link         The {@link SecondarySelfLink} annotation instance.
     * @param linkToInject The {@link Link} object where the {@code href} will be set.
     * @param model        The resource model instance to be processed.
     * @throws IllegalArgumentException if the primaryPathElement or secondaryPathElement
     *                                  fields are omitted in the {@link SecondarySelfLink} annotation.
     */
    private void setHref(SecondarySelfLink link, Link linkToInject, final AbstractModel model) {
        String primaryResourceName = link.primaryPathElement();
        if(primaryResourceName.isEmpty()) {
            throw new IllegalArgumentException("The primaryPathElement field can't be omitted");
        }
        long primaryId = model.getPrimaryId();
        String secondaryResourceName = link.secondaryPathElement();
        if(secondaryResourceName.isEmpty()) {
            throw new IllegalArgumentException("The secondaryPathElement field can't be omitted");
        }
        long id = model.getId();
        String relativeHref = primaryResourceName + "/" + primaryId + "/" + secondaryResourceName + "/" + id;
        String href = createHref(link.style(), relativeHref);
        linkToInject.setHref(href);
    }

    @Override
    protected boolean isAnnotationPresent(Field field) {
        return field.isAnnotationPresent(SecondarySelfLink.class);
    }

    /**
     * Checks whether the resource model instance has a primary resource associated with it,
     * indicating that it is being used in the context of a secondary resource.
     *
     * @param model The resource model instance to be checked.
     * @return {@code true} if the model has a primary resource; {@code false} otherwise.
     */
    private boolean hasPrimaryResource(final AbstractModel model) {
        return model.getPrimaryId() != 0;
    }
}

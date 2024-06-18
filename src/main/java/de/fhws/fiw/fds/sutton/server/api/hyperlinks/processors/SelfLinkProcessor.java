package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SelfLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;

/**
 * The {@link SelfLinkProcessor} class is a subclass of the {@link Processor} class
 * and is responsible for processing the {@link SelfLink} annotation.
 */
public class SelfLinkProcessor extends Processor {

    /**
     * Constructs a {@link SelfLinkProcessor} with the specified {@link SuttonUriInfo}.
     *
     * @param uriInfo The {@link SuttonUriInfo} providing the context for hyperlink creation.
     */
    public SelfLinkProcessor(SuttonUriInfo uriInfo) {
        super(uriInfo);
    }

    @Override
    protected boolean isAnnotationPresent(final Field field) {
        return field.isAnnotationPresent(SelfLink.class);
    }
    @Override
    protected void processField(final Field field, final AbstractModel model) {
        field.setAccessible(true);

        if(hasPrimaryResource(model)) {
            return;
        }

        SelfLink selfLink = field.getAnnotation(SelfLink.class);

        Link linkToInject = new Link();

        setHref(selfLink, linkToInject, model);

        linkToInject.setRel("self");

        linkToInject.setType(selfLink.type());

        try {
            field.set(model, linkToInject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Creates and sets the {@code href} attribute of the hyperlink based on the
     * {@link SelfLink} annotation and the resource model instance.
     *
     * @param link         The {@link SelfLink} annotation instance.
     * @param linkToInject The {@link Link} object where the {@code href} will be set.
     * @param model        The resource model instance to be processed.
     */
    private void setHref(SelfLink link, Link linkToInject, final AbstractModel model) {
        String relativeHref = link.pathElement() + "/" + model.getId();
        String href = createHref(link.style(), relativeHref);
        linkToInject.setHref(href);
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

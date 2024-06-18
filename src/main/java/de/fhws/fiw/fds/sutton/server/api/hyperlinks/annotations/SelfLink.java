package de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link SelfLink} annotation is used to generate and inject "self" hyperlinks
 * into resource model classes within the Sutton framework. These hyperlinks are intended
 * to reference the resource itself, providing a URI to access it. The annotation
 * can be applied to an instance field of type {@link Link} within a resource model class. If the
 * {@code primaryId} of the model is set then {@link SelfLink} will not inject the hyperlink into the resource.
 * In this case the {@link SecondarySelfLink} should be used.
 *
 * @see SecondarySelfLink
 * @see SuttonLink
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SelfLink {

    /**
     * Describes the resource and is used to construct the {@code href} attribute
     * of the hyperlink. This attribute must be provided to ensure the self link
     * accurately represents the resource's URI.
     */
    String pathElement();

    /**
     * Specifies the media type of the hyperlink.
     * By default, it is set to "application/json".
     */
    String type() default "application/json";

    /**
     * Specifies the style of hyperlink injection.
     * There are three possible styles:
     * <ul>
     *     <li>ABSOLUTE: Includes the protocol, host, port, and context path.</li>
     *     <li>RELATIVE: Injects the href as provided, without additional contextual information.</li>
     *     <li>ABSOLUTE_PATH: Appends the href only to the context path.</li>
     * </ul>
     * By default, it is set to ABSOLUTE.
     */
    Style style() default Style.ABSOLUTE;
}

package de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link SecondarySelfLink} annotation is designed for use within the Sutton framework
 * to dynamically generate and inject "self" hyperlinks into resource model classes when
 * these models are accessed as secondary resources in relation to a primary resource.
 * This annotation ensures that the generated hyperlink accurately reflects the nested
 * URI structure, preserving the context of the primary resource while linking to the
 * secondary entity.
 *
 * If the model is not a secondary resource (i.e., its primaryId is not set), then
 * {@link SecondarySelfLink} will not inject the hyperlink. In such cases, the
 * {@link SelfLink} annotation should be used instead for injecting the "self" hyperlink.
 *
 * @see SelfLink
 * @see SuttonLink
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecondarySelfLink {

    /**
     * Identifies the primary resource within the nested URI structure. This property
     * is necessary to construct the href of the hyperlink, ensuring that the primary
     * resource's context is preserved in the link to the secondary entity.
     */
    String primaryPathElement();

    /**
     * Specifies the secondary resource. This property is utilized to append the
     * secondary resource's identifier to the href, completing the construction of
     * the hyperlink that accurately represents the secondary resource's location
     * within the context of the primary resource.
     */
    String secondaryPathElement();

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

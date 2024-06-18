package de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code SuttonLink} annotation is used to annotate instance fields of type
 * {@link  de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link}
 * within model classes to support dynamic hyperlinks generation and injection.
 * It allows specifying various attributes of the hyperlink such as href, relation type,
 * media type, and injection style. It also allows to define conditions, under which a hyperlink should
 * be injected into the resource class. This could be done by using {@link Condition} or {@link ConditionMethod}
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SuttonLink {

    /**
     * Specifies the href of the hyperlink to be injected.
     */
    String value() default "";

    /**
     * Specifies the relation type of the hyperlink to be injected.
     * By default, it is an empty string.
     */
    String rel() default "";

    /**
     * Specifies the media type of the hyperlink.
     * By default, it is set to "application/json".
     */
    String type() default "application/json";

    /**
     * Specifies the condition under which the hyperlink should be injected.
     */
    Condition condition() default @Condition(field = "", value = "");

    /**
     * Specifies the method to be used to evaluate the condition for hyperlink injection.
     * The method to be used for condition evaluation should have no parameters and should return a boolean value.
     */
    ConditionMethod conditionMethod() default @ConditionMethod(method = "");

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

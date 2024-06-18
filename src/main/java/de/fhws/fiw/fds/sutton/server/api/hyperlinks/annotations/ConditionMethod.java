package de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link ConditionMethod} annotation is designed to be used in conjunction
 * with the {@link SuttonLink} annotation to specify a method within the model class
 * that determines whether a hyperlink should be injected. The specified method is
 * evaluated, and if it returns {@code true}, the link is injected; otherwise, it is not.
 * The method to be evaluated must have no parameters and must return a boolean value.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionMethod {

    /**
     * Specifies the name of the method within the resource model class that should
     * be evaluated to decide whether the link should be injected. The method must
     * return a boolean value and accept no parameters.
     */
    String method();
}

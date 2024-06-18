package de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link Condition} annotation is used in conjunction with the {@link SuttonLink}
 * annotation to specify a condition that should be evaluated to decide whether a hyperlink
 * should be injected into a resource class. It defines the field to be evaluated, the
 * operation for comparison, and the value to compare against.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {

    /**
     * References an instance field from the resource class or its superclass,
     * which will be evaluated as part of the condition. The field must be of a
     * basic Java type such as integer, double, long, float, char, String, or Byte.
     */
    String field();

    /**
     * Specifies the operation used to evaluate the resource instance field against
     * the provided value. The default operation is EQUAL.
     */
    Operation operation() default Operation.EQUAL;

    /**
     * Represents the value against which the 'field' attribute should be evaluated.
     */
    String value();

    /**
     * Enum representing the possible operations for evaluating the condition.
     */
    enum Operation {
        EQUAL, NOT_EQUAL
    }
}

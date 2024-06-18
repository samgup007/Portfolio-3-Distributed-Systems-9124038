package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.ConditionMethod;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * The {@link ConditionMethodProcessor} class is responsible for processing the
 * {@link ConditionMethod} annotation, which is used in conjunction with the
 * {@link SuttonLink} annotation. This processor determines whether a specified
 * condition method within the resource model class evaluates to {@code true},
 * indicating that a hyperlink should be injected into the model.
 */
public class ConditionMethodProcessor {

    private final AbstractModel model;

    private final ConditionMethod conditionMethod;

    /**
     * Constructs a {@link ConditionMethodProcessor} with the specified model and
     * condition method annotation.
     *
     * @param model           The resource model instance to be processed.
     * @param conditionMethod The {@link ConditionMethod} annotation instance
     *                        applied to a "Link" field within the resource model.
     */
    public ConditionMethodProcessor(AbstractModel model, ConditionMethod conditionMethod) {
        this.model = model;
        this.conditionMethod = conditionMethod;
    }

    /**
     * Processes the {@link ConditionMethod} annotation to determine whether the
     * hyperlink should be injected based on the evaluation of the specified condition
     * method within the model.
     *
     * @return {@code true} if the condition method evaluates to {@code true} and the
     *         hyperlink should be injected; {@code false} otherwise.
     * @throws IllegalArgumentException if the method name is omitted, the specified
     *                                  method does not exist, the return type is not
     *                                  boolean, or the method has parameters.
     */
    public boolean processConditionMethod() {
        if (conditionMethod.method().isEmpty()) {
            throw new IllegalArgumentException("Method name can't be omitted");
        }

        Method[] entityDeclairedMethod = model.getClass().getDeclaredMethods();

        Method method = Arrays.stream(entityDeclairedMethod)
                .filter(m -> m.getName().equals(conditionMethod.method()))
                .findFirst()
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(conditionMethod.method() + " isn't a valid method from the "
                            + model.getClass().getSimpleName() + " class");
                });

        Class<?> methodReturnType = method.getReturnType();

        if (!(methodReturnType.getSimpleName().equalsIgnoreCase(Boolean.class.getSimpleName()))) {
            throw new IllegalArgumentException(conditionMethod.method() + " return type must be of type boolean");
        }

        if(method.getParameterCount() != 0) {
            throw new IllegalArgumentException(conditionMethod.method() + " is not allowed to have parameters");
        }

        method.setAccessible(true);

        try {
            return (boolean) method.invoke(model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}

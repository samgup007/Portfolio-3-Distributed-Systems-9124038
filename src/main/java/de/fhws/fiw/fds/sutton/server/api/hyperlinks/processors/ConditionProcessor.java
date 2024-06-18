package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.Condition;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@link ConditionProcessor} class is responsible for processing the {@link Condition}
 * annotation used in conjunction with the {@link SuttonLink} annotation. It evaluates
 * a specified condition to decide whether a hyperlink should be injected into a resource
 * model class.
 *
 * This processor utilizes reflection to dynamically access and evaluate the fields of
 * an {@link AbstractModel} instance based on the criteria defined in the {@link Condition}
 * annotation.
 */
public class ConditionProcessor {

    private final AbstractModel model;

    private final Condition condition;

    /**
     * Constructs a {@link ConditionProcessor} with the specified model and condition.
     *
     * @param model     The model instance to be processed.
     * @param condition The {@link Condition} annotation defining the injection condition.
     */
    public ConditionProcessor(AbstractModel model, Condition condition) {
        this.model = model;
        this.condition = condition;
    }

    /**
     * Processes the {@link Condition} annotation and evaluates the condition.
     *
     * @return {@code true} if the condition is met and the hyperlink should be injected;
     *         {@code false} otherwise.
     */
    public boolean processCondition() {
        evaluatePropertiesExistence();

        Field entityField = getEntityField();

        entityField.setAccessible(true);

        try {
            var fieldValue = entityField.get(model);

            return evaluateCondition(fieldValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Evaluates the condition based on the field value and the specified criteria
     * in the {@code @Condition} annotation.
     *
     * @param fieldValue The value of the field to be evaluated.
     * @return {@code true} if the condition is met; {@code false} otherwise.
     */
    private boolean evaluateCondition(Object fieldValue) {
        if (fieldValue instanceof Integer) {
            Integer value = Integer.valueOf(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        if (fieldValue instanceof Double) {
            Double value = Double.parseDouble(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        if (fieldValue instanceof Long) {
            Long value = Long.parseLong(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        if (fieldValue instanceof Float) {
            Float value = Float.parseFloat(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        if (fieldValue instanceof String) {
            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(condition.value());
        }

        if (fieldValue instanceof Boolean) {
            Boolean value = Boolean.parseBoolean(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        if(fieldValue instanceof Byte) {
            Byte value = Byte.valueOf(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        throw new IllegalArgumentException(condition.value() + " is not a primitive java data type");
    }

    /**
     * Validates the existence of the "field" and "value" attributes in the {@link Condition}
     * annotation.
     * @throws IllegalArgumentException if either attribute is omitted.
     */
    private void evaluatePropertiesExistence() {
        final String conditionFieldAsString = this.condition.field();
        final String conditionValue = this.condition.value();

        if (conditionFieldAsString.isEmpty()) {
            throw new IllegalArgumentException("Field property of the @Condition annotation can't be omitted");
        }

        if (conditionValue.isEmpty()) {
            throw new IllegalArgumentException("Value property of the @Condition annotation can't be omitted");
        }
    }

    /**
     * Retrieves the field specified by the "field" attribute of the {@link Condition}
     * annotation from the model or its superclass.
     *
     * @return The {@link Field} object representing the specified field.
     * @throws IllegalArgumentException if the specified field is not present in the model or its superclass.
     */
    private Field getEntityField() {
        List<Field> entityFields = Arrays.stream(model.getClass().getDeclaredFields())
                .collect(Collectors.toList());

        List<Field> superclassFields = Arrays.stream(model.getClass().getSuperclass().getDeclaredFields())
                .toList();

        entityFields.addAll(superclassFields);

        Optional<Field> result = entityFields.stream()
                .filter(f -> f.getName().equals(this.condition.field()))
                .findFirst();

        return result.orElseThrow(() -> {
            throw new IllegalArgumentException(condition.field() + " is not a valid field of the " +
                    model.getClass().getSimpleName());
        });
    }
}

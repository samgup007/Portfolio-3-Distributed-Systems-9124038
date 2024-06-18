package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.Style;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The {@link SuttonLinkProcessor} class is an instance of the {@link Processor} class
 * that is responsible for processing the {@link SuttonLink} annotation.
 */
public class SuttonLinkProcessor extends Processor {

    /**
     * Constructs a {@link SuttonLinkProcessor} with the specified {@link SuttonUriInfo}.
     *
     * @param uriInfo The {@code SuttonUriInfo} providing the context for hyperlink creation.
     */
    public SuttonLinkProcessor(SuttonUriInfo uriInfo) {
        super(uriInfo);
    }

    @Override
    protected boolean isAnnotationPresent(Field field) {
        return field.isAnnotationPresent(SuttonLink.class);
    }

    @Override
    protected void processField(final Field field, final AbstractModel model) {
        field.setAccessible(true);

        SuttonLink suttonLink = field.getAnnotation(SuttonLink.class);

        if (!suttonLink.conditionMethod().method().isEmpty()) {
            ConditionMethodProcessor processor = new ConditionMethodProcessor(model, suttonLink.conditionMethod());
            boolean isLinkInjectable = processor.processConditionMethod();
            if (!isLinkInjectable) {
                return;
            }
        } else if (!suttonLink.condition().field().isEmpty() && !suttonLink.condition().value().isEmpty()) {
            ConditionProcessor conditionProcessor = new ConditionProcessor(model, suttonLink.condition());
            boolean isLinkInjectable = conditionProcessor.processCondition();
            if (!isLinkInjectable) {
                return;
            }
        }

        Link linkToInject = new Link();

        if (!suttonLink.value().isEmpty()) {
            final String href = processHref(suttonLink.value(), suttonLink.style(), model);
            linkToInject.setHref(href);
        }

        if (!suttonLink.rel().isEmpty()) {
            linkToInject.setRel(suttonLink.rel());
        }

        linkToInject.setType(suttonLink.type());

        try {
            field.set(model, linkToInject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Processes the href attribute given by the {@link SuttonLink} annotation.
     * It extracts template variables within the href, replaces them with values
     * from the resource model instance, and creates the final href of the hyperlink
     * according to the injectionStyle attribute.
     *
     * @param href           The href template provided by the {@code @SuttonLink} annotation.
     * @param injectionStyle The style of hyperlink injection.
     * @param model          The resource model instance to be processed.
     * @return The final href string after processing template variables and applying the injection style.
     */
    private String processHref(final String href, final Style injectionStyle, AbstractModel model) {
        List<String> templateVariables = extractUriTemplateVariables(href);

        if (templateVariables.isEmpty()) {
            return createHref(injectionStyle, href);
        }

        String result = href;

        for (String variable : templateVariables) {
            String field = extractVariable(variable);

            Object fieldValue = getFieldValue(field, model);

            result = result.replace(variable, fieldValue.toString());
        }

        return createHref(injectionStyle, result);
    }

    /**
     * Retrieves the value of a template variable from the resource model instance
     * or its superclass.
     *
     * @param field The name of the field to retrieve its value from the resource model instance in case the field
     *              exists.
     * @param model The resource model instance to be processed.
     * @return The value of the field.
     * @throws RuntimeException if the field is not accessible.
     * @throws IllegalArgumentException if the field is not present in the model or its superclass.
     */
    private Object getFieldValue(final String field, AbstractModel model) {
        List<Field> entityFields = Arrays.stream(model.getClass().getDeclaredFields())
                .collect(Collectors.toList());

        List<Field> superclassFields = Arrays.stream(model.getClass().getSuperclass().getDeclaredFields())
                .toList();

        entityFields.addAll(superclassFields);

        Optional<Field> result = entityFields.stream()
                .filter(f -> f.getName().equals(field))
                .findFirst();

        if (result.isPresent()) {
            Field entityField = result.get();
            entityField.setAccessible(true);
            try {
                return entityField.get(model);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException(field + " is not a valid field of the " +
                    model.getClass().getSimpleName());
        }
    }

    /**
     * Extracts the field name from a template variable. The template variable
     * consists of an opening '{', a closing '}', and the name of the variable
     * is preceded by a '$' sign.
     *
     * @param variableTemplate The template variable to extract the field name from.
     * @return A string representing the field name.
     */
    private String extractVariable(final String variableTemplate) {
        return variableTemplate.substring(2, variableTemplate.length() - 1);
    }

    /**
     * Extracts template variables from the given href, if they exist.
     *
     * @param href The href template to extract variables from.
     * @return A list of strings representing the template variables found in the href.
     */
    private List<String> extractUriTemplateVariables(final String href) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)}");
        Matcher matcher = pattern.matcher(href);

        return matcher.results()
                .map(MatchResult::group)
                .toList();
    }
}

package tech.spring.structure.scaffold.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import tech.spring.structure.auth.evaluator.StructureSecurityExpressionEvaluator;
import tech.spring.structure.scaffold.ScaffoldAuthorize;
import tech.spring.structure.scaffold.ScaffoldIgnore;
import tech.spring.structure.scaffold.ScaffoldProperty;
import tech.spring.structure.scaffold.Scaffolding;
import tech.spring.structure.scaffold.config.ScaffoldConfig;
import tech.spring.structure.scaffold.model.Property;
import tech.spring.structure.scaffold.model.Scaffold;
import tech.spring.structure.scaffold.model.Validation;

@Service
public class ScaffoldService {

    private final static Logger logger = LoggerFactory.getLogger(ScaffoldService.class);

    private final static String JAVAX_VALIDATION_CONSTRAINTS_PACKAGE = "javax.validation.constraints";

    @Autowired
    private ScaffoldConfig scaffoldConfig;

    @Autowired
    private StructureSecurityExpressionEvaluator securityExpressionEvaluator;

    private final List<Scaffold> scaffolding = new ArrayList<Scaffold>();

    public void scanEntities() throws ClassNotFoundException {
        for (String modelPackage : scaffoldConfig.getPackages()) {
            ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
            provider.addIncludeFilter(new AnnotationTypeFilter(Scaffolding.class));
            Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(modelPackage);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                Scaffold scaffold = buildScaffold(clazz);
                Optional<String> authorization = getScaffoldAuthorization(clazz);
                if (authorization.isPresent()) {
                    scaffold.setAuthorization(authorization.get());
                }
                Optional<Scaffolding> scaffolding = getScaffolding(clazz);
                if (scaffolding.isPresent()) {

                }
                this.scaffolding.add(scaffold);
            }
        }
    }

    public Optional<Scaffold> get(HttpServletRequest request, HttpServletResponse response, String model) throws AccessDeniedException {
        Optional<Scaffold> scaffold = findScaffoldByName(model);
        if (scaffold.isPresent()) {
            Optional<String> authorization = Optional.ofNullable(scaffold.get().getAuthorization());
            if (authorization.isPresent()) {
                if (!securityExpressionEvaluator.evaluate(authorization.get(), request, response)) {
                    throw new AccessDeniedException("You are not authorized for " + model + " scaffolding!");
                }
            }
        }
        return scaffold;
    }

    public List<Scaffold> get(HttpServletRequest request, HttpServletResponse response) {
        return scaffolding.stream().filter(scaffold -> {
            Optional<String> authorization = Optional.ofNullable(scaffold.getAuthorization());
            return authorization.isPresent() ? securityExpressionEvaluator.evaluate(authorization.get(), request, response) : true;
        }).collect(Collectors.toList());
    }

    private Optional<Scaffold> findScaffoldByName(String name) {
        return scaffolding.stream().filter(scaffold -> scaffold.getName().equals(name)).findAny();
    }

    private Scaffold buildScaffold(Class<?> clazz) {
        Scaffold scaffold = Scaffold.of(clazz.getSimpleName());
        List<Field> fields = getFields(clazz);
        List<String> order = getPropertyOrder(clazz);
        List<Property> unorderedProperties = getProperties(fields);
        scaffold.setProperties(orderProperties(unorderedProperties, order));
        return scaffold;
    }

    private List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Optional<Class<?>> superClass = Optional.ofNullable(clazz.getSuperclass());
        if (superClass.isPresent()) {
            List<Field> superClassFields = getFields(clazz.getSuperclass());
            superClassFields.addAll(fields);
            return superClassFields;
        }
        return fields;
    }

    private List<String> getPropertyOrder(Class<?> clazz) {
        List<String> order = new ArrayList<String>();
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            if (annotation instanceof Scaffolding) {
                order.addAll(Arrays.asList(((Scaffolding) annotation).order()));
                break;
            }
        }
        return order;
    }

    private List<Property> getProperties(List<Field> fields) {
        List<Property> properties = new ArrayList<Property>();
        for (Field field : fields) {
            Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
            if (!ignoreProperty(field)) {

                String type;
                if (Collection.class.isAssignableFrom(field.getType())) {
                    ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                    Class<?> genericClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    type = genericClass.getSimpleName();

                } else {
                    type = field.getType().getSimpleName();
                }

                Property property = Property.of(field.getName(), type);
                for (Annotation annotation : fieldAnnotations) {
                    if (ClassUtils.getPackageName(annotation.annotationType()).equals(JAVAX_VALIDATION_CONSTRAINTS_PACKAGE)) {
                        if (annotation instanceof AssertFalse) {
                            property.addValidations(getAssertFalseValidations((AssertFalse) annotation));
                        } else if (annotation instanceof AssertTrue) {
                            property.addValidations(getAssertTrueValidations((AssertTrue) annotation));
                        } else if (annotation instanceof DecimalMax) {
                            property.addValidations(getDecimalMaxValidations((DecimalMax) annotation));
                        } else if (annotation instanceof DecimalMin) {
                            property.addValidations(getDecimalMinValidations((DecimalMin) annotation));
                        } else if (annotation instanceof Digits) {
                            property.addValidations(getDigitsValidations((Digits) annotation));
                        } else if (annotation instanceof Future) {
                            property.addValidations(getFutureValidations((Future) annotation));
                        } else if (annotation instanceof Max) {
                            property.addValidations(getMaxValidations((Max) annotation));
                        } else if (annotation instanceof Min) {
                            property.addValidations(getMinValidations((Min) annotation));
                        } else if (annotation instanceof NotNull) {
                            property.addValidations(getNotNullValidations((NotNull) annotation));
                        } else if (annotation instanceof Null) {
                            property.addValidations(getNullValidations((Null) annotation));
                        } else if (annotation instanceof Past) {
                            property.addValidations(getPastValidations((Past) annotation));
                        } else if (annotation instanceof Pattern) {
                            property.addValidations(getPatternValidations((Pattern) annotation));
                        } else if (annotation instanceof Size) {
                            property.addValidations(getSizeValidations((Size) annotation));
                        } else {
                            logger.warn("Unsupported javax validation constraint: " + annotation.annotationType());
                        }
                    } else {
                        if (annotation instanceof ScaffoldProperty) {
                            ScaffoldProperty scaffoldProperty = (ScaffoldProperty) annotation;
                            property.setType(scaffoldProperty.type());
                            property.setHelp(scaffoldProperty.help());
                            property.setAutocomplete(scaffoldProperty.autocomplete());
                            property.setAutofocus(scaffoldProperty.autofocus());
                            property.setHidden(scaffoldProperty.hidden());
                            property.setDisabled(scaffoldProperty.disabled());
                            if (scaffoldProperty.gloss().length() > 0) {
                                property.setGloss(scaffoldProperty.gloss());
                            }
                            if (field.getType().isEnum()) {
                                property.setOptions(Arrays.asList(field.getType().getEnumConstants()));
                            }
                        }
                    }
                }
                properties.add(property);
            }
        }
        return properties;
    }

    private List<Property> orderProperties(List<Property> unorderedProperties, List<String> order) {
        List<Property> orderedProperties = new ArrayList<Property>();
        for (String orderPropertyName : order) {
            for (Property unorderedProperty : unorderedProperties) {
                if (unorderedProperty.getName().equals(orderPropertyName)) {
                    orderedProperties.add(unorderedProperty);
                    unorderedProperties.remove(unorderedProperty);
                    break;
                }
            }
        }
        for (Property remainingUnorderedProperty : unorderedProperties) {
            orderedProperties.add(remainingUnorderedProperty);
        }
        return orderedProperties;
    }

    private Optional<String> getScaffoldAuthorization(Class<?> clazz) {
        Optional<String> authorization = Optional.empty();
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            if (annotation instanceof ScaffoldAuthorize) {
                authorization = Optional.of(((ScaffoldAuthorize) annotation).value());
                break;
            }
        }
        return authorization;
    }

    private Optional<Scaffolding> getScaffolding(Class<?> clazz) {
        Optional<Scaffolding> scaffolding = Optional.empty();
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            if (annotation instanceof Scaffolding) {
                scaffolding = Optional.of(((Scaffolding) annotation));
                break;
            }
        }
        return scaffolding;
    }

    private boolean ignoreProperty(Field field) {
        return field.isSynthetic() || ignore(field.getDeclaredAnnotations());
    }

    private boolean ignore(Annotation[] annotations) {
        boolean ignore = false;
        for (Annotation annotation : annotations) {
            if (annotation instanceof ScaffoldIgnore) {
                ignore = true;
                break;
            }
        }
        return ignore;
    }

    private List<Validation> getAssertFalseValidations(AssertFalse annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must be false!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), "false", message));
        return validations;
    }

    private List<Validation> getAssertTrueValidations(AssertTrue annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must be true!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), "true", message));
        return validations;
    }

    private List<Validation> getDecimalMaxValidations(DecimalMax annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must a decimal no greater than " + annotation.value() + "!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), annotation.value(), message));
        return validations;
    }

    private List<Validation> getDecimalMinValidations(DecimalMin annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must be a decimal at least " + annotation.value() + "!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), annotation.value(), message));
        return validations;
    }

    private List<Validation> getDigitsValidations(Digits annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must be a number!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), message));
        return validations;
    }

    private List<Validation> getFutureValidations(Future annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must be in the future!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), message));
        return validations;
    }

    private List<Validation> getMaxValidations(Max annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must be no greater than " + annotation.value() + " characters!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), annotation.value(), message));
        return validations;
    }

    private List<Validation> getMinValidations(Min annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must be at least " + annotation.value() + " characters!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), annotation.value(), message));
        return validations;
    }

    private List<Validation> getNotNullValidations(NotNull annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must not be null!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), message));
        return validations;
    }

    private List<Validation> getNullValidations(Null annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must be null!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), message));
        return validations;
    }

    private List<Validation> getPastValidations(Past annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must be in the past!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), message));
        return validations;
    }

    private List<Validation> getPatternValidations(Pattern annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String message = getMessage(annotation.message(), "Must match pattern!");
        validations.add(Validation.of(annotation.annotationType().getSimpleName(), annotation.regexp(), message));
        return validations;
    }

    private List<Validation> getSizeValidations(Size annotation) {
        List<Validation> validations = new ArrayList<Validation>();
        String minMessage = getMessage(annotation.message(), "Must be at least " + annotation.min() + " characters!");
        String maxMessage = getMessage(annotation.message(), "Must be no greater than " + annotation.max() + " characters!");
        validations.add(Validation.of("MinLength", annotation.min(), minMessage));
        validations.add(Validation.of("MaxLength", annotation.max(), maxMessage));
        return validations;
    }

    private String getMessage(String annotationMessage, String defaultMessage) {
        return annotationMessage.contains(JAVAX_VALIDATION_CONSTRAINTS_PACKAGE) ? defaultMessage : annotationMessage;
    }

}

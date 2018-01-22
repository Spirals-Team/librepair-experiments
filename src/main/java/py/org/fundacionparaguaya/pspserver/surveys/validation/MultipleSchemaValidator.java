package py.org.fundacionparaguaya.pspserver.surveys.validation;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveySchema;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by rodrigovillalba on 10/26/17.
 */
public interface MultipleSchemaValidator extends MultipleSchemaValidatorFunction<SurveySchema, String, Object, ValidationResults> {

    static MultipleSchemaValidator all(SchemaValidator... validations) {
        return (SurveySchema schema, String name, Object value) -> new ValidationList(Arrays.stream(validations)
                .map(v -> v.apply(schema, name, value))
                .filter(r -> !r.isValid())
                .collect(Collectors.toList()));
    }
}

package py.org.fundacionparaguaya.pspserver.surveys.validation;

import java.util.Optional;

/**
 * Created by rodrigovillalba on 10/24/17.
 */

public interface ValidationResult {
    static ValidationResult valid(){
        return ValidationSupport.validResult();
    }

    static ValidationResult invalid(String reason){
        return new Invalid(reason);
    }

    boolean isValid();

    Optional<String> getReason();

    String getPropertyName();

    static ValidationResult invalid(String propertyName, String reason) {
        return new Invalid(propertyName, reason);
    }
}

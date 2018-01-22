package py.org.fundacionparaguaya.pspserver.surveys.validation;

import java.util.Optional;

/**
 * Created by rodrigovillalba on 10/24/17.
 */
class Invalid implements ValidationResult {

    private final String reason;
    private final String propertyName;

    Invalid(String reason){
        this.reason = reason;
        this.propertyName = null;
    }

    public Invalid(String propertyName, String reason) {
        this.reason = reason;
        this.propertyName = propertyName;
    }

    public boolean isValid(){
        return false;
    }

    public Optional<String> getReason(){
        return Optional.of(reason);
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    // equals and hashCode on field reason
}

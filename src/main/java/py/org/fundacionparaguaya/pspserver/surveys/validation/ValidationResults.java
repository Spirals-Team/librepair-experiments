package py.org.fundacionparaguaya.pspserver.surveys.validation;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigovillalba on 10/26/17.
 *
 * Represents a collection of #ValidationResult.
 *
 */
public interface ValidationResults  {

    /**
     *
     * @return <code>true</code> only if all its results are valid
     */
    boolean isValid();

    Map<String, Collection<String>> asMap();

    List<ValidationResult> asList();

    boolean add(ValidationResult result);

    boolean addAll(ValidationResults results);
}

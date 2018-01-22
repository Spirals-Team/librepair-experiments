package py.org.fundacionparaguaya.pspserver.surveys.validation;

import com.google.common.collect.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rodrigovillalba on 10/26/17.
 */
public class ValidationList extends ForwardingList<ValidationResult> implements ValidationResults {

    private List<ValidationResult> delegate;

    public ValidationList() {
        this.delegate = Lists.newLinkedList();
    }

    public ValidationList(List<ValidationResult> list) {
        this.delegate = list;
    }

    @Override
    public boolean isValid() {
        return delegate
                .stream()
                .filter(r -> !r.isValid())
                .collect(Collectors.toList())
                .isEmpty();
    }

    @Override
    public Map<String, Collection<String>> asMap() {
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        this.delegate.stream()
                .filter(result -> !result.isValid())
                .forEach(result -> multimap.put(result.getPropertyName(), result.getReason().get()));
        return multimap.asMap();
    }

    @Override
    public List<ValidationResult> asList() {
        return delegate;
    }

    @Override
    public boolean addAll(ValidationResults results) {
        return delegate.addAll(results.asList());
    }

    @Override
    protected List<ValidationResult> delegate() {
        return delegate;
    }
}

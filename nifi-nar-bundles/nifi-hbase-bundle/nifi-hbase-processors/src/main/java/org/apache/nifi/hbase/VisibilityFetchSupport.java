package org.apache.nifi.hbase;

import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.components.Validator;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.ProcessContext;

import java.util.ArrayList;
import java.util.List;

public interface VisibilityFetchSupport {
    PropertyDescriptor AUTHORIZATIONS = new PropertyDescriptor.Builder()
        .name("hbase-fetch-row-authorizations")
        .displayName("Authorizations")
        .description("The list of authorizations to pass to the scanner. This will be ignored if cell visibility labels are not in use.")
        .required(false)
        .expressionLanguageSupported(false)
        .addValidator(Validator.VALID)
        .build();

    default List<String> getAuthorizations(ProcessContext context, FlowFile flowFile) {
        final String authorizationString = context.getProperty(AUTHORIZATIONS).evaluateAttributeExpressions(flowFile).getValue().trim();
        List<String> authorizations = new ArrayList<>();
        if (authorizationString != null && !authorizationString.equals("")) {
            String[] parts = authorizationString.split(",");
            for (String part : parts) {
                authorizations.add(part.trim());
            }
        }

        return authorizations;
    }
}

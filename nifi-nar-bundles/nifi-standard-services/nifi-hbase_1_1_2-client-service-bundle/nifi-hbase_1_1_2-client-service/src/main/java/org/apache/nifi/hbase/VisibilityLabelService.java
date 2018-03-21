package org.apache.nifi.hbase;

import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.nifi.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface VisibilityLabelService {
    PropertyDescriptor AUTHORIZATIONS = new PropertyDescriptor.Builder()
        .name("hb-lu-authorizations")
        .displayName("Authorizations")
        .description("The list of authorization tokens to be used with cell visibility if it is enabled. These will be used to " +
                "override the default authorization list for the user accessing HBase.")
        .required(false)
        .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
        .build();

    default List<String> getAuthorizations(ConfigurationContext context) {
        List<String> tokens = new ArrayList<>();
        String authorizationString = context.getProperty(AUTHORIZATIONS).isSet()
                ? context.getProperty(AUTHORIZATIONS).getValue()
                : "";
        if (!StringUtils.isEmpty(authorizationString)) {
            tokens = Arrays.asList(authorizationString.split(",[\\s]*"));
        }

        return tokens;
    }
}

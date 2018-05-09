/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.components.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.nifi.components.ValidationResult;
import org.apache.nifi.controller.service.ControllerServiceProvider;

public class ValidationState {
    private final ValidationStatus status;
    private final Collection<ValidationResult> validationErrors;
    private final ControllerServiceProvider serviceProvider;
    private final List<String> invalidServiceIds;

    public ValidationState(final ValidationStatus status, final Collection<ValidationResult> validationErrors, final ControllerServiceProvider serviceProvider, final List<String> invalidServiceIds) {
        this.status = status;
        this.validationErrors = validationErrors;
        this.serviceProvider = serviceProvider;
        this.invalidServiceIds = invalidServiceIds;
    }

    public ValidationStatus getStatus() {
        // If the status is VALID or VALIDATING, then we are done. However, if the status is INVALID, then we want
        // to check why that is the case. If the only cause of invalidity is that a dependent service is disabled,
        // then we want to check if the service is still disabled. If the service is now enabled, then we can consider
        // the validation status to be valid.
        if (status != ValidationStatus.INVALID) {
            return status;
        }

        if (invalidServiceIds.size() != validationErrors.size()) {
            return status;
        }

        // Check if all services have now been enabled
        final boolean allEnabled = invalidServiceIds.stream().allMatch(serviceId -> serviceProvider.isControllerServiceEnabled(serviceId));
        if (allEnabled) {
            return ValidationStatus.VALID;
        }

        return status;
    }

    public Collection<ValidationResult> getValidationErrors() {
        if (validationErrors.isEmpty() || invalidServiceIds.isEmpty()) {
            return validationErrors;
        }

        // Filter out any Validation Results that are DisabledServiceValidationResult if the corresponding service is now enabled.
        final List<ValidationResult> relevantResults = new ArrayList<>();
        for (final ValidationResult result : validationErrors) {
            if (result instanceof DisabledServiceValidationResult) {
                final String serviceId = ((DisabledServiceValidationResult) result).getControllerServiceIdentifier();
                final boolean enabled = serviceProvider.isControllerServiceEnabled(serviceId);
                if (!enabled) {
                    relevantResults.add(result);
                }
            } else {
                relevantResults.add(result);
            }
        }

        return relevantResults;
    }
}

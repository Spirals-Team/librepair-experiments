/*
 * (C) Copyright 2017 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.handler;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import io.github.bonigarcia.Arguments;
import io.github.bonigarcia.Binary;
import io.github.bonigarcia.Extensions;
import io.github.bonigarcia.Options;

/**
 * Resolver for OperaDriver.
 *
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.2.0
 */
public class OperaDriverHandler extends DriverHandler {

    public OperaDriverHandler() {
        super();
    }

    public OperaDriverHandler(Parameter parameter, ExtensionContext context) {
        super(parameter, context);
    }

    @Override
    public void resolve() {
        try {
            Optional<Object> testInstance = context.getTestInstance();
            Optional<Capabilities> capabilities = annotationsReader
                    .getCapabilities(parameter, testInstance);
            OperaOptions operaOptions = (OperaOptions) getOptions(parameter,
                    testInstance);
            if (capabilities.isPresent()) {
                operaOptions.merge(capabilities.get());
            }
            object = new OperaDriver(operaOptions);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Override
    public MutableCapabilities getOptions(Parameter parameter,
            Optional<Object> testInstance)
            throws IOException, IllegalAccessException {
        OperaOptions operaOptions = new OperaOptions();

        if (parameter != null) {
            // @Arguments
            Arguments arguments = parameter.getAnnotation(Arguments.class);
            if (arguments != null) {
                stream(arguments.value()).forEach(operaOptions::addArguments);
            }

            // @Extensions
            Extensions extensions = parameter.getAnnotation(Extensions.class);
            if (extensions != null) {
                for (String extension : extensions.value()) {
                    operaOptions.addExtensions(getExtension(extension));
                }
            }

            // @Binary
            Binary binary = parameter.getAnnotation(Binary.class);
            if (binary != null) {
                operaOptions.setBinary(binary.value());
            }

            // @Options
            Object optionsFromAnnotatedField = annotationsReader
                    .getOptionsFromAnnotatedField(testInstance, Options.class);
            if (optionsFromAnnotatedField != null) {
                operaOptions = ((OperaOptions) optionsFromAnnotatedField)
                        .merge(operaOptions);
            }
        }

        return operaOptions;
    }

}

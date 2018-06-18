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

import static io.github.bonigarcia.SeleniumJupiter.config;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.BrowsersTemplate.Browser;
import io.github.bonigarcia.DockerBrowser;
import io.github.bonigarcia.SeleniumExtension;
import io.github.bonigarcia.SeleniumJupiterException;

/**
 * Resolver for RemoteWebDriver.
 *
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.2.0
 */
public class RemoteDriverHandler extends DriverHandler {

    private DockerDriverHandler dockerDriverHandler;
    private Browser browser;
    private SeleniumExtension parent;
    private ParameterContext parameterContext;

    public RemoteDriverHandler(Parameter parameter, ExtensionContext context) {
        super(parameter, context);
    }

    public RemoteDriverHandler(Parameter parameter, ExtensionContext context,
            Browser browser) {
        super(parameter, context);
        this.browser = browser;
    }

    @Override
    public void resolve() {
        try {
            Optional<Object> testInstance = context.getTestInstance();
            dockerDriverHandler = new DockerDriverHandler(context, parameter,
                    testInstance, annotationsReader, containerMap,
                    dockerService, selenoidConfig);

            if (browser != null) {
                object = dockerDriverHandler.resolve(browser.toBrowserType(),
                        browser.getVersion());
            } else {
                Optional<DockerBrowser> dockerBrowser = annotationsReader
                        .getDocker(parameter);

                if (dockerBrowser.isPresent()) {
                    object = dockerDriverHandler.resolve(dockerBrowser.get());
                } else {
                    Optional<Capabilities> capabilities = annotationsReader
                            .getCapabilities(parameter, testInstance);
                    Optional<URL> url = annotationsReader.getUrl(parameter,
                            testInstance);

                    if (url.isPresent() && capabilities.isPresent()) {
                        object = resolveRemote(url.get(), capabilities.get());
                    } else {
                        object = resolveGeneric();
                    }
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Override
    public String getName() {
        if (dockerDriverHandler != null) {
            return dockerDriverHandler.getName();
        } else {
            return super.getName();
        }
    }

    private WebDriver resolveRemote(URL url, Capabilities capabilities) {
        return new RemoteWebDriver(url, capabilities);
    }

    private WebDriver resolveGeneric() {
        String defaultBrowser = config().getDefaultBrowser();
        String defaultVersion = config().getDefaultVersion();
        String defaultBrowserFallback = config().getDefaultBrowserFallback();
        String defaultBrowserFallbackVersion = config()
                .getDefaultBrowserFallbackVersion();
        String separator = ",";

        List<String> browserCandidates = new ArrayList<>();
        List<String> versionCandidates = new ArrayList<>();
        browserCandidates.add(defaultBrowser);
        versionCandidates.add(defaultVersion);

        if (defaultBrowserFallback.contains(separator)) {
            browserCandidates
                    .addAll(asList(defaultBrowserFallback.split(separator)));
        }
        if (defaultBrowserFallbackVersion.contains(separator)) {
            versionCandidates.addAll(
                    asList(defaultBrowserFallbackVersion.split(separator)));
        }
        assert browserCandidates.size() == versionCandidates
                .size() : "Number of browser and versions for fallback does not match";

        Iterator<String> browserIterator = browserCandidates.iterator();
        Iterator<String> versionIterator = versionCandidates.iterator();

        do {
            if (!browserIterator.hasNext()) {
                throw new SeleniumJupiterException(
                        "Browser candidate not found");
            }
            String browserCandidate = browserIterator.next();
            String versionCandidate = versionIterator.next();

            Browser candidate = new Browser(browserCandidate, versionCandidate);
            log.debug("Using generic handler, trying with {}",
                    browserCandidate);
            parent.setBrowserList(singletonList(candidate));
            try {
                object = parent.resolveParameter(parameterContext, context);
            } catch (Exception e) {
                log.debug("There was an error with {} {}", browserCandidate,
                        e.getMessage());
                object = null;
            }
        } while (object == null);
        return (WebDriver) object;
    }

    @Override
    public void cleanup() {
        if (dockerDriverHandler != null) {
            dockerDriverHandler.cleanup();
        }
    }

    public void setParent(SeleniumExtension parent) {
        this.parent = parent;
    }

    public void setParameterContext(ParameterContext parameterContext) {
        this.parameterContext = parameterContext;
    }

}

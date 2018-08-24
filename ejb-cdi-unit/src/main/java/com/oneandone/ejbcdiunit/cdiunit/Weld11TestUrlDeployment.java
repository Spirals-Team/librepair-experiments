package com.oneandone.ejbcdiunit.cdiunit;

/*
 *    Copyright 2011 Bryn Cooke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * copied from cdi-unit version 3.1.4
 */

import java.io.IOException;

import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.spi.CDI11Deployment;
import org.jboss.weld.resources.spi.ResourceLoader;

public class Weld11TestUrlDeployment extends WeldTestUrlDeployment implements CDI11Deployment {

    public Weld11TestUrlDeployment(ResourceLoader resourceLoader,
            Bootstrap bootstrap, WeldTestConfig weldTestConfig) throws IOException {
        super(resourceLoader, bootstrap, weldTestConfig);
    }

}

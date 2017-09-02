/*
 *  Copyright 2017 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.swagger.oas.inflector.config;


import io.swagger.oas.models.Operation;

/**
 * Behaviour for instantiating controllers - provide your custom implementation to the Configuration
 * class for hooking into DI frameworks, script engines, etc
 */

public interface ControllerFactory {

    /**
     * Instantiates the provided controller class
     *
     * @param cls the class to instantiate
     * @param operation the operation to instantiate
     * @return an instance of the class
     * @throws IllegalAccessException The class cannot be initialized
     * @throws InstantiationException The class cannot be initialized
     */

    Object instantiateController(Class<? extends Object> cls, Operation operation) throws IllegalAccessException, InstantiationException;
}

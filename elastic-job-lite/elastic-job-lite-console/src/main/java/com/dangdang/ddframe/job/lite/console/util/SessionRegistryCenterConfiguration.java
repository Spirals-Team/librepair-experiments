/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.job.lite.console.util;

import com.dangdang.ddframe.job.lite.console.domain.RegistryCenterConfiguration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionRegistryCenterConfiguration {
    
    private static ThreadLocal<RegistryCenterConfiguration> regCenterConfig = new ThreadLocal<>();
    
    public static RegistryCenterConfiguration getRegistryCenterConfiguration() {
        return regCenterConfig.get();
    }
    
    public static void setRegistryCenterConfiguration(final RegistryCenterConfiguration regCenterConfig) {
        SessionRegistryCenterConfiguration.regCenterConfig.set(regCenterConfig);
    }
    
    public static void clear() {
        regCenterConfig.remove();
    }
}

/*
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
 */
package com.facebook.presto.proxy;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

import static io.airlift.configuration.ConfigBinder.configBinder;
import static io.airlift.http.client.HttpClientBinder.httpClientBinder;
import static io.airlift.jaxrs.JaxrsBinder.jaxrsBinder;

public class ProxyModule
        implements Module
{
    @Override
    public void configure(Binder binder)
    {
        httpClientBinder(binder).bindHttpClient("proxy", ForProxy.class);

        configBinder(binder).bindConfig(ProxyConfig.class);

        jaxrsBinder(binder).bind(ProxyResource.class);

        binder.bind(JsonWebTokenHandler.class).in(Scopes.SINGLETON);
    }
}

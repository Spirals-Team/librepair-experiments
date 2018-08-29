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
package com.alipay.sofa.rpc.local.start;

import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.context.RpcRuntimeContext;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.alipay.sofa.rpc.test.EchoService;
import com.alipay.sofa.rpc.test.EchoServiceImpl;
import com.alipay.sofa.rpc.test.HelloService;
import com.alipay.sofa.rpc.test.HelloServiceImpl;

import java.io.File;

/**
 *
 *
 * @author <a href=mailto:zhanggeng.zg@antfin.com>GengZhang</a>
 */
public class LocalBoltServerMain {

    /**
     * slf4j Logger for this class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(LocalBoltServerMain.class);

    public static void main(String[] args) {

        String file = System.getProperty("user.home") + File.separator
            + "localFileTest" + File.separator + "localRegistry.reg";

        RegistryConfig registryConfig = new RegistryConfig().setProtocol("local")
            .setFile(file);

        ServerConfig serverConfig = new ServerConfig()
            .setPort(22222)
            .setDaemon(false);

        ServerConfig serverConfig2 = new ServerConfig()
            .setPort(22200)
            .setProtocol(RpcConstants.PROTOCOL_TYPE_BOLT)
            .setDaemon(false);

        ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
            .setInterfaceId(HelloService.class.getName())
            .setRef(new HelloServiceImpl())
            .setServer(serverConfig)
            .setRegistry(registryConfig);

        ProviderConfig<EchoService> providerConfig2 = new ProviderConfig<EchoService>()
            .setInterfaceId(EchoService.class.getName())
            .setRef(new EchoServiceImpl())
            .setServer(serverConfig)
            .setRegistry(registryConfig);

        providerConfig.export();
        providerConfig2.export();

        LOGGER.warn("started at pid {}", RpcRuntimeContext.PID);
    }

}

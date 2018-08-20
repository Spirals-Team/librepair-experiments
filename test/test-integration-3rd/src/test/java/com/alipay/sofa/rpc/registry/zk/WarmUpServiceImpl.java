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
package com.alipay.sofa.rpc.registry.zk;

/**
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei.Liengen</a>
 * @version $Id: WamUpServiceImpl.java, v 0.1 2018年04月23日 上午11:06 LiWei.Liengen Exp $
 */
public class WarmUpServiceImpl implements WarmUpService {

    private final int port;

    public WarmUpServiceImpl(int port) {
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
    }
}
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
package com.alipay.sofa.rpc.protocol;

import com.alipay.sofa.rpc.common.annotation.Unstable;
import com.alipay.sofa.rpc.ext.Extensible;

/**
 * 协议：包括基本信息，协商接口，编码器，解码器
 *
 * @author <a href=mailto:zhanggeng.zg@antfin.com>GengZhang</a>
 */
@Extensible(coded = true)
@Unstable
public interface Protocol {

    /**
     * 协议基本信息
     *
     * @return ProtocolInfo
     */
    public ProtocolInfo protocolInfo();

    /**
     * 协议编码器
     *
     * @return ProtocolEncoder
     */
    public ProtocolEncoder encoder();

    /**
     * 协议解码器
     *
     * @return ProtocolEncoder
     */
    public ProtocolDecoder decoder();

    /**
     * 协议协商器
     *
     * @return ProtocolEncoder
     */
    public ProtocolNegotiator negotiator();
}

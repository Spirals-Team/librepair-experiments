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
package com.alipay.sofa.rpc.codec.protobuf;

import com.alipay.sofa.rpc.codec.Serializer;
import com.alipay.sofa.rpc.common.RemotingConstants;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.common.utils.CodecUtils;
import com.alipay.sofa.rpc.config.ConfigUniqueNameGenerator;
import com.alipay.sofa.rpc.context.RpcInternalContext;
import com.alipay.sofa.rpc.core.exception.RpcErrorType;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.transport.AbstractByteBuf;
import com.alipay.sofa.rpc.transport.ByteArrayWrapperByteBuf;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Protobuf serializer.
 *
 * @author <a href=mailto:zhanggeng.zg@antfin.com>GengZhang</a>
 */
@Extension(value = "protobuf", code = 11)
public class ProtobufSerializer implements Serializer {

    private ProtobufHelper      protobufHelper     = new ProtobufHelper();

    /**
     * Encode method name
     */
    private static final String METHOD_TOBYTEARRAY = "toByteArray";
    /**
     * Decode method name
     */
    private static final String METHOD_PARSEFROM   = "parseFrom";

    @Override
    public AbstractByteBuf encode(Object object, Map<String, String> context) throws SofaRpcException {
        if (object == null) {
            throw buildSerializeError("Unsupported null message!");
        } else if (object instanceof SofaRequest) {
            return encodeSofaRequest((SofaRequest) object, context);
        } else if (object instanceof SofaResponse) {
            return encodeSofaResponse((SofaResponse) object, context);
        } else if (protobufHelper.isProtoBufMessageObject(object)) {
            Class clazz = object.getClass();
            Method method = protobufHelper.toByteArrayMethodMap.get(clazz);
            if (method == null) {
                try {
                    method = clazz.getMethod(METHOD_TOBYTEARRAY);
                    method.setAccessible(true);
                    protobufHelper.toByteArrayMethodMap.put(clazz, method);
                } catch (Exception e) {
                    throw buildSerializeError("Cannot found method " + clazz.getName()
                        + ".toByteArray(), please check the generated code.", e);
                }
            }
            try {
                return new ByteArrayWrapperByteBuf((byte[]) method.invoke(object));
            } catch (Exception e) {
                throw buildSerializeError("Error when invoke " + clazz.getName() + ".toByteArray().", e);
            }
        } else if (object instanceof String) {
            return new ByteArrayWrapperByteBuf(((String) object).getBytes(RpcConstants.DEFAULT_CHARSET));
        } else {
            throw buildSerializeError("Unsupported class:" + object.getClass().getName()
                + ", only support protobuf message");
        }
    }

    private AbstractByteBuf encodeSofaResponse(SofaResponse sofaResponse, Map<String, String> context)
        throws SofaRpcException {
        AbstractByteBuf byteBuf;
        if (sofaResponse.isError()) {
            // 框架异常：错误则body序列化的是错误字符串
            byteBuf = encode(sofaResponse.getErrorMsg(), context);
        } else {
            // 正确返回则解析序列化的protobuf返回对象
            Object appResponse = sofaResponse.getAppResponse();
            if (appResponse instanceof Throwable) {
                // 业务异常序列化的是错误字符串
                byteBuf = encode(((Throwable) appResponse).getMessage(), context);
            } else {
                byteBuf = encode(appResponse, context);
            }
        }
        return byteBuf;
    }

    private AbstractByteBuf encodeSofaRequest(SofaRequest sofaRequest, Map<String, String> context)
        throws SofaRpcException {
        Object[] args = sofaRequest.getMethodArgs();
        if (args.length > 1) {
            throw buildSerializeError("Protobuf only support one parameter!");
        }
        return encode(args[0], context);
    }

    private SofaRpcException buildSerializeError(String message) {
        boolean isProvider = RpcInternalContext.getContext().isProviderSide();
        return new SofaRpcException(isProvider ? RpcErrorType.SERVER_SERIALIZE : RpcErrorType.CLIENT_SERIALIZE, message);
    }

    private SofaRpcException buildSerializeError(String message, Throwable throwable) {
        boolean isProvider = RpcInternalContext.getContext().isProviderSide();
        return new SofaRpcException(isProvider ? RpcErrorType.SERVER_SERIALIZE : RpcErrorType.CLIENT_SERIALIZE,
            message, throwable);
    }

    @Override
    public Object decode(AbstractByteBuf data, Class clazz, Map<String, String> context) throws SofaRpcException {
        if (clazz == null) {
            throw buildDeserializeError("class is null!");
        } else if (protobufHelper.isProtoBufMessageClass(clazz)) {
            if (data == null || data.readableBytes() == 0) {
                try {
                    Constructor constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    return constructor.newInstance();
                } catch (Exception e) {
                    throw buildDeserializeError("Error when invoke " + clazz.getName() + ".newInstance().", e);
                }
            } else {
                Method method = protobufHelper.parseFromMethodMap.get(clazz);
                if (method == null) {
                    try {
                        method = clazz.getMethod(METHOD_PARSEFROM, byte[].class);
                        if (!Modifier.isStatic(method.getModifiers())) {
                            throw buildDeserializeError("Cannot found static method " + clazz.getName()
                                + ".parseFrom(byte[]), please check the generated code");
                        }
                        method.setAccessible(true);
                        protobufHelper.parseFromMethodMap.put(clazz, method);
                    } catch (NoSuchMethodException e) {
                        throw buildDeserializeError("Cannot found method " + clazz.getName()
                            + ".parseFrom(byte[]), please check the generated code", e);
                    }
                }
                try {
                    return method.invoke(null, data.array());
                } catch (Exception e) {
                    throw buildDeserializeError("Error when invoke " + clazz.getName() + ".parseFrom(byte[]).", e);
                }
            }
        } else if (clazz == String.class) {
            return new String(data.array(), RpcConstants.DEFAULT_CHARSET);
        } else {
            throw buildDeserializeError("Unsupported class:" + clazz.getName()
                + ", only support protobuf message");
        }
    }

    @Override
    public Object decode(AbstractByteBuf data, Object template, Map<String, String> context) throws SofaRpcException {
        if (template instanceof SofaRequest) {
            return decodeSofaRequest(data, (SofaRequest) template, context);
        } else if (template instanceof SofaResponse) {
            return decodeSofaResponse(data, (SofaResponse) template, context);
        } else {
            throw buildDeserializeError("Unsupported class:" + template
                + ", only support SofaRequest&SofaResponse");
        }
    }

    private SofaResponse decodeSofaResponse(AbstractByteBuf data, SofaResponse sofaResponse,
                                            Map<String, String> header) {
        boolean isError = false;
        if (header != null) {
            if ("true".equals(header.remove(RemotingConstants.HEAD_RESPONSE_ERROR))) {
                isError = true;
            }
            if (!header.isEmpty()) {
                sofaResponse.setResponseProps(header);
            }
        }
        if (isError) {
            String errorMessage = (String) decode(data, String.class, header);
            sofaResponse.setErrorMsg(errorMessage);
        } else {
            // 根据接口+方法名找到参数类型
            Class responseClass = protobufHelper.getResClass(header.get(RemotingConstants.HEAD_TARGET_SERVICE),
                header.get(RemotingConstants.HEAD_METHOD_NAME));
            try {
                if (data == null || data.readableBytes() == 0) {
                    Constructor constructor = responseClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    sofaResponse.setAppResponse(constructor.newInstance());
                } else {
                    Object pbRes = decode(data, responseClass, header);
                    sofaResponse.setAppResponse(pbRes);
                }
            } catch (Exception e) {
                throw buildDeserializeError("", e);
            }
        }
        return sofaResponse;
    }

    private SofaRequest decodeSofaRequest(AbstractByteBuf data, SofaRequest sofaRequest, Map<String, String> headerMap) {

        // 解析request信息
        sofaRequest.setMethodName(headerMap.remove(RemotingConstants.HEAD_METHOD_NAME));
        sofaRequest.setTargetAppName(headerMap.remove(RemotingConstants.HEAD_TARGET_APP));
        String targetServiceName = headerMap.remove(RemotingConstants.HEAD_TARGET_SERVICE);
        String interfaceName = ConfigUniqueNameGenerator.getInterfaceName(targetServiceName);
        sofaRequest.setTargetServiceUniqueName(targetServiceName);
        sofaRequest.setInterfaceName(interfaceName);

        // 解析trace信息
        Map<String, String> traceMap = new HashMap<String, String>(16);
        CodecUtils.treeCopyTo(RemotingConstants.RPC_TRACE_NAME + ".", headerMap, traceMap, true);
        sofaRequest.addRequestProp(RemotingConstants.RPC_TRACE_NAME, traceMap);
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            sofaRequest.addRequestProp(entry.getKey(), entry.getValue());
        }

        // 根据接口+方法名找到参数类型 此处要处理byte[]为空的吗
        Class requestClass = protobufHelper.getReqClass(sofaRequest.getTargetServiceUniqueName(),
            sofaRequest.getMethodName());
        Object pbReq = decode(data, requestClass, headerMap);
        sofaRequest.setMethodArgs(new Object[] { pbReq });
        sofaRequest.setMethodArgSigs(new String[] { requestClass.getName() });
        return sofaRequest;
    }

    private SofaRpcException buildDeserializeError(String message) {
        boolean isProvider = RpcInternalContext.getContext().isProviderSide();
        return new SofaRpcException(isProvider ? RpcErrorType.SERVER_DESERIALIZE : RpcErrorType.CLIENT_DESERIALIZE,
            message);
    }

    private SofaRpcException buildDeserializeError(String message, Throwable throwable) {
        boolean isProvider = RpcInternalContext.getContext().isProviderSide();
        return new SofaRpcException(isProvider ? RpcErrorType.SERVER_DESERIALIZE : RpcErrorType.CLIENT_DESERIALIZE,
            message, throwable);
    }
}

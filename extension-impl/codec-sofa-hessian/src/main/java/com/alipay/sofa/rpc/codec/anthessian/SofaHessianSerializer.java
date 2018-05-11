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
package com.alipay.sofa.rpc.codec.anthessian;

import com.alipay.hessian.ClassNameResolver;
import com.alipay.hessian.NameBlackListFilter;
import com.alipay.hessian.generic.model.GenericObject;
import com.alipay.sofa.rpc.codec.AbstractSerializer;
import com.alipay.sofa.rpc.common.RemotingConstants;
import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcOptions;
import com.alipay.sofa.rpc.common.SofaConfigs;
import com.alipay.sofa.rpc.common.SofaOptions;
import com.alipay.sofa.rpc.common.struct.UnsafeByteArrayInputStream;
import com.alipay.sofa.rpc.common.struct.UnsafeByteArrayOutputStream;
import com.alipay.sofa.rpc.common.utils.ClassTypeUtils;
import com.alipay.sofa.rpc.common.utils.ClassUtils;
import com.alipay.sofa.rpc.config.ConfigUniqueNameGenerator;
import com.alipay.sofa.rpc.context.RpcInternalContext;
import com.alipay.sofa.rpc.core.exception.RpcErrorType;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.exception.SofaRpcRuntimeException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.log.LogCodes;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.alipay.sofa.rpc.transport.AbstractByteBuf;
import com.alipay.sofa.rpc.transport.ByteArrayWrapperByteBuf;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author <a href="mailto:zhanggeng.zg@antfin.com">GengZhang</a>
 */
@Extension(value = "anthessian", code = 1)
public class SofaHessianSerializer extends AbstractSerializer {

    /**
     * Logger for SofaHessianSerializer
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SofaHessianSerializer.class);

    protected SerializerFactory serializerFactory;
    protected SerializerFactory genericSerializerFactory;

    public SofaHessianSerializer() {
        if (RpcConfigs.getBooleanValue(RpcOptions.MULTIPLE_CLASSLOADER_ENABLE)) {
            serializerFactory = new MultipleClassLoaderSofaSerializerFactory();
            genericSerializerFactory = new GenericMultipleClassLoaderSofaSerializerFactory();
        } else {
            serializerFactory = new SingleClassLoaderSofaSerializerFactory();
            genericSerializerFactory = new GenericSingleClassLoaderSofaSerializerFactory();
        }
        if (RpcConfigs.getBooleanValue(RpcOptions.SERIALIZE_BLACKLIST_ENABLE) &&
            SofaConfigs.getBooleanValue(SofaOptions.CONFIG_SERIALIZE_BLACKLIST, true)) {
            ClassNameResolver resolver = new ClassNameResolver();
            resolver.addFilter(new NameBlackListFilter(BlackListFileLoader.SOFA_SERIALIZE_BLACK_LIST, 8192));
            serializerFactory.setClassNameResolver(resolver);
            genericSerializerFactory.setClassNameResolver(resolver);
        }
    }

    @Override
    public AbstractByteBuf encode(Object object, Map<String, String> context) {

        if (object instanceof SofaRequest) {
            return encodeSofaRequest((SofaRequest) object, context);
        } else if (object instanceof SofaResponse) {
            return encodeSofaResponse((SofaResponse) object, context);
        } else {
            // TODO
            return null;
        }
    }

    private AbstractByteBuf encodeSofaRequest(SofaRequest sofaRequest, Map<String, String> context) {
        try {
            UnsafeByteArrayOutputStream byteArray = new UnsafeByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(byteArray);

            // 根据SerializeType信息决定序列化器
            boolean genericSerialize = genericSerializeRequest(sofaRequest.getSerializeFactoryType());
            if (genericSerialize) {
                output.setSerializerFactory(genericSerializerFactory);
            } else {
                output.setSerializerFactory(serializerFactory);
            }

            output.writeObject(sofaRequest);
            final Object[] args = sofaRequest.getMethodArgs();
            if (args != null) {
                for (Object arg : args) {
                    output.writeObject(arg);
                }
            }
            output.close();

            return new ByteArrayWrapperByteBuf(byteArray.toByteArray());
        } catch (IOException ex) {
            throw buildSerializeError(ex.getMessage(), ex);
        }
    }

    private AbstractByteBuf encodeSofaResponse(SofaResponse response, Map<String, String> context) {
        try {
            UnsafeByteArrayOutputStream byteArray = new UnsafeByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(byteArray);
            output.setSerializerFactory(serializerFactory);
            output.writeObject(response);
            output.close();

            return new ByteArrayWrapperByteBuf(byteArray.toByteArray());
        } catch (IOException ex) {
            throw buildSerializeError(ex.getMessage(), ex);
        }
    }

    private SofaRpcException buildSerializeError(String message, Map<String, String> context) {
        boolean isProvider = RpcInternalContext.getContext().isProviderSide();
        return new SofaRpcException(isProvider ? RpcErrorType.SERVER_SERIALIZE : RpcErrorType.CLIENT_SERIALIZE, message);
    }

    private SofaRpcException buildSerializeError(String message, Throwable throwable) {
        boolean isProvider = RpcInternalContext.getContext().isProviderSide();
        return new SofaRpcException(isProvider ? RpcErrorType.SERVER_SERIALIZE : RpcErrorType.CLIENT_SERIALIZE,
            message, throwable);
    }

    protected boolean genericSerializeRequest(Integer serializeType) {
        return serializeType != null && serializeType != RemotingConstants.SERIALIZE_FACTORY_NORMAL;
    }

    @Override
    public Object decode(AbstractByteBuf data, Class clazz, Map<String, String> context) throws SofaRpcException {
        if (SofaRequest.class.equals(clazz)) {
            return decodeSofaRequest(data, context);
        } else if (SofaResponse.class.equals(clazz)) {
            return decodeSofaResponse(data, context);
        } else {
            // TODO
            return null;
        }
    }

    @Override
    public Object decode(AbstractByteBuf data, Object template, Map<String, String> context) throws SofaRpcException {
        return decode(data, template.getClass(), context);
    }

    private SofaResponse decodeSofaResponse(AbstractByteBuf data, Map<String, String> context) throws SofaRpcException {
        try {
            UnsafeByteArrayInputStream input = new UnsafeByteArrayInputStream(data.array());
            Hessian2Input hessianInput = new Hessian2Input(input);
            // 根据SerializeType信息决定序列化器
            Object object;
            boolean genericSerialize = genericSerializeResponse(context
                .get(RemotingConstants.INVOKE_CTX_SERIALIZE_FACTORY_TYPE));
            if (genericSerialize) {
                hessianInput.setSerializerFactory(genericSerializerFactory);
                GenericObject genericObject = (GenericObject) hessianInput.readObject();
                SofaResponse sofaResponse = new SofaResponse();
                sofaResponse.setErrorMsg((String) genericObject.getField("errorMsg"));
                sofaResponse.setAppResponse(genericObject.getField("appResponse"));
                object = sofaResponse;
            } else {
                hessianInput.setSerializerFactory(serializerFactory);
                object = hessianInput.readObject();
            }
            hessianInput.close();
            return (SofaResponse) object;
        } catch (IOException ex) {
            throw buildDeserializeError(ex.getMessage(), ex);
        }
    }

    private SofaRequest decodeSofaRequest(AbstractByteBuf data, Map<String, String> context) throws SofaRpcException {
        try {
            UnsafeByteArrayInputStream input = new UnsafeByteArrayInputStream(data.array());
            Hessian2Input hessianInput = new Hessian2Input(input);
            hessianInput.setSerializerFactory(serializerFactory);
            Object object = hessianInput.readObject();
            if (object instanceof SofaRequest) {
                final SofaRequest sofaRequest = (SofaRequest) object;
                String targetServiceName = sofaRequest.getTargetServiceUniqueName();
                if (targetServiceName == null) {
                    throw buildDeserializeError("target service name is null!");
                }
                String interfaceName = ConfigUniqueNameGenerator.getInterfaceName(targetServiceName);
                sofaRequest.setInterfaceName(interfaceName);

                String[] sig = sofaRequest.getMethodArgSigs();
                Class<?>[] classSig = new Class[sig.length];
                generateArgTypes(sig, classSig);

                final Object[] args = new Object[sig.length];
                for (int i = 0; i < sofaRequest.getMethodArgSigs().length; ++i) {
                    args[i] = hessianInput.readObject(classSig[i]);
                }
                sofaRequest.setMethodArgs(args);
                hessianInput.close();
                return sofaRequest;
            }
            return null;
        } catch (IOException ex) {
            throw buildDeserializeError(ex.getMessage(), ex);
        }
    }

    private boolean genericSerializeResponse(String serializeType) {
        return serializeType != null && serializeType.equals(RemotingConstants.SERIALIZE_FACTORY_GENERIC + "");
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

    protected void generateArgTypes(final String[] sig, final Class[] classSig) throws IOException {
        for (int x = 0; x < sig.length; x++) {
            String name = ClassTypeUtils.canonicalNameToJvmName(sig[x]);
            Class<?> signature = getPrimitiveType(name);
            if (signature != null) {
                classSig[x] = signature;
            } else {
                try {
                    signature = getJdkType(name);
                } catch (ClassNotFoundException e1) {
                    signature = null;
                }
                if (signature != null) {
                    classSig[x] = signature;
                } else {
                    try {
                        classSig[x] = ClassUtils.forName(name, true);
                    } catch (SofaRpcRuntimeException e) {
                        LOGGER.error(LogCodes.getLog(LogCodes.ERROR_DECODE_REQ_SIG_CLASS_NOT_FOUND, name), e);
                        throw new IOException(LogCodes.getLog(LogCodes.ERROR_DECODE_REQ_SIG_CLASS_NOT_FOUND, name));
                    }
                }
                if (classSig[x] == null) {
                    throw new IOException(LogCodes.getLog(LogCodes.ERROR_DECODE_REQ_SIG_CLASS_NOT_FOUND, sig[x]));
                }

            }
        }
    }

    private Class<?> getPrimitiveType(String name) {
        if (null != name) {
            if ("byte".equals(name)) {
                return Byte.TYPE;
            }
            if ("short".equals(name)) {
                return Short.TYPE;
            }
            if ("int".equals(name)) {
                return Integer.TYPE;
            }
            if ("long".equals(name)) {
                return Long.TYPE;
            }
            if ("char".equals(name)) {
                return Character.TYPE;
            }
            if ("float".equals(name)) {
                return Float.TYPE;
            }
            if ("double".equals(name)) {
                return Double.TYPE;
            }
            if ("boolean".equals(name)) {
                return Boolean.TYPE;
            }
            if ("void".equals(name)) {
                return Void.TYPE;
            }
        }
        return null;
    }

    private Class<?> getJdkType(String name) throws ClassNotFoundException {
        if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("sun.")) {
            return Class.forName(name);
        }
        return null;
    }
}

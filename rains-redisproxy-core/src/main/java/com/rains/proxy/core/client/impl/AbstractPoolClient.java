/**
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
package com.rains.proxy.core.client.impl;



import com.rains.proxy.core.client.Client;
import com.rains.proxy.core.command.impl.RedisCommand;
import com.rains.proxy.core.config.RedisProxyPoolConfig;
import com.rains.proxy.core.connection.IConnection;
import com.rains.proxy.core.exception.RedisException;
import com.rains.proxy.core.log.impl.LoggerUtils;
import com.rains.proxy.core.pool.IPoolEntry;
import com.rains.proxy.core.pool.PooledFactory;
import com.rains.proxy.core.pool.IRedisProxyPool;
import com.rains.proxy.core.pool.exception.RedisProxyPoolException;
import com.rains.proxy.core.pool.util.PoolUtils;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author dourx
 * @version V1.0
 * 创建日期 2018/6/4
 */
public abstract class AbstractPoolClient implements Client {
	
	protected IRedisProxyPool<IConnection> pool;
    protected RedisProxyPoolConfig redisProxyPoolConfig;
    protected PooledFactory<IConnection> factory;

	/**
	 * 
	 */
	public AbstractPoolClient(RedisProxyPoolConfig redisProxyPoolConfig) {
		super();
		this.redisProxyPoolConfig =redisProxyPoolConfig;
	}
    
	protected void initPool() {
		try{
            factory = createChannelFactory();
            pool = PoolUtils.createPool(redisProxyPoolConfig, factory);
		}catch(Exception e){
			LoggerUtils.error("initPool fail,reason:{},message:{}",e.getCause(),e.getMessage(), e);
		}
	}
	
	/**
	 * 创建一个工厂类
	 * @return
	 */
    protected abstract PooledFactory<IConnection> createChannelFactory();
    
    public abstract void write(RedisCommand request, ChannelHandlerContext frontCtx);
    
    protected IPoolEntry<IConnection> borrowObject() throws Exception {
        if(pool==null){
            LoggerUtils.error("borrowObject Error : pool is null ");
            throw new RedisException("borrowObject Error : pool is null ");
        }

    	IPoolEntry<IConnection> nettyChannelEntry=pool.borrowEntry();
        if (nettyChannelEntry != null&&nettyChannelEntry.getObject()!=null) {
            return nettyChannelEntry;
        }
        
        String errorMsg = this.getClass().getSimpleName() + " borrowObject Error";
        LoggerUtils.error(errorMsg);
        throw new RedisException(errorMsg);
    }


    protected void returnObject(IPoolEntry<IConnection> entry) {
        if (entry == null) {
            return;
        }
        try {
        	pool.returnEntry(entry);
        } catch (RedisProxyPoolException ie) {
        	LoggerUtils.error( "{} return client Error" ,this.getClass().getSimpleName() , ie);
        }
    }

}

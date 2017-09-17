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

package com.dangdang.ddframe.rdb.sharding.reg.base;

/**
 * Registry center.
 * 
 * @author zhangliang
 */
public interface RegistryCenter {
    
    /**
     * Initialize registry center.
     */
    void init();
    
    /**
     * Close registry center.
     */
    void close();
    
    /**
     * Get data from registry center.
     * 
     * @param key key of data
     * @return value of data
     */
    String get(String key);
    
    /**
     * Adjust data is existed or not.
     * 
     * @param key key of data
     * @return data is existed or not
     */
    boolean isExisted(String key);
    
    /**
     * Persist data.
     * 
     * @param key key of data
     * @param value value of data
     */
    void persist(String key, String value);
    
    /**
     * Update data.
     *
     * @param key key of data
     * @param value value of data
     */
    void update(String key, String value);
    
    /**
     * Remove data.
     *
     * @param key key of data
     */
    void remove(String key);
    
    /**
     * Get registry center's raw client.
     * 
     * <p>Such as: zookeeper or redis client.</p>
     * 
     * @return raw client of registry center
     */
    Object getRawClient();
}

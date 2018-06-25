/*
 * Copyright 2016 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.common.util.crypt;

import io.apiman.common.util.AesEncrypter;

import java.util.Map;

/**
 * A default data encrypter for the gateway.
 *
 * @author eric.wittmann@redhat.com
 * @author Rachel Yordán <ryordan@redhat.com>
 */
public class AesDataEncrypter implements IDataEncrypter {
    
    private String secretKey;

    /**
     * Constructor.
     * @param config
     */
    public AesDataEncrypter(Map<String, String> config) {
        secretKey = config.get("secretKey"); //$NON-NLS-1$
        
        if (secretKey == null) {
            throw new RuntimeException("Missing configuration property: apiman-manager.config.secretKey"); //$NON-NLS-1$
        }
    }
    
    /**
     * @see io.apiman.common.util.crypt.IDataEncrypter#encrypt(java.lang.String, io.apiman.common.util.crypt.DataEncryptionContext)
     */
    @Override
    public String encrypt(String plainText, DataEncryptionContext context) {
        return AesEncrypter.encrypt(secretKey, plainText);
    }
    
    /**
     * @see io.apiman.common.util.crypt.IDataEncrypter#decrypt(java.lang.String, io.apiman.common.util.crypt.DataEncryptionContext)
     */
    @Override
    public String decrypt(String encryptedText, DataEncryptionContext context) {
        return AesEncrypter.decrypt(secretKey, encryptedText);
    }
}

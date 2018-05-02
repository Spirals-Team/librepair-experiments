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
package org.apache.nifi.processors.solr.kerberos;

import org.apache.commons.lang3.StringUtils;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom JAAS Configuration object for a provided principal and keytab.
 */
public class KerberosKeytabConfiguration extends Configuration {

    public static final String APP_NAME = "solr-kerberos-keytab";

    private final String principal;
    private final String keytabFile;

    private final AppConfigurationEntry kerberosKeytabConfigEntry;

    public KerberosKeytabConfiguration(final String principal, final String keytabFile) {
        if (StringUtils.isBlank(principal)) {
            throw new IllegalArgumentException("Principal cannot be null");
        }

        if (StringUtils.isBlank(keytabFile)) {
            throw new IllegalArgumentException("Keytab file cannot be null");
        }

        this.principal = principal;
        this.keytabFile = keytabFile;

        // TODO handle different option names based on JVM
        final Map<String,String> configOptions = new HashMap<>();
        configOptions.put("keyTab", keytabFile);
        configOptions.put("principal", principal);
        configOptions.put("useTicketCache", "false");
        configOptions.put("useKeyTab", "true");
        configOptions.put("storeKey", "true");
        configOptions.put("debug", "true");

        this.kerberosKeytabConfigEntry = new AppConfigurationEntry(
                getKrb5LoginModuleName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                configOptions);
    }

    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
        if (APP_NAME.equals(name)) {
            return new AppConfigurationEntry[] {kerberosKeytabConfigEntry};
        } else {
            return null;
        }
    }

    public String getPrincipal() {
        return principal;
    }

    public String getKeytabFile() {
        return keytabFile;
    }

    public static String getKrb5LoginModuleName() {
        return System.getProperty("java.vendor").contains("IBM")
                ? "com.ibm.security.auth.module.Krb5LoginModule"
                : "com.sun.security.auth.module.Krb5LoginModule";
    }

}

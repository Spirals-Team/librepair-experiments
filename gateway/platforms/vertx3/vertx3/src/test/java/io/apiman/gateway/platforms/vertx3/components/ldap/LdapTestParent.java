/*
 * Copyright 2015 JBoss Inc
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

package io.apiman.gateway.platforms.vertx3.components.ldap;

import io.apiman.gateway.engine.components.ILdapComponent;
import io.apiman.gateway.engine.components.ldap.LdapConfigBean;
import io.apiman.gateway.platforms.vertx3.components.LdapClientComponentImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.junit.RunTestOnContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.directory.server.annotations.CreateLdapServer;
import org.apache.directory.server.annotations.CreateTransport;
import org.apache.directory.server.core.integ.AbstractLdapTestUnit;
import org.apache.directory.server.core.integ.FrameworkRunner;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmPartition;
import org.apache.directory.server.i18n.I18n;
import org.apache.directory.shared.ldap.entry.DefaultServerEntry;
import org.apache.directory.shared.ldap.entry.ServerEntry;
import org.apache.directory.shared.ldap.ldif.LdifEntry;
import org.apache.directory.shared.ldap.ldif.LdifReader;
import org.apache.directory.shared.ldap.name.DN;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Marc Savy {@literal <msavy@redhat.com>}
 */
@SuppressWarnings({ "nls", "javadoc" })
@RunWith(FrameworkRunner.class)
@CreateLdapServer(transports = { @CreateTransport(protocol = "LDAP", port = 7654) })
public class LdapTestParent extends AbstractLdapTestUnit {
    @Rule
    public RunTestOnContext rule = new RunTestOnContext();
    public LdapConfigBean config;
    private JdbmPartition partition;
    public static final String LDAP_SERVER_HOST = "localhost";

    public ILdapComponent ldapClientComponent = new LdapClientComponentImpl(Vertx.vertx(), null, null);

    @Before
    public void beforeClass() throws Exception {
        config = new LdapConfigBean();
        config.setHost(LDAP_SERVER_HOST);
        config.setPort(ldapServer.getPort());
        setupLdap();
    }

    /**
     * Thanks to Eric for this setup code.
     */
    public void setupLdap() throws Exception {
        if (partition != null) {
            return;
        }
        File targetDir = new File("target");
        if (!targetDir.isDirectory()) {
            throw new Exception("Couldn't find maven target directory: " + targetDir);
        }
        File partitionDir = new File(targetDir, "_ldap-partition");
        if (partitionDir.exists()) {
            FileUtils.deleteDirectory(partitionDir);
        }
        partitionDir.mkdirs();

        final File partitionDirectory = partitionDir;
        partition = new JdbmPartition();
        partition.setId("apiman");
        partition.setPartitionDir(partitionDirectory);
        partition.setSchemaManager(service.getSchemaManager());
        partition.setSuffix("o=apiman");
        service.addPartition(partition);

        // Inject the foo root entry if it does not already exist
        try {
            service.getAdminSession().lookup(partition.getSuffixDn());
        } catch (Exception lnnfe) {
            DN dn = new DN("o=apiman");
            ServerEntry entry = service.newEntry(dn);
            entry.add("objectClass", "top", "domain", "extensibleObject");
            entry.add("dc", "apiman");
            entry.add("cn", "o=apiman");
            service.getAdminSession().add(entry);
        }

        try {
            injectLdifFiles("io/apiman/gateway/platforms/vertx3/users.ldif");
        } catch (Exception e) {
            throw e;
        }
    }

    public static void injectLdifFiles(String... ldifFiles) throws Exception {
        if (ldifFiles != null && ldifFiles.length > 0) {
            for (String ldifFile : ldifFiles) {
                InputStream is = null;
                try {
                    is = LdapTestParent.class.getClassLoader().getResourceAsStream(ldifFile);
                    if (is == null) {
                        throw new FileNotFoundException("LDIF file '" + ldifFile + "' not found.");
                    } else {
                        try {
                            LdifReader ldifReader = new LdifReader(is);
                            for (LdifEntry entry : ldifReader) {
                                injectEntry(entry);
                            }
                            ldifReader.close();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                } finally {
                    IOUtils.closeQuietly(is);
                }
            }
        }
    }

    private static void injectEntry(LdifEntry entry) throws Exception {
        if (entry.isChangeAdd()) {
            service.getAdminSession().add(
                    new DefaultServerEntry(service.getSchemaManager(), entry.getEntry()));
        } else if (entry.isChangeModify()) {
            service.getAdminSession().modify(entry.getDn(), entry.getModificationItems());
        } else {
            String message = I18n.err(I18n.ERR_117, entry.getChangeType());
            throw new NamingException(message);
        }
    }
}

package com.cmpl.web.configuration.modules;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cmpl.web.configuration.modules.backup.BackupConfiguration;
import com.cmpl.web.configuration.modules.backup.BackupExportConfiguration;
import com.cmpl.web.configuration.modules.backup.BackupImportConfiguration;
import com.cmpl.web.configuration.modules.facebook.FacebookAutoConfiguration;
import com.cmpl.web.configuration.modules.facebook.FacebookConfiguration;
import com.cmpl.web.configuration.modules.google.GoogleConfiguration;

@Configuration
@Import({GoogleConfiguration.class, FacebookAutoConfiguration.class, FacebookConfiguration.class,
    BackupConfiguration.class, BackupExportConfiguration.class, BackupImportConfiguration.class})
public class ModulesConfiguration {

}

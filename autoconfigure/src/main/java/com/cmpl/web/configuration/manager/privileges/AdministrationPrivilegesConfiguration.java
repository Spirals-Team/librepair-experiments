package com.cmpl.web.configuration.manager.privileges;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RolePrivilegeConfiguration.class, ResponsibilityPrivilegeConfiguration.class, UserPrivilegeConfiguration.class,
    GroupPrivilegeConfiguration.class, MembershipPrivilegeConfiguration.class})
public class AdministrationPrivilegesConfiguration {

}

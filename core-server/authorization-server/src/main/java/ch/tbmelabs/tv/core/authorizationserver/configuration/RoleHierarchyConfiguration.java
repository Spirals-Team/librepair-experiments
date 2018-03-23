package ch.tbmelabs.tv.core.authorizationserver.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@Configuration
public class RoleHierarchyConfiguration {
  private static final List<String> ROLE_HIERARCHY = new ArrayList<>();

  static {
    ROLE_HIERARCHY.add(SecurityRole.GANDALF);
    ROLE_HIERARCHY.add(SecurityRole.SERVER_ADMIN);
    ROLE_HIERARCHY.add(SecurityRole.SERVER_SUPPORT);
    ROLE_HIERARCHY.add(SecurityRole.CONTENT_ADMIN);
    ROLE_HIERARCHY.add(SecurityRole.CONTENT_SUPPORT);
    ROLE_HIERARCHY.add(SecurityRole.PREMIUM_USER);
    ROLE_HIERARCHY.add(SecurityRole.USER);
    ROLE_HIERARCHY.add(SecurityRole.GUEST);
    ROLE_HIERARCHY.add(SecurityRole.ANONYMOUS);
  }

  private String getRoleHierarchy() {
    StringBuilder builder = new StringBuilder();

    builder.append(ROLE_HIERARCHY.get(0) + " > " + ROLE_HIERARCHY.get(1));

    IntStream.range(2, ROLE_HIERARCHY.size()).forEach(index -> {
      builder.append(" AND ").append(ROLE_HIERARCHY.get(index - 1)).append(" > ").append(ROLE_HIERARCHY.get(index));
    });

    return builder.toString();
  }

  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy(getRoleHierarchy());
    return roleHierarchy;
  }
}
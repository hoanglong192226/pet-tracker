package com.pet.management.tracker.util;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class RoleUtil {

  private static final String ROLE_PREFIX = "ROLE_";

  private RoleUtil() {

  }

  public static String getRoleFromAuthorities(Collection<GrantedAuthority> authorities) {
    if (authorities == null || authorities.isEmpty()) {
      return null;
    }

    String roleWithPrefix = authorities.iterator().next().getAuthority();

    return roleWithPrefix.startsWith(ROLE_PREFIX) ? roleWithPrefix.substring(5) : roleWithPrefix;
  }

}

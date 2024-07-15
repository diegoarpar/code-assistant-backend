package com.co.code.assistant.utils;

import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;

public final class SystemEnvHelper {
  public static final String LOCAL = "dev";
  public static final String TEST = "test";
  public static final String PROD = "prod";
  public static final String SYSTEM_SCOPE = System.getenv("SCOPE");
  public static final String SYSTEM_ENV = System.getProperty("app.env");

  private SystemEnvHelper() {
  }

  public static @NotNull String getEnv() {
    String env = ((String) Optional.ofNullable(SYSTEM_ENV).orElse("")).toLowerCase(Locale.ROOT);
    if (!env.isEmpty()) {
      return env;
    } else {
      return Strings.isNullOrEmpty(getScopeName()) ? "dev" : "prod";
    }
  }

  public static @NotNull String getScopeName() {
    return ((String)Optional.ofNullable(SYSTEM_SCOPE).orElse("")).toLowerCase(Locale.ROOT);
  }

  public static boolean isTestEnv() {
    return "test".equals(getEnv());
  }

  public static boolean isLocalEnv() {
    return "dev".equals(getEnv());
  }

  public static boolean isProdEnv() {
    return "prod".equals(getEnv());
  }

  public static boolean isTestScope() {
    return getScopeName().startsWith("test");
  }
}

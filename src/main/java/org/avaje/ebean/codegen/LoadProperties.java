package org.avaje.ebean.codegen;

import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Helper to load configuration properties.
 */
class LoadProperties {

  static Properties load(Log log) throws IOException {
    return new LoadProperties().readProperties(log);
  }

  private Properties readProperties(Log log) throws IOException {

    Properties properties = new Properties();

    File configFile = getConfigFile();
    if (configFile != null && configFile.exists()) {
      log.info("loading task properties from " + configFile.getAbsolutePath());
      FileInputStream is = new FileInputStream(configFile);
      properties.load(is);
    }
    return properties;
  }

  private File getConfigFile() {
    String config = System.getProperty("config");
    if (config != null) {
      File configFile = new File(config);
      if (!configFile.exists()) {
        throw new IllegalArgumentException("config file " + config + " not found?");
      }
      return configFile;
    }

    File configFile = new File("config/ebean-codegen.properties");
    if (!configFile.exists()) {
      configFile = new File("ebean-codegen.properties");
      if (!configFile.exists()) {
        return null;
      }
    }

    return configFile;
  }

}

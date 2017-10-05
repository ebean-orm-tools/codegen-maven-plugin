package io.ebean.codegen;

import org.apache.maven.plugin.logging.Log;

public class LogAdapter {

  private final Log log;

  public LogAdapter(Log log) {
    this.log = log;
  }

  public void error(String message, Exception e) {
    log.error(message, e);
  }

  public void error(String message) {
    log.error(message);
  }
}

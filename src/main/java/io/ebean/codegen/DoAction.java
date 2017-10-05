package io.ebean.codegen;

import org.codehaus.plexus.util.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class DoAction {

  private final LogAdapter log;

  DoAction(LogAdapter log) {
    this.log = log;
  }

  File copyTestProperties(File testResources) {

    //File testResources = new File(testSource);//, "resources");
    if (!testResources.exists() && !testResources.mkdirs()) {
      log.error("Could not create src test resources ?");
      return null;

    } else {
      try {
        File testProps = new File(testResources, "test-ebean.properties");
        if (testProps.exists()) {
          log.error(testProps.getAbsolutePath()+" already exists? leaving as is.");
          return null;

        } else {
          copyResource(testProps, "/tp-test-ebean.properties");
          //info("... added test-ebean.properties");
          return testProps;
        }
      } catch (IOException e) {
        log.error("Failed to copy test-ebean.properties", e);
        return null;
      }
    }
  }

  private void info(String message) {

  }

  private void copyResource(File testProps, String name) throws IOException {

    InputStream in = this.getClass().getResourceAsStream(name);
    FileOutputStream fos = new FileOutputStream(testProps);
    IOUtil.copy(in, fos);

    fos.flush();
    fos.close();
  }
}

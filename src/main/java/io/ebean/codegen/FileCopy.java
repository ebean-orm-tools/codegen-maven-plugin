package io.ebean.codegen;

import org.codehaus.plexus.util.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class FileCopy {

  static void copy(File toFile, String fromResource) throws IOException {

    new FileCopy().copyResource(toFile, fromResource);
  }

  void copyResource(File toFile, String fromResource) throws IOException {

    InputStream in = this.getClass().getResourceAsStream(fromResource);
    FileOutputStream fos = new FileOutputStream(toFile);
    IOUtil.copy(in, fos);

    fos.flush();
    fos.close();
  }
}

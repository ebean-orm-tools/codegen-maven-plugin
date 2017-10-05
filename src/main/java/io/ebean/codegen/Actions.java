package io.ebean.codegen;

public class Actions {

  private boolean addEbeanManifest;

  private String manifestEntityPackage;

  private boolean addTestProperties;

  public void setAddEbeanManifest(boolean generateEbeanManifest) {
    this.addEbeanManifest = generateEbeanManifest;
  }

  public boolean isAddEbeanManifest() {
    return addEbeanManifest;
  }

  public void setManifestEntityPackage(String manifestEntityPackage) {
    this.manifestEntityPackage = manifestEntityPackage;
  }

  public String getManifestEntityPackage() {
    return manifestEntityPackage;
  }

  public void setAddTestProperties(boolean addTestProperties) {
    this.addTestProperties = addTestProperties;
  }

  public boolean isAddTestProperties() {
    return addTestProperties;
  }
}

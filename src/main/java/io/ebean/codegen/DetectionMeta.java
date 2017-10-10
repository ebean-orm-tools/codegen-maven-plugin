package io.ebean.codegen;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DetectionMeta {

  Set<String> testClasspath = new LinkedHashSet<>();
  Set<String> runtimeClasspath = new LinkedHashSet<>();

  List<String> resourceDirs = new ArrayList<>();
  List<String> testResourceDirs = new ArrayList<>();

  List<String> compileSourceRoots;
  List<String> testSourceRoots;
  private String outputDirectory;
  private String testOutputDirectory;

  public List<String> getTestSourceRoots() {
    return testSourceRoots;
  }

  public void setTestSourceRoots(List<String> testSourceRoots) {
    this.testSourceRoots = testSourceRoots;
  }

  public void addSourceRoots(List<String> compileSourceRoots) {
    this.compileSourceRoots = compileSourceRoots;
  }

  public void addResourceDirectory(String directory) {
    resourceDirs.add(directory);
  }

  public void addTestResourceDirectory(String directory) {
    testResourceDirs.add(directory);
  }

  public void addTestClassPath(List<String> classpathElements) {
    testClasspath.addAll(classpathElements);
  }

  public void addRuntimeClassPath(List<String> classpathElements) {
    runtimeClasspath.addAll(classpathElements);
  }

  public void setOutputDirectory(String outputDirectory) {
    this.outputDirectory = outputDirectory;
  }

  public String getOutputDirectory() {
    return outputDirectory;
  }

  public void setTestOutputDirectory(String testOutputDirectory) {
    this.testOutputDirectory = testOutputDirectory;
  }

  public String getTestOutputDirectory() {
    return testOutputDirectory;
  }
}

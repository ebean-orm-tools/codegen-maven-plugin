package io.ebean.codegen;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Mojo(name = "init", defaultPhase = LifecyclePhase.NONE, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class InitMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", required = true, readonly = true)
  private MavenProject project;

  public void execute() {


    // java or kotlin
    // add ebena.mf if needed
    // What package are entity beans in?
    // What package for @Transactional ?
    // Use query beans?

    // What target DB?
    // Add test-ebean.properties if needed

    // Use Docker for testing?
    // Add docker-run.properties
    // Add ebean-docker-run dependency

    // generate kotlin/java finders
    // add query-bean dependency?

    // generate example MappedSuper and entity bean?

    // add logging entries ?

    DetectionMeta meta = new DetectionMeta();

    meta.addSourceRoots(project.getCompileSourceRoots());
    meta.setTestSourceRoots(project.getTestCompileSourceRoots());
    for (Resource resource : project.getResources()) {
      meta.addResourceDirectory(resource.getDirectory());
    }
    for (Resource resource : project.getTestResources()) {
      meta.addTestResourceDirectory(resource.getDirectory());
    }

    try {
      meta.addTestClassPath(project.getTestClasspathElements());
      meta.addRuntimeClassPath(project.getRuntimeClasspathElements());
      meta.addRuntimeClassPath(project.getCompileClasspathElements());

      Detection detection = new Detection(meta);
      detection.run();

      Interaction interaction = new Interaction(detection);


      LogAdapter logAdapter = new LogAdapter(getLog());

      interaction.run(new DoAction(logAdapter));

      getLog().info("---- " + detection.toString());
      getLog().info("---- " + detection.state());
      getLog().info("---- cpd: " + detection.getClasspathDection());

    } catch (Exception e) {
      getLog().error("Error running detection on project", e);
    }


    copyTestResources();
    copyMainResources();
  }


  private String getSourceMain() {
    return System.getProperty("src.main", "src/main");
  }

  private String getSourceTest() {
    return System.getProperty("src.test", "src/test");
  }

  private void copyMainResources() {

    File mainSource = new File(getSourceMain());
    if (!mainSource.exists()) {
      getLog().error("No " + getSourceMain() + " directory to add resources to?");
    } else {
      copyMainProperties(mainSource);
    }
  }

  private void copyTestResources() {

    File testSource = new File(getSourceTest());
    if (!testSource.exists()) {
      getLog().error("No " + getSourceTest() + " directory to add test resources to?");
    } else {
      copyDbMigration(testSource);
      //copyTestProperties(testSource);
      copyTestLogging(testSource);
    }
  }

  private void copyMainProperties(File mainSource) {

    File resources = new File(mainSource, "resources");
    if (!resources.exists() && !resources.mkdirs()) {
      getLog().error("Could not create src/main/resources ?");

    } else {
      try {
        File props = new File(resources, "ebean.properties");
        if (props.exists()) {
          getLog().info("ebean.properties already exists, leaving as is.");
        } else {
          copyResource(props, "/tp-ebean.properties");
          getLog().info("... added ebean.properties");
        }
      } catch (IOException e) {
        getLog().error("Failed to copy ebean.properties", e);
      }
    }
  }

//  private void copyTestProperties(File testSource) {
//
//    File testResources = new File(testSource, "resources");
//    if (!testResources.exists() && !testResources.mkdirs()) {
//      getLog().error("Could not create src/test/resources ?");
//
//    } else {
//      try {
//        File testProps = new File(testResources, "test-ebean.properties");
//        if (testProps.exists()) {
//          getLog().info("test-ebean.properties already exists, leaving as is.");
//        } else {
//          copyResource(testProps, "/tp-test-ebean.properties");
//          getLog().info("... added test-ebean.properties");
//        }
//      } catch (IOException e) {
//        getLog().error("Failed to copy test-ebean.properties", e);
//      }
//    }
//  }


  private void copyTestLogging(File testSource) {

    File testResources = new File(testSource, "resources");
    if (!testResources.exists() && !testResources.mkdirs()) {
      getLog().error("Could not create " + getSourceTest() + "/resources ?");

    } else {
      try {
        File testProps = new File(testResources, "logback-test.xml");
        if (testProps.exists()) {
          getLog().info("logback-test.xml already exists, leaving as is.");
        } else {
          copyResource(testProps, "/tp-logback-test.xml");
          getLog().info("... added logback-test.xml");
        }
      } catch (IOException e) {
        getLog().error("Failed to copy logback-test.xml", e);
      }
    }
  }

  private void copyDbMigration(File testSource) {

    File testJavaMain = new File(testSource, "java/main");
    if (!testJavaMain.exists() && !testJavaMain.mkdirs()) {
      getLog().error("Failed to make " + getSourceTest() + "/java/main ?");
    } else {
      try {
        File dbMigJava = new File(testJavaMain, "MainDbMigration.java");
        if (dbMigJava.exists()) {
          getLog().info("MainDbMigration.java already exists, leaving as is.");
        } else {
          copyResource(dbMigJava, "/tp-MainDbMigration.java");
          getLog().info("... added MainDbMigration.java");
        }

      } catch (IOException e) {
        getLog().error("Failed to copy MainDbMigration.java", e);
      }
    }
  }

  private void copyResource(File testProps, String name) throws IOException {

    InputStream in = this.getClass().getResourceAsStream(name);
    FileOutputStream fos = new FileOutputStream(testProps);
    IOUtil.copy(in, fos);

    fos.flush();
    fos.close();
  }
}

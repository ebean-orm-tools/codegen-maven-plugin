package org.avaje.ebean.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "help", defaultPhase = LifecyclePhase.NONE)
public class HelpMojo extends AbstractMojo {

  public void execute() {

    println("Help:");
    println(" ");
    println("mvn ebean:init");
    println("    ... use this to add properties, logging and MainDbMigration.java   ");
    println(" ");
    println("mvn -Dpackage=<your package> ebean:generate-finders");
    println("    ... use this to generate finders and attach them to entity beans   ");
    println("    ... where <your package> is the package containing entity beans   ");
    println("    ... e.g mvn -Dpackage=org.example.domain ebean:generate-finders");
    println(" ");
    println("mvn -Dpackage=org.example.domain ebean:generate-finders-only");
    println("    ... use this to generate finders only   ");
    println(" ");
    println("mvn -Dpackage=org.example.domain ebean:generate-querybeans");
    println("    ... use this to generate query beans (alternative to using the java annotation processor)");
    println(" ");
  }

  void println(String line) {
    getLog().info(line);
  }


}

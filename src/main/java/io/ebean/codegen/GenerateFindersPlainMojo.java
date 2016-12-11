package io.ebean.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "generate-finders-only", defaultPhase = LifecyclePhase.NONE)
public class GenerateFindersPlainMojo extends AbstractMojo {

  public void execute() {
    Generate generate = new Generate();
    generate.execute(getLog(), Mode.FINDERS_PLAIN);
  }

}

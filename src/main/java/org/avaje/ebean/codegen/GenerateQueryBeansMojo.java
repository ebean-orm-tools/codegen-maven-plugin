package org.avaje.ebean.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "generate-querybeans", defaultPhase = LifecyclePhase.NONE)
public class GenerateQueryBeansMojo extends AbstractMojo {

  public void execute() {
    Generate generate = new Generate();
    generate.execute(getLog(), Mode.QUERY_BEANS);
  }

}

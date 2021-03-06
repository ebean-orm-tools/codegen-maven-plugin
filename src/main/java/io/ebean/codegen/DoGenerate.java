package io.ebean.codegen;

import io.ebean.typequery.generator.Generator;
import io.ebean.typequery.generator.GeneratorConfig;

import java.io.IOException;
import java.util.List;

public class DoGenerate {

  private final Detection detection;

  private final InteractionHelp help;

  public DoGenerate(Detection detection, InteractionHelp help) {
    this.detection = detection;
    this.help = help;
  }

  public void generateQueryBeans() {

    GeneratorConfig config = createConfig();
    if (config == null) return;

    Generator generator = new Generator(config);
    try {
      generator.generateQueryBeans();

    } catch (IOException e) {
      help.acknowledge("Error " + e);
      e.printStackTrace();
    }
  }


  public void generateFinders() {

    GeneratorConfig config = createConfig();
    if (config == null) return;

    Generator generator = new Generator(config);
    try {

      generator.generateFinders();
      help.acknowledge("  ... generated finders: " + generator.getFinders());

      generator.modifyEntityBeansAddFinderField();
      help.acknowledge("   ... linked finders: " + generator.getFinderLinks());

    } catch (IOException e) {
      help.acknowledge("Error " + e);
      e.printStackTrace();
    }
  }

  private GeneratorConfig createConfig() {
    GeneratorConfig config = new GeneratorConfig();
    config.setClassesDirectory(detection.getMeta().getOutputDirectory());

    boolean kotlinInClassPath = detection.isKotlinInClassPath();
    boolean queryBeanInClassPath = detection.isQueryBeanInClassPath();
    boolean ebeanElasticInClassPath = detection.isEbeanElasticInClassPath();

    if (kotlinInClassPath) {
      config.setLang("kt");
    }

    List<String> sourceRoots = detection.getMeta().compileSourceRoots;
    if (sourceRoots == null || sourceRoots.size() != 1) {
      help.acknowledge("Error - single source root required.");
      return null;
    }

    config.setDestDirectory(sourceRoots.get(0));

    help.acknowledge("  settings used - kotlin:" + kotlinInClassPath
      + " queryBeans:" + queryBeanInClassPath + " docstore:" + ebeanElasticInClassPath
      + " package:" + detection.getEntityPackage());

    config.setEntityBeanPackage(detection.getEntityPackage());
    config.setAddFinderWherePublic(true);
    config.setOverwriteExistingFinders(false);
    config.setAddFinderWhereMethod(queryBeanInClassPath);
    config.setAddFinderTextMethod(ebeanElasticInClassPath);
    return config;
  }
}

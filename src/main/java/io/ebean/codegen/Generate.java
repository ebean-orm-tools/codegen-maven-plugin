package io.ebean.codegen;

import org.apache.maven.plugin.logging.Log;
import io.ebean.typequery.generator.Generator;
import io.ebean.typequery.generator.GeneratorConfig;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Generates query beans, finders etc based on the Mode.
 */
class Generate {

  void execute(Log log, Mode mode) {

    log.debug("generate ..." + new File(".").getAbsolutePath());
    try {
      Properties properties = LoadProperties.load(log);
      log.debug("properties: " + properties);

      String entityBeanPackage = System.getProperty("package");
      if (entityBeanPackage == null) {
        entityBeanPackage = properties.getProperty("package");
      }
      if (entityBeanPackage == null) {
        log.error("No package specified. Not generating anything !!");
        return;
      }

      GeneratorConfig config = new GeneratorConfig();

      String classesDirectory = properties.getProperty("classesDirectory");
      if (classesDirectory != null) {
        config.setClassesDirectory(classesDirectory);
      }
      String destDirectory = properties.getProperty("destDirectory");
      if (destDirectory != null) {
        config.setDestDirectory(destDirectory);
      }
      String destResourceDirectory = properties.getProperty("destResourceDirectory");
      if (destResourceDirectory != null) {
        config.setDestResourceDirectory(destResourceDirectory);
      }

      config.setEntityBeanPackage(entityBeanPackage);
      config.setAddFinderWherePublic(true);
      config.setOverwriteExistingFinders(false);

      Generator generator = new Generator(config);

      if (mode.isGenerateQueryBeans()) {
        generator.generateQueryBeans();
        log.info("Generated "+generator.getQueryBeanCount()+" query beans");
      }
      if (mode.isGenerateFinders()) {
        generator.generateFinders();
        log.info("Generated finders: "+generator.getFinders());
      }
      if (mode.isAttachFinders()) {
        generator.modifyEntityBeansAddFinderField();
        log.info("   Linked finders: "+generator.getFinderLinks());
      }

    } catch (IOException e) {
      log.error("Error trying to generate finders etc", e);
    }
  }

}

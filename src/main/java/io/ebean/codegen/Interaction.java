package io.ebean.codegen;

import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.util.List;

public class Interaction {

  private Actions actions = new Actions();

  private final Detection detection;

  private final InteractionHelp help;

  public Interaction(Detection detection) {
    this.detection = detection;
    this.help = new InteractionHelp(detection, actions);
  }

  public void run(DoAction doAction) {
    try {
      help.outputHeading();
      help.outputAllGoodBits();

      if (!detection.isEbeanManifestFound()) {
        actions.setAddEbeanManifest(true);
        help.questionEntityBeanPackage();
        help.questionTransactionalPackage();
        help.questionQueryBeanPackage();
      }

      if (!detection.isTestEbeanProperties()) {
        actions.setAddTestProperties(true);
      }

      if (!detection.isTestEbeanProperties()) {

          help.desc("I'd like to add test-ebean.properties, this can be used to");
          help.desc("configure Ebean when running tests (which DB to use etc)");
          String ans = help.askYesNo("Should I add test-ebean.properties?");
          if (ans.equalsIgnoreCase("Yes")) {
            List<String> testResourceDirs = detection.getMeta().testResourceDirs;
            if (!testResourceDirs.isEmpty()) {

              File testRes = new File(testResourceDirs.get(0));
              if (!testRes.exists() && testRes.isDirectory()) {
                throw new IllegalStateException("Expected test resource directory at "+testRes.getAbsolutePath());
              }
              actions.setAddTestProperties(true);
              File file = doAction.copyTestProperties(testRes);
              if (file != null) {
                help.acknowledge("  ... I have added " + file.getAbsolutePath());
              }
            }

          }
      }

    } finally {
      AnsiConsole.systemUninstall();
    }
  }
}

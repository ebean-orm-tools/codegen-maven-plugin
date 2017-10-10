package io.ebean.codegen;

import org.fusesource.jansi.AnsiConsole;

class Interaction {

  private Actions actions = new Actions();

  private final Detection detection;

  private final InteractionHelp help;

  Interaction(Detection detection) {
    this.detection = detection;
    this.help = new InteractionHelp(detection, actions);
  }


  void run() {
    try {
      help.outputHeading();
      help.outputAllGoodBits();

      boolean quit = false;
      while (!quit) {
        QuestionOptions options = createOptions();
        help.question("Commands:");
        help.outOps(options);
        String answer = help.askKey("Select an command:", options);
        quit = isQuit(answer);
        if (quit) {
          help.acknowledge("  done.");
          help.acknowledge(" ");
          help.acknowledge(" ");
        } else {
          executeCommand(answer);
        }
      }

    } finally {
      AnsiConsole.systemUninstall();
    }
  }

  private void executeCommand(String answer) {
    switch (answer) {
      case "M":
        executeManifest();
        break;
      case "P":
        executeAddTestProperties();
        break;
      case "L":
        executeAddTestLogging();
        break;
      case "G":
        executeAddDbMigration();
        break;
      case "D":
        executeAddDockerRun();
        break;
      case "F":
        executeGenerateFinders();
        break;
      case "T":
        executeGenerateQueryBeans();
        break;
    }
  }

  private boolean isQuit(String answer) {
    answer = answer.trim();
    return "Q".equalsIgnoreCase(answer) || "X".equalsIgnoreCase(answer);
  }

  private QuestionOptions createOptions() {
    QuestionOptions options = new QuestionOptions();
    if (!detection.isEbeanManifestFound()) {
      options.add("M", "Manifest - add ebean.mf to control enhancement (recommended)");
    }
    if (!detection.isTestEbeanProperties()) {
      options.add("P", "Test properties - Add test-ebean.properties to configure Ebean when running tests (recommended)");
    }
    if (!detection.isTestLoggingEntry()) {
      options.add("L", "Logging - Add test logging entry to log SQL when running tests (recommended)");
    }
    if (!detection.isDbMigration()) {
      options.add("G", "Generate migrations - Add GenerateDbMigration for generating DB migration scripts (recommended)");
    }
    if (!detection.isDockerRunProperties()) {
      options.add("D", "Docker - Add support for running tests against Docker containers (Postgres, ElasticSearch etc)");
    }

    options.add("F", "Finders - generate finders");
    options.add("T", "Type safe query beans - manually generate them (rather than via APT/KAPT)");
    options.add("Q", "Quit");

    return options;
  }


  private void executeAddDockerRun() {
    help.acknowledge("  docker run");
  }

  private void executeAddDbMigration() {
    new DoAddGenerateMigration(detection, help).run();
  }

  private void executeAddTestLogging() {
    help.acknowledge("  executeAddTestLogging");
  }

  private void executeGenerateFinders() {
    new DoGenerate(detection, help).generateFinders();
  }

  private void executeGenerateQueryBeans() {
    new DoGenerate(detection, help).generateQueryBeans();
  }

  private void executeManifest() {
    new DoAddManifest(detection, help).run();

  }

  private void executeAddTestProperties() {
    new DoAddTestProperties(detection, help).run();
  }
}

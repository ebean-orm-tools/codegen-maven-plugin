package io.ebean.codegen;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

class InteractionHelp {

  private final Detection detection;

  private final Actions actions;

  private final PrintStream out;

  InteractionHelp(Detection detection, Actions actions) {
    this.detection = detection;
    this.actions = actions;

    AnsiConsole.systemInstall();
    this.out = AnsiConsole.out();
  }

  void questionEntityBeanPackage() {

    List<String> list = detection.getDetectedPackages();
    if (list.isEmpty()) {
      String answer = ask("Enter a package that will contain the entity beans: ");
      question("ta: " + answer);
      actions.setManifestEntityPackage(answer);

    } else {
      QuestionOptions options = new QuestionOptions();
      options.addAll(list);
      options.add("O", "Other");

      question("Select a package that will contain the entity beans");
      String answer = outOptions(options);
      actions.setManifestEntityPackage(answer);
    }


  }

  String outOptions(QuestionOptions options) {

    Set<Map.Entry<String, String>> entries = options.entries();
    for (Map.Entry<String, String> entry : entries) {
      PrintStream out = AnsiConsole.out();
      out.print(Ansi.ansi().bold().fgBrightBlue().a("  "+entry.getKey()).reset());
      out.println(Ansi.ansi().fgBlue().a(" - "+entry.getValue()).reset());
    }
    String answer = ask("select one of the options above");
    return options.selected(answer);
  }

  String ask(String ask) {
//    PrintStream out = AnsiConsole.out();
    out.println(Ansi.ansi().bold().fgBrightBlue().a(ask).reset());

    return System.console().readLine();
  }

  String ask(String ask, QuestionOptions options) {

    out.print(Ansi.ansi().bold().fgBrightBlue().a("  "+ask).reset());

    if (options != null) {
      outOptionKeys(options);
    }

    plain(" > ");
    AnsiConsole.out().flush();

    String answer = System.console().readLine();
    if (options != null) {
      answer = options.selected(answer);
    }
    return answer;
  }

  private void outOptionKeys(QuestionOptions options) {

    plain(" [");
    Set<String> keys = options.keys();
    int i = 0;
    for (String key : keys) {
      if (i++ != 0) {
        plain("/");
      }
      bYell(key);
    }
    plain("]");
  }

  void question(String content) {
    PrintStream out = AnsiConsole.out();
    out.println(Ansi.ansi().bold().fgBrightBlue().a(content).reset());
  }

  void outputAllGood(String key, String description) {


    int gap = 40 - key.length();

    out.print(Ansi.ansi().bold().fgGreen().a("  PASS").reset());
    out.print(Ansi.ansi().bold().a(" - ").reset());
    out.print(Ansi.ansi().bold().fgBrightBlue().a(key).reset());
    out.println(Ansi.ansi().a(padding(gap).toString()+"  - "+description).reset());
  }

  private String padding(int gap) {
    StringBuilder sbGap = new StringBuilder(gap);
    if (gap > 0) {
      for (int i = 0; i < gap; i++) {
        sbGap.append(' ');
      }
    }
    return sbGap.toString();
  }

  void outputManifest() {

    String pad = "          ";

    int padOut = 40;
    plain(pad);
    blueBold(detection.getEntityPackages().toString(), padOut);
    plain("  ... enhancement for entity beans\n");

    plain(pad);
    blueBold(detection.getTransactionalPackages().toString(), padOut);
    plain("  ... enhancement for @Transactional\n");

    plain(pad);
    blueBold(detection.getQuerybeanPackages().toString(), padOut);
    plain("  ... query bean caller enhancement\n");
  }

  void plain(String content) {
    out.print(Ansi.ansi().a(content).reset());
  }

  void plainLn(String content) {
    out.println(Ansi.ansi().a(content).reset());
  }

  void blueBold(String content, int padTo) {
    int pad = padTo - content.length();
    out.print(Ansi.ansi().bold().fgBlue().a(content).reset());
    padOut(pad);
  }

  void yell(String content, int padTo) {
    int pad = padTo - content.length();
    yell(content);
    padOut(pad);
  }

  void yell(String content) {
    out.print(Ansi.ansi().bold().fgYellow().a(content).reset());
  }

  void bYell(String content) {
    out.print(Ansi.ansi().bold().fgBrightYellow().a(content).reset());
  }

  void acknowledge(String content) {
    out.println(Ansi.ansi().fgRed().a(content).reset());
  }


  void padOut(int len) {
    plain(padding(len));
  }

  void outputHeading() {
    AnsiConsole.out().println(Ansi.ansi().fgBrightMagenta().a("---------------------------------------------").reset());
    AnsiConsole.out().print(Ansi.ansi().bold().fgYellow().a("EBEAN : INIT").reset());
    AnsiConsole.out().println(Ansi.ansi().fgBrightMagenta().a("  - interactive ebean initialiser").reset());
    AnsiConsole.out().println(Ansi.ansi().fgBrightMagenta().a("---------------------------------------------").reset());
  }

  public void questionTransactionalPackage() {

  }

  public void questionQueryBeanPackage() {

  }

  void outputAllGoodBits() {

    if (detection.isEbeanManifestFound()) {
      outputAllGood("ebean.mf", "controlling packages that are enhanced");
      outputManifest();
    }
    if (detection.isTestEbeanProperties()) {
      outputAllGood("test-ebean.properties", "configuration when running tests");
    }
    if (detection.isTestLoggingEntry()) {
      outputAllGood(detection.getLoggerType(), "entry for logging SQL when running tests");
    }
    if (detection.isDbMigration()) {
      outputAllGood(detection.getDbMigrationFile(), "for generation of DB migrations");
    }

    plainLn(" ");
    plainLn(" ");
  }

  void desc(String content) {
    plainLn("  "+content);
  }

  public String askYesNo(String content) {
    QuestionOptions yn = new QuestionOptions();
    yn.add("Y", "Yes");
    yn.add("N", "No");
    return ask(content, yn);
  }

}

package io.ebean.codegen;

/**
 * Generation mode.
 */
enum Mode {

  /**
   * Just generate query beans.
   */
  QUERY_BEANS(true, false, false),

  /**
   * Generate finders and attach them.
   */
  FINDERS(false, true, true),

  /**
   * Generate finders and not attach them.
   */
  FINDERS_PLAIN(false, true, false);

  private boolean queryBeans;
  private boolean finders;
  private boolean attachFinders;

  Mode(boolean queryBeans, boolean finders, boolean attachFinders) {
    this.queryBeans = queryBeans;
    this.finders = finders;
    this.attachFinders = attachFinders;
  }

  boolean isGenerateQueryBeans() {
    return queryBeans;
  }

  boolean isGenerateFinders() {
    return finders;
  }

  boolean isAttachFinders() {
    return attachFinders;
  }
}

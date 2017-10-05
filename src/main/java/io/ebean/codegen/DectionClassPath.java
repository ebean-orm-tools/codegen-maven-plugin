package io.ebean.codegen;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class DectionClassPath {

  private final Map<String,String> dbMap = new LinkedHashMap<>();

  private final Set<String> targetDatabases = new HashSet<>();

  private boolean kotlin;

  private boolean ebeanDockerRun;

  private boolean ebeanQueryBeans;

  private boolean querybeanGenerator;

  DectionClassPath() {
    dbMap.put("postgres", "postgres");
    dbMap.put("oracle", "oracle");
    dbMap.put("mysql", "mysql");
  }

  public String toString() {
    return "dbs:"+targetDatabases+" kotlin:"+kotlin+" dockerRun:"+ebeanDockerRun+" qb:"+ebeanQueryBeans+" qbg:"+querybeanGenerator;
  }

  public Set<String> getTargetDatabases() {
    return targetDatabases;
  }

  public boolean isKotlin() {
    return kotlin;
  }

  public boolean isEbeanDockerRun() {
    return ebeanDockerRun;
  }

  public boolean isEbeanQueryBeans() {
    return ebeanQueryBeans;
  }

  public boolean isQuerybeanGenerator() {
    return querybeanGenerator;
  }

  public void check(String entry) {

    if (!kotlin) {
      kotlin = entry.contains("org.jetbrains.kotlin");
    }
    if (!ebeanDockerRun) {
      ebeanDockerRun = entry.contains("ebean-docker-run");
    }
    if (!ebeanQueryBeans) {
      ebeanQueryBeans = entry.contains("ebean-querybean");
    }
    if (!querybeanGenerator) {
      querybeanGenerator = entry.contains("querybean-generator");
    }

    Set<Map.Entry<String, String>> dbs = dbMap.entrySet();
    for (Map.Entry<String, String> db : dbs) {
      if (entry.contains(db.getValue())) {
        targetDatabases.add(db.getKey());
      }
    }

  }
}

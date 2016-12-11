package main

import io.ebean.Platform
import io.ebean.dbmigration.DbMigration
import java.io.IOException


object MainDbMigration {

  @Throws(IOException::class)
  @JvmStatic fun main(args: Array<String>) {


    //System.setProperty("ddl.migration.version", "4.5.6_12");
    //System.setProperty("ddl.migration.name", "support end dating");

    val dbMigration = DbMigration()
    dbMigration.setPlatform(Platform.POSTGRES)
    dbMigration.generateMigration()
  }
}
# codegen-maven-plugin

This plugin generates "Finders".

It is included as part of the ebean-enhancement tile but also can be included separately via:

```xml
<plugin>
  <groupId>org.avaje.ebeanorm</groupId>
  <artifactId>codegen-maven-plugin</artifactId>
  <version>1.2</version>
</plugin>
```

## Usage:

Show help
```console
mvn ebean-codegen:help
```

Generate MainDbMigration.java, properties files, logback-test.xml
```console
mvn ebean-codegen:init
```

Generate finders (where entity beans are in org.example.domain package)
```console
mvn ebean-codegen:generate-finders -Dpackage=org.example.domain
```

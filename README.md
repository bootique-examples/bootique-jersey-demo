[![Build Status](https://travis-ci.org/bootique-examples/bootique-liquibase-demo.svg)](https://travis-ci.org/bootique-examples/bootique-liquibase-demo)
# bootique-liquibase-demo

A simple example that explains how to migrate data via [Liquibase](http://www.liquibase.org) integrated for [Bootique](http://bootique.io). 
   
*For additional help/questions about this example send a message to
[Bootique forum](https://groups.google.com/forum/#!forum/bootique-user).*
   
## Prerequisites
      
    * Java 1.8 or newer.
    * Apache Maven.
      
## Build the Demo
      
Here is how to build it:
```bash
git clone git@github.com:bootique-examples/bootique-liquibase-demo.git        
cd bootique-liquibase-demo
mvn package
 ```   
## Run the Demo

Now you can check the options available in your app:
```bash
java -jar target/bootique-liquibase-demo-2.0.jar
```

```
OPTIONS
      --catalog=catalog_name
           Catalog against which 'liquibase dropAll' will be executed.

      --config=yaml_location
           Specifies YAML config location, which can be a file path or a URL.

      -h, --help
           Prints this message.

      -H, --help-config
           Prints information about application modules and their configuration options.

      --lb-changelog-sync
           Mark all changes as executed in the database.

      --lb-changelog-sync-sql
           Writes SQL to mark all changes as executed in the database to STDOUT.

      --lb-clear-check-sums
           Clears all checksums in the current changelog, so they will be recalculated next update.

      -x [val], --lb-context[=val]
           Specifies Liquibase context to control which changeSets will be executed in migration run.

      -d [val], --lb-default-schema[=val]
           Specifies the default schema to use for managed database objects and for Liquibase control tables.

      --lb-drop-all
           Drops all database objects in the configured schema(s). Note that functions, procedures and packages are not dropped.

      -u, --lb-update
           Updates DB with available migrations

      -v, --lb-validate
           Checks the changelog for errors.

      --schema=schema_name
           Schema against which 'liquibase dropAll' will be executed.

      --select-test
           Select from DB
```

Provide required configuration via *config1.yml*:
```yaml
jdbc:
  test:
    url: jdbc:derby:target/derby/migrations1;create=true
    maxActive: 2

liquibase:
  datasource: test
  # changelog provided in java
```
Run migration:
```bash
java -jar target/bootique-liquibase-demo-2.0.jar --config=classpath:io.bootique.liquibase.demo/config1.yml --lb-update
```
    
Check data via --select-test command:
```bash
java -jar target/bootique-liquibase-demo-2.0.jar --config=classpath:io.bootique.liquibase.demo/config1.yml --select-test
```

Newly created table "TEST" data is successfully printed:   
```
...
    ID : 1
    NAME : Test Name
    ORDER : 1
```
Noticeable point is **"last wins" strategy**, returning the last change log collection passed to Liquibase. 
Consider configuration  *config2.yml*:
```yaml
jdbc:
  test:
    url: jdbc:derby:target/derby/migrations1;create=true
    maxActive: 2

liquibase:
  datasource: test
  changeLogs: # change log is declared directly in the file
    - classpath:io.bootique.liquibase.demo/changelog_1_2.yml
```
Firstly, rebuild simply having called:
```bash
mvn clean package
```
    
Then run migration with *config2.yml*:
```bash
java -jar target/bootique-liquibase-demo-2.0.jar --config=classpath:io.bootique.liquibase.demo/config2.yml --lb-update
```
    
Check data via --select-test command:
```bash
java -jar target/bootique-liquibase-demo-2.0.jar --config=classpath:io.bootique.liquibase.demo/config2.yml --select-test        
```    
    
Expected result is error since "TEST" table created in changelog_2.xml overridden by YAML config:
```
...
    Caused by: ERROR 42X05: Table/View 'TEST' does not exist.
    	at org.apache.derby.iapi.error.StandardException.newException(Unknown Source)
```





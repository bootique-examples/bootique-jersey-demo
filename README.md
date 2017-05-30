  [![Build Status](https://travis-ci.org/bootique-examples/bootique-liquibase-demo.svg)](https://travis-ci.org/bootique-examples/bootique-liquibase-demo)
# bootique-liquibase-demo

A simple example that explains how to migrate data into a database via [Liquibase](http://www.liquibase.org) integrated for [Bootique](http://bootique.io). 
   
*For additional help/questions about this example send a message to
[Bootique forum](https://groups.google.com/forum/#!forum/bootique-user).*
   
## Prerequisites
      
    * Java 1.8 or newer.
    * Apache Maven.
      
## Build the Demo
      
Here is how to build it:
        
        git clone git@github.com:bootique-examples/bootique-liquibase-demo.git
        cd bootique-liquibase-demo
        mvn package
      
## Run the Demo

Now you can check the options available in your app:
   
    java -jar target/bootique.liquibase.demo-1.0-SNAPSHOT.jar
    
    OPTIONS
      --changelog-sync
           Mark all changes as executed in the database.

      --clear-check-sums
           Clears all checksums in the current changelog, so they will be
           recalculated next update.

      --config=yaml_location
           Specifies YAML config location, which can be a file path or a URL.

      -h, --help
           Prints this message.

      -H, --help-config
           Prints information about application modules and their configuration
           options.

      -u, --update
           Updates DB with available migrations

      -v, --validate
           Checks the changelog for errors.

Provide required configuration via *config1.yml*:

    jdbc:
      test:
        url: jdbc:derby:target/derby/migrations1;create=true
        maxActive: 2
    
    liquibase:
      datasource: test
      # changelog provided in java

Run migration:

    --config=classpath:io.bootique.liquibase.demo/config1.yml --update
    
Check data migration via --select-test command:
    
    --config=classpath:io.bootique.liquibase.demo/config1.yml --select-test

Newly created table "TEST" data is successfully printed:   
    
    ...
        ID : 1
        NAME : Test Name
        ORDER : 1

Noticeable point is **"last wins" strategy**, returning the last change log collection passed to Liquibase. 
Consider configuration  *config2.yml*:

    jdbc:
      test:
        url: jdbc:derby:target/derby/migrations1;create=true
        maxActive: 2
    
    liquibase:
      datasource: test
      changeLogs: # change log is declared directly in the file
        - classpath:io.bootique.liquibase.demo/changelog_1_2.yml

Firstly, clear database simply having called:
    
    mvn clean
    
Run migration:

    --config=classpath:io.bootique.liquibase.demo/config2.yml --update
    
Check data migration via --select-test command:
        
    --config=classpath:io.bootique.liquibase.demo/config2.yml --select-test
    
Expected result: 

    ...
        Caused by: ERROR 42X05: Table/View 'TEST' does not exist.
        	at org.apache.derby.iapi.error.StandardException.newException(Unknown Source)





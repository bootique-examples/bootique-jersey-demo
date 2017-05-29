  [![Build Status](https://travis-ci.org/bootique-examples/bootique-liquibase-demo.svg)](https://travis-ci.org/bootique-examples/bootique-liquibase-demo)
# bootique-liquibase-demo

A simple example that explains how to ...
   
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

 
Migration *.yml*:
    
    jdbc:
      test:
        url: jdbc:derby:target/derby/migrations1;create=true
        maxActive: 2
    
    liquibase:
      datasource: test
      changeLogs:
        - classpath:io/bootique/liquibase/changeset1.sql



## TBD commands


    --config=classpath:io.bootique.liquibase.demo/migrations1.yml --update


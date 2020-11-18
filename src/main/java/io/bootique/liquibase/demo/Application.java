package io.bootique.liquibase.demo;

import io.bootique.BQCoreModule;
import io.bootique.BaseModule;
import io.bootique.Bootique;
import io.bootique.di.Binder;
import io.bootique.liquibase.LiquibaseModule;
import io.bootique.resource.ResourceFactory;

public class Application extends BaseModule {
    public static void main(String[] args) {
        Bootique.app(args)
                .autoLoadModules()
                .module(Application.class)
                .exec()
                .exit();
    }

    @Override
    public void configure(Binder binder) {
        BQCoreModule.extend(binder)
                .addCommand(SelectTestCommand.class);

        //add change log
        LiquibaseModule.extend(binder)
                .addChangeLog(new ResourceFactory("classpath:io.bootique.liquibase.demo/changelog_2.xml"));
    }
}

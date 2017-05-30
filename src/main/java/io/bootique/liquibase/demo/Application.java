package io.bootique.liquibase.demo;

import com.google.inject.Binder;
import com.google.inject.Module;
import io.bootique.BQCoreModule;
import io.bootique.Bootique;
import io.bootique.liquibase.LiquibaseModule;
import io.bootique.resource.ResourceFactory;

public class Application implements Module {
    public static final void main(String[] args) {
        Bootique.app(args).module(Application.class).autoLoadModules().run();
    }

    @Override
    public void configure(Binder binder) {
        BQCoreModule.extend(binder)
                .addCommand(SelectTestCommand.class);

        //add change log
        LiquibaseModule.extend(binder).addChangeLog(new ResourceFactory("classpath:io.bootique.liquibase.demo/changelog_2.xml"));
    }
}

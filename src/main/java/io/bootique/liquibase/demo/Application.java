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
                .addCommand(SelectCommand.class);

        LiquibaseModule.extend(binder).initAllExtensions().addChangeLog(new ResourceFactory("classpath:io.bootique.liquibase.demo/changelog2.xml"));
    }
}

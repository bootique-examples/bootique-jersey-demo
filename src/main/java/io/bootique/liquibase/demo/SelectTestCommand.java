package io.bootique.liquibase.demo;

import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.jdbc.DataSourceFactory;
import io.bootique.meta.application.CommandMetadata;

import javax.inject.Inject;
import javax.inject.Provider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTestCommand extends CommandWithMetadata {

    private Provider<DataSourceFactory> dataSource;

    @Inject
    public SelectTestCommand(Provider<DataSourceFactory> dataSource) {
        super(CommandMetadata.builder(SelectTestCommand.class)
                .name("select-test")
                .shortName('s')
                .description("Select from DB")
                .build());
        this.dataSource = dataSource;
    }

    @Override
    public CommandOutcome run(Cli cli) {
        try (Connection connection = dataSource.get().forName("test").getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT * FROM TEST")) {

                    while (rs.next()) {
                        System.out.println("ID : " + rs.getString("ID"));
                        System.out.println("NAME : " + rs.getString("NAME"));
                        System.out.println("ORDER : " + rs.getInt("ORDER"));
                    }
                }
            }
        } catch (SQLException e) {
            return CommandOutcome.failed(1, e);
        }

        return CommandOutcome.succeeded();
    }

}

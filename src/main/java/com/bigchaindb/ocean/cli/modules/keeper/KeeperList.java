package com.bigchaindb.ocean.cli.modules.keeper;

import com.bigchaindb.ocean.cli.KeeperCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.bigchaindb.ocean.cli.helpers.KeeperReflections;
import picocli.CommandLine;

import java.util.Collection;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "list",
        description = "List of Keeper Contracts")
public class KeeperList implements Callable {

    @CommandLine.ParentCommand
    KeeperCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    CommandResult list() throws CLIException {

        try {
            System.out.println("List of Keeper Contracts: ");

            Collection<Class> constructors = KeeperReflections.getConstructors();
            constructors.forEach(m -> {
                System.out.println(m.getSimpleName());
            });
        } catch (Exception e) {
            throw new CLIException(e.getMessage());

        } finally {
            parent.cli.progressBar.doStop();
        }

        return CommandResult.successResult();

    }

    @Override
    public Object call() throws CLIException {
        return list();
    }
}

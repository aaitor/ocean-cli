package com.bigchaindb.ocean.cli.modules.keeper;

import com.bigchaindb.ocean.cli.KeeperCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.bigchaindb.ocean.cli.helpers.KeeperReflections;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "describe",
        description = "Describe a Keeper contract")
public class KeeperDescribe implements Callable {

    @CommandLine.ParentCommand
    KeeperCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0", arity = "1")
    String contractName= null;


    CommandResult describe() throws CLIException {

        try {

            if (!KeeperReflections.contractExists(contractName))
                throw new CLIException("The Contract name provided doesn't exist: " + contractName);

            System.out.println("Description of Contract: ");

            Class clazz= KeeperReflections.getContractClass(contractName);
            KeeperReflections.printClassInformation(clazz);


        } catch (Exception e) {
            throw new CLIException(e.getMessage());

        } finally {
            //parent.cli.progressBar.doStop();
        }

        return CommandResult.successResult();

    }



    @Override
    public Object call() throws CLIException {
        return describe();
    }
}

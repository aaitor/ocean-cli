package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.OceanCLI;
import com.bigchaindb.ocean.cli.modules.keeper.KeeperDescribe;
import com.bigchaindb.ocean.cli.modules.keeper.KeeperList;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "keeper",
        subcommands = {
                KeeperList.class,
                KeeperDescribe.class
        },
        description = "Keeper Smart Contracts interface")
public class KeeperCLI implements Callable {

    @CommandLine.ParentCommand
    public OceanCLI cli;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @Override
    public Object call() {
        spec.commandLine().usage(System.out);
        return null;
    }


}

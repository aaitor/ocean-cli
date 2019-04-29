package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.OceanCLI;
import picocli.CommandLine;
import com.bigchaindb.ocean.cli.modules.utils.UtilsInfo;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "utils",
        subcommands = {
                UtilsInfo.class
        },
        description = "Utils interface")
public class UtilsCLI implements Callable {

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

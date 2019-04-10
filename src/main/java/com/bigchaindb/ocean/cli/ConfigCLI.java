package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.cli.config.ConfigClean;
import com.bigchaindb.ocean.OceanCLI;
import com.bigchaindb.ocean.cli.config.ConfigShow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "config",
        subcommands = {ConfigClean.class, ConfigShow.class},
        description = "Reading and setting application config")
public class ConfigCLI implements Runnable {

    private static final Logger log = LogManager.getLogger(AccountsCLI.class);

    @CommandLine.ParentCommand
    public OceanCLI cli;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }
}

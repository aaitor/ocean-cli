package com.bigchaindb.ocean;

import com.bigchaindb.ocean.cli.AccountsCLI;
import com.bigchaindb.ocean.cli.AssetsCLI;
import com.bigchaindb.ocean.cli.ConfigCLI;
import com.bigchaindb.ocean.cli.TokensCLI;
import com.bigchaindb.ocean.cli.model.exceptions.CLIException;
import com.bigchaindb.ocean.dto.SquidBase;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;


@Command(name = "ocean-cli",
        versionProvider = OceanCLI.SquidVersionProvider.class,
        version = "OceanCLI v0.0.1",
        description = "Prints usage help and version help when requested.%n",
        header = {
                "@|blue ~ Welcome to Ocean CLI ~\n" +
                ""},
        footer = "\nbuilt by BigchainDB GmbH\n",
        subcommands = {
            ConfigCLI.class,
            AccountsCLI.class,
            TokensCLI.class,
            AssetsCLI.class
        })
public class OceanCLI extends SquidBase implements Callable {

    @CommandLine.Option(names = { "-h", "--help" },
            usageHelp = true,
            description = "Displays this help message and quits.")
    private boolean helpRequested = false;

    @CommandLine.Option(names = {"-V", "--version"},
            versionHelp = true,
            description = "Display version info")
    boolean versionInfoRequested;

    public OceanCLI() throws CLIException {
    }

    @Override
    public Object call() {
        CommandLine.usage(this, System.out);
        return null;
    }

    public static void main(String[] args) {

        try {
            CommandLine.call(new OceanCLI(), System.err, args);
        } catch (CLIException e) {}
    }

    static class SquidVersionProvider implements CommandLine.IVersionProvider {

        @CommandLine.ParentCommand
        OceanCLI parent;

        @Override
        public String[] getVersion() throws Exception {
            return new String[] {
                    "@|blue OceanCLI|@ v0.0.1"
                            //+ "OceanController: " + parent.oceanController.toString()

            };
        }
    }

}

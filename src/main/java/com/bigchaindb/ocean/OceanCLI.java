package com.bigchaindb.ocean;

import com.bigchaindb.ocean.cli.AccountsCLI;
import com.bigchaindb.ocean.cli.AssetsCLI;
import com.bigchaindb.ocean.cli.ConfigCLI;
import com.bigchaindb.ocean.cli.TokensCLI;
import com.bigchaindb.ocean.dto.SquidBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Command;


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
public class OceanCLI extends SquidBase implements Runnable {


    private static final Logger log = LogManager.getLogger(OceanCLI.class);

    @CommandLine.Option(names = { "-h", "--help" },
            usageHelp = true,
            description = "Displays this help message and quits.")
    private boolean helpRequested = false;

    @CommandLine.Option(names = {"-V", "--version"},
            versionHelp = true,
            description = "Display version info")
    boolean versionInfoRequested;

    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }

    public static void main(String[] args) {
        CommandLine.run(new OceanCLI(), System.err, args);
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

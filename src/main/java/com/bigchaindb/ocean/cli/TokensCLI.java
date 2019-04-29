package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.cli.modules.tokens.TokensRequest;
import com.bigchaindb.ocean.cli.modules.tokens.TokensTransfer;
import com.bigchaindb.ocean.OceanCLI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "tokens",
        subcommands = {TokensRequest.class, TokensTransfer.class},
        description = "Permits receive and transfer Ocean Tokens.")
public class TokensCLI implements Runnable {

    private static final Logger log = LogManager.getLogger(TokensCLI.class);

    @CommandLine.ParentCommand
    public OceanCLI cli;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }
}

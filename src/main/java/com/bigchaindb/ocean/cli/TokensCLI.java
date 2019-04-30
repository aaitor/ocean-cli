package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.OceanCLI;
import com.bigchaindb.ocean.cli.modules.tokens.TokensRequest;
import com.bigchaindb.ocean.cli.modules.tokens.TokensTransfer;
import picocli.CommandLine;

@CommandLine.Command(
        name = "tokens",
        subcommands = {TokensRequest.class, TokensTransfer.class},
        description = "Allows to request Ocean Tokens and transfer to other accounts.")
public class TokensCLI implements Runnable {

    @CommandLine.ParentCommand
    public OceanCLI cli;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }
}

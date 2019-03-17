package com.github.aaitor.cli;

import com.github.aaitor.OceanCLI;
import com.github.aaitor.cli.assets.AssetsCreate;
import com.github.aaitor.cli.assets.AssetsResolve;
import com.github.aaitor.cli.tokens.TokensRequest;
import com.github.aaitor.cli.tokens.TokensTransfer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "assets",
        subcommands = {AssetsCreate.class, AssetsResolve.class},
        description = "Registering of assets in the network")
public class AssetsCLI implements Runnable {

    private static final Logger log = LogManager.getLogger(AssetsCLI.class);

    @CommandLine.ParentCommand
    public OceanCLI cli;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }
}

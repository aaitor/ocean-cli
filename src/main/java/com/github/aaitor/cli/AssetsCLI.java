package com.github.aaitor.cli;

import com.github.aaitor.OceanCLI;
import com.github.aaitor.cli.assets.AssetsCreate;
import com.github.aaitor.cli.assets.AssetsImport;
import com.github.aaitor.cli.assets.AssetsResolve;
import com.github.aaitor.cli.assets.AssetsSearch;
import com.github.aaitor.cli.tokens.TokensRequest;
import com.github.aaitor.cli.tokens.TokensTransfer;
import com.github.aaitor.cli.utils.Constants;
import com.oceanprotocol.squid.models.service.ProviderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "assets",
        subcommands = {AssetsCreate.class, AssetsImport.class, AssetsResolve.class, AssetsSearch.class},
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

    public ProviderConfig serviceEndpointsBuilder()  {

        return new ProviderConfig(
                cli.getNetworkConfig().getString("brizo.url") + Constants.consumeUri,
                cli.getNetworkConfig().getString("brizo.url") + Constants.initializeUri,
                cli.getNetworkConfig().getString("aquarius.url") + Constants.metadataUri,
                cli.getNetworkConfig().getString("secretstore.url"),
                cli.getNetworkConfig().getString("provider.address")
        );

    }

}

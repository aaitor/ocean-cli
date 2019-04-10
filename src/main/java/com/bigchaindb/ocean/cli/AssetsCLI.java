package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.cli.assets.*;
import com.bigchaindb.ocean.cli.utils.Constants;
import com.bigchaindb.ocean.OceanCLI;
import com.oceanprotocol.squid.models.service.ProviderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "assets",
        subcommands = {AssetsCreate.class, AssetsImport.class, AssetsResolve.class, AssetsSearch.class, AssetsOrder.class, AssetsConsume.class},
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
                cli.getNetworkConfig().getString("aquarius-internal.url") + Constants.metadataUri,
                cli.getNetworkConfig().getString("secretstore.url"),
                cli.getNetworkConfig().getString("provider.address")
        );

    }

}

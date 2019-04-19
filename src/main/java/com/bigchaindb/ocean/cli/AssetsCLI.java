package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.OceanCLI;
import com.bigchaindb.ocean.cli.assets.*;
import com.bigchaindb.ocean.cli.utils.Constants;
import com.oceanprotocol.squid.models.service.ProviderConfig;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "assets",
        subcommands = {
                AssetsCreate.class,
                AssetsImport.class,
                AssetsResolve.class,
                AssetsSearch.class,
                AssetsOrder.class,
                AssetsConsume.class},
        description = "Assets handler")
public class AssetsCLI implements Callable {

    @CommandLine.ParentCommand
    public OceanCLI cli;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @Override
    public Object call() {
        spec.commandLine().usage(System.out);
        return null;
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

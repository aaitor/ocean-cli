package com.bigchaindb.ocean.cli.assets;

import com.bigchaindb.ocean.cli.AssetsCLI;
import com.bigchaindb.ocean.cli.model.CommandResult;
import com.bigchaindb.ocean.cli.model.exceptions.CLIException;
import com.bigchaindb.ocean.cli.utils.CommandLineUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.exceptions.DIDFormatException;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.DID;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "resolve",
        description = "Resolve an asset using a given DID")
public class AssetsResolve implements Callable {

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @CommandLine.Parameters(index = "0")
    String did;

    CommandResult resolve() throws CLIException {

        DDO ddo;
        try {
            System.out.println("Resolving " + did);

            parent.cli.progressBar.start();

            ddo = parent.cli.getOceanAPI().getAssetsAPI()
                    .resolve(new DID(did));

            System.out.println(CommandLineUtils.prettyJson(ddo.getMetadataService().toJson()));

        } catch (DDOException | DIDFormatException | EthereumException | JsonProcessingException e) {
            throw new CLIException("Error resolving DDO: " + e.getMessage());
        } finally {
            parent.cli.progressBar.doStop();
        }
        return CommandResult.successResult().setResult(ddo);
    }

    @Override
    public Object call() throws CLIException {
        return resolve();
    }
}

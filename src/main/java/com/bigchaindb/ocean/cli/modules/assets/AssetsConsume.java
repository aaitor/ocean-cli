package com.bigchaindb.ocean.cli.modules.assets;

import com.bigchaindb.ocean.cli.AssetsCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.oceanprotocol.squid.exceptions.ConsumeServiceException;
import com.oceanprotocol.squid.exceptions.DIDFormatException;
import com.oceanprotocol.squid.models.DID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "consume",
        description = "Download a previously ordered asset given a DID")
public class AssetsConsume implements Callable {

    private static final Logger log = LogManager.getLogger(AssetsConsume.class);

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @CommandLine.Parameters(index = "0")
    String did;

    @CommandLine.Option(names = { "-a", "--serviceAgreementId" }, required = true, description = "service agreement id")
    String serviceAgreementId;

    @CommandLine.Option(names = { "-s", "--serviceDefinitionId" }, required = false, description = "service definition id to consume")
    String serviceDefinitionId= "0";


    @CommandLine.Option(names = { "-p", "--path" }, required = false, description = "path where to download the asset")
    String path= "";

    CommandResult consume() throws CLIException {
        try {
            if (null == path || path.isEmpty())
                path= parent.cli.getMainConfig().getString("consume.basePath");

            System.out.println("Downloading asset with DID " + did);

            DID assetDid= new DID(did);

            parent.cli.progressBar.start();

            Boolean status = parent.cli.getOceanAPI().getAssetsAPI()
                    .consume(serviceAgreementId, assetDid, serviceDefinitionId, path);

            if (status)
                System.out.println("Files downloaded to " + path);
            else
                throw new CLIException("Unable to download files to " + path);

        } catch (DIDFormatException | ConsumeServiceException e) {
            throw new CLIException(e.getMessage());
        } finally {
            parent.cli.progressBar.doStop();
        }
        return CommandResult.successResult();
    }


    @Override
    public CommandResult call() throws CLIException {
        return consume();
    }
}

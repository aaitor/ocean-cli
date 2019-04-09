package com.github.aaitor.cli.assets;

import com.github.aaitor.cli.AssetsCLI;
import com.oceanprotocol.squid.exceptions.ConsumeServiceException;
import com.oceanprotocol.squid.exceptions.DIDFormatException;
import com.oceanprotocol.squid.exceptions.OrderException;
import com.oceanprotocol.squid.models.DID;
import com.oceanprotocol.squid.models.asset.OrderResult;
import com.oceanprotocol.squid.models.service.Service;
import io.reactivex.Flowable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "consume",
        description = "Download a previously ordered asset given a DID")
public class AssetsConsume implements Runnable {

    private static final Logger log = LogManager.getLogger(AssetsConsume.class);

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @CommandLine.Parameters(index = "0")
    String did;

    @CommandLine.Option(names = { "-s", "--serviceDefinitionId" }, required = false, description = "service definition id to consume")
    String serviceDefinitionId= "0";

    @CommandLine.Option(names = { "-p", "--path" }, required = false, description = "path where to download the asset")
    String path= "";

    void consume() {
        try {
            if (null == path || path.isEmpty())
                path= parent.cli.getNetworkConfig().getString("consume.basePath");

            log.info("Consuming did " + did);

            DID assetDid= new DID(did);

            Boolean status = parent.cli.getOceanAPI().getAssetsAPI()
                    .consume(serviceDefinitionId, assetDid, Service.DEFAULT_ACCESS_SERVICE_ID, path);

            if (status)
                log.info("Files downloaded to " + path);
            else
                log.error("Unable to download files to " + path);

        } catch (DIDFormatException | ConsumeServiceException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public void run() {
        consume();
    }
}

package com.bigchaindb.ocean.cli.assets;

import com.bigchaindb.ocean.cli.AssetsCLI;
import com.fasterxml.jackson.core.type.TypeReference;
import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.asset.AssetMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@CommandLine.Command(
        name = "import",
        description = "Import an asset using the metadata in JSON format ")
public class AssetsImport implements Runnable {

    private static final Logger log = LogManager.getLogger(AssetsImport.class);

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0")
    String metadataFile;

    void importAsset() {
        try {
            log.info("Importing asset");

            DDO ddo = parent.cli.getOceanAPI().getAssetsAPI()
                    .create(assetMetadataBuilder(metadataFile), parent.serviceEndpointsBuilder());

            System.out.println("Asset Created: " + ddo.getDid().toString());

        } catch (IOException e) {
            log.error("Error parsing metadata");
            log.error(e.getMessage());
        } catch (DDOException e) {
            log.error(e.getMessage());
        }
    }


    AssetMetadata assetMetadataBuilder(String filePath) throws IOException {
        String jsonContent =  new String(Files.readAllBytes(Paths.get(filePath)));
        return DDO.fromJSON(new TypeReference<AssetMetadata>() {}, jsonContent);
    }

    @Override
    public void run() {
        importAsset();
    }
}

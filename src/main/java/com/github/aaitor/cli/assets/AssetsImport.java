package com.github.aaitor.cli.assets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.aaitor.cli.AssetsCLI;
import com.github.aaitor.cli.utils.Constants;
import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.asset.AssetMetadata;
import com.oceanprotocol.squid.models.service.ProviderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@CommandLine.Command(
        name = "import",
        description = "Import an asset using the metadata in JSON format ")
public class AssetsImport implements Runnable {

    private static final Logger log = LogManager.getLogger(AssetsImport.class);

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(names = { "-m", "--metadata" }, required = true, description = "Asset metadata")
    String metadataFile;

    void create() {
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
        create();
    }
}

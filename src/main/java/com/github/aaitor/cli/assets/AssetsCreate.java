package com.github.aaitor.cli.assets;

import com.github.aaitor.cli.AssetsCLI;
import com.github.aaitor.cli.utils.Constants;
import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.exceptions.DIDFormatException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.asset.AssetMetadata;
import com.oceanprotocol.squid.models.service.ProviderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@CommandLine.Command(
        name = "create",
        description = "Create an Asset")
public class AssetsCreate implements Runnable {

    private static final Logger log = LogManager.getLogger(AssetsCreate.class);

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    // $ ocean-cli assets create --title title --dateCreated 2019
    // --author aitor --license CC-BY --contentType text/csv --price 10
    // --url https://raw.githubusercontent.com/oceanprotocol/OEPs/master/8/README.md

    @CommandLine.Option(names = { "-t", "--title" }, required = true, description = "the asset title")
    String title;

    @CommandLine.Option(names = { "-d", "--dateCreated" }, required = true,
            description = "when the file was created (format: " + DDO.DATE_PATTERN + ", example: 2012-10-10T17:00:000Z)")
    String dateCreated;

    @CommandLine.Option(names = { "-a", "--author" }, required = true, description = "the author of the file")
    String author;

    @CommandLine.Option(names = { "-l", "--license" }, required = false,
            defaultValue = "No License Specified", description = "license of the asset, default value: ${DEFAULT-VALUE}")
    String license;

    @CommandLine.Option(names = { "-c", "--contentType" }, required = true, description = "file content type")
    String contentType;

    @CommandLine.Option(names = { "-p", "--price" }, required = true, description = "the asset price")
    BigInteger price;

    @CommandLine.Option(names = { "-u", "--url" }, required = true, description = "the asset url")
    String url;

    void create() {
        try {
            log.info("Creating a new asset");

            DDO ddo = parent.cli.getOceanAPI().getAssetsAPI()
                    .create(assetMetadataBuilder(), serviceEndpointsBuilder());

            System.out.println("Asset Created: " + ddo.getDid().toString());

        } catch (ParseException e) {
            log.error("Error parsing date. Expected format: " + DDO.DATE_PATTERN);
            log.error(e.getMessage());
        } catch (DDOException e) {
            log.error(e.getMessage());
        }
    }

    ProviderConfig serviceEndpointsBuilder()  {

        return new ProviderConfig(
                parent.cli.getNetworkConfig().getString("brizo.url") + Constants.consumeUri,
                parent.cli.getNetworkConfig().getString("brizo.url") + Constants.initializeUri,
                parent.cli.getNetworkConfig().getString("aquarius.url") + Constants.metadataUri,
                parent.cli.getNetworkConfig().getString("secretstore.url"),
                parent.cli.getNetworkConfig().getString("provider.address")
        );

    }

    AssetMetadata assetMetadataBuilder() throws ParseException {
        AssetMetadata metadata= AssetMetadata.builder();

        metadata.base.name= title;
        metadata.base.type= AssetMetadata.assetTypes.dataset.toString();
        metadata.base.dateCreated= new SimpleDateFormat(DDO.DATE_PATTERN, Locale.ENGLISH).parse(dateCreated);
        metadata.base.author= author;
        metadata.base.license= license;
        metadata.base.price= price.toString();
        ArrayList<AssetMetadata.File> files= new ArrayList<>();
        AssetMetadata.File file = new AssetMetadata.File();
        file.index= 0;
        file.url= url;
        file.contentType= contentType;
        files.add(file);
        metadata.base.files = files;

        return metadata;
    }

    @Override
    public void run() {
        create();
    }
}

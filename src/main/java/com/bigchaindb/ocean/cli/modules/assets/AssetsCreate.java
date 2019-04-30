package com.bigchaindb.ocean.cli.modules.assets;

import com.bigchaindb.ocean.cli.AssetsCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.asset.AssetMetadata;
import picocli.CommandLine;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "create",
        description = "Create an Asset")
public class AssetsCreate implements Callable {

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    // $ ocean-cli assets create --title title --dateCreated 2012-10-10T17:00:000Z
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

    CommandResult create() throws CLIException {

        DDO ddo;
        try {
            System.out.println("Creating a new asset");

            parent.cli.progressBar.start();

            ddo = parent.cli.getOceanAPI().getAssetsAPI()
                    .create(assetMetadataBuilder(), parent.serviceEndpointsBuilder());

            System.out.println();
            System.out.println("Asset Created: " + ddo.getDid().toString());

        } catch (ParseException e) {
            throw new CLIException("Error parsing date. Expected format: " + DDO.DATE_PATTERN + "\n" + e.getMessage());
        } catch (DDOException e) {
            throw new CLIException("Error with DDO " + e.getMessage());
        } finally {
            parent.cli.progressBar.doStop();
        }

        return CommandResult.successResult().setResult(ddo);
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
    public Object call() throws CLIException {
        return create();
    }
}

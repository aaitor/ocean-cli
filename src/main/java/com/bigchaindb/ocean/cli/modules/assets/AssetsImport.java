package com.bigchaindb.ocean.cli.modules.assets;

import com.bigchaindb.ocean.cli.AssetsCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.asset.AssetMetadata;
import com.oceanprotocol.squid.models.service.ComputingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "import",
        description = "Import an asset using the metadata in JSON format ")
public class AssetsImport implements Callable {

    private static final Logger log = LogManager.getLogger(AssetsImport.class);

    private static final String  ACCESS_SERVICE_TYPE  = "access";
    private static final String  COMPUTING_SERVICE_TYPE  = "computing";

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(names = { "-t", "--serviceType" }, required = true, description = "service type: Allowed types: access, computing")
    String serviceType;

    @CommandLine.Option(names = { "-m", "--metadata" }, required = true, description = "metadata json file")
    String metadataFile;

    @CommandLine.Option(names = { "-p", "--provider" }, required = false, description = "provider json file. Only needed for computing services")
    String providerFile;

    CommandResult importAsset() throws CLIException {

        DDO ddo = null;

        try {
            System.out.println("Importing asset using file " + metadataFile);

            parent.cli.progressBar.start();

            if (serviceType.equals(ACCESS_SERVICE_TYPE))
                ddo = parent.cli.getOceanAPI().getAssetsAPI()
                        .create(assetMetadataBuilder(metadataFile), parent.serviceEndpointsBuilder());

            else if (serviceType.equals(COMPUTING_SERVICE_TYPE)) {

                if (providerFile == null || providerFile.isEmpty())
                    throw new CLIException("A Provider json file is needed to create a computing service");

                ddo = parent.cli.getOceanAPI().getAssetsAPI()
                        .createComputingService(assetMetadataBuilder(metadataFile), parent.serviceEndpointsBuilder(), providerBuilder(providerFile));
            }

            System.out.println("Asset Created: " + ddo.getDid().toString());

        } catch (IOException e) {
            throw new CLIException("Error parsing metadata " + e.getMessage());
        } catch (DDOException e) {
            throw new CLIException("Error with DDO " + e.getMessage());
        }catch (CLIException e) {
            throw e;
        } finally {
            parent.cli.progressBar.doStop();
        }
        return CommandResult.successResult().setResult(ddo);

    }

    AssetMetadata assetMetadataBuilder(String filePath) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        return DDO.fromJSON(new TypeReference<AssetMetadata>() {
        }, jsonContent);
    }

    ComputingService.Provider providerBuilder(String filePath) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        return DDO.fromJSON(new TypeReference<ComputingService.Provider>() {
        }, jsonContent);
    }

    @Override
    public Object call() throws CLIException {
        return importAsset();
    }
}
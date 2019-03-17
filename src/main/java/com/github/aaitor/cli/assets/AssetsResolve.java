package com.github.aaitor.cli.assets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.aaitor.cli.AssetsCLI;
import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.exceptions.DIDFormatException;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.DID;
import com.oceanprotocol.squid.models.asset.AssetMetadata;
import com.oceanprotocol.squid.models.service.ServiceEndpoints;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@CommandLine.Command(
        name = "resolve",
        description = "Resolve an asset using a given DID")
public class AssetsResolve implements Runnable {

    private static final Logger log = LogManager.getLogger(AssetsResolve.class);

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @CommandLine.Parameters(index = "0")
    String did;

    void resolve() {
        try {
            log.info("Resolving did " + did);

            DDO ddo = parent.cli.getOceanAPI().getAssetsAPI()
                    .resolve(new DID(did));

            System.out.println(ddo.toJson().toString());

        } catch (DDOException | DIDFormatException | EthereumException | JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        resolve();
    }
}

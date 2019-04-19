package com.bigchaindb.ocean.cli.assets;

import com.bigchaindb.ocean.cli.AssetsCLI;
import com.bigchaindb.ocean.cli.model.CommandResult;
import com.bigchaindb.ocean.cli.model.exceptions.CLIException;
import com.oceanprotocol.squid.exceptions.DIDFormatException;
import com.oceanprotocol.squid.exceptions.OrderException;
import com.oceanprotocol.squid.models.DID;
import com.oceanprotocol.squid.models.asset.OrderResult;
import io.reactivex.Flowable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "order",
        description = "Order an asset given DID")
public class AssetsOrder implements Callable {

    private static final Logger log = LogManager.getLogger(AssetsOrder.class);

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @CommandLine.Parameters(index = "0")
    String did;

    @CommandLine.Option(names = { "-s", "--serviceDefinitionId" }, required = false, description = "service definition id to order")
    String serviceDefinitionId= "0";

    CommandResult order() throws CLIException {

        OrderResult orderResult;
        try {
            System.out.println("Ordering did " + did);

            parent.cli.progressBar.start();

            DID assetDid= new DID(did);

            Flowable<OrderResult> response = parent.cli.getOceanAPI().getAssetsAPI()
                    .order(assetDid, serviceDefinitionId);

            orderResult = response.blockingFirst();

            if (null == orderResult.getServiceAgreementId()) {
                throw new CLIException("Unable to initialize order");
            }

            if (orderResult.isAccessGranted())
                System.out.println("Access Granted. ServiceAgreementId: " + orderResult.getServiceAgreementId());
            else
                throw new CLIException("Access Not Granted. ServiceAgreementId: " + orderResult.getServiceAgreementId());


        } catch (DIDFormatException | OrderException e) {
            throw new CLIException(e.getMessage());
        } finally {
            parent.cli.progressBar.doStop();
        }
        return CommandResult.successResult().setResult(orderResult);

    }

    @Override
    public CommandResult call() throws CLIException {
        return order();
    }
}

package com.bigchaindb.ocean.cli.assets;

import com.bigchaindb.ocean.cli.AssetsCLI;
import com.oceanprotocol.squid.exceptions.DIDFormatException;
import com.oceanprotocol.squid.exceptions.OrderException;
import com.oceanprotocol.squid.models.DID;
import com.oceanprotocol.squid.models.asset.OrderResult;
import io.reactivex.Flowable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "order",
        description = "Order an asset given DID")
public class AssetsOrder implements Runnable {

    private static final Logger log = LogManager.getLogger(AssetsOrder.class);

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @CommandLine.Parameters(index = "0")
    String did;

    @CommandLine.Option(names = { "-s", "--serviceDefinitionId" }, required = false, description = "service definition id to order")
    String serviceDefinitionId= "0";

    void order() {
        try {
            log.info("Ordering did " + did);

            DID assetDid= new DID(did);

            Flowable<OrderResult> response = parent.cli.getOceanAPI().getAssetsAPI()
                    .order(assetDid, serviceDefinitionId);

            OrderResult orderResult = response.blockingFirst();

            if (null == orderResult.getServiceAgreementId()) {
                log.error("Unable to initialize order");
                return;
            }

            if (orderResult.isAccessGranted())
                log.info("Access Granted. ServiceAgreementId: " + orderResult.getServiceAgreementId());
            else
                log.error("Access Not Granted. ServiceAgreementId: " + orderResult.getServiceAgreementId());


        } catch (DIDFormatException | OrderException e) {
            log.error(e.getMessage());
        }
    }
/*

    private AccessService getAccessService(String serviceDefinitionId) throws ServiceException {
        for (Service service: services) {
            if (service.serviceDefinitionId.equals(serviceDefinitionId) && service.type.equals(Service.serviceTypes.Access.toString())) {
                return (AccessService) service;
            }
        }
        throw new ServiceException("Access Service with serviceDefinitionId=" + serviceDefinitionId + " not found");
    }
*/

    @Override
    public void run() {
        order();
    }
}

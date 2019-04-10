package com.bigchaindb.ocean.cli.tokens;

import com.bigchaindb.ocean.cli.TokensCLI;
import com.oceanprotocol.squid.exceptions.EthereumException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.math.BigInteger;

import static com.bigchaindb.ocean.cli.utils.Constants.TransactionSuccess;

@CommandLine.Command(
        name = "request",
        description = "Request some TokensCLI")
public class TokensRequest implements Runnable {

    private static final Logger log = LogManager.getLogger(TokensRequest.class);

    @CommandLine.ParentCommand
    TokensCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0")
    BigInteger numberTokens;

    void request() {
        try {
            log.info("Requesting " + numberTokens.longValue() +
                    " tokens for " + parent.cli.getOceanAPI().getMainAccount().getAddress() +
                    " address");

            String status= parent.cli.getOceanAPI().getTokensAPI()
                    .request(numberTokens)
                    .getStatus();

            if (status.equals(TransactionSuccess))
                System.out.println("Success!");
        } catch (EthereumException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        request();
    }
}

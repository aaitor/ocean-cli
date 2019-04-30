package com.bigchaindb.ocean.cli.modules.tokens;

import com.bigchaindb.ocean.cli.TokensCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.oceanprotocol.squid.exceptions.EthereumException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.math.BigInteger;
import java.util.concurrent.Callable;

import static com.bigchaindb.ocean.cli.helpers.Constants.TransactionSuccess;

@CommandLine.Command(
        name = "request",
        description = "Request some Ocean Tokens")
public class TokensRequest implements Callable {

    private static final Logger log = LogManager.getLogger(TokensRequest.class);

    @CommandLine.ParentCommand
    TokensCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0")
    BigInteger numberTokens;

    CommandResult request() throws CLIException {
        try {
            System.out.println("Requesting " + numberTokens.longValue() +
                    " Ocean Token/s for " + parent.cli.getOceanAPI().getMainAccount().getAddress() +
                    " address");

            parent.cli.progressBar.start();

            String status= parent.cli.getOceanAPI().getTokensAPI()
                    .request(numberTokens)
                    .getStatus();

            if (status.equals(TransactionSuccess))
                System.out.println("\nSuccess!\n");

        } catch (EthereumException e) {
            log.error(e.getMessage());
            throw new CLIException(e.getMessage());
        } finally {
            parent.cli.progressBar.doStop();
        }
        return CommandResult.successResult();

    }

    @Override
    public CommandResult call() throws CLIException {
        return request();
    }
}

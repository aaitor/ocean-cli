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
        name = "transfer",
        description = "Transfer TokensCLI between accounts")
public class TokensTransfer implements Callable {

    private static final Logger log = LogManager.getLogger(TokensTransfer.class);

    @CommandLine.ParentCommand
    TokensCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0")
    String receiverAddress;

    @CommandLine.Parameters(index = "1")
    BigInteger drops;

    CommandResult transfer() throws CLIException {
        try {
            System.out.println("Transfering " + drops.longValue() +
                    " Ocean Vodkas from " + parent.cli.getOceanAPI().getMainAccount().getAddress() +
                    " to " + receiverAddress);

            parent.cli.progressBar.start();

            String status= parent.cli.getOceanAPI().getTokensAPI()
                    .transfer(receiverAddress, drops)
                    .getStatus();

            if (status.equals(TransactionSuccess))
                System.out.println("Success!");
        } catch (EthereumException e) {
            log.error(e.getMessage());
            throw new CLIException(e.getMessage());
        } finally {
            parent.cli.progressBar.doStop();
        }
        return CommandResult.successResult();    }

    @Override
    public CommandResult call() throws CLIException {
        return transfer();
    }
}

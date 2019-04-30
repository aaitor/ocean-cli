package com.bigchaindb.ocean.cli.modules.accounts;

import com.bigchaindb.ocean.cli.AccountsCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.models.Account;
import com.oceanprotocol.squid.models.Balance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "balance",
        description = "Get the balance of an account")
public class AccountsBalance implements Callable {

    private static final Logger log = LogManager.getLogger(AccountsBalance.class);

    @CommandLine.ParentCommand
    AccountsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0")
    String accountAddress;

    CommandResult balance() throws CLIException {
        Balance balance;
        try {
            System.out.println("Account: " + accountAddress + "\n");

            balance = parent.cli.getOceanAPI().getAccountsAPI()
                    .balance(new Account(accountAddress, null));


            System.out.println("Balance in current network (" + parent.cli.getMainConfig().getString("network")+ "):"
                    + "\n\tPOA Ether: " + balance.getEth() + " ETH"
                    + "\n\tOcean Tokens: " + balance.getOceanTokens() + " OCEAN = " + balance.getDrops() + " Vodkas");

        } catch (EthereumException e) {
            log.error(e.getMessage());
            throw new CLIException(e.getMessage());
        }

        return CommandResult.successResult();

    }

    @Override
    public CommandResult call() throws CLIException {
        return balance();
    }
}

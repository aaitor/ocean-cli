package com.bigchaindb.ocean.cli.modules.accounts;

import com.bigchaindb.ocean.cli.AccountsCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.models.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "list",
        description = "List all the existing accounts")
public class AccountsList implements Callable {

    private static final Logger log = LogManager.getLogger(AccountsList.class);

    @CommandLine.ParentCommand
    AccountsCLI parent;

    CommandResult list() {
        log.debug("Listing accounts:");
        List<Account> accounts = null;
        try {
            accounts= parent.cli.getOceanAPI().getAccountsAPI().list();
        } catch (EthereumException e) {
            log.error("Unable to retrieve accounts info: " + e.getMessage());
        }

        for (Account account: accounts) {
            System.out.println(account.getAddress());
        }
        return CommandResult.successResult();
    }

    @Override
    public CommandResult call() {
        return list();
    }
}

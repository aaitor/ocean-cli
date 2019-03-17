package com.github.aaitor.cli.accounts;

import com.github.aaitor.OceanCLI;
import com.github.aaitor.cli.Accounts;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.models.Account;
import com.oceanprotocol.squid.models.Balance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(
        name = "list",
        description = "List all the existing accounts")
public class AccountsList implements Runnable {

    private static final Logger log = LogManager.getLogger(AccountsList.class);

    @CommandLine.ParentCommand
    Accounts parent;

    void list() {
        log.debug("Listing accounts:");
        List<Account> accounts = null;
        try {
            accounts= parent.cli.getOceanAPI().getAccountsAPI().list();
        } catch (EthereumException e) {
            log.error("Unable to retrieve accounts info: " + e.getMessage());
        }

        for (Account account: accounts) {
            log.info("Account: " + account.toString());
        }
    }

    @Override
    public void run() {
        list();
    }
}

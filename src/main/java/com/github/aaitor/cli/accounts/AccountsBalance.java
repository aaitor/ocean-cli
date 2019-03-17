package com.github.aaitor.cli.accounts;

import com.github.aaitor.cli.Accounts;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.models.Account;
import com.oceanprotocol.squid.models.Balance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(
        name = "balance",
        description = "Get the balance of an account")
public class AccountsBalance implements Runnable {

    private static final Logger log = LogManager.getLogger(AccountsBalance.class);

    @CommandLine.ParentCommand
    Accounts parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0")
    String accountAddress;

    void balance() {
        try {
            Balance balance = parent.cli.getOceanAPI().getAccountsAPI()
                    .balance(new Account(accountAddress, null));

            log.info("Account [" + accountAddress + "] balance: " + balance.toString());
        } catch (EthereumException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        balance();
        //spec.commandLine().usage(System.out);
    }
}

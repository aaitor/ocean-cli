package com.bigchaindb.ocean.cli.modules.accounts;

import com.bigchaindb.ocean.cli.AccountsCLI;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.models.Account;
import com.oceanprotocol.squid.models.Balance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "balance",
        description = "Get the balance of an account")
public class AccountsBalance implements Runnable {

    private static final Logger log = LogManager.getLogger(AccountsBalance.class);

    @CommandLine.ParentCommand
    AccountsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0")
    String accountAddress;

    void balance() {
        try {
            Balance balance = parent.cli.getOceanAPI().getAccountsAPI()
                    .balance(new Account(accountAddress, null));

            System.out.println("Account: " + accountAddress);
            System.out.println("Balance: " + balance.toString());
        } catch (EthereumException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        balance();
    }
}

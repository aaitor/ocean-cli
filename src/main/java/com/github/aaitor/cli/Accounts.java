package com.github.aaitor.cli;

import com.github.aaitor.OceanCLI;
import com.github.aaitor.cli.accounts.AccountsBalance;
import com.github.aaitor.cli.accounts.AccountsList;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.oceanprotocol.squid.models.Account;
import com.oceanprotocol.squid.models.Balance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "accounts",
        subcommands = {AccountsList.class, AccountsBalance.class},
        description = "Allowing to interact with the accounts.")
public class Accounts implements Runnable {

    private static final Logger log = LogManager.getLogger(Accounts.class);

    @CommandLine.ParentCommand
    public OceanCLI cli;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }
}

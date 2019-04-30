package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.cli.modules.accounts.AccountsBalance;
import com.bigchaindb.ocean.cli.modules.accounts.AccountsList;
import com.bigchaindb.ocean.OceanCLI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "accounts",
        subcommands = {AccountsList.class, AccountsBalance.class},
        description = "Allowing to interact with the accounts.")
public class AccountsCLI implements Runnable {

    private static final Logger log = LogManager.getLogger(AccountsCLI.class);

    @CommandLine.ParentCommand
    public OceanCLI cli;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }
}

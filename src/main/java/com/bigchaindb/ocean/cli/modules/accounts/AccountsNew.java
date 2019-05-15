package com.bigchaindb.ocean.cli.modules.accounts;

import com.bigchaindb.ocean.cli.AccountsCLI;
import com.bigchaindb.ocean.cli.helpers.AccountsHelper;
import com.bigchaindb.ocean.cli.helpers.Constants;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.google.common.base.Charsets;
import com.oceanprotocol.squid.exceptions.EthereumException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.CipherException;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Properties;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "new",
        description = "Create a new account")
public class AccountsNew implements Callable {

    private static final Logger log = LogManager.getLogger(AccountsNew.class);

    @CommandLine.ParentCommand
    AccountsCLI parent;

    @CommandLine.Option(names = { "-p", "--password" }, required = false, description = "new account password, if it's not given will be auto-generated")
    String password;

    @CommandLine.Option(names = { "-d", "--destination" }, required = false, description = "destination path")
    String filePath;

    @CommandLine.Option(names = { "-m", "--main" }, required = false, defaultValue = "false", description = "configure the new account as main account in the config file")
    boolean makeMainAccount;

    CommandResult newAccount() throws CLIException {
        log.debug("Creating new account:");

        try {
            if (null == filePath || filePath.isEmpty())
                filePath= Constants.accountsFolder;

            String accountPath= AccountsHelper.createAccount(password, filePath);
            String address= AccountsHelper.getAddressFromFilePath(accountPath);

            System.out.println("Account for address " + address + " created at " + filePath + File.separator + accountPath);

            if (makeMainAccount)    {
                System.out.println("Over-writing config file");
                if (!setupNewAccountAsDefault(address, password, filePath + File.separator + accountPath)) {
                    log.error("Unable to setup account " + address + " as default in the configuration");
                    return CommandResult.errorResult();
                }
                System.out.println("New account " + address + " added as default account in the configuration");
            }

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | CipherException | IOException e) {
            log.error("Unable to create account: " + e.getMessage());
            throw new CLIException("Unable to create account: " + e.getMessage());
        }

        return CommandResult.successResult();
    }

    @Override
    public CommandResult call() throws CLIException {
        return newAccount();
    }

    private boolean setupNewAccountAsDefault(String address, String password, String filePath)  {

        try {
            String defaultConfigContent= FileUtils.readFileToString(new File(Constants.mainConfigFile), Charsets.UTF_8);
            String newConfigContent= defaultConfigContent
                    .replaceAll("account.main.address=\"(.*?)\"", "account.main.address=\""+ address +"\"")
                    .replaceAll("account.main.password=\"(.*?)\"", "account.main.password=\""+ password +"\"")
                    .replaceAll("account.main.credentialsFile=\"(.*?)\"", "account.main.credentialsFile=\""+ filePath +"\"");


            FileUtils.writeStringToFile(new File(Constants.mainConfigFile), newConfigContent, Charsets.UTF_8);

            /*Properties properties = new Properties();
            parent.cli.getMainConfig().entrySet().forEach(e ->
                    properties.setProperty(e.getKey(), parent.cli.getMainConfig().getString(e.getKey()))
            );

            properties.setProperty("account.main.address", address);
            properties.setProperty("account.main.password", password);
            properties.setProperty("account.main.credentialsFile", filePath);

            //File targetFile = new File(Constants.mainConfigFile);
            properties.store(new FileWriter(Constants.mainConfigFile), null);
*/


            return true;
        } catch (IOException ex)    {
            log.error("Unable to setup account as default: " + ex.getMessage());
        }

        return false;
    }
}

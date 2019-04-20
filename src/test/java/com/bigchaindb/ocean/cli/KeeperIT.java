package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.OceanCLI;
import com.bigchaindb.ocean.cli.model.CommandResult;
import com.bigchaindb.ocean.cli.model.exceptions.CLIException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.asset.OrderResult;
import org.junit.Test;
import picocli.CommandLine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KeeperIT extends CLITestingHelper {


    @Test
    public void keeperList() throws CLIException {
        String[] args= {"keeper", "list"};
        CommandResult result = (CommandResult) CommandLine.call(new OceanCLI(), args);
        assertTrue(result.isSuccess());

    }

    @Test
    public void keeperDescribe() throws CLIException {
        String[] args= {"keeper", "describe", "OceanToken"};
        CommandResult result = (CommandResult) CommandLine.call(new OceanCLI(), args);
        assertTrue(result.isSuccess());

    }

}
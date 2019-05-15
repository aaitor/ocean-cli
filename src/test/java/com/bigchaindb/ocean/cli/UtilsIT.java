package com.bigchaindb.ocean.cli;

import com.bigchaindb.ocean.OceanCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import org.junit.Test;
import picocli.CommandLine;

import static org.junit.Assert.assertTrue;

public class UtilsIT {


    @Test
    public void utilsInfo() throws CLIException {
        String[] args= {"utils", "info", "https://oceanprotocol.com/tech-whitepaper.pdf"};
        CommandResult result = (CommandResult) CommandLine.call(new OceanCLI(), args);
        assertTrue(result.isSuccess());

    }


}
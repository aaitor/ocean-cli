package com.bigchaindb.ocean.cli.models.exceptions;

import com.bigchaindb.ocean.cli.modules.assets.AssetsSearch;
import com.oceanprotocol.squid.exceptions.OceanException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CLIException extends OceanException {

    private static final Logger log = LogManager.getLogger(AssetsSearch.class);

    public CLIException(String message, Throwable e) {
        super(message, e);
    }

    public CLIException(String message) {
        super(message);
        log.error(message);
    }
}

package com.github.aaitor.dto;

import com.oceanprotocol.squid.api.OceanAPI;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SquidBase {

    private static final Logger log = LogManager.getLogger(SquidBase.class);

    private final Config config = ConfigFactory.load();
    private OceanAPI oceanAPI;


    public SquidBase() {
        try {

            oceanAPI = OceanAPI.getInstance(config);

        } catch (Exception e) {
            log.error("Unable to initialize Ocean connections: " + e.getMessage());
        }
    }

    public OceanAPI getOceanAPI()   {
        return oceanAPI;
    }

    public Config getConfig()    {
        return config;
    }

}

package com.bigchaindb.ocean.dto;

import com.bigchaindb.ocean.cli.model.exceptions.CLIException;
import com.bigchaindb.ocean.cli.utils.Constants;
import com.bigchaindb.ocean.cli.utils.ProgressBar;
import com.oceanprotocol.squid.api.OceanAPI;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Properties;

public class SquidBase {

    private static final Logger log = LogManager.getLogger(SquidBase.class);

    private Config mainConfig;
    private Config networkConfig= null;
    private String networkName;
    private OceanAPI oceanAPI;

    public ProgressBar progressBar= new ProgressBar();

    public SquidBase() throws CLIException {
        try {
            mainConfig=  ConfigFactory.load();
        } catch (Exception e) {
            log.error("Unable load the main config file: " + e.getMessage());
            throw new CLIException("Unable load the main config file: " + e.getMessage());
        }

        try {

            networkName= mainConfig.getString("network");
            String networkFile= Constants.networkFolder + "/" + networkName + ".conf";
            if (!isConfigurationAvailable(networkFile))    {
                log.error("Unable to load network config file " + networkFile);
                log.error("Please, copy the config file to the " + networkFile + " path");
                System.exit(1);
            }

            networkConfig= ConfigFactory.parseFile(new File(networkFile));
            oceanAPI = OceanAPI.getInstance(joinConfig(networkConfig, mainConfig));

            progressBar.setSpinner(mainConfig.getInt("spinner.id"));

        } catch (Exception e) {
            log.error("Unable to initialize Ocean connections: " + e.getMessage());
            throw new CLIException("Unable to initialize Ocean connections: " + e.getMessage());
        }
    }

    public OceanAPI getOceanAPI()   {
        return oceanAPI;
    }

    public Config getNetworkConfig()    {
        return networkConfig;
    }

    public Config getMainConfig()    {
        return mainConfig;
    }

    private boolean isConfigurationAvailable(String file)    {
        File configFile = new File(file);
        return configFile.exists();
    }

    private static Properties joinConfig(Config c1, Config c2) {
        Properties properties = new Properties();
        c1.entrySet().forEach(e -> properties.setProperty(e.getKey(), c1.getString(e.getKey())));
        c2.entrySet().forEach(e -> properties.setProperty(e.getKey(), c2.getString(e.getKey())));
        return properties;
    }

}

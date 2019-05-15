package com.bigchaindb.ocean.dto;

import com.bigchaindb.ocean.cli.helpers.Constants;
import com.bigchaindb.ocean.cli.helpers.ProgressBar;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.oceanprotocol.squid.api.OceanAPI;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
            initializeBaseConfig();

            mainConfig=  ConfigFactory.load();
        } catch (Exception e) {
            log.error("Unable load the main config file: " + e.getMessage());
            throw new CLIException("Unable load the main config file: " + e.getMessage());
        }


        if (!fileExists(Constants.mainConfigFile))    {
            copyResourceFileToPath("src/main/resources/application.conf", Constants.mainConfigFile);
        }

        try {

            networkName= mainConfig.getString("network");
            String networkFile= Constants.networkFolder + File.separator + networkName + ".conf";
            if (!fileExists(networkFile))    {
                copyResourceFileToPath("src/main/resources/networks/" + networkName + ".conf", networkFile);
            }

            if (!fileExists(networkFile))    {
                log.error("Unable to load network config file " + networkFile);
                log.error("Please, copy the config file to the " + networkFile + " path");
                throw new CLIException("Unable load the network config file: " + networkFile);
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

    private boolean fileExists(String path)    {
        File configFile = new File(path);
        return configFile.exists();
    }

    private boolean initializeBaseConfig() throws CLIException {
        if (!fileExists(Constants.configFolder))    {
            boolean success = (new File(Constants.configFolder)).mkdirs();
            if (!success) {
                log.error("Unable to create main config folder " + Constants.configFolder);
                throw new CLIException("Unable to create main config folder " + Constants.configFolder);
            }

        }

        if (!fileExists(Constants.accountsFolder))    {
            boolean success = (new File(Constants.accountsFolder)).mkdirs();
        }


        if (!fileExists(Constants.logsConfigFile))    {
            copyResourceFileToPath("src/main/resources/log4j2.properties", Constants.logsConfigFile);
        }

        if (!fileExists(Constants.mainConfigFile))    {
            copyResourceFileToPath("src/main/resources/application.conf", Constants.mainConfigFile);
        }
        return true;
    }

    private static Properties joinConfig(Config c1, Config c2) {
        Properties properties = new Properties();
        c1.entrySet().forEach(e -> properties.setProperty(e.getKey(), c1.getString(e.getKey())));
        c2.entrySet().forEach(e -> properties.setProperty(e.getKey(), c2.getString(e.getKey())));
        return properties;
    }




    public static boolean copyResourceFileToPath(String inputPath, String outputPath) {
        try {
            System.out.println("Copying " + inputPath + " to " + outputPath);

            InputStream initialStream = FileUtils.openInputStream(new File(inputPath));
            File targetFile = new File(outputPath);
            FileUtils.copyInputStreamToFile(initialStream, targetFile);
            return true;
        } catch (IOException e) {
            log.error("Unable to copy " + inputPath + " to " + outputPath);
            log.error(e.getMessage());
        }
        return false;
    }

}

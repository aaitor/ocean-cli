package com.github.aaitor.cli.config;

import com.github.aaitor.cli.ConfigCLI;
import com.github.aaitor.cli.utils.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

@CommandLine.Command(
        name = "clean",
        description = "Remove any existing config")
public class ConfigClean implements Runnable {

    private static final Logger log = LogManager.getLogger(ConfigClean.class);

    @CommandLine.ParentCommand
    ConfigCLI parent;

    void clean() {
        log.debug("Cleaning existing config:");
        try {
            FileUtils.deleteDirectory(new File(Constants.configFolder));
        } catch (IOException e) {
            log.error("Unable to delete config folder " + Constants.configFolder);
        }
    }

    @Override
    public void run() {
        clean();
    }
}

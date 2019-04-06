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
        name = "show",
        description = "Show all the config information used")
public class ConfigShow implements Runnable {

    private static final Logger log = LogManager.getLogger(ConfigShow.class);

    @CommandLine.ParentCommand
    ConfigCLI parent;

    void showConfig() {
        log.info("Main config:");
        parent.cli.getMainConfig().entrySet().forEach(e ->{
            System.out.println("  " + e.getKey() + ": " + e.getValue().render());
        });

        log.info("Network config:");
        parent.cli.getNetworkConfig().entrySet().forEach(e -> {
            System.out.println("  " + e.getKey() + ": " + e.getValue().render());
        });

    }

    @Override
    public void run() {
        showConfig();
    }
}

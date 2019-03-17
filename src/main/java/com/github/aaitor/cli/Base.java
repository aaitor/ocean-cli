package com.github.aaitor.cli;

import com.github.aaitor.OceanCLI;
import picocli.CommandLine;

@CommandLine.Command(name = "base",
        description = "Base functions."
)
public class Base implements Runnable {

    @CommandLine.ParentCommand
    OceanCLI parent;

    @CommandLine.Option(names = {"-d", "--did"}, description = "resolves a given DID")
    private boolean isDid = false;

    void resolveDid()   {
        System.out.println("Resolving DID");
    }

    @Override
    public void run() {
        if (isDid)
            resolveDid();

        System.out.println("Base");
    }
}

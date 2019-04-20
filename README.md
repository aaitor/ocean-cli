# Ocean Command Line Interface (Ocean CLI)

> 🦑 Ocean Protocol Command Line Interface (CLI) powered by Squid Java

---


Table of Contents
=================

   * [Ocean Command Line Interface (Ocean CLI)](#ocean-command-line-interface-ocean-cli)
      * [Features](#features)
         * [Compiling](#compiling)
         * [Installation](#installation)
         * [Running Ocean CLI](#running-ocean-cli)
            * [Connecting to a different network](#connecting-to-a-different-network)
            * [Using a different logging config file](#using-a-different-logging-config-file)
      * [License](#license)


---

## Features

Ocean Protocol Command Line tool allowing to interact with the Ocean stack

This library is under heavy development

### Compiling

You can compile the application using the following command:

```bash
mvn clean package
```

### Installation

After compiling the application, you will see in the target folder a zip file with the config files required.
Extract the files in the `~/.local/share/ocean-cli` folder of the user home.

The final folder should have the following structure:

```bash
$ ls -la ~/.local/share/ocean-cli/
total 20
drwxrwxr-x  3 aitor aitor 4096 Mar 24 20:35 .
drwx------ 55 aitor aitor 4096 Mar 24 20:35 ..
-rw-rw-r--  1 aitor aitor   17 Mar 23 18:39 application.conf
-rw-rw-r--  1 aitor aitor  449 Mar 24 20:33 log4j2.properties
drwxr-xr-x  2 aitor aitor 4096 Mar 24 20:34 networks

```


### Running Ocean CLI

This should generate the JAR package in the target folder. You can run the application using the usual way (`java -jar app.jar`). 
Also, setting up an alias would make quicker and easier to run the application:
```bash
alias ocean-cli='java $OCEAN_OPTS -jar target/ocean-cli-*-shaded.jar'
```

#### Connecting to a different network

All the parameters can be modified in the config files found in `~/.local/share/ocean-cli`. Also it's possible to setup the **OCEAN_OPTS** 
environment variable overriding the options by default.

For example, connecting to a different network is so simple as setting up the following environment variable and running again the ocean-cli:

```bash
export OCEAN_OPTS=" $OCEAN_OPTS -Dnetwork=nile"
``` 

#### Using a different logging config file

```bash
export OCEAN_OPTS=" $OCEAN_OPTS -Dlog4j.configurationFile=$HOME/.local/share/ocean-cli/log4j2.properties"
``` 



```
# Show version information (DONE)
ocean-cli --version

# Show config information (DONE)
ocean-cli config show

# Clean previous configuration parameters (DONE)
ocean-cli config clean

# Show help (DONE)
ocean-cli --help

# List existing accounts  (DONE)
ocean-cli accounts list 

# Get account balance  (DONE)
ocean-cli accounts balance 0x123

# Request some tokens (DONE)
ocean-cli tokens request 5

# Transfers tokens to other account (DONE)
ocean-cli tokens transfer 0x123 5

# Creates an asset (DONE)
ocean-cli assets create --title title --dateCreated 2019 --author aitor --license CC-BY --contentType text/csv --price 10 --url https://raw.githubusercontent.com/oceanprotocol/OEPs/master/8/README.md

# Imports an asset from a JSON file (DONE)
ocean-cli assets import metadata.json

# Resolves a did (DONE)
ocean-cli assets resolve did:op:123

# Search for assets (DONE)
ocean-cli assets search query

# Order
ocean-cli assets order did:op:123 (DONE)

# Consume
ocean-cli assets consume did:op:123 (DONE)

# Get Keeper Smart Contracts
ocean-cli keeper list (DONE)

# Get Smart Contract methods
ocean-cli keeper describe OceanToken (DONE)

# Call Contract method
ocean-cli keeper exec OceanToken request 1

# Get resource remote information
ocean-cli utils info http://xxx.com/file.zip
```


## License


```

                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.


```



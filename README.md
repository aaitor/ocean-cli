# Trident
🦑 Ocean Protocol Command Line Interface (CLI) powered by Squid Java

> 🐳 Ocean Command Line Interface

---


---

## Features

Ocean Protocol Command Line tool allowing to interact with the Ocean stack

This library is under heavy development



### Running Ocean CLI

You can compile the application using the following command:

```bash
mvn clean package
```

This should generate the JAR package in the target folder. You can run the application using the usual way (`java -jar app.jar`). 
Also, setting up an alias would make quicker and easier to run the application:
```bash
alias ocean-cli="java -jar target/ocean-cli-*-shaded.jar"
```

```
# Show version information (DONE)
ocean-cli --version

# Show config information
ocean-cli --showConfig

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

# Imports an asset from a JSON file
ocean-cli assets import metadata.json

# Resolves a did
ocean-cli assets resolve did:op:123

# Search for assets
ocean-cli assets search query

# Order
ocean-cli assets order did:op:123

# Consume
ocean-cli assets consume did:op:123
```


## License

This library is a personal project implemented for fun.
It's not created as part of the Ocean Protocol Foundation.

```

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```



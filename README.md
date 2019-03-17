# Trident
🦑 Ocean Protocol Command Line Interface (CLI) powered by Squid Java

> 🐳 Ocean Command Line Interface

---


---

## Features

Ocean Protocol Command Line tool allowing to interact with the Ocean stack

This library is under heavy development


### Expected Lifecycle

```
# Show version information (DONE)
cli --version

# Show config information
cli --showConfig

# Show help (DONE)
cli --help

# List existing accounts  (DONE)
cli accounts list 

# Get account balance  (DONE)
cli accounts balance 0x123

# Request some tokens
cli tokens request 5

# Transfer tokens to other account
cli tokens transfer 0x123 5

# Creates an asset
cli assets create metadata.json

# Resolves a did
cli assets resolve did:op:123

# Search for assets
cli assets search query

# Order
cli assets order did:op:123

# Consume
cli assets consume did:op:123
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



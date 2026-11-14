# Spud's Shops — multi-loader port

One branch contains the shared code and loader integrations for this Minecraft generation.

- `common/src/main/java`: shared gameplay, storage, permissions, screens and renderers.
- `common/src/main/resources` and `common/src/main/generated/resources`: shared assets and data.
- `fabric`: Fabric entry points and platform integration.
- `neoforge`: NeoForge entry points and platform integration.

The common sources are compiled separately against each loader's Minecraft API. The small
`platform` classes have the same names and contract in both loader modules; they are never
packaged together. There is no third "common mod" to install.

## Build

Use Java 25 and run from the repository root:

```powershell
.\gradlew.bat build
.\gradlew.bat :fabric:build
.\gradlew.bat :neoforge:build
```

Artifacts are in `fabric/build/libs` and `neoforge/build/libs`. Install only the regular JAR
for the selected loader, not the `-sources.jar`. Fabric requires Fabric API.

Fabric targets Minecraft 26.1–26.1.2. The first NeoForge candidate targets 26.1.2 with
NeoForge 26.1.2.126; compatibility with earlier NeoForge/Minecraft versions is not yet claimed.

## Manual verification

Build success is not runtime verification. Use a separate test profile and a copy of a world.
Check each loader separately:

1. Startup, creative tab, all shop types, names and recipes.
2. Owner/customer screens, top and bottom shelf inventory, tabs and settings.
3. Successful and rejected purchases, full inventory and matching item components.
4. Save/restart: stock, payment, prices, owner and contracts.
5. Multiplayer: owner, customer and clerk rights, contract revocation and protected block breaking.
6. Items and prices facing north/east/south/west, including resource reload.

Report the loader/version, exact steps and logs. No assistant-run Minecraft tests are used.
Known trading/permission concerns identified before this restructuring are not fixed by it.

Original mod: https://github.com/Milo-Cat/spuds-shops
Original author: Spudacious5705. This is an unofficial port maintained by Azurblau,
with AI-assisted code adaptation.

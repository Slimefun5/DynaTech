# DynaTech

[![Build Status](https://Slimefun5.github.io/builds/Slimefun5/DynaTech/stable/badge.svg)](https://Slimefun5.github.io/builds/Slimefun5/DynaTech/stable)
![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/Slimefun5/DynaTech/total)
[![GitHub Followers](https://img.shields.io/github/followers/Slimefun5?style=social)](https://github.com/Slimefun5)
[![GitHub Stars](https://img.shields.io/github/stars/Slimefun5/DynaTech?style=social)](https://github.com/Slimefun5/DynaTech)
[![bStats](https://bStats.org/signatures/bukkit/DynaTech.svg)](https://bStats.org/plugin/bukkit/DynaTech/31440)

A Slimefun addon that adds various technological machines, generators, and tools.

## Requirements
- Java 25
- Paper 1.16.* - 26.1.*
- [Slimefun 5](https://github.com/Slimefun5/Slimefun5)

## Machines
- **Auto-Kitchen** - If you have ExoticGarden installed, this machine will become available. It automatically crafts any Kitchen recipe inserted into it.
- **Growth Chambers** - Automatically grow some plants. We have multiple variants for all your needs. Supports Exotic Garden saplings, plants, and bushes.
- **Antigravity Bubble** - Temporary creative flight within a 45 block area when powered.
- **Weather Controller** - Controls the weather when given a key item (Sunflower > Clear, Lilac > Rain, Creeper Head > Thunder).
- **Potion Sprinkler** - A ranged potion applier, the potions have durability basically. Has a 10 block range.
- **Barbed Wire** - Pushes mob back in a radius. Has a 9 block range, perfect for mob farms.
- **Material Hive** - An infinite resource generator, requires Bees and a stack of the output items. For the Material Hive, each bee you put in minuses the amount of time it takes to produce the material, so 128 bees is better then 1 bee, please note each type of bee has a different seconds minus amount. Check bee section for more info.
- **Wireless Charger** - Charges Rechargeable Items in a Players inventory in a 16 block radius around it
- **Seed Plucker** - Plucks seed from plant-based material, supports Exotic Garden Fruits, but not essences.
- **Item Band Manager** - Manages the application and ripping of Item Bands.
- **Orechid** - Changes Stone and Netherack into Overworld and Nether ores respectively
- **Wireless Energy Bank** - Stores energy to be transfer wirelessly Using the Wireless Energy Point
- **Wireless Energy Point** - Transfers energy wirelessly using the Energy from the Wireless Energy Bank
- **Wireless Item Input** - Input point for 1 way wireless item transfer
- **Wireless Item Output** - Output Point for 1 way wireless item transfer
- **Tesseract** - Two way item and energy transport.
- External Heater - Externally heats Furnace-like blocks.
## Generators
- **Hydro Generator** - Generates energy from flowing water (Waterlog the generator)
- **Dragon Egg Generator** - Generates energy from the warmth of a dragon egg. (Place the dragon egg on top)
- **Chipping Generator** - Generates energy from damaged and or durability based items
- **Culinary Generator** - Generates energy from food energy, suports Exotic Garden Food in the Food Category.
- **Stardust Reactor** - Generates energy from Star Dust, and lots of it

## Tools
- **Picnic Basket** - Its an upgraded Cooler. It can eat any ExoticGarden custom foods, or just regular vanilla foods, it has a configurable blacklist in the items.yml
- **Electrical Stimulator** - Feed the player for energy.
- **Inventory Filter** - Upon item pickup, if item is the same as one in the Inventory Filter's filter it voids the item.
- **Angel Gem** - Permanent Creative Flight, it has some speed settings.
- **Scoop** - The only way to get naturals Bees. Scoop them into item form.
- **Dimensional Home** - Gives you a small home in another dimension to teleport to and from. (it sends to you back to your bed spawn if you are in the dimension.)
- **Tesseract Binder** - Binds Tesseract in a better manner then binding themselves via direct linking
- **Portable Fluid Tank** - Holds 16 buckets of any fluid. Can place them back down too

## Bees
- **Bee** - A Natural Bee, for each one -2 seconds to resource creation time in Material Hive.
- **Robotic Bee** - A Robotic bee made of magic and scrap parts. -2 seconds to resource creation time in Material Hive.
- **Advanced Robotic Bee** - An Advanced version of the Robotic Bee. -10 seconds to resource creation time in Material Hive.

## Item Bands
- **Healthy Item Band** - When applied to armor or weapons gain 4 hearts while wearing or holding the item in your main hand.
- **Hasty Item Band** - When applied to armor or weapons gain 2 levels of haste while wearing or hold the item in your main hand.

## Integrations
 - **Vex Mob Data Card** - If InfinityExpansion is installed then you get a Vex Mob Data Card to help with Ghostly Essence and Vex Gems
 - **Phantom Mob Data Card** - If InfinityExpansion is install then you get a Phantom Mob Data card to help with Phantom Membrane
## Credits
 [NCBPFluffyBear](https://github.com/ncbpfluffybear) for their autocrafter code since it helped alot with the Auto-Kitchen.

 [Mooy1](https://github.com/mooy1) for their hydro generator code so I could figure out how to do waterlogging right.

 [Seggan](https://github.com/seggan) for showing me how to make a good container class instead of using Slimefun's default.

 [WalshyDev](https://github.com/WalshyDev) for answering my spam in the programming help channel.

 [Slimefun Discord](https://slimefun.dev/discord) for putting up with my outright spam of the programming help channel.

 [Slimefun5](https://github.com/Slimefun5/Slimefun5) for being incredibly intuitive to make an addon for and overall being generally helpful when needing examples.

## Developer API

You can easily depend on this project using [github-gradle](https://github.com/intisy/github-gradle).

In your `build.gradle.kts`:

```kotlin
plugins {
    id("io.github.intisy.github-gradle") version "1.8.2.1"
}

dependencies {
    "githubCompileOnly"("Slimefun5:DynaTech:v1.0.3.3")
}
```

## Wiki

[Read more on the Slimefun Wiki...](https://github.com/Slimefun5/Wiki/wiki/DynaTech)

## Discord

You can find Slimefun's community on Discord! Click the badge below to join the server for suggestions/questions or other discussions about this plugin.

<p align="center">
  <a href="https://discord.gg/CbBYZBEWdR">
    <img src="https://discordapp.com/api/guilds/738626600539160576/widget.png?style=banner2" alt="Discord"/>
  </a>
</p>

## License

This project is open-source and licensed under the MIT License.

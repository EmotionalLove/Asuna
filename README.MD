### About Adorufu

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a2cc5fb986d44f5fa71edfee1edea817)](https://app.codacy.com/app/EmotionalLove/Adorufu_Client?utm_source=github.com&utm_medium=referral&utm_content=EmotionalLove/Adorufu_Client&utm_campaign=Badge_Grade_Settings)

Adorufu is a minecraft mod that assists the player in survival anarchy environments. "Adorufu" is Japanese for "Adolf"

### Installation tutorial

Download the latest release of `Adorufu.jar` from [here](https://github.com/EmotionalLove/Adorufu_Client/releases/) and select the latest version and download the jar, than put it into your `.minecraft/mods` folder. Once you've logged into a world. Type `-bind clickgui <key>` and hit that key to open the ClickGUI, from there you can toggle modules. If you don't know what a module does, you can type `-help module <the name of the module>` and it'll tell you it's purpose. You can do the same with commands using `-help command <name of command>`, and it'll tell you the command's syntax and description.

If you'd like to reposition the HUD elements (like the tps counter), you can open `.minecraft/AdorufuData.yml` in WordPad or TextEdit, and edit the bit that reads like the following:

```
Adorufu:
  gui:
    hud:
      watermark: LT
      hacklist: RB
      coordinates: LB
      saturation: RT
      inventorystats: RT
      horsestats: RT
      fps: LB
      tps: RT
```

`RT` means right-top, `LB` means left-bottom, `RB` means right-bottom, `LT` means left-top. Make sure the game is CLOSED when editing these values.

If you have any other questions, you can ask for help in our discord server

### Thank yous

Adorufu would not be possible without help...

[086](https://github.com/zeroeightysix/) - taught me how to use mixin

[leijurv and Brady](https://github.com/cabaletta/baritone) - For the baritone pathfinder

[0x22](https://github.com/0-x-2-2) - For forcing me to fix certain things in my code :kissing_smiling_eyes:

[wine](https://github.com/wine) - For fixing my lazy coding

### Workspace setup tutorial

So, like, basically you have two options for working on Adorufu. You can make an Adorufu plugin or write into the client itself, it depends on what you're trying to do.

If you want to fix something in Adorufu's code or add a feature into Adorufu to be distributed in the official releases, you'll want to **Fork and Clone**.

If you want to add a feature into Adorufu and distribute it among your friends (or publicly), you'll want to **create a plugin**

**Creating a plugin** is explained [here](http://sashadev.me/EmotionalLove/AdorufuExamplePlugin), but it doesn't go in depth very far, you'll have to refer to the example code to get a grasp on what to do.

**Forking and cloning** is simple. Just create a fork of this repo, and then clone it, either through IntelliJ (I highly recommend this over eclipse), or through command line (`git clone http://github.com/YourUsername/Adorufu_Client`). Once you do that, simply do `./gradlew setupDecompWorkspace` (unix) or `gradlew.bat setupDecompWorkspace` (windows), and after that finishes, do `./gradlew genIntellijRuns` (unix) or `gradlew.bat genIntellijRuns` if you're using IntelliJ. To build the jarfiles, do `./gradlew build` (unix) or `gradlew.bat build` (windows). Built jars are located in `projectroot/build/libs/`. From there you can create pull requests or do whatever.

### Official Discord
https://discord.gg/W2skUdj

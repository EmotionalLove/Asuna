package com.sasha.xdolf;

import com.sasha.eventsys.SimpleEventManager;
import com.sasha.xdolf.command.CommandProcessor;
import com.sasha.xdolf.command.commands.AboutCommand;
import com.sasha.xdolf.friend.FriendManager;
import com.sasha.xdolf.gui.XdolfHUD;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.misc.ModuleState;
import com.sasha.xdolf.misc.TPS;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.modules.ModuleCoordinates;
import com.sasha.xdolf.module.modules.ModuleFPS;
import com.sasha.xdolf.module.modules.ModuleKillaura;
import com.sasha.xdolf.module.modules.ModuleTPS;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Mod(modid = XdolfMod.MODID, name = XdolfMod.NAME, version = XdolfMod.VERSION)
public class XdolfMod {
    public static final String MODID = "xdolfforge";
    public static final String NAME = "Xdolf";
    public static final String VERSION = "4.0";

    public static Logger logger = LogManager.getLogger("Xdolf " + VERSION);
    public static SimpleEventManager EVENT_MANAGER = new SimpleEventManager();
    public static XdolfDataManager DATA_MANAGER = new XdolfDataManager();
    public static FriendManager FRIEND_MANAGER;
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(8);
    public static XdolfHUD HUD;

    public static Minecraft mc = Minecraft.getMinecraft();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ((ScheduledThreadPoolExecutor) scheduler).setRemoveOnCancelPolicy(true);
        FRIEND_MANAGER= new FriendManager();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Xdolf is initialising...");
        logMsg(true, "Loading TTF fonts...");
        Fonts.loadFonts();
        logMsg(true, "Done!");
        logMsg(true, "Registering commands and modules...");
        this.registerCommands();
        this.registerModules();
        EVENT_MANAGER.registerListener(new CommandProcessor());
        EVENT_MANAGER.registerListener(new ModuleManager());
        logMsg(true, "Done!");
        logMsg(true, "Initialising HUD");
        HUD = new XdolfHUD();
        HUD.setupHUD();
        logMsg(true, "Done!");
        TPS.INSTANCE = new TPS();
        EVENT_MANAGER.registerListener(TPS.INSTANCE);
        XdolfMod.scheduler.schedule(() -> {//todo test
            ModuleManager.moduleRegistry.forEach(mod -> {
                try {
                    if (DATA_MANAGER.getSavedModuleState(mod.getModuleName())) {
                        mod.forceState(ModuleState.ENABLE, false);
                    }
                }catch (IOException e){e.printStackTrace();}
            });
        }, 0, TimeUnit.SECONDS);
        logMsg(true, "Xdolf cleanly initialised!");
    }

    private void registerCommands() {
        CommandProcessor.commandRegistry.clear();
        CommandProcessor.commandRegistry.add(new AboutCommand());
    }

    private void registerModules(){
        ModuleManager.moduleRegistry.clear();
        ModuleManager.moduleRegistry.add(new ModuleKillaura());
        ModuleManager.moduleRegistry.add(new ModuleTPS());
        ModuleManager.moduleRegistry.add(new ModuleFPS());
        ModuleManager.moduleRegistry.add(new ModuleCoordinates());
    }

    public static void logMsg(boolean consoleOnly, String logMsg) {
        logger.log(Level.INFO, logMsg);
        if (consoleOnly) return;
        mc.player.sendMessage(new TextComponentString("\2477[\2474Xdolf\2477] \2476" + logMsg));
    }
    public static void logErr(boolean consoleOnly, String logMsg) {
        logger.log(Level.ERROR, logMsg);
        if (consoleOnly) return;
        mc.player.sendMessage(new TextComponentString("\2477[\2474Xdolf \247cERROR\2477] \247c" + logMsg));
    }
    public static void logWarn(boolean consoleOnly, String logMsg) {
        logger.log(Level.WARN, logMsg);
        if (consoleOnly) return;
        mc.player.sendMessage(new TextComponentString("\2477[\2474Xdolf \247eWARNING\2477] \247e" + logMsg));
    }
}

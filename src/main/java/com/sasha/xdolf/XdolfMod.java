package com.sasha.xdolf;

import com.sasha.eventsys.SimpleEventManager;
import com.sasha.xdolf.command.CommandProcessor;
import com.sasha.xdolf.command.commands.AboutCommand;
import com.sasha.xdolf.friend.FriendManager;
import com.sasha.xdolf.gui.XdolfModHUD;
import com.sasha.xdolf.module.ModuleUtils;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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
    public static XdolfModHUD HUD;

    public static Minecraft mc = Minecraft.getMinecraft();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ((ScheduledThreadPoolExecutor) scheduler).setRemoveOnCancelPolicy(true);
        FRIEND_MANAGER= new FriendManager();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Xdolf is initialising...");
        this.registerCommands();
        this.registerModules();
        EVENT_MANAGER.registerListener(new CommandProcessor());
        HUD = new XdolfModHUD();
    }

    private void registerCommands() {
        CommandProcessor.commandRegistry.clear();
        CommandProcessor.commandRegistry.add(new AboutCommand());
    }

    private void registerModules(){
        ModuleUtils.moduleRegistry.clear();

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

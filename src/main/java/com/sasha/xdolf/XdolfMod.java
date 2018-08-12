package com.sasha.xdolf;

import com.sasha.eventsys.SimpleEventManager;
import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.CommandProcessor;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.command.commands.*;
import com.sasha.xdolf.events.ClientOverlayRenderEvent;
import com.sasha.xdolf.exception.XdolfException;
import com.sasha.xdolf.friend.FriendManager;
import com.sasha.xdolf.gui.hud.XdolfHUD;
import com.sasha.xdolf.gui.fonts.Fonts;
import com.sasha.xdolf.gui.hud.renderableobjects.*;
import com.sasha.xdolf.misc.ModuleState;
import com.sasha.xdolf.misc.TPS;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.ModuleManager;
import com.sasha.xdolf.module.XdolfModule;
import com.sasha.xdolf.module.modules.*;
import com.sasha.xdolf.module.modules.hudelements.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Mod(modid = XdolfMod.MODID, name = XdolfMod.NAME, version = XdolfMod.VERSION)
public class XdolfMod {
    public static final String MODID = "xdolfforge";
    public static final String NAME = "Xdolf";
    public static final String VERSION = "4.0";

    private static Logger logger = LogManager.getLogger("Xdolf " + VERSION);
    public static SimpleEventManager EVENT_MANAGER = new SimpleEventManager();
    public static XdolfDataManager DATA_MANAGER = new XdolfDataManager();
    public static FriendManager FRIEND_MANAGER;
    public static XdolfPerformanceAnalyser PERFORMANCE_ANAL; // no, stop, this ISN'T lewd... I SWEAR!!!
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(8);

    public static Minecraft minecraft = Minecraft.getMinecraft();

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
         logMsg(true, "Registering commands, renderables and modules...");
        scheduler.schedule(() -> {
            try {
                this.registerCommands();
                this.registerModules();
                this.registerRenderables();
            } catch (Exception e){
                throw new XdolfException("Couldn't register! " + e.getMessage() );
            }
        }, 0, TimeUnit.NANOSECONDS);
        EVENT_MANAGER.registerListener(new CommandProcessor());
        EVENT_MANAGER.registerListener(new ModuleManager());
        logMsg(true, "Done!");
        logMsg(true, "Initialising HUD");
        XdolfHUD.setupHUD();
        EVENT_MANAGER.registerListener(new XdolfHUD());
        logMsg(true, "Done!");
        logMsg(true, "Loading Xray");
        scheduler.schedule(() -> ModuleXray.xrayBlocks =DATA_MANAGER.getXrayBlocks(), 250, TimeUnit.MICROSECONDS);
        TPS.INSTANCE = new TPS();
        EVENT_MANAGER.registerListener(TPS.INSTANCE);
        XdolfMod.scheduler.schedule(() -> {//todo test
            ModuleManager.moduleRegistry.forEach(mod -> {
                try {
                    if (DATA_MANAGER.getSavedModuleState(mod.getModuleName())) {
                        mod.forceState(ModuleState.ENABLE, false, true);
                    }
                    if (mod.hasForcefulAnnotation(mod.getClass())){
                        mod.forceState(ModuleState.ENABLE, false, true);
                    }
                    mod.setKeyBind(DATA_MANAGER.getSavedKeybind(mod));
                }catch (IOException e){e.printStackTrace();}
            });
        }, 500, TimeUnit.MILLISECONDS);
        logMsg(true, "Xdolf cleanly initialised!");
        MinecraftForge.EVENT_BUS.register(new ForgeEvent());
        PERFORMANCE_ANAL = new XdolfPerformanceAnalyser();
    }

    private void registerCommands() throws Exception{
        CommandProcessor.commandRegistry.clear();
        CommandProcessor.commandRegistry.add(new AboutCommand());
        CommandProcessor.commandRegistry.add(new ToggleCommand());
        CommandProcessor.commandRegistry.add(new ModulesCommand());
        CommandProcessor.commandRegistry.add(new HelpCommand());
        CommandProcessor.commandRegistry.add(new BindCommand());
        CommandProcessor.commandRegistry.add(new XrayCommand());
        CommandProcessor.commandRegistry.add(new LagCommand());
    }

    private void registerModules() throws Exception {
        ModuleManager.moduleRegistry.clear();
        ModuleManager.register(new ModuleXray());
        ModuleManager.register(new ModuleTPS());
        ModuleManager.register(new ModuleFPS());
        ModuleManager.register(new ModuleCoordinates());
        ModuleManager.register(new ModuleSaturation());
        ModuleManager.register(new ModuleInventoryStats());
        ModuleManager.register(new ModuleHorsestats());
        ModuleManager.register(new ModuleHacklist());
        ModuleManager.register(new ModuleWatermark());
        ModuleManager.register(new ModuleKillaura());
        ModuleManager.register(new ModuleStorageESP());
        ModuleManager.register(new ModuleTracers());
        ModuleManager.register(new ModuleAntiHunger());
        ModuleManager.register(new ModuleClickGUI());
        ModuleManager.register(new ModuleNightVision());
        ModuleManager.register(new ModuleNoSlow());
        ModuleManager.register(new ModuleAnnouncer());
        ModuleManager.register(new ModuleAFKFish());
        ModuleManager.register(new ModuleAutoRespawn());
        ModuleManager.register(new ModuleChunkTrace());
        ModuleManager.register(new ModuleFreecam());
        ModuleManager.register(new ModuleCrystalAura());
        ModuleManager.register(new ModuleCrystalLog());
        ModuleManager.register(new ModuleFlight());
        /*
        ModuleManager.moduleRegistry.clear();
        Reflections reflections = new Reflections(ModuleXray.class.getPackage().getName());
        Set<Class<? extends XdolfModule>> moduleClasses = reflections.getSubTypesOf(XdolfModule.class);
        for (Class<?> moduleClass : moduleClasses) {
            XdolfMod.logMsg(true, "Registered " +moduleClass.getName());
            ModuleManager.register((XdolfModule)moduleClass.getConstructor().newInstance());
        }*///todo fix
    }

    private void registerRenderables(){
        XdolfHUD.registeredHudElements.clear();
        XdolfHUD.registeredHudElements.add(new RenderableWatermark());
        XdolfHUD.registeredHudElements.add(new RenderableHacklist());
        XdolfHUD.registeredHudElements.add(new RenderableCoordinates());
        XdolfHUD.registeredHudElements.add(new RenderableSaturation());
        XdolfHUD.registeredHudElements.add(new RenderableInventoryStats());
        XdolfHUD.registeredHudElements.add(new RenderableHorseStats());
        XdolfHUD.registeredHudElements.add(new RenderableFramerate());
        XdolfHUD.registeredHudElements.add(new RenderableTickrate());
    }

    public static void logMsg(boolean consoleOnly, String logMsg) {
        logger.log(Level.INFO, logMsg);
        if (consoleOnly) return;
        minecraft.player.sendMessage(new TextComponentString("\2478[\2474Xdolf\2478] \2477" + logMsg));
    }
    public static void logMsg(String logMsg) {
        minecraft.player.sendMessage(new TextComponentString("\2477" + logMsg));
    }
    public static void logErr(boolean consoleOnly, String logMsg) {
        logger.log(Level.ERROR, logMsg);
        if (consoleOnly) return;
        minecraft.player.sendMessage(new TextComponentString("\2478[\2474Xdolf \247cERROR\2478] \247c" + logMsg));
    }
    public static void logWarn(boolean consoleOnly, String logMsg) {
        logger.log(Level.WARN, logMsg);
        if (consoleOnly) return;
        minecraft.player.sendMessage(new TextComponentString("\2478[\2474Xdolf \247eWARNING\2478] \247e" + logMsg));
    }
}
class ForgeEvent {
    @SubscribeEvent
    public void onRenderLost(RenderGameOverlayEvent.Post e){
        RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.TEXT;
        if (e.getType() == target){
            ClientOverlayRenderEvent event = new ClientOverlayRenderEvent(e.getPartialTicks());
            XdolfMod.EVENT_MANAGER.invokeEvent(event);
        }
    }
}

package com.sasha.adorufu;

import com.sasha.adorufu.command.CommandHandler;
import com.sasha.adorufu.waypoint.WaypointManager;
import com.sasha.eventsys.SimpleEventManager;
import com.sasha.adorufu.command.commands.*;
import com.sasha.adorufu.events.ClientOverlayRenderEvent;
import com.sasha.adorufu.exception.AdorufuException;
import com.sasha.adorufu.friend.FriendManager;
import com.sasha.adorufu.gui.hud.AdorufuHUD;
import com.sasha.adorufu.gui.fonts.Fonts;
import com.sasha.adorufu.gui.hud.renderableobjects.*;
import com.sasha.adorufu.misc.ModuleState;
import com.sasha.adorufu.misc.TPS;
import com.sasha.adorufu.module.modules.ModuleAntiAFK;
import com.sasha.adorufu.module.ModuleManager;
import com.sasha.adorufu.module.modules.*;
import com.sasha.adorufu.module.modules.hudelements.*;
import com.sasha.simplecmdsys.SimpleCommandProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Mod(modid = AdorufuMod.MODID, name = AdorufuMod.NAME, version = AdorufuMod.VERSION, canBeDeactivated = true)
public class AdorufuMod {
    public static final String MODID = "adorufuforge";
    public static final String NAME = "Adorufu";
    public static final String JAP_NAME = "\u30A2\u30C9\u30EB\u30D5";
    public static final String VERSION = "1.0.3";


    private static Logger logger = LogManager.getLogger("Adorufu " + VERSION);
    public static SimpleEventManager EVENT_MANAGER = new SimpleEventManager();
    public static AdorufuDataManager DATA_MANAGER = new AdorufuDataManager();
    public static FriendManager FRIEND_MANAGER;
    public static WaypointManager WAYPOINT_MANAGER;
    public static SimpleCommandProcessor COMMAND_PROCESSOR = new SimpleCommandProcessor("-");
    public static AdorufuPerformanceAnalyser PERFORMANCE_ANAL; // no, stop, this ISN'T lewd... I SWEAR!!!
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(8);

    public static Minecraft minecraft = Minecraft.getMinecraft();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ((ScheduledThreadPoolExecutor) scheduler).setRemoveOnCancelPolicy(true);
        FRIEND_MANAGER= new FriendManager();
        try {
            if (DATA_MANAGER.getDRPEnabled()) {
                AdorufuDiscordPresense.setupPresense();
                Runtime.getRuntime().addShutdownHook(new Thread(()-> {
                    AdorufuDiscordPresense.discordRpc.Discord_Shutdown();
                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
            AdorufuDiscordPresense.setupPresense();
            Runtime.getRuntime().addShutdownHook(new Thread(()-> {
                AdorufuDiscordPresense.discordRpc.Discord_Shutdown();
            }));
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Adorufu is initialising...");
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
                throw new AdorufuException("Couldn't register! " + e.getMessage() );
            }
        }, 0, TimeUnit.NANOSECONDS);
        EVENT_MANAGER.registerListener(new CommandHandler());
        EVENT_MANAGER.registerListener(new ModuleManager());
        logMsg(true, "Done!");
        logMsg(true, "Initialising HUD");
        AdorufuHUD.setupHUD();
        EVENT_MANAGER.registerListener(new AdorufuHUD());
        logMsg(true, "Done!");
        logMsg(true, "Loading Xray");
        scheduler.schedule(() -> ModuleXray.xrayBlocks =
                           DATA_MANAGER.getXrayBlocks(), 250, TimeUnit.MICROSECONDS);
        TPS.INSTANCE = new TPS();
        EVENT_MANAGER.registerListener(TPS.INSTANCE);
        AdorufuMod.scheduler.schedule(() -> {//todo test
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
        MinecraftForge.EVENT_BUS.register(new ForgeEvent());
        scheduler.schedule(() -> {
            try {
                ModuleEntitySpeed.speed = (double)AdorufuMod.DATA_MANAGER.loadSomeGenericValue("Adorufu.values", "entityspeed", 2.5f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PERFORMANCE_ANAL = new AdorufuPerformanceAnalyser();
            WAYPOINT_MANAGER = new WaypointManager();
            try {
                DATA_MANAGER.loadPlayerIdentities();
                DATA_MANAGER.identityCacheMap.forEach((uuid, id) -> {
                    if (id.shouldUpdate()) {
                        id.updateDisplayName();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, TimeUnit.NANOSECONDS);
        EVENT_MANAGER.registerListener(new AdorufuUpdateChecker());
        logMsg(true, "Adorufu cleanly initialised!");
    }

    private void registerCommands() throws Exception{
        COMMAND_PROCESSOR.register(AboutCommand.class);
        COMMAND_PROCESSOR.register(ToggleCommand.class);
        COMMAND_PROCESSOR.register(ModulesCommand.class);
        COMMAND_PROCESSOR.register(HelpCommand.class);
        COMMAND_PROCESSOR.register(BindCommand.class);
        COMMAND_PROCESSOR.register(XrayCommand.class);
        COMMAND_PROCESSOR.register(LagCommand.class);
        COMMAND_PROCESSOR.register(IgnoreCommand.class);
        COMMAND_PROCESSOR.register(IgnorelistCommand.class);
        COMMAND_PROCESSOR.register(YawCommand.class);
        COMMAND_PROCESSOR.register(FriendCommand.class);
        COMMAND_PROCESSOR.register(FriendlistCommand.class);
        COMMAND_PROCESSOR.register(WaypointCommand.class);
        COMMAND_PROCESSOR.register(EntitySpeedCommand.class);
    }

    private void registerModules() throws Exception {
        ModuleManager.moduleRegistry.clear();
        ModuleManager.register(new ModuleXray());
        ModuleManager.register(new ModuleWireframe());
        ModuleManager.register(new ModuleNamePlates());
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
        ModuleManager.register(new ModuleCrystalLogout());
        ModuleManager.register(new ModuleFlight());
        ModuleManager.register(new ModuleJesus());
        ModuleManager.register(new ModuleClientIgnore());
        ModuleManager.register(new ModuleAutoIgnore());
        ModuleManager.register(new ModuleAutoSprint());
        /*ModuleManager.register(new ModuleCameraClip());*/ //todo
        ModuleManager.register(new ModuleElytraBoost());
        ModuleManager.register(new ModuleElytraFlight());
        ModuleManager.register(new ModuleEntitySpeed());
        ModuleManager.register(new ModuleLowJump());
        ModuleManager.register(new ModuleMiddleClickBlock());
        ModuleManager.register(new ModuleExtendedTablist());
        ModuleManager.register(new ModuleAntiAFK());
        ModuleManager.register(new ModuleYawLock());
        ModuleManager.register(new ModuleQueueTime());
        ModuleManager.register(new ModuleNoPush());
        ModuleManager.register(new ModulePacketFly());
        ModuleManager.register(new ModulePigControl());
        ModuleManager.register(new ModuleAutoTotem());
        ModuleManager.register(new ModuleWaypointGUI());
        ModuleManager.register(new ModuleWaypoints());
        ModuleManager.register(new ModuleWolfIdentity());
        ModuleManager.register(new ModuleGhostBlockWarning());
        ModuleManager.register(new ModuleAntiFireOverlay());
        /*
        ModuleManager.moduleRegistry.clear();
        Reflections reflections = new Reflections(ModuleXray.class.getPackage().getName());
        Set<Class<? extends AdorufuModule>> moduleClasses = reflections.getSubTypesOf(AdorufuModule.class);
        for (Class<?> moduleClass : moduleClasses) {
            AdorufuMod.logMsg(true, "Registered " +moduleClass.getName());
            ModuleManager.register((AdorufuModule)moduleClass.getConstructor().newInstance());
        }*///todo fix
    }

    private void registerRenderables(){
        AdorufuHUD.registeredHudElements.clear();
        AdorufuHUD.registeredHudElements.add(new RenderableWatermark());
        AdorufuHUD.registeredHudElements.add(new RenderableHacklist());
        AdorufuHUD.registeredHudElements.add(new RenderableCoordinates());
        AdorufuHUD.registeredHudElements.add(new RenderableSaturation());
        AdorufuHUD.registeredHudElements.add(new RenderableInventoryStats());
        AdorufuHUD.registeredHudElements.add(new RenderableHorseStats());
        AdorufuHUD.registeredHudElements.add(new RenderableFramerate());
        AdorufuHUD.registeredHudElements.add(new RenderableTickrate());
    }

    public static void logMsg(boolean consoleOnly, String logMsg) {
        logger.log(Level.INFO, logMsg);
        if (consoleOnly) return;
        minecraft.player.sendMessage(new TextComponentString("\2478[\2474Adorufu\2478] \2477" + logMsg));
    }
    public static void logMsg(String logMsg) {
        minecraft.player.sendMessage(new TextComponentString("\2477" + logMsg));
    }
    public static void logErr(boolean consoleOnly, String logMsg) {
        logger.log(Level.ERROR, logMsg);
        if (consoleOnly) return;
        minecraft.player.sendMessage(new TextComponentString("\2478[\2474Adorufu \247cERROR\2478] \247c" + logMsg));
    }
    public static void logWarn(boolean consoleOnly, String logMsg) {
        logger.log(Level.WARN, logMsg);
        if (consoleOnly) return;
        minecraft.player.sendMessage(new TextComponentString("\2478[\2474Adorufu \247eWARNING\2478] \247e" + logMsg));
    }
}
class ForgeEvent {
    @SubscribeEvent
    public void onRenderLost(RenderGameOverlayEvent.Post e){
        RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.TEXT;
        if (e.getType() == target){
            ClientOverlayRenderEvent event = new ClientOverlayRenderEvent(e.getPartialTicks());
            AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        }
    }
}

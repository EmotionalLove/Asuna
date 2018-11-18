/*
 * Copyright (c) Sasha Stevens 2018.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.sasha.adorufu.mod;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.sasha.adorufu.api.AdorufuPlugin;
import com.sasha.adorufu.api.AdorufuPluginLoader;
import com.sasha.adorufu.api.adapter.MinecraftMappingUpdater;
import com.sasha.adorufu.mod.command.CommandHandler;
import com.sasha.adorufu.mod.command.commands.*;
import com.sasha.adorufu.mod.desktop.AdorufuSystemTrayManager;
import com.sasha.adorufu.mod.desktop.AdorufuWindowsBatteryManager;
import com.sasha.adorufu.mod.events.adorufu.AdorufuDataFileRetrievedEvent;
import com.sasha.adorufu.mod.events.client.ClientInputUpdateEvent;
import com.sasha.adorufu.mod.events.client.ClientOverlayRenderEvent;
import com.sasha.adorufu.mod.exception.AdorufuException;
import com.sasha.adorufu.mod.feature.AdorufuDiscordPresense;
import com.sasha.adorufu.mod.feature.IAdorufuFeature;
import com.sasha.adorufu.mod.feature.impl.*;
import com.sasha.adorufu.mod.friend.FriendManager;
import com.sasha.adorufu.mod.gui.fonts.FontManager;
import com.sasha.adorufu.mod.gui.hud.AdorufuHUD;
import com.sasha.adorufu.mod.gui.hud.renderableobjects.*;
import com.sasha.adorufu.mod.misc.Manager;
import com.sasha.adorufu.mod.misc.TPS;
import com.sasha.adorufu.mod.remote.RemoteDataManager;
import com.sasha.adorufu.mod.waypoint.WaypointManager;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleEventManager;
import com.sasha.eventsys.SimpleListener;
import com.sasha.simplecmdsys.SimpleCommandProcessor;
import com.sasha.simplesettings.SettingHandler;
import com.sun.jna.Native;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.sasha.adorufu.mod.misc.Manager.Renderable.renderableRegistry;

@Mod(modid = AdorufuMod.MODID, name = AdorufuMod.NAME, version = AdorufuMod.VERSION, canBeDeactivated = true, clientSideOnly = true)
public class AdorufuMod implements SimpleListener {

    public static final String MODID = "adorufuforge";
    public static final String NAME = "Adorufu";
    public static final String JAP_NAME = "\u30A2\u30C9\u30EB\u30D5";
    public static final String VERSION = "1.6";
    public static SimpleEventManager EVENT_MANAGER = new SimpleEventManager();
    @Deprecated public static AdorufuDataManager DATA_MANAGER = new AdorufuDataManager();
    public static SettingHandler SETTING_HANDLER = new SettingHandler("AdorufuSettingData");
    /**
     * desktop notifications
     */
    public static AdorufuSystemTrayManager TRAY_MANAGER;
    public static FriendManager FRIEND_MANAGER;
    /**
     * pretty fonts
     */
    public static FontManager FONT_MANAGER;
    public static WaypointManager WAYPOINT_MANAGER;
    public static RemoteDataManager REMOTE_DATA_MANAGER = new RemoteDataManager();
    /**
     * commands
     */
    public static SimpleCommandProcessor COMMAND_PROCESSOR;
    /**
     * checks windows battery level
     */
    public static AdorufuWindowsBatteryManager.SYSTEM_POWER_STATUS BATTERY_MANAGER;
    public static AdorufuPerformanceAnalyser PERFORMANCE_ANAL = new AdorufuPerformanceAnalyser(); // no, stop, this ISN'T lewd... I SWEAR!!!
    public static AdorufuWindowsBatteryManager BATTERY_MANAGER_INTERFACE;
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    public static AdorufuHUD adorufuHUD;
    public static Minecraft minecraft = Minecraft.getMinecraft();
    private static Logger logger = LogManager.getLogger("Adorufu " + VERSION);


    ///////////// FORGE INIT ///////////////


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftMappingUpdater.updateMappings();
        ((ScheduledThreadPoolExecutor) scheduler).setRemoveOnCancelPolicy(true);
        FRIEND_MANAGER = new FriendManager();
        AdorufuDiscordPresense.setupPresense();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            AdorufuDiscordPresense.discordRpc.Discord_Shutdown();
        }));
        try {
            if (Util.getOSType() == Util.EnumOS.WINDOWS) {
                BATTERY_MANAGER_INTERFACE = (AdorufuWindowsBatteryManager) Native.loadLibrary("Kernel32", AdorufuWindowsBatteryManager.class);
                AdorufuWindowsBatteryManager.SYSTEM_POWER_STATUS batteryStatus = new AdorufuWindowsBatteryManager.SYSTEM_POWER_STATUS();
                BATTERY_MANAGER_INTERFACE.GetSystemPowerStatus(batteryStatus);
                logMsg(true, batteryStatus.getBatteryLifePercent());
                BATTERY_MANAGER = batteryStatus;
            }
        } catch (Exception x) {
            //
        }
        // plugin loading
        AdorufuPluginLoader pluginLoader = new AdorufuPluginLoader();
        try {
            logMsg(true, "Finding plugins...");
            List<File> files = pluginLoader.findPlugins();
            if (files.size() == 0) {
                logMsg(true, "No plugins to load, continuing the initialisation of vanilla Adorufu");
                return;
            }
            logMsg(true, "Preparing plugins...");
            int i = pluginLoader.preparePlugins(files);
            logMsg(true, "Prepared " + i + " plugin(s)");
            pluginLoader.loadPlugins();
            logMsg(true, "Successfully loaded plugins!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Adorufu is initialising...");
        logMsg(true, "Registering commands, renderables and features...");
        adorufuHUD = new AdorufuHUD();
        scheduler.schedule(() -> {
            try {
                COMMAND_PROCESSOR = new SimpleCommandProcessor("-");
                logMsg(true, "Registering commands...");
                this.registerCommands();
                logMsg(true, "Registering features...");
                try {
                    this.registerFeaturesOld();
                }
                catch (Exception e) {
                    e.printStackTrace(); // why isn't anything loading on phi's computer?????????????????????????????
                }
                logMsg(true, "Registering renderables...");
                this.registerRenderables();
                EVENT_MANAGER.registerListener(new CommandHandler());
                EVENT_MANAGER.registerListener(adorufuHUD);
                TPS.INSTANCE = new TPS();
                EVENT_MANAGER.registerListener(TPS.INSTANCE);
                WAYPOINT_MANAGER = new WaypointManager();
                DATA_MANAGER.loadPlayerIdentities();
                DATA_MANAGER.identityCacheMap.forEach((uuid, id) -> {
                    if (id.shouldUpdate()) {
                        id.updateDisplayName();
                    }
                });
                TRAY_MANAGER = new AdorufuSystemTrayManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, TimeUnit.NANOSECONDS);
        MinecraftForge.EVENT_BUS.register(new ForgeEvent());
        EVENT_MANAGER.registerListener(new AdorufuUpdateChecker());
        EVENT_MANAGER.registerListener(this);
        logMsg(true, "Adorufu cleanly initialised!");
    }


    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        FONT_MANAGER = new FontManager();
        FONT_MANAGER.loadFonts(); // I would load this on a separate thread if I could, because it takes forEVER to execute.
        if (AdorufuPluginLoader.getLoadedPlugins().size() > 0)
            AdorufuMod.logWarn(true, "Adorufu was loaded with plugins! " +
                    "Please make sure that you know ABSOLUTELY EVERYTHING your installed plugins are doing, as" +
                    " developers can run malicious code in their plugins.");
        Manager.Data.recoverSettings();
        adorufuHUD.setupHUD();
        Runtime.getRuntime().addShutdownHook(new Thread(Manager.Data::saveCurrentSettings));
    }



    ////////// END FORGE INIT ////////////////////////



    /**
     * Set a key to be pressed
     */
    public static void setPressed(KeyBinding key, boolean pressed) {
        KeyBinding.setKeyBindState(key.getKeyCode(), pressed);
    }

    /**
     * Log a regular message
     */
    public static void logMsg(boolean consoleOnly, String logMsg) {
        logger.log(Level.INFO, logMsg);
        if (consoleOnly) return;
        minecraft.player.sendMessage(new TextComponentString("\2478[\2474" + JAP_NAME + "\2478] \2477" + logMsg));
    }

    /**
     * Log message without the Adorufu branding to chat
     */
    public static void logMsg(String logMsg) {
        minecraft.player.sendMessage(new TextComponentString("\2477" + logMsg));
    }

    /**
     * Log an error
     */
    public static void logErr(boolean consoleOnly, String logMsg) {
        logger.log(Level.ERROR, logMsg);
        if (consoleOnly) return;
        minecraft.player.sendMessage(new TextComponentString("\2478[\2474" + JAP_NAME + " \247cERROR\2478] \247c" + logMsg));
    }

    /**
     * Log a warning
     */
    public static void logWarn(boolean consoleOnly, String logMsg) {
        logger.log(Level.WARN, logMsg);
        if (consoleOnly) return;
        minecraft.player.sendMessage(new TextComponentString("\2478[\2474" + JAP_NAME + " \247eWARNING\2478] \247e" + logMsg));
    }



    @Deprecated
    private void registerFeaturesOld() {
        Manager.Feature.featureRegistry.clear();
        Manager.Feature.registerFeature(new XrayFeature());
        Manager.Feature.registerFeature(new WireframeFeature());
        Manager.Feature.registerFeature(new NamePlatesFeature());
        Manager.Feature.registerFeature(new TickrateRenderableFeature());
        Manager.Feature.registerFeature(new FramerateRenderableFeature());
        Manager.Feature.registerFeature(new CoordinatesRenderableFeature());
        Manager.Feature.registerFeature(new SaturationRenderableFeature());
        Manager.Feature.registerFeature(new InventoryStatsRenderableFeature());
        Manager.Feature.registerFeature(new HorsestatsRenderableFeature());
        Manager.Feature.registerFeature(new FeaturelistRenderableFeature());
        Manager.Feature.registerFeature(new WatermarkRenderableFeature());
        Manager.Feature.registerFeature(new KillauraFeature());
        Manager.Feature.registerFeature(new StorageESPFeature());
        Manager.Feature.registerFeature(new TracersFeature());
        Manager.Feature.registerFeature(new AntiHungerFeature());
        Manager.Feature.registerFeature(new ClickGUIFeature());
        Manager.Feature.registerFeature(new NightVisionFeature());
        Manager.Feature.registerFeature(new NoSlowFeature());
        Manager.Feature.registerFeature(new AnnouncerFeature());
        Manager.Feature.registerFeature(new AFKFishFeature());
        Manager.Feature.registerFeature(new AutoRespawnFeature());
        Manager.Feature.registerFeature(new ChunkTraceFeature());
        Manager.Feature.registerFeature(new FreecamFeature());
        Manager.Feature.registerFeature(new CrystalAuraFeature());
        Manager.Feature.registerFeature(new CrystalLogoutFeature());
        Manager.Feature.registerFeature(new JesusFeature());
        Manager.Feature.registerFeature(new ClientIgnoreFeature());
        Manager.Feature.registerFeature(new CarpetPlacerFeature());
        Manager.Feature.registerFeature(new AutoIgnoreFeature());
        Manager.Feature.registerFeature(new AutoSprintFeature());
        Manager.Feature.registerFeature(new CameraClipFeature());
        Manager.Feature.registerFeature(new ElytraBoostFeature());
        Manager.Feature.registerFeature(new ElytraFlightFeature());
        Manager.Feature.registerFeature(new EntitySpeedFeature());
        Manager.Feature.registerFeature(new LowJumpFeature());
        Manager.Feature.registerFeature(new MiddleClickBlockFeature());
        Manager.Feature.registerFeature(new ExtendedTablistFeature());
        Manager.Feature.registerFeature(new AntiAFKFeature());
        Manager.Feature.registerFeature(new YawLockFeature());
        Manager.Feature.registerFeature(new QueueTimeFeature());
        Manager.Feature.registerFeature(new NoPushFeature());
        Manager.Feature.registerFeature(new PacketFlyFeature());
        Manager.Feature.registerFeature(new PigControlFeature());
        Manager.Feature.registerFeature(new AutoTotemFeature());
        Manager.Feature.registerFeature(new WaypointGUIFeature());
        Manager.Feature.registerFeature(new WaypointsFeature());
        Manager.Feature.registerFeature(new TamedIdentityFeature());
        Manager.Feature.registerFeature(new GhostBlockWarningFeature());
        Manager.Feature.registerFeature(new AntiFireOverlayFeature());
        Manager.Feature.registerFeature(new MinecraftMusicFeature());
        Manager.Feature.registerFeature(new BlinkFeature()); // No clue if this is what blink is suppposed to do... i dont pvp...
        Manager.Feature.registerFeature(new JoinLeaveMessagesFeature());
        Manager.Feature.registerFeature(new CraftInventoryFeature());
        Manager.Feature.registerFeature(new KnockbackSuppressFeature());
        Manager.Feature.registerFeature(new EquipmentDamageRenderableFeature());
        Manager.Feature.registerFeature(new DesktopNotificationsFeature());
        Manager.Feature.registerFeature(new CPUControlFeature());
        Manager.Feature.registerFeature(new BatteryLifeRenderableFeature());
        Manager.Feature.registerFeature(new BookForgerFeature());
        Manager.Feature.registerFeature(new AutoWalkFeature());
        Manager.Feature.registerFeature(new BoatFlyFeature());
        Manager.Feature.registerFeature(new AutoReconnectFeature());
        Manager.Feature.registerFeature(new NameProtectFeature());
        Manager.Feature.registerFeature(new SafewalkFeature());
        Manager.Feature.registerFeature(new PortalGodModeFeature());
        Manager.Feature.registerFeature(new AutoEatFeature());
        Manager.Feature.registerFeature(new ShulkerSpyFeature());
        Manager.Feature.registerFeature(new PeekFeature());
        Manager.Data.settingRegistry.addAll(Manager.Feature.featureRegistry);
        AdorufuPluginLoader.getLoadedPlugins().forEach(AdorufuPlugin::onFeatureRegistration);
    }

    private void reload(boolean async) {
        Thread thread = new Thread(() -> {
            try {
                //Manager.Module.moduleRegistry.forEach(m -> m.forceState(ModuleState.DISABLE, false, false));
                this.registerRenderables();
                AdorufuMod.logMsg(true, "Adorufu successfully reloaded.");
            } catch (Exception e) {
                throw new AdorufuException("Severe error occurred whilst reloading client " + e.getMessage());
            }
        });
        if (async) {
            thread.start();
            return;
        }
        thread.run();
    }

    private void registerCommands() throws Exception {
        COMMAND_PROCESSOR.register(AboutCommand.class);
        COMMAND_PROCESSOR.register(ToggleCommand.class);
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
        COMMAND_PROCESSOR.register(FilterCommand.class);
        COMMAND_PROCESSOR.register(FilterlistCommand.class);
        COMMAND_PROCESSOR.register(UpdateCommand.class);
        COMMAND_PROCESSOR.register(PluginsCommand.class);
        COMMAND_PROCESSOR.register(AutoReconnectCommand.class);
        COMMAND_PROCESSOR.register(PathCommand.class);
        AdorufuPluginLoader.getLoadedPlugins().forEach(AdorufuPlugin::onCommandRegistration);
    }

    private void registerFeatures() {
        AdorufuMod.logMsg(true, "Finding and initialising features...");
        try {
            ImmutableSet<ClassPath.ClassInfo> classes = Manager.findClasses("com.sasha.adorufu.mod.feature.impl");
            if (classes.isEmpty()) {
                throw new AdorufuException("I can't find any features!!!!!!!!!!! There is a problem!!!!!!!!! AAAAAAAAAAAAA");
            }
            classes.forEach(e -> {
                AdorufuMod.logMsg(true, "Found " + e.getSimpleName() + ".class");
                try {
                    IAdorufuFeature feature = (IAdorufuFeature) e.load().getConstructor().newInstance();
                    Manager.Feature.registerFeature(feature);
                    AdorufuMod.logMsg(true, "Registered " + feature.getFeatureName() + "!");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    AdorufuMod.logErr(true, "A severe uncaught exception occurred whilst initialising " + e.getSimpleName() + "!");
                }
            });
        }catch (IOException ex) {
            ex.printStackTrace();
            logErr(true, "A severe exception occurred whilst registering Adorufu's features!");
        }
        AdorufuPluginLoader.getLoadedPlugins().forEach(AdorufuPlugin::onFeatureRegistration);
        Manager.Data.settingRegistry.addAll(Manager.Feature.featureRegistry);
    }

    private void registerRenderables() {
        renderableRegistry.clear();
        Manager.Renderable.register(new RenderableWatermark());
        Manager.Renderable.register(new RenderableFeatureList());
        Manager.Renderable.register(new RenderableCoordinates());
        Manager.Renderable.register(new RenderableSaturation());
        Manager.Renderable.register(new RenderableInventoryStats());
        Manager.Renderable.register(new RenderableHorseStats());
        Manager.Renderable.register(new RenderableFramerate());
        Manager.Renderable.register(new RenderableTickrate());
        Manager.Renderable.register(new RenderableEquipmentDamage());
        Manager.Renderable.register(new RenderableBatteryLife());
        AdorufuPluginLoader.getLoadedPlugins().forEach(AdorufuPlugin::onRenderableRegistration);
    }

    @SimpleEventHandler
    public void onDataFileRetrieved(AdorufuDataFileRetrievedEvent e) {
        this.reload(true);
    }
}

class ForgeEvent {
    @SubscribeEvent
    public void onRenderLost(RenderGameOverlayEvent.Post e) {
        RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.TEXT;
        if (e.getType() == target) {
            ClientOverlayRenderEvent event = new ClientOverlayRenderEvent(e.getPartialTicks());
            AdorufuMod.EVENT_MANAGER.invokeEvent(event);
        }
    }

    @SubscribeEvent
    public void onMoveUpdate(InputUpdateEvent e) {
        ClientInputUpdateEvent updateEvent = new ClientInputUpdateEvent(e.getMovementInput());
        AdorufuMod.EVENT_MANAGER.invokeEvent(updateEvent);
    }
}

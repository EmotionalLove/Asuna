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
import com.sasha.adorufu.mod.feature.impl.AFKFishFeature;
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
import java.lang.reflect.InvocationTargetException;
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
    public static final String VERSION = "1.6pre";
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
/*
    private void registerModules() {
        Manager.Module.moduleRegistry.clear();
        Manager.Module.register(new XrayFeature());
        Manager.Module.register(new WireframeFeature());
        Manager.Module.register(new NamePlatesFeature());
        Manager.Module.register(new TpsRenderableFeature());
        Manager.Module.register(new FpsRenderableFeature());
        Manager.Module.register(new CoordinatesRenderableFeature());
        Manager.Module.register(new SaturationRenderableFeature());
        Manager.Module.register(new InventoryStatsRenderableFeature());
        Manager.Module.register(new HorsestatsRenderableFeature());
        Manager.Module.register(new FeaturelistRenderableFeature());
        Manager.Module.register(new WatermarkRenderableFeature());
        Manager.Module.register(new KillauraFeature());
        Manager.Module.register(new StorageESPFeature());
        Manager.Module.register(new TracersFeature());
        Manager.Module.register(new AntiHungerFeature());
        Manager.Module.register(new ClickGUIFeature());
        Manager.Module.register(new NightVisionFeature());
        Manager.Module.register(new NoSlowFeature());
        Manager.Module.register(new AnnouncerFeature());
        Manager.Module.register(new AFKFishFeature());
        Manager.Module.register(new AutoRespawnFeature());
        Manager.Module.register(new ChunkTraceFeature());
        Manager.Module.register(new FreecamFeature());
        Manager.Module.register(new CrystalAuraFeature());
        Manager.Module.register(new CrystalLogoutFeature());
        Manager.Module.register(new JesusFeature());
        Manager.Module.register(new ClientIgnoreFeature());
        Manager.Module.register(new AutoIgnoreFeature());
        Manager.Module.register(new AutoSprintFeature());
        Manager.Module.register(new CameraClipFeature());
        Manager.Module.register(new ElytraBoostFeature());
        Manager.Module.register(new ElytraFlightFeature());
        Manager.Module.register(new EntitySpeedFeature());
        Manager.Module.register(new LowJumpFeature());
        Manager.Module.register(new MiddleClickBlockFeature());
        Manager.Module.register(new ExtendedTablistFeature());
        Manager.Module.register(new AntiAFKFeature());
        Manager.Module.register(new YawLockFeature());
        Manager.Module.register(new QueueTimeFeature());
        Manager.Module.register(new NoPushFeature());
        Manager.Module.register(new PacketFlyFeature());
        Manager.Module.register(new PigControlFeature());
        Manager.Module.register(new AutoTotemFeature());
        Manager.Module.register(new WaypointGUIFeature());
        Manager.Module.register(new WaypointsFeature());
        Manager.Module.register(new TamedIdentityFeature());
        Manager.Module.register(new GhostBlockWarningFeature());
        Manager.Module.register(new AntiFireOverlayFeature());
        Manager.Module.register(new MinecraftMusicFeature());
        Manager.Module.register(new BlinkFeature()); // No clue if this is what blink is suppposed to do... i dont pvp...
        Manager.Module.register(new ModuleAutoArmor());
        Manager.Module.register(new JoinLeaveMessagesFeature());
        Manager.Module.register(new CraftInventoryFeature());
        Manager.Module.register(new KnockbackSuppressFeature());
        Manager.Module.register(new EquipmentDamageRenderableFeature());
        Manager.Module.register(new DesktopNotificationsFeature());
        Manager.Module.register(new CPUControlFeature());
        Manager.Module.register(new BatteryLifeRenderableFeature());
        Manager.Module.register(new ModulePowerBow());
        Manager.Module.register(new BookForgerFeature());
        Manager.Module.register(new AutoWalkFeature());
        Manager.Module.register(new BoatFlyFeature());
        Manager.Module.register(new AutoReconnectFeature());
        Manager.Module.register(new NameProtectFeature());
        Manager.Module.register(new SafewalkFeature());
        Manager.Module.register(new PortalGodModeFeature());
        Manager.Module.register(new AutoEatFeature());
        Manager.Module.register(new ShulkerSpyFeature());
        AdorufuPluginLoader.getLoadedPlugins().forEach(AdorufuPlugin::onFeatureRegistration);
    }
*/

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Adorufu is initialising...");
        logMsg(true, "Registering commands, renderables and impl...");
        scheduler.schedule(() -> {
            try {
                COMMAND_PROCESSOR = new SimpleCommandProcessor("-");
                this.registerCommands();
                this.registerFeatures();
                this.registerRenderables();
                EVENT_MANAGER.registerListener(new CommandHandler());
                //EVENT_MANAGER.registerListener(new Manager.Feature());
                adorufuHUD = new AdorufuHUD();
                EVENT_MANAGER.registerListener(adorufuHUD);
                TPS.INSTANCE = new TPS();
                EVENT_MANAGER.registerListener(TPS.INSTANCE);
                //loadBindsAndStates();
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

    private void registerFeatures() throws IOException {
        AdorufuMod.logMsg(true, "Finding and initialising features...");
        Manager.Feature.featureRegistry.clear();
        Manager.findClasses(AFKFishFeature.class.getPackage().getName())
                .forEach(e -> {
                    AdorufuMod.logMsg(true, "Found " + e.getSimpleName() + ".class");
                    try {
                        IAdorufuFeature feature = (IAdorufuFeature) e.load().getConstructor().newInstance();
                        Manager.Feature.registerFeature(feature);
                        AdorufuMod.logMsg(true, "Registered " + feature.getFeatureName() + "!");
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
                        e1.printStackTrace();
                        AdorufuMod.logErr(true, "A severe uncaught exception occurred whilst initialising " + e.getSimpleName() + "!");
                    }
                });
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

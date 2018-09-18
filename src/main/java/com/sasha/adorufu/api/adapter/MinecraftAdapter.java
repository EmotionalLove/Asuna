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

package com.sasha.adorufu.api.adapter;

import com.google.common.util.concurrent.ListenableFuture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.util.ISearchTree;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Snooper;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.Session;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import org.lwjgl.LWJGLException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;

/**
 * Created by Sasha at 5:52 PM on 9/17/2018
 */
public class MinecraftAdapter {

    public void run() {
        mc.run();
    }

    public void init() throws LWJGLException, IOException {
        mc.init();
    }

    public void populateSearchTreeManager() {
        mc.populateSearchTreeManager();
    }

    public void registerMetadataSerializers() {
        mc.registerMetadataSerializers();
    }

    public void createDisplay() throws LWJGLException {
        mc.createDisplay();
    }

    public void setInitialDisplayMode() throws LWJGLException {
        mc.setInitialDisplayMode();
    }

    public void setWindowIcon() {
        mc.setWindowIcon();
    }

    public static boolean isJvm64bit() {
        return Minecraft.isJvm64bit();
    }

    public Framebuffer getFramebuffer() {
        return mc.getFramebuffer();
    }

    public String getVersion() {
        return mc.getVersion();
    }

    public String getVersionType() {
        return mc.getVersionType();
    }

    public void startTimerHackThread() {
        mc.startTimerHackThread();
    }

    public void crashed(CrashReport crash) {
        mc.crashed(crash);
    }

    public void displayCrashReport(CrashReport crashReportIn) {
        mc.displayCrashReport(crashReportIn);
    }

    public boolean isUnicode() {
        return mc.isUnicode();
    }

    public void refreshResources() {
        mc.refreshResources();
    }

    public ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        return mc.readImageToBuffer(imageStream);
    }

    public void updateDisplayMode() throws LWJGLException {
        mc.updateDisplayMode();
    }

    public void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException {
        mc.drawSplashScreen(textureManagerInstance);
    }

    public void draw(int posX, int posY, int texU, int texV, int width, int height, int red, int green, int blue, int alpha) {
        mc.draw(posX, posY, texU, texV, width, height, red, green, blue, alpha);
    }

    public ISaveFormat getSaveLoader() {
        return mc.getSaveLoader();
    }

    public void displayGuiScreen(@Nullable GuiScreen guiScreenIn) {
        mc.displayGuiScreen(guiScreenIn);
    }

    public void checkGLError(String message) {
        mc.checkGLError(message);
    }

    public void shutdownMinecraftApplet() {
        mc.shutdownMinecraftApplet();
    }

    public void runGameLoop() throws IOException {
        mc.runGameLoop();
    }

    public void updateDisplay() {
        mc.updateDisplay();
    }

    public void checkWindowResize() {
        mc.checkWindowResize();
    }

    public int getLimitFramerate() {
        return mc.getLimitFramerate();
    }

    public boolean isFramerateLimitBelowMax() {
        return mc.isFramerateLimitBelowMax();
    }

    public void freeMemory() {
        mc.freeMemory();
    }

    public void updateDebugProfilerName(int keyCount) {
        mc.updateDebugProfilerName(keyCount);
    }

    public void displayDebugInfo(long elapsedTicksTime) {
        mc.displayDebugInfo(elapsedTicksTime);
    }

    public void shutdown() {
        mc.shutdown();
    }

    public void setIngameFocus() {
        mc.setIngameFocus();
    }

    public void setIngameNotInFocus() {
        mc.setIngameNotInFocus();
    }

    public void displayInGameMenu() {
        mc.displayInGameMenu();
    }

    public void sendClickBlockToController(boolean leftClick) {
        mc.sendClickBlockToController(leftClick);
    }

    public void clickMouse() {
        mc.clickMouse();
    }

    public void rightClickMouse() {
        mc.rightClickMouse();
    }

    public void toggleFullscreen() {
        mc.toggleFullscreen();
    }

    public void resize(int width, int height) {
        mc.resize(width, height);
    }

    public void updateFramebufferSize() {
        mc.updateFramebufferSize();
    }

    public MusicTicker getMusicTicker() {
        return mc.getMusicTicker();
    }

    public void runTick() throws IOException {
        mc.runTick();
    }

    public void runTickKeyboard() throws IOException {
        mc.runTickKeyboard();
    }

    public boolean processKeyF3(int auxKey) {
        return mc.processKeyF3(auxKey);
    }

    public void processKeyBinds() {
        mc.processKeyBinds();
    }

    public void runTickMouse() throws IOException {
        mc.runTickMouse();
    }

    public void debugFeedbackTranslated(String untranslatedTemplate, Object... objs) {
        mc.debugFeedbackTranslated(untranslatedTemplate, objs);
    }

    public void launchIntegratedServer(String folderName, String worldName, @Nullable WorldSettings worldSettingsIn) {
        mc.launchIntegratedServer(folderName, worldName, worldSettingsIn);
    }

    public void loadWorld(@Nullable WorldClient worldClientIn) {
        mc.loadWorld(worldClientIn);
    }

    public void loadWorld(@Nullable WorldClient worldClientIn, String loadingMessage) {
        mc.loadWorld(worldClientIn, loadingMessage);
    }

    public void setDimensionAndSpawnPlayer(int dimension) {
        mc.setDimensionAndSpawnPlayer(dimension);
    }

    public boolean isDemo() {
        return mc.isDemo();
    }

    @Nullable
    public NetHandlerPlayClient getConnection() {
        return mc.getConnection();
    }

    public static boolean isGuiEnabled() {
        return Minecraft.isGuiEnabled();
    }

    public static boolean isFancyGraphicsEnabled() {
        return Minecraft.isFancyGraphicsEnabled();
    }

    public static boolean isAmbientOcclusionEnabled() {
        return Minecraft.isAmbientOcclusionEnabled();
    }

    public void middleClickMouse() {
        mc.middleClickMouse();
    }

    public ItemStack storeTEInStack(ItemStack stack, TileEntity te) {
        return mc.storeTEInStack(stack, te);
    }

    public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash) {
        return mc.addGraphicsAndWorldToCrashReport(theCrash);
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public ListenableFuture<Object> scheduleResourcesRefresh() {
        return mc.scheduleResourcesRefresh();
    }

    public void addServerStatsToSnooper(Snooper playerSnooper) {
        mc.addServerStatsToSnooper(playerSnooper);
    }

    public String getCurrentAction() {
        return mc.getCurrentAction();
    }

    public void addServerTypeToSnooper(Snooper playerSnooper) {
        mc.addServerTypeToSnooper(playerSnooper);
    }

    public static int getGLMaximumTextureSize() {
        return Minecraft.getGLMaximumTextureSize();
    }

    public boolean isSnooperEnabled() {
        return mc.isSnooperEnabled();
    }

    public void setServerData(ServerData serverDataIn) {
        mc.setServerData(serverDataIn);
    }

    @Nullable
    public ServerData getCurrentServerData() {
        return mc.getCurrentServerData();
    }

    public boolean isIntegratedServerRunning() {
        return mc.isIntegratedServerRunning();
    }

    public boolean isSingleplayer() {
        return mc.isSingleplayer();
    }

    @Nullable
    public IntegratedServer getIntegratedServer() {
        return mc.getIntegratedServer();
    }

    public static void stopIntegratedServer() {
        Minecraft.stopIntegratedServer();
    }

    public Snooper getPlayerUsageSnooper() {
        return mc.getPlayerUsageSnooper();
    }

    public static long getSystemTime() {
        return Minecraft.getSystemTime();
    }

    public boolean isFullScreen() {
        return mc.isFullScreen();
    }

    public Session getSession() {
        return mc.getSession();
    }

    public PropertyMap getProfileProperties() {
        return mc.getProfileProperties();
    }

    public Proxy getProxy() {
        return mc.getProxy();
    }

    public TextureManager getTextureManager() {
        return mc.getTextureManager();
    }

    public IResourceManager getResourceManager() {
        return mc.getResourceManager();
    }

    public ResourcePackRepository getResourcePackRepository() {
        return mc.getResourcePackRepository();
    }

    public LanguageManager getLanguageManager() {
        return mc.getLanguageManager();
    }

    public TextureMap getTextureMapBlocks() {
        return mc.getTextureMapBlocks();
    }

    public boolean isJava64bit() {
        return mc.isJava64bit();
    }

    public boolean isGamePaused() {
        return mc.isGamePaused();
    }

    public SoundHandler getSoundHandler() {
        return mc.getSoundHandler();
    }

    public MusicTicker.MusicType getAmbientMusicType() {
        return mc.getAmbientMusicType();
    }

    public void dispatchKeypresses() {
        mc.dispatchKeypresses();
    }

    public MinecraftSessionService getSessionService() {
        return mc.getSessionService();
    }

    public SkinManager getSkinManager() {
        return mc.getSkinManager();
    }

    @Nullable
    public Entity getRenderViewEntity() {
        return mc.getRenderViewEntity();
    }

    public void setRenderViewEntity(Entity viewingEntity) {
        mc.setRenderViewEntity(viewingEntity);
    }

    public <V> ListenableFuture<V> addScheduledTask(Callable<V> callableToSchedule) {
        return mc.addScheduledTask(callableToSchedule);
    }

    public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
        return mc.addScheduledTask(runnableToSchedule);
    }

    public boolean isCallingFromMinecraftThread() {
        return mc.isCallingFromMinecraftThread();
    }

    public BlockRendererDispatcher getBlockRendererDispatcher() {
        return mc.getBlockRendererDispatcher();
    }

    public RenderManager getRenderManager() {
        return mc.getRenderManager();
    }

    public RenderItem getRenderItem() {
        return mc.getRenderItem();
    }

    public ItemRenderer getItemRenderer() {
        return mc.getItemRenderer();
    }

    public <T> ISearchTree<T> getSearchTree(SearchTreeManager.Key<T> key) {
        return mc.getSearchTree(key);
    }

    public static int getDebugFPS() {
        return Minecraft.getDebugFPS();
    }

    public FrameTimer getFrameTimer() {
        return mc.getFrameTimer();
    }

    public boolean isConnectedToRealms() {
        return mc.isConnectedToRealms();
    }

    public void setConnectedToRealms(boolean isConnected) {
        mc.setConnectedToRealms(isConnected);
    }

    public DataFixer getDataFixer() {
        return mc.getDataFixer();
    }

    public float getRenderPartialTicks() {
        return mc.getRenderPartialTicks();
    }

    public float getTickLength() {
        return mc.getTickLength();
    }

    public BlockColors getBlockColors() {
        return mc.getBlockColors();
    }

    public ItemColors getItemColors() {
        return mc.getItemColors();
    }

    public boolean isReducedDebug() {
        return mc.isReducedDebug();
    }

    public GuiToast getToastGui() {
        return mc.getToastGui();
    }

    public Tutorial getTutorial() {
        return mc.getTutorial();
    }

    public SearchTreeManager getSearchTreeManager() {
        return mc.getSearchTreeManager();
    }

    private final Minecraft mc;

    public MinecraftAdapter(Minecraft mc) {
        this.mc = mc;
    }
}

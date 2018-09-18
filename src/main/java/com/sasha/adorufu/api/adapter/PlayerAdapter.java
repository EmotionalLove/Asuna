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

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.command.CommandResultStats;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.*;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Sasha at 5:31 PM on 9/17/2018
 * im out of ideas kms
 */
public class PlayerAdapter {

    /*
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    public float timeInPortal;
    public float prevTimeInPortal;
     */

    private final EntityPlayerSP player;

    public PlayerAdapter(EntityPlayerSP player) {
        this.player = player;
    }
    public int getSprintingTicksLeft() {
        return player.sprintingTicksLeft;
    }
    public float getRenderArmYaw() {
        return player.renderArmYaw;
    }
    public float getRenderArmPitch() {
        return player.renderArmPitch;
    }
    public float getPrevRenderArmYaw() {
        return player.prevRenderArmYaw;
    }
    public float getPrevRenderArmPitch() {
        return player.prevRenderArmPitch;
    }
    public float getTimeInPortal() {
        return player.timeInPortal;
    }
    public float getPrevTimeInPortal() {
        return player.prevTimeInPortal;
    }
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return player.attackEntityFrom(source, amount);
    }
    public void heal(float healAmount)
    {
        player.heal(healAmount);
    }
    public boolean startRiding(Entity entityIn, boolean force)
    {
        return player.startRiding(entityIn, force);
    }
    public void dismountRidingEntity()
    {
        player.dismountRidingEntity();
    }

    public Vec3d getLook(float partialTicks) {
        return player.getLook(partialTicks);
    }

    @Nullable
    public EntityItem dropItem(boolean dropAll) {
        return player.dropItem(dropAll);
    }

    public ItemStack dropItemAndGetStack(EntityItem p_184816_1_) {
        return player.dropItemAndGetStack(p_184816_1_);
    }

    public void sendChatMessage(String message) {
        player.sendChatMessage(message);
    }

    public void swingArm(EnumHand hand) {
        player.swingArm(hand);
    }

    public void respawnPlayer() {
        player.respawnPlayer();
    }

    public void closeScreen() {
        player.closeScreen();
    }

    public void closeScreenAndDropStack() {
        player.closeScreenAndDropStack();
    }

    public void setPlayerSPHealth(float health) {
        player.setPlayerSPHealth(health);
    }

    public void addStat(StatBase stat, int amount) {
        player.addStat(stat, amount);
    }

    public void sendPlayerAbilities() {
        player.sendPlayerAbilities();
    }

    public boolean isUser() {
        return player.isUser();
    }

    public void sendHorseInventory() {
        player.sendHorseInventory();
    }

    public void setServerBrand(String brand) {
        player.setServerBrand(brand);
    }

    public String getServerBrand() {
        return player.getServerBrand();
    }

    public StatisticsManager getStatFileWriter() {
        return player.getStatFileWriter();
    }

    public RecipeBook getRecipeBook() {
        return player.getRecipeBook();
    }

    public void removeRecipeHighlight(IRecipe p_193103_1_) {
        player.removeRecipeHighlight(p_193103_1_);
    }

    public int getPermissionLevel() {
        return player.getPermissionLevel();
    }

    public void setPermissionLevel(int p_184839_1_) {
        player.setPermissionLevel(p_184839_1_);
    }

    public void sendStatusMessage(ITextComponent chatComponent, boolean actionBar) {
        player.sendStatusMessage(chatComponent, actionBar);
    }

    public void setSprinting(boolean sprinting) {
        player.setSprinting(sprinting);
    }

    public void setXPStats(float currentXP, int maxXP, int level) {
        player.setXPStats(currentXP, maxXP, level);
    }

    public void sendMessage(ITextComponent component) {
        player.sendMessage(component);
    }

    public boolean canUseCommand(int permLevel, String commandName) {
        return player.canUseCommand(permLevel, commandName);
    }

    public void handleStatusUpdate(byte id) {
        player.handleStatusUpdate(id);
    }

    public BlockPos getPosition() {
        return player.getPosition();
    }

    public void playSound(SoundEvent soundIn, float volume, float pitch) {
        player.playSound(soundIn, volume, pitch);
    }

    public boolean isServerWorld() {
        return player.isServerWorld();
    }

    public void setActiveHand(EnumHand hand) {
        player.setActiveHand(hand);
    }

    public boolean isHandActive() {
        return player.isHandActive();
    }

    public void resetActiveHand() {
        player.resetActiveHand();
    }

    public EnumHand getActiveHand() {
        return player.getActiveHand();
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        player.notifyDataManagerChange(key);
    }

    public boolean isRidingHorse() {
        return player.isRidingHorse();
    }

    public float getHorseJumpPower() {
        return player.getHorseJumpPower();
    }

    public void openEditSign(TileEntitySign signTile) {
        player.openEditSign(signTile);
    }

    public void displayGuiEditCommandCart(CommandBlockBaseLogic commandBlock) {
        player.displayGuiEditCommandCart(commandBlock);
    }

    public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock) {
        player.displayGuiCommandBlock(commandBlock);
    }

    public void openEditStructure(TileEntityStructure structure) {
        player.openEditStructure(structure);
    }

    public void openBook(ItemStack stack, EnumHand hand) {
        player.openBook(stack, hand);
    }

    public void displayGUIChest(IInventory chestInventory) {
        player.displayGUIChest(chestInventory);
    }

    public void openGuiHorseInventory(AbstractHorse horse, IInventory inventoryIn) {
        player.openGuiHorseInventory(horse, inventoryIn);
    }

    public void displayGui(IInteractionObject guiOwner) {
        player.displayGui(guiOwner);
    }

    public void displayVillagerTradeGui(IMerchant villager) {
        player.displayVillagerTradeGui(villager);
    }

    public void onCriticalHit(Entity entityHit) {
        player.onCriticalHit(entityHit);
    }

    public void onEnchantmentCritical(Entity entityHit) {
        player.onEnchantmentCritical(entityHit);
    }

    public boolean isSneaking() {
        return player.isSneaking();
    }

    public void updateEntityActionState() {
        player.updateEntityActionState();
    }

    public void onLivingUpdate() {
        player.onLivingUpdate();
    }

    public void updateRidden() {
        player.updateRidden();
    }

    public boolean isRowingBoat() {
        return player.isRowingBoat();
    }

    @Nullable
    public PotionEffect removeActivePotionEffect(@Nullable Potion potioneffectin) {
        return player.removeActivePotionEffect(potioneffectin);
    }

    public void move(MoverType type, double x, double y, double z) {
        player.move(type, x, y, z);
    }

    public boolean isAutoJumpEnabled() {
        return player.isAutoJumpEnabled();
    }

    public boolean isSpectator() {
        return player.isSpectator();
    }

    public boolean isCreative() {
        return player.isCreative();
    }

    public boolean hasPlayerInfo() {
        return player.hasPlayerInfo();
    }

    public boolean hasSkin() {
        return player.hasSkin();
    }

    public ResourceLocation getLocationSkin() {
        return player.getLocationSkin();
    }

    @Nullable
    public ResourceLocation getLocationCape() {
        return player.getLocationCape();
    }

    public boolean isPlayerInfoSet() {
        return player.isPlayerInfoSet();
    }

    @Nullable
    public ResourceLocation getLocationElytra() {
        return player.getLocationElytra();
    }

    public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
        return AbstractClientPlayer.getDownloadImageSkin(resourceLocationIn, username);
    }

    public static ResourceLocation getLocationSkin(String username) {
        return AbstractClientPlayer.getLocationSkin(username);
    }

    public String getSkinType() {
        return player.getSkinType();
    }

    public float getFovModifier() {
        return player.getFovModifier();
    }

    public int getMaxInPortalTime() {
        return player.getMaxInPortalTime();
    }

    public int getPortalCooldown() {
        return player.getPortalCooldown();
    }

    public SoundCategory getSoundCategory() {
        return player.getSoundCategory();
    }

    @SideOnly(Side.CLIENT)
    public void preparePlayerToSpawn() {
        player.preparePlayerToSpawn();
    }

    public int getScore() {
        return player.getScore();
    }

    public void setScore(int scoreIn) {
        player.setScore(scoreIn);
    }

    public void addScore(int scoreIn) {
        player.addScore(scoreIn);
    }

    public void onDeath(DamageSource cause) {
        player.onDeath(cause);
    }

    @Nullable
    public EntityItem dropItem(ItemStack itemStackIn, boolean unused) {
        return player.dropItem(itemStackIn, unused);
    }

    @Nullable
    public EntityItem dropItem(ItemStack droppedItem, boolean dropAround, boolean traceItem) {
        return player.dropItem(droppedItem, dropAround, traceItem);
    }

    @Deprecated
    public float getDigSpeed(IBlockState state) {
        return player.getDigSpeed(state);
    }

    public float getDigSpeed(IBlockState state, BlockPos pos) {
        return player.getDigSpeed(state, pos);
    }

    public boolean canHarvestBlock(IBlockState state) {
        return player.canHarvestBlock(state);
    }

    public static void registerFixesPlayer(DataFixer fixer) {
        EntityPlayer.registerFixesPlayer(fixer);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        player.readEntityFromNBT(compound);
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        player.writeEntityToNBT(compound);
    }

    public boolean canAttackPlayer(EntityPlayer other) {
        return player.canAttackPlayer(other);
    }

    public float getArmorVisibility() {
        return player.getArmorVisibility();
    }

    public EnumActionResult interactOn(Entity p_190775_1_, EnumHand p_190775_2_) {
        return player.interactOn(p_190775_1_, p_190775_2_);
    }

    public double getYOffset() {
        return player.getYOffset();
    }

    public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
        player.attackTargetEntityWithCurrentItem(targetEntity);
    }

    public void disableShield(boolean p_190777_1_) {
        player.disableShield(p_190777_1_);
    }

    public void spawnSweepParticles() {
        player.spawnSweepParticles();
    }

    public void setDead() {
        player.setDead();
    }

    public boolean isEntityInsideOpaqueBlock() {
        return player.isEntityInsideOpaqueBlock();
    }

    public GameProfile getGameProfile() {
        return player.getGameProfile();
    }

    public EntityPlayer.SleepResult trySleep(BlockPos bedLocation) {
        return player.trySleep(bedLocation);
    }

    public void wakeUpPlayer(boolean immediately, boolean updateWorldFlag, boolean setSpawn) {
        player.wakeUpPlayer(immediately, updateWorldFlag, setSpawn);
    }

    @Nullable
    public static BlockPos getBedSpawnLocation(World worldIn, BlockPos bedLocation, boolean forceSpawn) {
        return EntityPlayer.getBedSpawnLocation(worldIn, bedLocation, forceSpawn);
    }

    @SideOnly(Side.CLIENT)
    public float getBedOrientationInDegrees() {
        return player.getBedOrientationInDegrees();
    }

    public boolean isPlayerSleeping() {
        return player.isPlayerSleeping();
    }

    public boolean isPlayerFullyAsleep() {
        return player.isPlayerFullyAsleep();
    }

    @SideOnly(Side.CLIENT)
    public int getSleepTimer() {
        return player.getSleepTimer();
    }

    public BlockPos getBedLocation() {
        return player.getBedLocation();
    }

    @Deprecated
    public boolean isSpawnForced() {
        return player.isSpawnForced();
    }

    public void setSpawnPoint(BlockPos pos, boolean forced) {
        player.setSpawnPoint(pos, forced);
    }

    public void addStat(StatBase stat) {
        player.addStat(stat);
    }

    public void takeStat(StatBase stat) {
        player.takeStat(stat);
    }

    public void unlockRecipes(List<IRecipe> p_192021_1_) {
        player.unlockRecipes(p_192021_1_);
    }

    public void unlockRecipes(ResourceLocation[] p_193102_1_) {
        player.unlockRecipes(p_193102_1_);
    }

    public void resetRecipes(List<IRecipe> p_192022_1_) {
        player.resetRecipes(p_192022_1_);
    }

    public void jump() {
        player.jump();
    }

    public void travel(float strafe, float vertical, float forward) {
        player.travel(strafe, vertical, forward);
    }

    public float getAIMoveSpeed() {
        return player.getAIMoveSpeed();
    }

    public void addMovementStat(double p_71000_1_, double p_71000_3_, double p_71000_5_) {
        player.addMovementStat(p_71000_1_, p_71000_3_, p_71000_5_);
    }

    public void fall(float distance, float damageMultiplier) {
        player.fall(distance, damageMultiplier);
    }

    public void onKillEntity(EntityLivingBase entityLivingIn) {
        player.onKillEntity(entityLivingIn);
    }

    public void setInWeb() {
        player.setInWeb();
    }

    public void addExperience(int amount) {
        player.addExperience(amount);
    }

    public int getXPSeed() {
        return player.getXPSeed();
    }

    public void onEnchant(ItemStack enchantedItem, int cost) {
        player.onEnchant(enchantedItem, cost);
    }

    public void addExperienceLevel(int levels) {
        player.addExperienceLevel(levels);
    }

    public int xpBarCap() {
        return player.xpBarCap();
    }

    public void addExhaustion(float exhaustion) {
        player.addExhaustion(exhaustion);
    }

    public FoodStats getFoodStats() {
        return player.getFoodStats();
    }

    public boolean canEat(boolean ignoreHunger) {
        return player.canEat(ignoreHunger);
    }

    public boolean shouldHeal() {
        return player.shouldHeal();
    }

    public boolean isAllowEdit() {
        return player.isAllowEdit();
    }

    public boolean canPlayerEdit(BlockPos pos, EnumFacing facing, ItemStack stack) {
        return player.canPlayerEdit(pos, facing, stack);
    }

    @SideOnly(Side.CLIENT)
    public boolean getAlwaysRenderNameTagForRender() {
        return player.getAlwaysRenderNameTagForRender();
    }

    public void setGameType(GameType gameType) {
        player.setGameType(gameType);
    }

    public String getName() {
        return player.getName();
    }

    public InventoryEnderChest getInventoryEnderChest() {
        return player.getInventoryEnderChest();
    }

    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return player.getItemStackFromSlot(slotIn);
    }

    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
        player.setItemStackToSlot(slotIn, stack);
    }

    public boolean addItemStackToInventory(ItemStack p_191521_1_) {
        return player.addItemStackToInventory(p_191521_1_);
    }

    public Iterable<ItemStack> getHeldEquipment() {
        return player.getHeldEquipment();
    }

    public Iterable<ItemStack> getArmorInventoryList() {
        return player.getArmorInventoryList();
    }

    public boolean addShoulderEntity(NBTTagCompound p_192027_1_) {
        return player.addShoulderEntity(p_192027_1_);
    }

    @SideOnly(Side.CLIENT)
    public boolean isInvisibleToPlayer(EntityPlayer player) {
        return this.player.isInvisibleToPlayer(player);
    }

    public boolean isPushedByWater() {
        return player.isPushedByWater();
    }

    public Scoreboard getWorldScoreboard() {
        return player.getWorldScoreboard();
    }

    public Team getTeam() {
        return player.getTeam();
    }

    public ITextComponent getDisplayName() {
        return player.getDisplayName();
    }

    public float getEyeHeight() {
        return player.getEyeHeight();
    }

    public void setAbsorptionAmount(float amount) {
        player.setAbsorptionAmount(amount);
    }

    public float getAbsorptionAmount() {
        return player.getAbsorptionAmount();
    }

    public static UUID getUUID(GameProfile profile) {
        return EntityPlayer.getUUID(profile);
    }

    public static UUID getOfflineUUID(String username) {
        return EntityPlayer.getOfflineUUID(username);
    }

    public boolean canOpen(LockCode code) {
        return player.canOpen(code);
    }

    @SideOnly(Side.CLIENT)
    public boolean isWearing(EnumPlayerModelParts part) {
        return player.isWearing(part);
    }

    public boolean sendCommandFeedback() {
        return player.sendCommandFeedback();
    }

    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        return player.replaceItemInInventory(inventorySlot, itemStackIn);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasReducedDebug() {
        return player.hasReducedDebug();
    }

    @SideOnly(Side.CLIENT)
    public void setReducedDebug(boolean reducedDebug) {
        player.setReducedDebug(reducedDebug);
    }

    public EnumHandSide getPrimaryHand() {
        return player.getPrimaryHand();
    }

    public void setPrimaryHand(EnumHandSide hand) {
        player.setPrimaryHand(hand);
    }

    public NBTTagCompound getLeftShoulderEntity() {
        return player.getLeftShoulderEntity();
    }

    public NBTTagCompound getRightShoulderEntity() {
        return player.getRightShoulderEntity();
    }

    public float getCooldownPeriod() {
        return player.getCooldownPeriod();
    }

    public float getCooledAttackStrength(float adjustTicks) {
        return player.getCooledAttackStrength(adjustTicks);
    }

    public void resetCooldown() {
        player.resetCooldown();
    }

    public CooldownTracker getCooldownTracker() {
        return player.getCooldownTracker();
    }

    public void applyEntityCollision(Entity entityIn) {
        player.applyEntityCollision(entityIn);
    }

    public float getLuck() {
        return player.getLuck();
    }

    public boolean canUseCommandBlock() {
        return player.canUseCommandBlock();
    }

    public void openGui(Object mod, int modGuiId, World world, int x, int y, int z) {
        player.openGui(mod, modGuiId, world, x, y, z);
    }

    public BlockPos getBedLocation(int dimension) {
        return player.getBedLocation(dimension);
    }

    public boolean isSpawnForced(int dimension) {
        return player.isSpawnForced(dimension);
    }

    public void setSpawnChunk(BlockPos pos, boolean forced, int dimension) {
        player.setSpawnChunk(pos, forced, dimension);
    }

    public float getDefaultEyeHeight() {
        return player.getDefaultEyeHeight();
    }

    public String getDisplayNameString() {
        return player.getDisplayNameString();
    }

    public void refreshDisplayName() {
        player.refreshDisplayName();
    }

    public void addPrefix(ITextComponent prefix) {
        player.addPrefix(prefix);
    }

    public void addSuffix(ITextComponent suffix) {
        player.addSuffix(suffix);
    }

    public Collection<ITextComponent> getPrefixes() {
        return player.getPrefixes();
    }

    public Collection<ITextComponent> getSuffixes() {
        return player.getSuffixes();
    }

    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return player.getCapability(capability, facing);
    }

    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return player.hasCapability(capability, facing);
    }

    public boolean hasSpawnDimension() {
        return player.hasSpawnDimension();
    }

    public int getSpawnDimension() {
        return player.getSpawnDimension();
    }

    public void setSpawnDimension(@Nullable Integer dimension) {
        player.setSpawnDimension(dimension);
    }

    public void onKillCommand() {
        player.onKillCommand();
    }

    public boolean canBreatheUnderwater() {
        return player.canBreatheUnderwater();
    }

    public void onEntityUpdate() {
        player.onEntityUpdate();
    }

    public boolean isChild() {
        return player.isChild();
    }

    public Random getRNG() {
        return player.getRNG();
    }

    @Nullable
    public EntityLivingBase getRevengeTarget() {
        return player.getRevengeTarget();
    }

    public int getRevengeTimer() {
        return player.getRevengeTimer();
    }

    public void setRevengeTarget(@Nullable EntityLivingBase livingBase) {
        player.setRevengeTarget(livingBase);
    }

    public EntityLivingBase getLastAttackedEntity() {
        return player.getLastAttackedEntity();
    }

    public int getLastAttackedEntityTime() {
        return player.getLastAttackedEntityTime();
    }

    public void setLastAttackedEntity(Entity entityIn) {
        player.setLastAttackedEntity(entityIn);
    }

    public int getIdleTime() {
        return player.getIdleTime();
    }

    public static boolean areAllPotionsAmbient(Collection<PotionEffect> potionEffects) {
        return EntityLivingBase.areAllPotionsAmbient(potionEffects);
    }

    public void clearActivePotions() {
        player.clearActivePotions();
    }

    public Collection<PotionEffect> getActivePotionEffects() {
        return player.getActivePotionEffects();
    }

    public Map<Potion, PotionEffect> getActivePotionMap() {
        return player.getActivePotionMap();
    }

    public boolean isPotionActive(Potion potionIn) {
        return player.isPotionActive(potionIn);
    }

    @Nullable
    public PotionEffect getActivePotionEffect(Potion potionIn) {
        return player.getActivePotionEffect(potionIn);
    }

    public void addPotionEffect(PotionEffect potioneffectIn) {
        player.addPotionEffect(potioneffectIn);
    }

    public boolean isPotionApplicable(PotionEffect potioneffectIn) {
        return player.isPotionApplicable(potioneffectIn);
    }

    public boolean isEntityUndead() {
        return player.isEntityUndead();
    }

    public void removePotionEffect(Potion potionIn) {
        player.removePotionEffect(potionIn);
    }

    public float getHealth() {
        return player.getHealth();
    }

    public void setHealth(float health) {
        player.setHealth(health);
    }

    @Nullable
    public DamageSource getLastDamageSource() {
        return player.getLastDamageSource();
    }

    public void renderBrokenItemStack(ItemStack stack) {
        player.renderBrokenItemStack(stack);
    }

    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
        player.knockBack(entityIn, strength, xRatio, zRatio);
    }

    public boolean isOnLadder() {
        return player.isOnLadder();
    }

    public boolean isEntityAlive() {
        return player.isEntityAlive();
    }

    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
        player.performHurtAnimation();
    }

    public int getTotalArmorValue() {
        return player.getTotalArmorValue();
    }

    public CombatTracker getCombatTracker() {
        return player.getCombatTracker();
    }

    @Nullable
    public EntityLivingBase getAttackingEntity() {
        return player.getAttackingEntity();
    }

    public float getMaxHealth() {
        return player.getMaxHealth();
    }

    public int getArrowCountInEntity() {
        return player.getArrowCountInEntity();
    }

    public void setArrowCountInEntity(int count) {
        player.setArrowCountInEntity(count);
    }

    public IAttributeInstance getEntityAttribute(IAttribute attribute) {
        return player.getEntityAttribute(attribute);
    }

    public AbstractAttributeMap getAttributeMap() {
        return player.getAttributeMap();
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return player.getCreatureAttribute();
    }

    public ItemStack getHeldItemMainhand() {
        return player.getHeldItemMainhand();
    }

    public ItemStack getHeldItemOffhand() {
        return player.getHeldItemOffhand();
    }

    public ItemStack getHeldItem(EnumHand hand) {
        return player.getHeldItem(hand);
    }

    public void setHeldItem(EnumHand hand, ItemStack stack) {
        player.setHeldItem(hand, stack);
    }

    public boolean hasItemInSlot(EntityEquipmentSlot p_190630_1_) {
        return player.hasItemInSlot(p_190630_1_);
    }

    public void dismountEntity(Entity entityIn) {
        player.dismountEntity(entityIn);
    }

    public void setAIMoveSpeed(float speedIn) {
        player.setAIMoveSpeed(speedIn);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        return player.attackEntityAsMob(entityIn);
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        player.setPositionAndRotationDirect(x, y, z, yaw, pitch, posRotationIncrements, teleport);
    }

    public void setJumping(boolean jumping) {
        player.setJumping(jumping);
    }

    public void onItemPickup(Entity entityIn, int quantity) {
        player.onItemPickup(entityIn, quantity);
    }

    public boolean canEntityBeSeen(Entity entityIn) {
        return player.canEntityBeSeen(entityIn);
    }

    @SideOnly(Side.CLIENT)
    public float getSwingProgress(float partialTickTime) {
        return player.getSwingProgress(partialTickTime);
    }

    public boolean canBeCollidedWith() {
        return player.canBeCollidedWith();
    }

    public boolean canBePushed() {
        return player.canBePushed();
    }

    public float getRotationYawHead() {
        return player.getRotationYawHead();
    }

    public void setRotationYawHead(float rotation) {
        player.setRotationYawHead(rotation);
    }

    public void setRenderYawOffset(float offset) {
        player.setRenderYawOffset(offset);
    }

    public void sendEnterCombat() {
        player.sendEnterCombat();
    }

    public void sendEndCombat() {
        player.sendEndCombat();
    }

    public void curePotionEffects(ItemStack curativeItem) {
        player.curePotionEffects(curativeItem);
    }

    public boolean shouldRiderFaceForward(EntityPlayer player) {
        return this.player.shouldRiderFaceForward(player);
    }

    public ItemStack getActiveItemStack() {
        return player.getActiveItemStack();
    }

    public int getItemInUseCount() {
        return player.getItemInUseCount();
    }

    public int getItemInUseMaxCount() {
        return player.getItemInUseMaxCount();
    }

    public void stopActiveHand() {
        player.stopActiveHand();
    }

    public boolean isActiveItemStackBlocking() {
        return player.isActiveItemStackBlocking();
    }

    public boolean isElytraFlying() {
        return player.isElytraFlying();
    }

    @SideOnly(Side.CLIENT)
    public int getTicksElytraFlying() {
        return player.getTicksElytraFlying();
    }

    public boolean attemptTeleport(double x, double y, double z) {
        return player.attemptTeleport(x, y, z);
    }

    public boolean canBeHitWithPotion() {
        return player.canBeHitWithPotion();
    }

    public boolean attackable() {
        return player.attackable();
    }

    @SideOnly(Side.CLIENT)
    public void setPartying(BlockPos pos, boolean p_191987_2_) {
        player.setPartying(pos, p_191987_2_);
    }

    public int getEntityId() {
        return player.getEntityId();
    }

    public void setEntityId(int id) {
        player.setEntityId(id);
    }

    public Set<String> getTags() {
        return player.getTags();
    }

    public boolean addTag(String tag) {
        return player.addTag(tag);
    }

    public boolean removeTag(String tag) {
        return player.removeTag(tag);
    }

    public EntityDataManager getDataManager() {
        return player.getDataManager();
    }

    public void setDropItemsWhenDead(boolean dropWhenDead) {
        player.setDropItemsWhenDead(dropWhenDead);
    }

    public void setPosition(double x, double y, double z) {
        player.setPosition(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public void turn(float yaw, float pitch) {
        player.turn(yaw, pitch);
    }

    public void setFire(int seconds) {
        player.setFire(seconds);
    }

    public void extinguish() {
        player.extinguish();
    }

    public boolean isOffsetPositionInLiquid(double x, double y, double z) {
        return player.isOffsetPositionInLiquid(x, y, z);
    }

    public void resetPositionToBB() {
        player.resetPositionToBB();
    }

    public boolean isSilent() {
        return player.isSilent();
    }

    public void setSilent(boolean isSilent) {
        player.setSilent(isSilent);
    }

    public boolean hasNoGravity() {
        return player.hasNoGravity();
    }

    public void setNoGravity(boolean noGravity) {
        player.setNoGravity(noGravity);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return player.getCollisionBoundingBox();
    }

    public boolean isImmuneToFire() {
        return player.isImmuneToFire();
    }

    public boolean isWet() {
        return player.isWet();
    }

    public boolean isInWater() {
        return player.isInWater();
    }

    public boolean isOverWater() {
        return player.isOverWater();
    }

    public boolean handleWaterMovement() {
        return player.handleWaterMovement();
    }

    public void spawnRunningParticles() {
        player.spawnRunningParticles();
    }

    public boolean isInsideOfMaterial(Material materialIn) {
        return player.isInsideOfMaterial(materialIn);
    }

    public boolean isInLava() {
        return player.isInLava();
    }

    public void moveRelative(float strafe, float up, float forward, float friction) {
        player.moveRelative(strafe, up, forward, friction);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return player.getBrightnessForRender();
    }

    public float getBrightness() {
        return player.getBrightness();
    }

    public void setWorld(World worldIn) {
        player.setWorld(worldIn);
    }

    public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
        player.setPositionAndRotation(x, y, z, yaw, pitch);
    }

    public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn) {
        player.moveToBlockPosAndAngles(pos, rotationYawIn, rotationPitchIn);
    }

    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
        player.setLocationAndAngles(x, y, z, yaw, pitch);
    }

    public float getDistance(Entity entityIn) {
        return player.getDistance(entityIn);
    }

    public double getDistanceSq(double x, double y, double z) {
        return player.getDistanceSq(x, y, z);
    }

    public double getDistanceSq(BlockPos pos) {
        return player.getDistanceSq(pos);
    }

    public double getDistanceSqToCenter(BlockPos pos) {
        return player.getDistanceSqToCenter(pos);
    }

    public double getDistance(double x, double y, double z) {
        return player.getDistance(x, y, z);
    }

    public double getDistanceSq(Entity entityIn) {
        return player.getDistanceSq(entityIn);
    }

    public void onCollideWithPlayer(EntityPlayer entityIn) {
        player.onCollideWithPlayer(entityIn);
    }

    public void addVelocity(double x, double y, double z) {
        player.addVelocity(x, y, z);
    }

    public Vec3d getPositionEyes(float partialTicks) {
        return player.getPositionEyes(partialTicks);
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    public RayTraceResult rayTrace(double blockReachDistance, float partialTicks) {
        return player.rayTrace(blockReachDistance, partialTicks);
    }

    public void awardKillScore(Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
        player.awardKillScore(p_191956_1_, p_191956_2_, p_191956_3_);
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return player.isInRangeToRender3d(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return player.isInRangeToRenderDist(distance);
    }

    public boolean writeToNBTAtomically(NBTTagCompound compound) {
        return player.writeToNBTAtomically(compound);
    }

    public boolean writeToNBTOptional(NBTTagCompound compound) {
        return player.writeToNBTOptional(compound);
    }

    public static void registerFixes(DataFixer fixer) {
        Entity.registerFixes(fixer);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return player.writeToNBT(compound);
    }

    public void readFromNBT(NBTTagCompound compound) {
        player.readFromNBT(compound);
    }

    @Nullable
    public EntityItem dropItem(Item itemIn, int size) {
        return player.dropItem(itemIn, size);
    }

    @Nullable
    public EntityItem dropItemWithOffset(Item itemIn, int size, float offsetY) {
        return player.dropItemWithOffset(itemIn, size, offsetY);
    }

    @Nullable
    public EntityItem entityDropItem(ItemStack stack, float offsetY) {
        return player.entityDropItem(stack, offsetY);
    }

    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        return this.player.processInitialInteract(player, hand);
    }

    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return player.getCollisionBox(entityIn);
    }

    public void updatePassenger(Entity passenger) {
        player.updatePassenger(passenger);
    }

    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate) {
        player.applyOrientationToEntity(entityToUpdate);
    }

    public double getMountedYOffset() {
        return player.getMountedYOffset();
    }

    public boolean startRiding(Entity entityIn) {
        return player.startRiding(entityIn);
    }

    public void removePassengers() {
        player.removePassengers();
    }

    public float getCollisionBorderSize() {
        return player.getCollisionBorderSize();
    }

    public Vec3d getLookVec() {
        return player.getLookVec();
    }

    @SideOnly(Side.CLIENT)
    public Vec2f getPitchYaw() {
        return player.getPitchYaw();
    }

    @SideOnly(Side.CLIENT)
    public Vec3d getForward() {
        return player.getForward();
    }

    public void setPortal(BlockPos pos) {
        player.setPortal(pos);
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z) {
        player.setVelocity(x, y, z);
    }

    public Iterable<ItemStack> getEquipmentAndArmor() {
        return player.getEquipmentAndArmor();
    }

    public boolean isBurning() {
        return player.isBurning();
    }

    public boolean isRiding() {
        return player.isRiding();
    }

    public boolean isBeingRidden() {
        return player.isBeingRidden();
    }

    public void setSneaking(boolean sneaking) {
        player.setSneaking(sneaking);
    }

    public boolean isSprinting() {
        return player.isSprinting();
    }

    public boolean isGlowing() {
        return player.isGlowing();
    }

    public void setGlowing(boolean glowingIn) {
        player.setGlowing(glowingIn);
    }

    public boolean isInvisible() {
        return player.isInvisible();
    }

    public boolean isOnSameTeam(Entity entityIn) {
        return player.isOnSameTeam(entityIn);
    }

    public boolean isOnScoreboardTeam(Team teamIn) {
        return player.isOnScoreboardTeam(teamIn);
    }

    public void setInvisible(boolean invisible) {
        player.setInvisible(invisible);
    }

    public int getAir() {
        return player.getAir();
    }

    public void setAir(int air) {
        player.setAir(air);
    }

    public void onStruckByLightning(EntityLightningBolt lightningBolt) {
        player.onStruckByLightning(lightningBolt);
    }

    @Nullable
    public Entity[] getParts() {
        return player.getParts();
    }

    public boolean isEntityEqual(Entity entityIn) {
        return player.isEntityEqual(entityIn);
    }

    public boolean canBeAttackedWithItem() {
        return player.canBeAttackedWithItem();
    }

    public boolean hitByEntity(Entity entityIn) {
        return player.hitByEntity(entityIn);
    }

    public boolean isEntityInvulnerable(DamageSource source) {
        return player.isEntityInvulnerable(source);
    }

    public boolean getIsInvulnerable() {
        return player.getIsInvulnerable();
    }

    public void setEntityInvulnerable(boolean isInvulnerable) {
        player.setEntityInvulnerable(isInvulnerable);
    }

    public void copyLocationAndAnglesFrom(Entity entityIn) {
        player.copyLocationAndAnglesFrom(entityIn);
    }

    @Nullable
    public Entity changeDimension(int dimensionIn) {
        return player.changeDimension(dimensionIn);
    }

    public boolean isNonBoss() {
        return player.isNonBoss();
    }

    public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
        return player.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
    }

    public boolean canExplosionDestroyBlock(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
        return player.canExplosionDestroyBlock(explosionIn, worldIn, pos, blockStateIn, p_174816_5_);
    }

    public int getMaxFallHeight() {
        return player.getMaxFallHeight();
    }

    public Vec3d getLastPortalVec() {
        return player.getLastPortalVec();
    }

    public EnumFacing getTeleportDirection() {
        return player.getTeleportDirection();
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return player.doesEntityNotTriggerPressurePlate();
    }

    public void addEntityCrashInfo(CrashReportCategory category) {
        player.addEntityCrashInfo(category);
    }

    public void setUniqueId(UUID uniqueIdIn) {
        player.setUniqueId(uniqueIdIn);
    }

    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return player.canRenderOnFire();
    }

    public UUID getUniqueID() {
        return player.getUniqueID();
    }

    public String getCachedUniqueIdString() {
        return player.getCachedUniqueIdString();
    }

    @SideOnly(Side.CLIENT)
    public static double getRenderDistanceWeight() {
        return Entity.getRenderDistanceWeight();
    }

    @SideOnly(Side.CLIENT)
    public static void setRenderDistanceWeight(double renderDistWeight) {
        Entity.setRenderDistanceWeight(renderDistWeight);
    }

    public void setCustomNameTag(String name) {
        player.setCustomNameTag(name);
    }

    public String getCustomNameTag() {
        return player.getCustomNameTag();
    }

    public boolean hasCustomName() {
        return player.hasCustomName();
    }

    public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
        player.setAlwaysRenderNameTag(alwaysRenderNameTag);
    }

    public boolean getAlwaysRenderNameTag() {
        return player.getAlwaysRenderNameTag();
    }

    public void setPositionAndUpdate(double x, double y, double z) {
        player.setPositionAndUpdate(x, y, z);
    }

    public EnumFacing getHorizontalFacing() {
        return player.getHorizontalFacing();
    }

    public EnumFacing getAdjustedHorizontalFacing() {
        return player.getAdjustedHorizontalFacing();
    }

    public boolean isSpectatedByPlayer(EntityPlayerMP player) {
        return this.player.isSpectatedByPlayer(player);
    }

    public AxisAlignedBB getEntityBoundingBox() {
        return player.getEntityBoundingBox();
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return player.getRenderBoundingBox();
    }

    public void setEntityBoundingBox(AxisAlignedBB bb) {
        player.setEntityBoundingBox(bb);
    }

    public boolean isOutsideBorder() {
        return player.isOutsideBorder();
    }

    public void setOutsideBorder(boolean outsideBorder) {
        player.setOutsideBorder(outsideBorder);
    }

    public Vec3d getPositionVector() {
        return player.getPositionVector();
    }

    public World getEntityWorld() {
        return player.getEntityWorld();
    }

    public Entity getCommandSenderEntity() {
        return player.getCommandSenderEntity();
    }

    public void setCommandStat(CommandResultStats.Type type, int amount) {
        player.setCommandStat(type, amount);
    }

    @Nullable
    public MinecraftServer getServer() {
        return player.getServer();
    }

    public CommandResultStats getCommandStats() {
        return player.getCommandStats();
    }

    public void setCommandStats(Entity entityIn) {
        player.setCommandStats(entityIn);
    }

    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
        return this.player.applyPlayerInteraction(player, vec, hand);
    }

    public boolean isImmuneToExplosions() {
        return player.isImmuneToExplosions();
    }

    public NBTTagCompound getEntityData() {
        return player.getEntityData();
    }

    public boolean shouldRiderSit() {
        return player.shouldRiderSit();
    }

    public ItemStack getPickedResult(RayTraceResult target) {
        return player.getPickedResult(target);
    }

    public UUID getPersistentID() {
        return player.getPersistentID();
    }

    @Deprecated
    public void resetEntityId() {
        player.resetEntityId();
    }

    public boolean shouldRenderInPass(int pass) {
        return player.shouldRenderInPass(pass);
    }

    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
        return player.isCreatureType(type, forSpawnCount);
    }

    public boolean canRiderInteract() {
        return player.canRiderInteract();
    }

    public boolean shouldDismountInWater(Entity rider) {
        return player.shouldDismountInWater(rider);
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        player.deserializeNBT(nbt);
    }

    public NBTTagCompound serializeNBT() {
        return player.serializeNBT();
    }

    public boolean canTrample(World world, Block block, BlockPos pos, float fallDistance) {
        return player.canTrample(world, block, pos, fallDistance);
    }

    public void addTrackingPlayer(EntityPlayerMP player) {
        this.player.addTrackingPlayer(player);
    }

    public void removeTrackingPlayer(EntityPlayerMP player) {
        this.player.removeTrackingPlayer(player);
    }

    public float getRotatedYaw(Rotation transformRotation) {
        return player.getRotatedYaw(transformRotation);
    }

    public float getMirroredYaw(Mirror transformMirror) {
        return player.getMirroredYaw(transformMirror);
    }

    public boolean ignoreItemEntityData() {
        return player.ignoreItemEntityData();
    }

    public boolean setPositionNonDirty() {
        return player.setPositionNonDirty();
    }

    @Nullable
    public Entity getControllingPassenger() {
        return player.getControllingPassenger();
    }

    public List<Entity> getPassengers() {
        return player.getPassengers();
    }

    public boolean isPassenger(Entity entityIn) {
        return player.isPassenger(entityIn);
    }

    public Collection<Entity> getRecursivePassengers() {
        return player.getRecursivePassengers();
    }

    public <T extends Entity> Collection<T> getRecursivePassengersByType(Class<T> entityClass) {
        return player.getRecursivePassengersByType(entityClass);
    }

    public Entity getLowestRidingEntity() {
        return player.getLowestRidingEntity();
    }

    public boolean isRidingSameEntity(Entity entityIn) {
        return player.isRidingSameEntity(entityIn);
    }

    public boolean isRidingOrBeingRiddenBy(Entity entityIn) {
        return player.isRidingOrBeingRiddenBy(entityIn);
    }

    public boolean canPassengerSteer() {
        return player.canPassengerSteer();
    }

    @Nullable
    public Entity getRidingEntity() {
        return player.getRidingEntity();
    }

    public EnumPushReaction getPushReaction() {
        return player.getPushReaction();
    }

    public void onUpdate()
    {
        player.onUpdate();
    }
}

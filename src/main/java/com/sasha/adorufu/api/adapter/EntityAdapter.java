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

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandResultStats;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Sasha at 5:54 PM on 9/17/2018
 */
public class EntityAdapter {

    public int getEntityId() {
        return entity.getEntityId();
    }

    public void setEntityId(int id) {
        entity.setEntityId(id);
    }

    public Set<String> getTags() {
        return entity.getTags();
    }

    public boolean addTag(String tag) {
        return entity.addTag(tag);
    }

    public boolean removeTag(String tag) {
        return entity.removeTag(tag);
    }

    public void onKillCommand() {
        entity.onKillCommand();
    }

    public EntityDataManager getDataManager() {
        return entity.getDataManager();
    }

    public void setDead() {
        entity.setDead();
    }

    public void setDropItemsWhenDead(boolean dropWhenDead) {
        entity.setDropItemsWhenDead(dropWhenDead);
    }

    public void setPosition(double x, double y, double z) {
        entity.setPosition(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public void turn(float yaw, float pitch) {
        entity.turn(yaw, pitch);
    }

    public void onUpdate() {
        entity.onUpdate();
    }

    public void onEntityUpdate() {
        entity.onEntityUpdate();
    }

    public int getMaxInPortalTime() {
        return entity.getMaxInPortalTime();
    }

    public void setFire(int seconds) {
        entity.setFire(seconds);
    }

    public void extinguish() {
        entity.extinguish();
    }

    public boolean isOffsetPositionInLiquid(double x, double y, double z) {
        return entity.isOffsetPositionInLiquid(x, y, z);
    }

    public void move(MoverType type, double x, double y, double z) {
        entity.move(type, x, y, z);
    }

    public void resetPositionToBB() {
        entity.resetPositionToBB();
    }

    public void playSound(SoundEvent soundIn, float volume, float pitch) {
        entity.playSound(soundIn, volume, pitch);
    }

    public boolean isSilent() {
        return entity.isSilent();
    }

    public void setSilent(boolean isSilent) {
        entity.setSilent(isSilent);
    }

    public boolean hasNoGravity() {
        return entity.hasNoGravity();
    }

    public void setNoGravity(boolean noGravity) {
        entity.setNoGravity(noGravity);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return entity.getCollisionBoundingBox();
    }

    public boolean isImmuneToFire() {
        return entity.isImmuneToFire();
    }

    public void fall(float distance, float damageMultiplier) {
        entity.fall(distance, damageMultiplier);
    }

    public boolean isWet() {
        return entity.isWet();
    }

    public boolean isInWater() {
        return entity.isInWater();
    }

    public boolean isOverWater() {
        return entity.isOverWater();
    }

    public boolean handleWaterMovement() {
        return entity.handleWaterMovement();
    }

    public void spawnRunningParticles() {
        entity.spawnRunningParticles();
    }

    public boolean isInsideOfMaterial(Material materialIn) {
        return entity.isInsideOfMaterial(materialIn);
    }

    public boolean isInLava() {
        return entity.isInLava();
    }

    public void moveRelative(float strafe, float up, float forward, float friction) {
        entity.moveRelative(strafe, up, forward, friction);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return entity.getBrightnessForRender();
    }

    public float getBrightness() {
        return entity.getBrightness();
    }

    public void setWorld(World worldIn) {
        entity.setWorld(worldIn);
    }

    public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
        entity.setPositionAndRotation(x, y, z, yaw, pitch);
    }

    public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn) {
        entity.moveToBlockPosAndAngles(pos, rotationYawIn, rotationPitchIn);
    }

    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
        entity.setLocationAndAngles(x, y, z, yaw, pitch);
    }

    public float getDistance(Entity entityIn) {
        return entity.getDistance(entityIn);
    }

    public double getDistanceSq(double x, double y, double z) {
        return entity.getDistanceSq(x, y, z);
    }

    public double getDistanceSq(BlockPos pos) {
        return entity.getDistanceSq(pos);
    }

    public double getDistanceSqToCenter(BlockPos pos) {
        return entity.getDistanceSqToCenter(pos);
    }

    public double getDistance(double x, double y, double z) {
        return entity.getDistance(x, y, z);
    }

    public double getDistanceSq(Entity entityIn) {
        return entity.getDistanceSq(entityIn);
    }

    public void onCollideWithPlayer(EntityPlayer entityIn) {
        entity.onCollideWithPlayer(entityIn);
    }

    public void applyEntityCollision(Entity entityIn) {
        entity.applyEntityCollision(entityIn);
    }

    public void addVelocity(double x, double y, double z) {
        entity.addVelocity(x, y, z);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return entity.attackEntityFrom(source, amount);
    }

    public Vec3d getLook(float partialTicks) {
        return entity.getLook(partialTicks);
    }

    public Vec3d getPositionEyes(float partialTicks) {
        return entity.getPositionEyes(partialTicks);
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    public RayTraceResult rayTrace(double blockReachDistance, float partialTicks) {
        return entity.rayTrace(blockReachDistance, partialTicks);
    }

    public boolean canBeCollidedWith() {
        return entity.canBeCollidedWith();
    }

    public boolean canBePushed() {
        return entity.canBePushed();
    }

    public void awardKillScore(Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
        entity.awardKillScore(p_191956_1_, p_191956_2_, p_191956_3_);
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return entity.isInRangeToRender3d(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return entity.isInRangeToRenderDist(distance);
    }

    public boolean writeToNBTAtomically(NBTTagCompound compound) {
        return entity.writeToNBTAtomically(compound);
    }

    public boolean writeToNBTOptional(NBTTagCompound compound) {
        return entity.writeToNBTOptional(compound);
    }

    public static void registerFixes(DataFixer fixer) {
        Entity.registerFixes(fixer);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return entity.writeToNBT(compound);
    }

    public void readFromNBT(NBTTagCompound compound) {
        entity.readFromNBT(compound);
    }

    @Nullable
    public EntityItem dropItem(Item itemIn, int size) {
        return entity.dropItem(itemIn, size);
    }

    @Nullable
    public EntityItem dropItemWithOffset(Item itemIn, int size, float offsetY) {
        return entity.dropItemWithOffset(itemIn, size, offsetY);
    }

    @Nullable
    public EntityItem entityDropItem(ItemStack stack, float offsetY) {
        return entity.entityDropItem(stack, offsetY);
    }

    public boolean isEntityAlive() {
        return entity.isEntityAlive();
    }

    public boolean isEntityInsideOpaqueBlock() {
        return entity.isEntityInsideOpaqueBlock();
    }

    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        return entity.processInitialInteract(player, hand);
    }

    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return entity.getCollisionBox(entityIn);
    }

    public void updateRidden() {
        entity.updateRidden();
    }

    public void updatePassenger(Entity passenger) {
        entity.updatePassenger(passenger);
    }

    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate) {
        entity.applyOrientationToEntity(entityToUpdate);
    }

    public double getYOffset() {
        return entity.getYOffset();
    }

    public double getMountedYOffset() {
        return entity.getMountedYOffset();
    }

    public boolean startRiding(Entity entityIn) {
        return entity.startRiding(entityIn);
    }

    public boolean startRiding(Entity entityIn, boolean force) {
        return entity.startRiding(entityIn, force);
    }

    public void removePassengers() {
        entity.removePassengers();
    }

    public void dismountRidingEntity() {
        entity.dismountRidingEntity();
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        entity.setPositionAndRotationDirect(x, y, z, yaw, pitch, posRotationIncrements, teleport);
    }

    public float getCollisionBorderSize() {
        return entity.getCollisionBorderSize();
    }

    public Vec3d getLookVec() {
        return entity.getLookVec();
    }

    @SideOnly(Side.CLIENT)
    public Vec2f getPitchYaw() {
        return entity.getPitchYaw();
    }

    @SideOnly(Side.CLIENT)
    public Vec3d getForward() {
        return entity.getForward();
    }

    public void setPortal(BlockPos pos) {
        entity.setPortal(pos);
    }

    public int getPortalCooldown() {
        return entity.getPortalCooldown();
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z) {
        entity.setVelocity(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        entity.handleStatusUpdate(id);
    }

    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
        entity.performHurtAnimation();
    }

    public Iterable<ItemStack> getHeldEquipment() {
        return entity.getHeldEquipment();
    }

    public Iterable<ItemStack> getArmorInventoryList() {
        return entity.getArmorInventoryList();
    }

    public Iterable<ItemStack> getEquipmentAndArmor() {
        return entity.getEquipmentAndArmor();
    }

    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
        entity.setItemStackToSlot(slotIn, stack);
    }

    public boolean isBurning() {
        return entity.isBurning();
    }

    public boolean isRiding() {
        return entity.isRiding();
    }

    public boolean isBeingRidden() {
        return entity.isBeingRidden();
    }

    public boolean isSneaking() {
        return entity.isSneaking();
    }

    public void setSneaking(boolean sneaking) {
        entity.setSneaking(sneaking);
    }

    public boolean isSprinting() {
        return entity.isSprinting();
    }

    public void setSprinting(boolean sprinting) {
        entity.setSprinting(sprinting);
    }

    public boolean isGlowing() {
        return entity.isGlowing();
    }

    public void setGlowing(boolean glowingIn) {
        entity.setGlowing(glowingIn);
    }

    public boolean isInvisible() {
        return entity.isInvisible();
    }

    @SideOnly(Side.CLIENT)
    public boolean isInvisibleToPlayer(EntityPlayer player) {
        return entity.isInvisibleToPlayer(player);
    }

    @Nullable
    public Team getTeam() {
        return entity.getTeam();
    }

    public boolean isOnSameTeam(Entity entityIn) {
        return entity.isOnSameTeam(entityIn);
    }

    public boolean isOnScoreboardTeam(Team teamIn) {
        return entity.isOnScoreboardTeam(teamIn);
    }

    public void setInvisible(boolean invisible) {
        entity.setInvisible(invisible);
    }

    public int getAir() {
        return entity.getAir();
    }

    public void setAir(int air) {
        entity.setAir(air);
    }

    public void onStruckByLightning(EntityLightningBolt lightningBolt) {
        entity.onStruckByLightning(lightningBolt);
    }

    public void onKillEntity(EntityLivingBase entityLivingIn) {
        entity.onKillEntity(entityLivingIn);
    }

    public void setInWeb() {
        entity.setInWeb();
    }

    public String getName() {
        return entity.getName();
    }

    @Nullable
    public Entity[] getParts() {
        return entity.getParts();
    }

    public boolean isEntityEqual(Entity entityIn) {
        return entity.isEntityEqual(entityIn);
    }

    public float getRotationYawHead() {
        return entity.getRotationYawHead();
    }

    public void setRotationYawHead(float rotation) {
        entity.setRotationYawHead(rotation);
    }

    public void setRenderYawOffset(float offset) {
        entity.setRenderYawOffset(offset);
    }

    public boolean canBeAttackedWithItem() {
        return entity.canBeAttackedWithItem();
    }

    public boolean hitByEntity(Entity entityIn) {
        return entity.hitByEntity(entityIn);
    }

    public boolean isEntityInvulnerable(DamageSource source) {
        return entity.isEntityInvulnerable(source);
    }

    public boolean getIsInvulnerable() {
        return entity.getIsInvulnerable();
    }

    public void setEntityInvulnerable(boolean isInvulnerable) {
        entity.setEntityInvulnerable(isInvulnerable);
    }

    public void copyLocationAndAnglesFrom(Entity entityIn) {
        entity.copyLocationAndAnglesFrom(entityIn);
    }

    @Nullable
    public Entity changeDimension(int dimensionIn) {
        return entity.changeDimension(dimensionIn);
    }

    public boolean isNonBoss() {
        return entity.isNonBoss();
    }

    public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
        return entity.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
    }

    public boolean canExplosionDestroyBlock(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
        return entity.canExplosionDestroyBlock(explosionIn, worldIn, pos, blockStateIn, p_174816_5_);
    }

    public int getMaxFallHeight() {
        return entity.getMaxFallHeight();
    }

    public Vec3d getLastPortalVec() {
        return entity.getLastPortalVec();
    }

    public EnumFacing getTeleportDirection() {
        return entity.getTeleportDirection();
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return entity.doesEntityNotTriggerPressurePlate();
    }

    public void addEntityCrashInfo(CrashReportCategory category) {
        entity.addEntityCrashInfo(category);
    }

    public void setUniqueId(UUID uniqueIdIn) {
        entity.setUniqueId(uniqueIdIn);
    }

    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return entity.canRenderOnFire();
    }

    public UUID getUniqueID() {
        return entity.getUniqueID();
    }

    public String getCachedUniqueIdString() {
        return entity.getCachedUniqueIdString();
    }

    public boolean isPushedByWater() {
        return entity.isPushedByWater();
    }

    @SideOnly(Side.CLIENT)
    public static double getRenderDistanceWeight() {
        return Entity.getRenderDistanceWeight();
    }

    @SideOnly(Side.CLIENT)
    public static void setRenderDistanceWeight(double renderDistWeight) {
        Entity.setRenderDistanceWeight(renderDistWeight);
    }

    public ITextComponent getDisplayName() {
        return entity.getDisplayName();
    }

    public void setCustomNameTag(String name) {
        entity.setCustomNameTag(name);
    }

    public String getCustomNameTag() {
        return entity.getCustomNameTag();
    }

    public boolean hasCustomName() {
        return entity.hasCustomName();
    }

    public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
        entity.setAlwaysRenderNameTag(alwaysRenderNameTag);
    }

    public boolean getAlwaysRenderNameTag() {
        return entity.getAlwaysRenderNameTag();
    }

    public void setPositionAndUpdate(double x, double y, double z) {
        entity.setPositionAndUpdate(x, y, z);
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        entity.notifyDataManagerChange(key);
    }

    @SideOnly(Side.CLIENT)
    public boolean getAlwaysRenderNameTagForRender() {
        return entity.getAlwaysRenderNameTagForRender();
    }

    public EnumFacing getHorizontalFacing() {
        return entity.getHorizontalFacing();
    }

    public EnumFacing getAdjustedHorizontalFacing() {
        return entity.getAdjustedHorizontalFacing();
    }

    public boolean isSpectatedByPlayer(EntityPlayerMP player) {
        return entity.isSpectatedByPlayer(player);
    }

    public AxisAlignedBB getEntityBoundingBox() {
        return entity.getEntityBoundingBox();
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return entity.getRenderBoundingBox();
    }

    public void setEntityBoundingBox(AxisAlignedBB bb) {
        entity.setEntityBoundingBox(bb);
    }

    public float getEyeHeight() {
        return entity.getEyeHeight();
    }

    public boolean isOutsideBorder() {
        return entity.isOutsideBorder();
    }

    public void setOutsideBorder(boolean outsideBorder) {
        entity.setOutsideBorder(outsideBorder);
    }

    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        return entity.replaceItemInInventory(inventorySlot, itemStackIn);
    }

    public void sendMessage(ITextComponent component) {
        entity.sendMessage(component);
    }

    public boolean canUseCommand(int permLevel, String commandName) {
        return entity.canUseCommand(permLevel, commandName);
    }

    public BlockPos getPosition() {
        return entity.getPosition();
    }

    public Vec3d getPositionVector() {
        return entity.getPositionVector();
    }

    public World getEntityWorld() {
        return entity.getEntityWorld();
    }

    public Entity getCommandSenderEntity() {
        return entity.getCommandSenderEntity();
    }

    public boolean sendCommandFeedback() {
        return entity.sendCommandFeedback();
    }

    public void setCommandStat(CommandResultStats.Type type, int amount) {
        entity.setCommandStat(type, amount);
    }

    @Nullable
    public MinecraftServer getServer() {
        return entity.getServer();
    }

    public CommandResultStats getCommandStats() {
        return entity.getCommandStats();
    }

    public void setCommandStats(Entity entityIn) {
        entity.setCommandStats(entityIn);
    }

    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
        return entity.applyPlayerInteraction(player, vec, hand);
    }

    public boolean isImmuneToExplosions() {
        return entity.isImmuneToExplosions();
    }

    public NBTTagCompound getEntityData() {
        return entity.getEntityData();
    }

    public boolean shouldRiderSit() {
        return entity.shouldRiderSit();
    }

    public ItemStack getPickedResult(RayTraceResult target) {
        return entity.getPickedResult(target);
    }

    public UUID getPersistentID() {
        return entity.getPersistentID();
    }

    @Deprecated
    public void resetEntityId() {
        entity.resetEntityId();
    }

    public boolean shouldRenderInPass(int pass) {
        return entity.shouldRenderInPass(pass);
    }

    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
        return entity.isCreatureType(type, forSpawnCount);
    }

    public boolean canRiderInteract() {
        return entity.canRiderInteract();
    }

    public boolean shouldDismountInWater(Entity rider) {
        return entity.shouldDismountInWater(rider);
    }

    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return entity.hasCapability(capability, facing);
    }

    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return entity.getCapability(capability, facing);
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        entity.deserializeNBT(nbt);
    }

    public NBTTagCompound serializeNBT() {
        return entity.serializeNBT();
    }

    public boolean canTrample(World world, Block block, BlockPos pos, float fallDistance) {
        return entity.canTrample(world, block, pos, fallDistance);
    }

    public void addTrackingPlayer(EntityPlayerMP player) {
        entity.addTrackingPlayer(player);
    }

    public void removeTrackingPlayer(EntityPlayerMP player) {
        entity.removeTrackingPlayer(player);
    }

    public float getRotatedYaw(Rotation transformRotation) {
        return entity.getRotatedYaw(transformRotation);
    }

    public float getMirroredYaw(Mirror transformMirror) {
        return entity.getMirroredYaw(transformMirror);
    }

    public boolean ignoreItemEntityData() {
        return entity.ignoreItemEntityData();
    }

    public boolean setPositionNonDirty() {
        return entity.setPositionNonDirty();
    }

    @Nullable
    public Entity getControllingPassenger() {
        return entity.getControllingPassenger();
    }

    public List<Entity> getPassengers() {
        return entity.getPassengers();
    }

    public boolean isPassenger(Entity entityIn) {
        return entity.isPassenger(entityIn);
    }

    public Collection<Entity> getRecursivePassengers() {
        return entity.getRecursivePassengers();
    }

    public <T extends Entity> Collection<T> getRecursivePassengersByType(Class<T> entityClass) {
        return entity.getRecursivePassengersByType(entityClass);
    }

    public Entity getLowestRidingEntity() {
        return entity.getLowestRidingEntity();
    }

    public boolean isRidingSameEntity(Entity entityIn) {
        return entity.isRidingSameEntity(entityIn);
    }

    public boolean isRidingOrBeingRiddenBy(Entity entityIn) {
        return entity.isRidingOrBeingRiddenBy(entityIn);
    }

    public boolean canPassengerSteer() {
        return entity.canPassengerSteer();
    }

    @Nullable
    public Entity getRidingEntity() {
        return entity.getRidingEntity();
    }

    public EnumPushReaction getPushReaction() {
        return entity.getPushReaction();
    }

    public SoundCategory getSoundCategory() {
        return entity.getSoundCategory();
    }

    private final Entity entity;

    public EntityAdapter(Entity entity) {
        this.entity = entity;
    }
}

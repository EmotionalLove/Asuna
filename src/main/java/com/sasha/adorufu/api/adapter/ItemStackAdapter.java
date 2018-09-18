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

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by Sasha at 5:58 PM on 9/17/2018
 */
public class ItemStackAdapter {

    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    public static void registerFixes(DataFixer fixer) {
        ItemStack.registerFixes(fixer);
    }

    public ItemStack splitStack(int amount) {
        return itemStack.splitStack(amount);
    }

    public Item getItem() {
        return itemStack.getItem();
    }

    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        return itemStack.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
    }

    public EnumActionResult onItemUseFirst(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        return itemStack.onItemUseFirst(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
    }

    public float getDestroySpeed(IBlockState blockIn) {
        return itemStack.getDestroySpeed(blockIn);
    }

    public ActionResult<ItemStack> useItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        return itemStack.useItemRightClick(worldIn, playerIn, hand);
    }

    public ItemStack onItemUseFinish(World worldIn, EntityLivingBase entityLiving) {
        return itemStack.onItemUseFinish(worldIn, entityLiving);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return itemStack.writeToNBT(nbt);
    }

    public int getMaxStackSize() {
        return itemStack.getMaxStackSize();
    }

    public boolean isStackable() {
        return itemStack.isStackable();
    }

    public boolean isItemStackDamageable() {
        return itemStack.isItemStackDamageable();
    }

    public boolean getHasSubtypes() {
        return itemStack.getHasSubtypes();
    }

    public boolean isItemDamaged() {
        return itemStack.isItemDamaged();
    }

    public int getItemDamage() {
        return itemStack.getItemDamage();
    }

    public int getMetadata() {
        return itemStack.getMetadata();
    }

    public void setItemDamage(int meta) {
        itemStack.setItemDamage(meta);
    }

    public int getMaxDamage() {
        return itemStack.getMaxDamage();
    }

    public boolean attemptDamageItem(int amount, Random rand, @Nullable EntityPlayerMP damager) {
        return itemStack.attemptDamageItem(amount, rand, damager);
    }

    public void damageItem(int amount, EntityLivingBase entityIn) {
        itemStack.damageItem(amount, entityIn);
    }

    public void hitEntity(EntityLivingBase entityIn, EntityPlayer playerIn) {
        itemStack.hitEntity(entityIn, playerIn);
    }

    public void onBlockDestroyed(World worldIn, IBlockState blockIn, BlockPos pos, EntityPlayer playerIn) {
        itemStack.onBlockDestroyed(worldIn, blockIn, pos, playerIn);
    }

    public boolean canHarvestBlock(IBlockState blockIn) {
        return itemStack.canHarvestBlock(blockIn);
    }

    public boolean interactWithEntity(EntityPlayer playerIn, EntityLivingBase entityIn, EnumHand hand) {
        return itemStack.interactWithEntity(playerIn, entityIn, hand);
    }

    public ItemStack copy() {
        return itemStack.copy();
    }

    public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemStackTagsEqual(stackA, stackB);
    }

    public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemStacksEqual(stackA, stackB);
    }

    public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemsEqual(stackA, stackB);
    }

    public static boolean areItemsEqualIgnoreDurability(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemsEqualIgnoreDurability(stackA, stackB);
    }

    public boolean isItemEqual(ItemStack other) {
        return itemStack.isItemEqual(other);
    }

    public boolean isItemEqualIgnoreDurability(ItemStack stack) {
        return itemStack.isItemEqualIgnoreDurability(stack);
    }

    public String getUnlocalizedName() {
        return itemStack.getUnlocalizedName();
    }

    public void updateAnimation(World worldIn, Entity entityIn, int inventorySlot, boolean isCurrentItem) {
        itemStack.updateAnimation(worldIn, entityIn, inventorySlot, isCurrentItem);
    }

    public void onCrafting(World worldIn, EntityPlayer playerIn, int amount) {
        itemStack.onCrafting(worldIn, playerIn, amount);
    }

    public int getMaxItemUseDuration() {
        return itemStack.getMaxItemUseDuration();
    }

    public EnumAction getItemUseAction() {
        return itemStack.getItemUseAction();
    }

    public void onPlayerStoppedUsing(World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        itemStack.onPlayerStoppedUsing(worldIn, entityLiving, timeLeft);
    }

    public boolean hasTagCompound() {
        return itemStack.hasTagCompound();
    }

    @Nullable
    public NBTTagCompound getTagCompound() {
        return itemStack.getTagCompound();
    }

    public NBTTagCompound getOrCreateSubCompound(String key) {
        return itemStack.getOrCreateSubCompound(key);
    }

    @Nullable
    public NBTTagCompound getSubCompound(String key) {
        return itemStack.getSubCompound(key);
    }

    public void removeSubCompound(String key) {
        itemStack.removeSubCompound(key);
    }

    public NBTTagList getEnchantmentTagList() {
        return itemStack.getEnchantmentTagList();
    }

    public void setTagCompound(@Nullable NBTTagCompound nbt) {
        itemStack.setTagCompound(nbt);
    }

    public String getDisplayName() {
        return itemStack.getDisplayName();
    }

    public ItemStack setTranslatableName(String p_190924_1_) {
        return itemStack.setTranslatableName(p_190924_1_);
    }

    public ItemStack setStackDisplayName(String displayName) {
        return itemStack.setStackDisplayName(displayName);
    }

    public void clearCustomName() {
        itemStack.clearCustomName();
    }

    public boolean hasDisplayName() {
        return itemStack.hasDisplayName();
    }

    @SideOnly(Side.CLIENT)
    public List<String> getTooltip(@Nullable EntityPlayer playerIn, ITooltipFlag advanced) {
        return itemStack.getTooltip(playerIn, advanced);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect() {
        return itemStack.hasEffect();
    }

    public EnumRarity getRarity() {
        return itemStack.getRarity();
    }

    public boolean isItemEnchantable() {
        return itemStack.isItemEnchantable();
    }

    public void addEnchantment(Enchantment ench, int level) {
        itemStack.addEnchantment(ench, level);
    }

    public boolean isItemEnchanted() {
        return itemStack.isItemEnchanted();
    }

    public void setTagInfo(String key, NBTBase value) {
        itemStack.setTagInfo(key, value);
    }

    public boolean canEditBlocks() {
        return itemStack.canEditBlocks();
    }

    public boolean isOnItemFrame() {
        return itemStack.isOnItemFrame();
    }

    public void setItemFrame(EntityItemFrame frame) {
        itemStack.setItemFrame(frame);
    }

    @Nullable
    public EntityItemFrame getItemFrame() {
        return itemStack.getItemFrame();
    }

    public int getRepairCost() {
        return itemStack.getRepairCost();
    }

    public void setRepairCost(int cost) {
        itemStack.setRepairCost(cost);
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        return itemStack.getAttributeModifiers(equipmentSlot);
    }

    public void addAttributeModifier(String attributeName, AttributeModifier modifier, @Nullable EntityEquipmentSlot equipmentSlot) {
        itemStack.addAttributeModifier(attributeName, modifier, equipmentSlot);
    }

    public ITextComponent getTextComponent() {
        return itemStack.getTextComponent();
    }

    public boolean canDestroy(Block blockIn) {
        return itemStack.canDestroy(blockIn);
    }

    public boolean canPlaceOn(Block blockIn) {
        return itemStack.canPlaceOn(blockIn);
    }

    public int getAnimationsToGo() {
        return itemStack.getAnimationsToGo();
    }

    public void setAnimationsToGo(int animations) {
        itemStack.setAnimationsToGo(animations);
    }

    public int getCount() {
        return itemStack.getCount();
    }

    public void setCount(int size) {
        itemStack.setCount(size);
    }

    public void grow(int quantity) {
        itemStack.grow(quantity);
    }

    public void shrink(int quantity) {
        itemStack.shrink(quantity);
    }

    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return itemStack.hasCapability(capability, facing);
    }

    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return itemStack.getCapability(capability, facing);
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        itemStack.deserializeNBT(nbt);
    }

    public NBTTagCompound serializeNBT() {
        return itemStack.serializeNBT();
    }

    public boolean areCapsCompatible(ItemStack other) {
        return itemStack.areCapsCompatible(other);
    }

    public static boolean areItemStacksEqualUsingNBTShareTag(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemStacksEqualUsingNBTShareTag(stackA, stackB);
    }

    public static boolean areItemStackShareTagsEqual(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemStackShareTagsEqual(stackA, stackB);
    }

    public boolean doesSneakBypassUse(IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return itemStack.doesSneakBypassUse(world, pos, player);
    }

    private final ItemStack itemStack;

    public ItemStackAdapter(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}

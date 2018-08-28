package com.sasha.adorufu.module.modules;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static net.minecraft.init.Items.*;

/**
 * Created by Sasha at 11:03 AM on 8/28/2018
 */
public class ModuleAutoArmor extends AdorufuModule {
    public ModuleAutoArmor() {
        super("AutoArmor", AdorufuCategory.COMBAT, false);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        ItemStack helmet = AdorufuMod.minecraft.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack chest = AdorufuMod.minecraft.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack legs = AdorufuMod.minecraft.player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack feet = AdorufuMod.minecraft.player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        if (AdorufuMod.minecraft.currentScreen == null) {
            if (isHelmet(helmet.getItem())) {

            }
        }
    }

    public boolean isHelmet(Item item) {
        if (item == LEATHER_HELMET) return true;
        if (item == CHAINMAIL_HELMET) return true;
        if (item == IRON_HELMET) return true;
        if (item == GOLDEN_HELMET) return true;
        if (item == DIAMOND_HELMET) return true;
        return false;
    }

    public void upgradeHelmetCheck(ItemStack currentHelmet) {
        LinkedHashMap<ItemStack, Integer> helms = new LinkedHashMap<>();
        for (int x = 9; x <= 44; x++) {
            ItemStack s = AdorufuMod.minecraft.player.inventory.getStackInSlot(x);
            if (isHelmet(s.getItem())) helms.put(s, x); // put all helmets in the map for processing, with their respective slot id
        }
        AtomicReference<ItemStack> betterHelmet = new AtomicReference<>();
        AtomicReference<Integer> betterSlot = new AtomicReference<>();
        betterHelmet.set(null);
        helms.forEach((helm, slot)-> {
            if(isBetter(betterHelmet.get() == null ? currentHelmet : betterHelmet.get(), helm)) {
                betterHelmet.set(helm);
                betterSlot.set(slot);
            }
        });
        if (betterHelmet.get() == null) {
            return; // there aren't any helmets better than what the player currently has.
        }
        AdorufuMod.minecraft.playerController.windowClick(0, 5, 0, ClickType.PICKUP, AdorufuMod.minecraft.player);
        AdorufuMod.minecraft.playerController.windowClick(0, betterSlot.get(), 0, ClickType.PICKUP, AdorufuMod.minecraft.player);
        AdorufuMod.minecraft.playerController.windowClick(0, 5, 0, ClickType.PICKUP, AdorufuMod.minecraft.player);
    }

    public boolean isBetter(ItemStack old, ItemStack neew) {
        MineralMaterial oldMineral = null;
        MineralMaterial newMineral = null;
        if (old.getItem().getUnlocalizedName().toLowerCase().contains("leather")) oldMineral = MineralMaterial.LEATHER;
        if (old.getItem().getUnlocalizedName().toLowerCase().contains("chain")) oldMineral = MineralMaterial.CHAIN;
        if (old.getItem().getUnlocalizedName().toLowerCase().contains("iron")) oldMineral = MineralMaterial.IRON;
        if (old.getItem().getUnlocalizedName().toLowerCase().contains("gold")) oldMineral = MineralMaterial.GOLD;
        if (old.getItem().getUnlocalizedName().toLowerCase().contains("diamond")) oldMineral = MineralMaterial.DIAMOND;
        if (oldMineral == null) {
            return false; // invalid
        }
        if (neew.getItem().getUnlocalizedName().toLowerCase().contains("leather")) newMineral = MineralMaterial.LEATHER;
        if (neew.getItem().getUnlocalizedName().toLowerCase().contains("chain")) newMineral = MineralMaterial.CHAIN;
        if (neew.getItem().getUnlocalizedName().toLowerCase().contains("iron")) newMineral = MineralMaterial.IRON;
        if (neew.getItem().getUnlocalizedName().toLowerCase().contains("gold")) newMineral = MineralMaterial.GOLD;
        if (neew.getItem().getUnlocalizedName().toLowerCase().contains("diamond")) newMineral = MineralMaterial.DIAMOND;
        if (newMineral == null) {
            return false; //invalid
        }
        if (oldMineral == MineralMaterial.LEATHER) {
            if (newMineral != MineralMaterial.LEATHER) {
                return true; // yas
            }
            return neew.getItemDamage() > old.getItemDamage();
        }
        if (oldMineral == MineralMaterial.CHAIN) {
            if (newMineral == MineralMaterial.LEATHER) {
                return false;
            }
            if (newMineral != MineralMaterial.CHAIN) {
                return true; // yas
            }
            return neew.getItemDamage() > old.getItemDamage();
        }
        if (oldMineral == MineralMaterial.GOLD) {
            if (newMineral == MineralMaterial.LEATHER || newMineral == MineralMaterial.CHAIN) {
                return false;
            }
            if (newMineral != MineralMaterial.GOLD) {
                return true; // yas
            }
            return neew.getItemDamage() > old.getItemDamage();
        }
        if (oldMineral == MineralMaterial.IRON) {
            if (newMineral == MineralMaterial.LEATHER || newMineral == MineralMaterial.CHAIN
                    || newMineral == MineralMaterial.GOLD) {
                return false;
            }
            if (newMineral != MineralMaterial.IRON) {
                return true; // yas
            }
            return neew.getItemDamage() > old.getItemDamage();
        }
        if (newMineral == MineralMaterial.LEATHER || newMineral == MineralMaterial.CHAIN
                || newMineral == MineralMaterial.GOLD || newMineral == MineralMaterial.IRON) {
            return false;
        }
        if (old.isItemEnchanted()) {
            //todo
        }
        return neew.getItemDamage() > old.getItemDamage();
    }
}
enum MineralMaterial {
    LEATHER, CHAIN, IRON, GOLD, DIAMOND
}

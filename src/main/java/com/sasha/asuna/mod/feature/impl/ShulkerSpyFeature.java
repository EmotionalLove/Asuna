/*
 * Copyright (c) Sasha Stevens (2017 - 2018)
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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.tileentity.TileEntityShulkerBox;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@FeatureInfo(description = "View the contents of shulker boxes being held by other nearby players")
public class ShulkerSpyFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    public ShulkerSpyFeature() {
        super("ShulkerSpy", AsunaCategory.RENDER);
    }

    public static ConcurrentHashMap<String, TileEntityShulkerBox> shulkerMap = new ConcurrentHashMap<>();
    private static String newMsg = "You can now view {}'s most recently held shulker box's contents with \"-peek {}\"!";

    /**
     * todo made a gui overlay thingy that shows the items in the grid
     */
    public void onTick() {
        if (!this.isEnabled()) return;
        List<Entity> valids = AsunaMod.minecraft.world.getLoadedEntityList()
                .stream()
                .filter(en -> en instanceof EntityOtherPlayerMP)
                .filter(mp -> ((EntityOtherPlayerMP) mp).getHeldItemMainhand().getItem() instanceof ItemShulkerBox)
                .collect(Collectors.toList());
        for (Entity valid : valids) {
            EntityOtherPlayerMP mp = (EntityOtherPlayerMP) valid;
            TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
            ItemShulkerBox shulker = (ItemShulkerBox) mp.getHeldItemMainhand().getItem();
            entityBox.blockType = shulker.getBlock();
            entityBox.setWorld(AsunaMod.minecraft.world);
            ItemStackHelper.loadAllItems(mp.getHeldItemMainhand().getTagCompound().getCompoundTag("BlockEntityTag"), entityBox.items);
            entityBox.readFromNBT(mp.getHeldItemMainhand().getTagCompound().getCompoundTag("BlockEntityTag"));
            entityBox.setCustomName(mp.getHeldItemMainhand().hasDisplayName() ? mp.getHeldItemMainhand().getDisplayName() : mp.getName() + "'s current shulker box");
            if (!shulkerMap.containsKey(mp.getName().toLowerCase())) {
                AsunaMod.logMsg(false, format(mp.getName()));
            }
            shulkerMap.put(mp.getName().toLowerCase(), entityBox);
        }
    }

    private String format(String name) {
        return newMsg.replace("{}", name);
    }

}

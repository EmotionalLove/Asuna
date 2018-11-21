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

package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.stream.Collectors;

@FeatureInfo(description = "View the contents of shulker boxes being held by other nearby players")
public class ShulkerSpyFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    public ShulkerSpyFeature() {
        super("ShulkerSpy", AsunaCategory.RENDER);
    }

    /**
     * todo made a gui overlay thingy that shows the items in the grid
     */
    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        for (Entity entity : AsunaMod.minecraft.world.getLoadedEntityList()) {
            if (entity instanceof EntityOtherPlayerMP
                    && ((EntityOtherPlayerMP) entity).getHeldItemMainhand().getItem() instanceof ItemShulkerBox) {
                NBTTagCompound tag = ((EntityOtherPlayerMP) entity).getHeldItemMainhand().getTagCompound();
                if (tag != null && tag.hasKey("BlockEntityTag", 10)) {
                    NBTTagCompound realTag = tag.getCompoundTag("BlockEntityTag");
                    if (realTag.hasKey("Items", 9)) {
                        NonNullList<ItemStack> shulkerContentsList = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
                        ItemStackHelper.loadAllItems(realTag, shulkerContentsList);
                        List<ItemStack> stacks = shulkerContentsList.stream().filter(e -> e.getItem() != Items.AIR).collect(Collectors.toList());
                        this.drawShulkerBox(stacks, entity);
                    }
                }
            }
        }
    }

    private void drawShulkerBox(List<ItemStack> items, Entity entity) {
        if (!items.isEmpty()) {
            GlStateManager.pushAttrib();
            GlStateManager.translate(((items.size() - 1) / 2f) * .5f, .6, 0);
            items.forEach(itemStack -> {
                GlStateManager.pushAttrib();
                RenderHelper.enableStandardItemLighting();
                GlStateManager.scale(.5, .5, 0);
                GlStateManager.disableLighting();
                AsunaMod.minecraft.getRenderItem().zLevel = -5;
                AsunaMod.minecraft.getRenderItem().renderItem(itemStack, itemStack.getItem() == Items.SHIELD ? ItemCameraTransforms.TransformType.FIXED : ItemCameraTransforms.TransformType.NONE);
                AsunaMod.minecraft.getRenderItem().zLevel = 0;
                GlStateManager.scale(2, 2, 0);
                GlStateManager.popAttrib();
                GlStateManager.translate(-.5f, 0, 0);
            });
            GlStateManager.popMatrix();
        }
    }

}

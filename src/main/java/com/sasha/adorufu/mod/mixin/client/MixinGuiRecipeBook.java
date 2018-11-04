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

package com.sasha.adorufu.mod.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonToggle;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.recipebook.GuiButtonRecipeTab;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.RecipeBookPage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

/**
 * Created by Sasha at 7:40 PM on 9/24/2018
 */
@Mixin(value = GuiRecipeBook.class, priority = 999)
public abstract class MixinGuiRecipeBook extends MixinGui {

    @Shadow @Final protected static ResourceLocation RECIPE_BOOK;
    @Shadow private Minecraft mc;
    @Shadow private int xOffset;
    @Shadow private int height;
    @Shadow private int width;
    @Shadow private GuiTextField searchBar;
    @Shadow @Final private List<GuiButtonRecipeTab> recipeTabs;
    @Shadow private GuiButtonToggle toggleRecipesBtn;
    @Shadow @Final private RecipeBookPage recipeBookPage;

    @Shadow public abstract boolean isVisible();

    /**
     * @author Sasha Stevens
     * @reason so that it doesn't crash when queueskip.net is being dumb
     */
    @Overwrite
    public void render(int mouseX, int mouseY, float partialTicks) {
        try {
            if (this.isVisible()) {
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.disableLighting();
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0F, 0.0F, 100.0F);
                this.mc.getTextureManager().bindTexture(RECIPE_BOOK);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                int i = (this.width - 147) / 2 - this.xOffset;
                int j = (this.height - 166) / 2;
                this.drawTexturedModalRect(i, j, 1, 1, 147, 166);
                this.searchBar.drawTextBox();
                RenderHelper.disableStandardItemLighting();

                for (GuiButtonRecipeTab guibuttonrecipetab : this.recipeTabs) {
                    guibuttonrecipetab.drawButton(this.mc, mouseX, mouseY, partialTicks);
                }

                this.toggleRecipesBtn.drawButton(this.mc, mouseX, mouseY, partialTicks);
                this.recipeBookPage.render(i, j, mouseX, mouseY, partialTicks);
                GlStateManager.popMatrix();
            }
        } catch (Exception e) {
        }
    }

}

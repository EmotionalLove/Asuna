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

package com.sasha.adorufu.mod.mixins;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Created by Sasha on 06/08/2018 at 1:34 PM
 **/
@IFMLLoadingPlugin.Name(value = "MixinLoaderForge")
public class MixinLoaderForge implements IFMLLoadingPlugin {
    protected static boolean isObfuscatedEnvironment;

    /**
     * I found out the hard way that you cannot make calls to AdorufuMod in this constructor... Don't do it.
     */
    public MixinLoaderForge() {
        String mixinConfig = "mixins.adorufu.json";
        String baritoneConfig = "mixins.baritone.json";
        String envObf = "searge";
        Mixins.addConfigurations(baritoneConfig, mixinConfig); //plz work
        MixinBootstrap.init();
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext(envObf);
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        isObfuscatedEnvironment = (boolean) (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}

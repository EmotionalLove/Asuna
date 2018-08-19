package com.sasha.adorufu.mixins;

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
    public MixinLoaderForge(){
        String mixinConfig = "mixins.adorufu.json";
        String envObf = "searge";
        MixinBootstrap.init();
        Mixins.addConfiguration(mixinConfig);
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

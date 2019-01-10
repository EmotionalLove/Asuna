package com.sasha.asuna.mod.feature.impl;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.IAsunaTickableFeature;

public class QuickUseFeature extends AbstractAsunaTogglableFeature implements IAsunaTickableFeature {

    public QuickUseFeature() {
        super("QuickUse", AsunaCategory.COMBAT);
    }

    @Override
    public void onTick() {
        AsunaMod.minecraft.rightClickDelayTimer = 0;
    }
}

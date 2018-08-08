package com.sasha.xdolf.misc;

import com.sasha.xdolf.XdolfMath;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

/**
 * Created by Sasha on 08/08/2018 at 2:38 PM
 **/
public class TPS {

    private static final float[] tickRates = new float[3];
    private static int nextIndex = 0;
    private static long timeLastTimeUpdate;

    public static float getTickRate()
    {
        float numTicks = 0.0F;
        float sumTickRates = 0.0F;
        for (float tickRate : tickRates) {
            if (tickRate > 0.0F) {
                sumTickRates += tickRate;
                numTicks += 1.0F;
            }
        }
        try {
            return XdolfMath.fround(sumTickRates / numTicks, 2);
        }catch (NumberFormatException e) {
            return 20f;
        }
    }

    public static void onTimeUpdate()
    {
        if (timeLastTimeUpdate != -1L)
        {
            float timeElapsed = (float)(System.currentTimeMillis() - timeLastTimeUpdate) / 1000.0F;
            tickRates[(nextIndex % tickRates.length)] = MathHelper.clamp(20.0F / timeElapsed, 0.0F, 20.0F);
            nextIndex += 1;
        }
        timeLastTimeUpdate = System.currentTimeMillis();
    }
    public static void reset()
    {
        nextIndex = 0;
        timeLastTimeUpdate = -1L;
        Arrays.fill(tickRates, 0.0F);
    }
}

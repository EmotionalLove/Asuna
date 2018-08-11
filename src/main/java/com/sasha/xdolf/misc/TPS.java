package com.sasha.xdolf.misc;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.ClientPacketRecieveEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

/**
 * Created by Sasha on 08/08/2018 at 2:38 PM
 **/
public class TPS implements SimpleListener {

    private static final float[] tickRates = new float[3];
    private static int nextIndex = 0;
    private static long timeLastTimeUpdate;

    public static TPS INSTANCE;

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

    @SimpleEventHandler
    public void onTimeUpdate(ClientPacketRecieveEvent e) {
        if (!(e.getRecievedPacket() instanceof SPacketTimeUpdate)){
            return;
        }
        if (timeLastTimeUpdate != -1L) {
            float timeElapsed = (float)(System.currentTimeMillis() - timeLastTimeUpdate) / 1000.0F;
            tickRates[(nextIndex % tickRates.length)] = MathHelper.clamp(20.0F / timeElapsed, 0.0F, 20.0F);
            nextIndex += 1;
        }
        timeLastTimeUpdate = System.currentTimeMillis();
    }

    public void reset() {
        nextIndex = 0;
        timeLastTimeUpdate = -1L;
        Arrays.fill(tickRates, 0.0F);
    }
}

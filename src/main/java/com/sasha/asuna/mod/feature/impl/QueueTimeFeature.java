/*
 * Copyright (c) Sasha Stevens (2016 - 2018)
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
import com.sasha.asuna.mod.events.client.ClientPacketRecieveEvent;
import com.sasha.asuna.mod.feature.AbstractAsunaTogglableFeature;
import com.sasha.asuna.mod.feature.AsunaCategory;
import com.sasha.asuna.mod.feature.annotation.FeatureInfo;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import net.minecraft.network.play.server.SPacketChat;

import java.util.concurrent.TimeUnit;

import static com.sasha.asuna.mod.feature.impl.AutoIgnoreFeature.stripColours;

@FeatureInfo(description = "Show the estimated time left in queue in chat")
public class QueueTimeFeature extends AbstractAsunaTogglableFeature implements SimpleListener {
    private static int lastQueuePos = -1;
    private static int queueMeasurementMilestone = 0;
    private static long preMeasurementMilestoneTime = 0;
    private String tu = "Calculating...";

    public QueueTimeFeature() {
        super("QueueTime", AsunaCategory.CHAT);
    }

    private static String convert(long miliSeconds) {
        int hrs = (int) TimeUnit.MILLISECONDS.toHours(miliSeconds) % 24;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(miliSeconds) % 60;
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(miliSeconds) % 60;
        return String.format("%02dh %02dm %02ds", hrs, min, sec);
    }

    @SimpleEventHandler
    public void onChatRecieved(ClientPacketRecieveEvent ev) {
        if (!this.isEnabled()) return;
        if (ev.getRecievedPacket() instanceof SPacketChat) {
            SPacketChat e = ev.getRecievedPacket();
            if (stripColours(e.getChatComponent().getUnformattedText()).startsWith("Position in queue: ")) {
                int queuepos = Integer.parseInt(stripColours(e.chatComponent.getUnformattedText()).replace("Position in queue: ", ""));
                //This runs when the module is initiated
                int milestone = 5;
                if (lastQueuePos == -1) {
                    lastQueuePos = queuepos;
                    AsunaMod.logMsg("Wait patiently for " + milestone + " spots in the queue to pass to ensure (semi)accurate measurement.");
                    queueMeasurementMilestone = queuepos - milestone;
                    preMeasurementMilestoneTime = System.nanoTime();
                    return;
                }
                //this should run recursively
                if (queueMeasurementMilestone >= queuepos) {
                    AsunaMod.logMsg("Queue measurement milestone reached. Re-Calculating...");
                    long estTimePerSpot = (System.nanoTime() - preMeasurementMilestoneTime) / milestone;
                    //Resetting...
                    preMeasurementMilestoneTime = System.nanoTime();
                    queueMeasurementMilestone = queuepos - milestone;

                    long estTimeWhole = estTimePerSpot * queuepos;
                    tu = convert(estTimeWhole);
                    return;

                }
                AsunaMod.logMsg("\247" + "6Estimated Time: " + "\247" + "r" + "\247" + "6" + "\247" + "l" + tu);

            }
        }
    }

}
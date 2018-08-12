package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.PlayerBlockBreakEvent;
import com.sasha.xdolf.events.PlayerBlockPlaceEvent;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import java.util.*;
import static com.sasha.xdolf.XdolfMod.logMsg;


/**
 * Created by Sasha on 11/08/2018 at 4:31 PM
 * WHY TF DOESNT THIS WORK OMG ;-; i SWEAr
 **/
@ModuleInfo(description = "Sends a message in chat every 30 seconds about what you're doing in the world.")
public class ModuleAnnouncer extends XdolfModule implements SimpleListener {

    static boolean swap = false;
    public static int counter = 0;

    private ArrayList<String> blocksPlacedStr = new ArrayList<>();
    private ArrayList<Integer> blocksPlacedInt = new ArrayList<>();
    private ArrayList<String> blocksBrokenStr = new ArrayList<>();
    private ArrayList<Integer> blocksBrokenInt = new ArrayList<>();



    public ModuleAnnouncer() {
        super("Announcer", XdolfCategory.CHAT, false);
    }

    @Override
    public void onEnable(){

    }

    @Override
    public void onDisable() {
       // AnnouncerTask.theThing.cancel(true);
    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        counter++;
        if (counter > 20*30) {
            logMsg(false, "Refreshing announcer");
            Random rand = new Random();
            int swap = rand.nextInt(10);
            if (swap > 5){
                if (blocksBrokenStr.isEmpty()) {
                    counter = 0;
                    return;
                }
                XdolfMod.minecraft.player.sendChatMessage("> I just mined " + blocksBrokenInt.get(0) + " " + blocksBrokenStr.get(0));
                blocksBrokenStr.remove(0);
                blocksBrokenInt.remove(0);
            }
            else {
                if (blocksPlacedStr.isEmpty()) {
                    counter = 0;
                    return;
                }
                XdolfMod.minecraft.player.sendChatMessage("> I just placed " + blocksPlacedInt.get(0) + " " + blocksPlacedStr.get(0));
                blocksPlacedInt.remove(0);
                blocksPlacedStr.remove(0);
            }
            counter = 0;
        }

    }
    @SimpleEventHandler
    public void onBlockBreak(PlayerBlockBreakEvent e){
        if (this.isEnabled()){
            logMsg("ok");
            if (blocksBrokenStr.contains(e.getBlock().getLocalizedName())){
                logMsg("ok 1");
                blocksBrokenInt.set(blocksBrokenStr.indexOf(e.getBlock().getLocalizedName()),
                        blocksPlacedInt.get(blocksBrokenStr.indexOf(e.getBlock().getLocalizedName()))+1);
                logMsg(blocksBrokenStr.get(0));
                return;
            }
            logMsg("oh ok");
            blocksBrokenStr.add(e.getBlock().getLocalizedName());
            blocksBrokenInt.add(1);
            logMsg(blocksBrokenStr.get(0));
        }
    }
    @SimpleEventHandler
    public void onBlockBreak(PlayerBlockPlaceEvent e){
        if (this.isEnabled()){
            logMsg("ok");
            if (blocksPlacedStr.contains(e.getBlock().getLocalizedName())){
                logMsg("ok 1");
                blocksPlacedInt.set(blocksPlacedStr.indexOf(e.getBlock().getLocalizedName()),
                        blocksPlacedInt.get(blocksPlacedStr.indexOf(e.getBlock().getLocalizedName()))+1);
                logMsg(blocksPlacedStr.get(0));
                return;
            }
            logMsg("oh ok");
            blocksPlacedStr.add(e.getBlock().getLocalizedName());
            blocksPlacedInt.add(1);
            logMsg(blocksPlacedStr.get(0));
        }
    }

}

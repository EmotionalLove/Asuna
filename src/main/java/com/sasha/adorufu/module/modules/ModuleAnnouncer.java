package com.sasha.adorufu.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.events.PlayerBlockBreakEvent;
import com.sasha.adorufu.events.PlayerBlockPlaceEvent;
import com.sasha.adorufu.module.ModuleInfo;
import com.sasha.adorufu.module.PostToggleExec;
import com.sasha.adorufu.module.AdorufuCategory;
import com.sasha.adorufu.module.AdorufuModule;
import java.util.*;
import static com.sasha.adorufu.AdorufuMod.logMsg;


/**
 * Created by Sasha on 11/08/2018 at 4:31 PM
 * WHY TF DOESNT THIS WORK OMG ;-; i SWEAr
 **/
@PostToggleExec
@ModuleInfo(description = "Sends a message in chat every 15 seconds about what you're doing in the world.")
public class ModuleAnnouncer extends AdorufuModule implements SimpleListener {

    static boolean swap = false;
    public static int counter = 0;

    private LinkedHashMap<String, Integer> blocksBrokenMap = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> blocksPlacedMap = new LinkedHashMap<>();



    public ModuleAnnouncer() {
        super("Announcer", AdorufuCategory.CHAT, false);
    }

    @Override
    public void onEnable(){

    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onTick() {
        if (!this.isEnabled()) return;
        counter++;
        if (counter > 20*15) {
            counter = 0;
            Random rand = new Random();
            boolean randBool = rand.nextBoolean();
            if (randBool) {
                if (blocksBrokenMap.isEmpty()) {
                    return;
                }
                String key = "";
                for (Map.Entry<String, Integer> stringIntegerEntry : blocksBrokenMap.entrySet()) {
                    key = stringIntegerEntry.getKey();
                    AdorufuMod.minecraft.player.sendChatMessage("> I just mined " + stringIntegerEntry.getValue() + " " +
                            stringIntegerEntry.getKey());
                    break;
                }
                blocksBrokenMap.remove(key);
            }
            else {
                if (blocksPlacedMap.isEmpty()) {
                    return;
                }
                String key = "";
                for (Map.Entry<String, Integer> stringIntegerEntry : blocksPlacedMap.entrySet()) {
                    key = stringIntegerEntry.getKey();
                    AdorufuMod.minecraft.player.sendChatMessage("> I just placed " + stringIntegerEntry.getValue() + " " +
                            stringIntegerEntry.getKey());
                    break;
                }
                blocksPlacedMap.remove(key);
            }
        }
    }
    @SimpleEventHandler
    public void onBlockBreak(PlayerBlockBreakEvent e){
        if (!this.isEnabled()) return;
        //logMsg("ok");
        if (blocksBrokenMap.containsKey(e.getBlock().getLocalizedName())){
            //logMsg("ok 1");
            blocksBrokenMap.put(e.getBlock().getLocalizedName(), blocksBrokenMap.get(e.getBlock().getLocalizedName())+1);
            return;
        }
        //logMsg("oh ok");
        blocksBrokenMap.put(e.getBlock().getLocalizedName(), 1);
    }
    @SimpleEventHandler
    public void onBlockBreak(PlayerBlockPlaceEvent e){
        if (!this.isEnabled()) return;
        //logMsg("ok");
        if (blocksPlacedMap.containsKey(e.getBlock().getLocalizedName())){
            //logMsg("ok 1");
            blocksPlacedMap.put(e.getBlock().getLocalizedName(), blocksPlacedMap.get(e.getBlock().getLocalizedName())+1);
            return;
        }
        //logMsg("oh ok");
        blocksPlacedMap.put(e.getBlock().getLocalizedName(), 1);
    }

}

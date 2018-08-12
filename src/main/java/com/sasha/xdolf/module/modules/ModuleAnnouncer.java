package com.sasha.xdolf.module.modules;

import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.events.PlayerBlockBreakEvent;
import com.sasha.xdolf.events.PlayerBlockPlaceEvent;
import com.sasha.xdolf.module.ModuleInfo;
import com.sasha.xdolf.module.XdolfCategory;
import com.sasha.xdolf.module.XdolfModule;
import net.minecraft.util.text.TextComponentString;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.sasha.xdolf.XdolfMod.logMsg;
import static com.sasha.xdolf.module.modules.AnnouncerTask.blocksBrokenMap;
import static com.sasha.xdolf.module.modules.AnnouncerTask.blocksPlacedMap;
import static com.sasha.xdolf.module.modules.ModuleAnnouncer.swap;

/**
 * Created by Sasha on 11/08/2018 at 4:31 PM
 **/
@ModuleInfo(description = "Sends a message in chat every 30 seconds about what you're doing in the world.")
public class ModuleAnnouncer extends XdolfModule implements SimpleListener {

    static boolean swap = false;
    public static int counter = 0;




    public ModuleAnnouncer() {
        super("Announcer", XdolfCategory.CHAT, false);
    }

    @Override
    public void onEnable(){
        blocksBrokenMap.clear();
        blocksPlacedMap.clear();
        //AnnouncerTask.theThing = XdolfMod.scheduler.scheduleAtFixedRate(new AnnouncerTask(), 0, 30, TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
       // AnnouncerTask.theThing.cancel(true);
    }

    @Override
    public void onTick() {
        counter++;
        if (counter > 20*30) {
            XdolfMod.scheduler.submit(new AnnouncerTask());
            counter = 0;
        }

    }
    @SimpleEventHandler
    public void onBlockBreak(PlayerBlockBreakEvent e){
        if (this.isEnabled()){
            if (blocksBrokenMap.containsKey(e.getBlock().getLocalizedName())){
                blocksBrokenMap.put(e.getBlock().getLocalizedName(), (blocksBrokenMap.get(e.getBlock().getLocalizedName()))+1);
                return;
            }
            blocksBrokenMap.put(e.getBlock().getLocalizedName(), 1);
        }
    }
    @SimpleEventHandler
    public void onBlockBreak(PlayerBlockPlaceEvent e){
        logMsg("brok");
        if (this.isEnabled()){
            if (blocksPlacedMap.containsKey(e.getBlock().getLocalizedName())){
                blocksPlacedMap.put(e.getBlock().getLocalizedName(), (blocksPlacedMap.get(e.getBlock().getLocalizedName()))+1);
                return;
            }
            blocksPlacedMap.put(e.getBlock().getLocalizedName(), 1);
        }
    }
}
class AnnouncerTask implements Runnable{
    //public static ScheduledFuture<?> theThing;
    public static LinkedHashMap<String, Integer> blocksBrokenMap = new LinkedHashMap<>();
    public static LinkedHashMap<String, Integer> blocksPlacedMap = new LinkedHashMap<>();
    @Override
    public void run() {
        //logMsg(false, "Refreshing announcer");
        Random rand = new Random();
        if (ModuleAnnouncer.swap && blocksBrokenMap.size() != 0){
            ArrayList<String> blockNames= new ArrayList<>(blocksBrokenMap.keySet());
            String blockname= (blockNames.get(rand.nextInt(blockNames.size())));
            int amt = blocksBrokenMap.get(blockname);
            XdolfMod.minecraft.player.sendChatMessage("> I just mined " + amt + " " + blockname);
            blocksBrokenMap.remove(blockname);
            ModuleAnnouncer.swap=!swap;
            return;
        }
        if (!ModuleAnnouncer.swap &&  blocksPlacedMap.size() != 0){
            ArrayList<String> blockNames= new ArrayList<>(blocksPlacedMap.keySet());
            String blockname= (blockNames.get(rand.nextInt(blockNames.size())));
            int amt = blocksPlacedMap.get(blockname);
            XdolfMod.minecraft.player.sendChatMessage("> I just placed " + amt + " " + blockname);
            blocksPlacedMap.remove(blockname);
            ModuleAnnouncer.swap=!swap;
            return;
        }
    }
}

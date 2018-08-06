package com.sasha.xdolf;

import com.sasha.eventsys.SimpleEventManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = XdolfMod.MODID, name = XdolfMod.NAME, version = XdolfMod.VERSION)
public class XdolfMod {
    public static final String MODID = "xdolfforge";
    public static final String NAME = "Xdolf";
    public static final String VERSION = "4.0";

    public static XdolfMod INSTANCE;

    public static Logger logger = LogManager.getLogger("Xdolf " + VERSION);
    public static SimpleEventManager EVENT_MANAGER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Xdolf is initialising...");
        INSTANCE = this;
        EVENT_MANAGER = new SimpleEventManager();
    }
}

package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.AdorufuMod;
import com.sasha.adorufu.module.AdorufuModule;
import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.simplecmdsys.SimpleCommandInfo;

import java.util.HashMap;

import static com.sasha.adorufu.AdorufuMod.*;
import static com.sasha.adorufu.module.ModuleManager.moduleRegistry;
/**
 * Created by Sasha on 10/08/2018 at 9:28 AM
 **/
@SimpleCommandInfo(description = "Provide info about the other commands.", syntax = {"['command'/'module'] [module name]"})
public class HelpCommand extends SimpleCommand {
    public HelpCommand() {
        super("help");
    }
    @Override
    public void onCommand(){
        if (this.getArguments() == null){
            logMsg(false, "Listing commands...");
            AdorufuMod.COMMAND_PROCESSOR.getCommandRegistry().forEach((clazz, ins)-> {
                String b = COMMAND_PROCESSOR.getCommandPrefix() + ((SimpleCommand)ins).getCommandName() + " | " + COMMAND_PROCESSOR.getDescription(((SimpleCommand)ins).getClass());
                logMsg(b);
            });
            return;
        }
        if (this.getArguments().length != 2 || !this.getArguments()[0].toLowerCase().matches("command|module")){
            logErr(false, "Incorrect syntax! Listing valid args:");
            for (String s : COMMAND_PROCESSOR.getSyntax(this.getClass())){
                logMsg(COMMAND_PROCESSOR.getCommandPrefix() + this.getCommandName() +" "+s);
            }
            return;
        }
        if (this.getArguments()[0].toLowerCase().equals("command")){
            for (HashMap.Entry<Class<? extends SimpleCommand>, Object> simpleEntry : COMMAND_PROCESSOR.getCommandRegistry().entrySet()) {
                SimpleCommand simpleCommand = (SimpleCommand) simpleEntry.getValue();
                if (simpleCommand.getCommandName().equalsIgnoreCase(this.getArguments()[1])){
                    logMsg(false, "\247c" + COMMAND_PROCESSOR.getDescription(simpleCommand.getClass()));
                    for (String sy : COMMAND_PROCESSOR.getSyntax(simpleCommand.getClass())) logMsg(COMMAND_PROCESSOR.getCommandPrefix() + simpleCommand.getCommandName()+" "+sy);
                }
            }
            return;
        }
        if (this.getArguments()[0].toLowerCase().equalsIgnoreCase("module")){
            for (AdorufuModule module : moduleRegistry) {
                if (module.getModuleName().toLowerCase().equalsIgnoreCase(this.getArguments()[1])){
                    logMsg(module.getDescription(module.getClass()));
                }
            }
        }
    }
}

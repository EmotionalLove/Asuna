package com.sasha.adorufu.command.commands;

import com.sasha.adorufu.command.CommandInfo;
import com.sasha.adorufu.command.AdorufuCommand;
import com.sasha.adorufu.exception.AdorufuCommandAnnotationException;
import com.sasha.adorufu.module.AdorufuModule;

import static com.sasha.adorufu.AdorufuMod.logErr;
import static com.sasha.adorufu.AdorufuMod.logMsg;
import static com.sasha.adorufu.command.CommandProcessor.commandRegistry;
import static com.sasha.adorufu.module.ModuleManager.moduleRegistry;

/**
 * Created by Sasha on 10/08/2018 at 9:28 AM
 **/
@CommandInfo(description = "Provide info about the other commands.", syntax = {"['command'/'module'] [module name]"})
public class HelpCommand extends AdorufuCommand {
    public HelpCommand() {
        super("help");
    }
    @Override
    public void onCommand(){
        if (this.getArguments() == null){
            logMsg(false, "Listing commands...");
            for (AdorufuCommand AdorufuCommand : commandRegistry) {
                CommandInfo d = AdorufuCommand.getClass().getAnnotation(CommandInfo.class);
                if (d == null){
                    throw new AdorufuCommandAnnotationException("The developer didn't decorate " + AdorufuCommand.getClass().getName() + " with a CommandInfo annotation!");
                }
                String b = AdorufuCommand.getCommandName(true) + " | " + d.description();
                logMsg(b);
            }
            return;
        }
        if (this.getArguments().length != 2 || !this.getArguments()[0].toLowerCase().matches("command|module")){
            logErr(false, "Incorrect syntax! Listing valid args:");
            for (String s : this.getSyntax(this.getClass())){
                logMsg(this.getCommandName(true) +" "+s);
            }
            return;
        }
        if (this.getArguments()[0].toLowerCase().equals("command")){
            for (AdorufuCommand AdorufuCommand : commandRegistry) {
                if (AdorufuCommand.getCommandName(false).equalsIgnoreCase(this.getArguments()[1])){
                    for (String sy : AdorufuCommand.getSyntax(AdorufuCommand.getClass())) logMsg(AdorufuCommand.getCommandName(true)+" "+sy);
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

package com.sasha.xdolf.command.commands;

import com.sasha.xdolf.command.CommandInfo;
import com.sasha.xdolf.command.XdolfCommand;
import com.sasha.xdolf.exception.XdolfCommandAnnotationException;
import com.sasha.xdolf.module.XdolfModule;

import static com.sasha.xdolf.XdolfMod.logErr;
import static com.sasha.xdolf.XdolfMod.logMsg;
import static com.sasha.xdolf.command.CommandProcessor.commandRegistry;
import static com.sasha.xdolf.module.ModuleManager.moduleRegistry;

/**
 * Created by Sasha on 10/08/2018 at 9:28 AM
 **/
@CommandInfo(description = "Provide info about the other commands.", syntax = {"['command'/'module'] [module name]"})
public class HelpCommand extends XdolfCommand {
    public HelpCommand() {
        super("help");
    }
    @Override
    public void onCommand(){
        if (this.getArguments() == null){
            logMsg(false, "Listing commands...");
            for (XdolfCommand xdolfCommand : commandRegistry) {
                CommandInfo d = xdolfCommand.getClass().getAnnotation(CommandInfo.class);
                if (d == null){
                    throw new XdolfCommandAnnotationException("The developer didn't decorate " + xdolfCommand.getClass().getName() + " with a CommandInfo annotation!");
                }
                String b = xdolfCommand.getCommandName(true) + " | " + d.description();
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
            for (XdolfCommand xdolfCommand : commandRegistry) {
                if (xdolfCommand.getCommandName(false).equalsIgnoreCase(this.getArguments()[1])){
                    for (String sy : xdolfCommand.getSyntax(xdolfCommand.getClass())) logMsg(xdolfCommand.getCommandName(true)+" "+sy);
                }
            }
            return;
        }
        if (this.getArguments()[0].toLowerCase().equalsIgnoreCase("module")){
            for (XdolfModule module : moduleRegistry) {
                if (module.getModuleName().toLowerCase().equalsIgnoreCase(this.getArguments()[1])){
                    logMsg(module.getDescription(module.getClass()));
                }
            }
        }
    }
}

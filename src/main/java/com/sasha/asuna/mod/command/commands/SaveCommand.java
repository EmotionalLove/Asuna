package com.sasha.asuna.mod.command.commands;

import com.sasha.asuna.mod.AsunaMod;
import com.sasha.asuna.mod.misc.Manager;
import com.sasha.simplecmdsys.SimpleCommand;

public class SaveCommand extends SimpleCommand {

    public SaveCommand() {
        super("save");
    }

    @Override
    public void onCommand() {
        Manager.Data.saveCurrentSettings();
        AsunaMod.logMsg("Saved!");
    }
}

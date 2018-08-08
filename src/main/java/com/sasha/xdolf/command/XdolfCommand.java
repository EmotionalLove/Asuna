package com.sasha.xdolf.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sasha on 08/08/2018 at 7:08 AM
 **/
public class XdolfCommand {
//todo
    private String commandName;
    private String[] commandArgs;

    public final static String commandDelimetre = "-";

    public XdolfCommand(String commandName){
        this.commandName = commandName;
    }

    /**
     * Will treat text surrounded by quotes as one argument.
     */
    public void setArguments(String theMessage){
        if (!theMessage.contains(" ")){
            this.commandArgs = null;
        }
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(theMessage);
        while (m.find()) {
            list.add(m.group(1).replace("\"", ""));
        }
        this.commandArgs = list.toArray(this.commandArgs);
    }
}

package com.sasha.xdolf.module;

import com.sasha.xdolf.XdolfMod;
import com.sasha.xdolf.misc.YMLParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sasha on 08/08/2018 at 9:17 AM
 **/
public abstract class ModuleUtils{

    private static Lock threadLock = new ReentrantLock();

    public static ArrayList<XdolfModule> moduleRegistry = new ArrayList<>();




}

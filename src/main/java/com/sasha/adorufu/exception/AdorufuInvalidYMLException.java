package com.sasha.adorufu.exception;

import com.sasha.adorufu.AdorufuMod;
import net.minecraft.crash.CrashReport;

/**
 * Created by Sasha at 11:06 AM on 9/2/2018
 */
public class AdorufuInvalidYMLException extends AdorufuException {
    public AdorufuInvalidYMLException(String error) {
        super(error);
        AdorufuMod.minecraft.displayCrashReport(new CrashReport(error, this));
    }
}

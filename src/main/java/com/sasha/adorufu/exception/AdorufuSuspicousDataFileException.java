package com.sasha.adorufu.exception;

/**
 * Created by Sasha at 3:49 PM on 8/25/2018
 */
public class AdorufuSuspicousDataFileException extends AdorufuException {
    /**
     * Used if the data file is awkwardly big.
     * @param error
     */
    public AdorufuSuspicousDataFileException(String error) {
        super(error);
    }
}

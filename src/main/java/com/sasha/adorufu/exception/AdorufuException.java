package com.sasha.adorufu.exception;

/**
 * Created by Sasha on 08/08/2018 at 7:12 AM
 **/


/**
 * Generic Adorufu exception (in case pepper does something dumb)
 */
public class AdorufuException extends Error {
    public AdorufuException(String error){
        super(error);
    }
}

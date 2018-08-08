package com.sasha.xdolf.exception;

/**
 * Created by Sasha on 08/08/2018 at 7:12 AM
 **/


/**
 * Generic Xdolf exception (in case pepper does something dumb)
 */
public class XdolfException extends Error {
    public XdolfException(String error){
        super(error);
    }
}

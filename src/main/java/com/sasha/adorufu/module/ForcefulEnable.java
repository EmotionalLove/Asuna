package com.sasha.adorufu.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sasha on 09/08/2018 at 8:37 PM
 * Decorate an AdorufuModule with this to forcefully keep it enabled.
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForcefulEnable {

}

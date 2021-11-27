package com.ludmila.homework19.application.config;

import org.reflections.Reflections;
/**
 *  27.11.2021
 *  20. Java IO
 * @author Ludmila Kondratyeva (National Aeronautics and Space Administration)
 */
public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> ifc);

    Reflections getScanner();
}
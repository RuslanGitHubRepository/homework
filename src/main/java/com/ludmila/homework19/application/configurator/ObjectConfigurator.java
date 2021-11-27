package com.ludmila.homework19.application.configurator;

import com.ludmila.homework19.application.ApplicationContext;

import java.net.URISyntaxException;

/**
 * 27.11.2021
 * 20. Java IO
 *
 * @author Ludmila Kondratyeva (National Aeronautics and Space Administration)
 * @version v1.0
 */
public interface ObjectConfigurator {
    void configure(Object t, ApplicationContext context);
}
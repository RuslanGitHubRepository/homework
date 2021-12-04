package com.ludmila.homework22.application.configurator;

import com.ludmila.homework22.application.ApplicationContext;

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
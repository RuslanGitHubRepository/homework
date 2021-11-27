package com.ludmila.homework19.application.configurator;

import com.ludmila.homework19.application.ApplicationContext;
import com.ludmila.homework19.application.injects.InjectByType;
import java.lang.reflect.Field;

/**
 * 27.11.2021
 * 20. Java IO
 *
 * @author Ludmila Kondratyeva (National Aeronautics and Space Administration)
 * @version v1.0
 */

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                try {
                    Object object = context.getObject(field.getType());
                    field.set(t, object);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }

            }
        }
    }
}
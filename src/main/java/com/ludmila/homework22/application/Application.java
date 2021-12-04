package com.ludmila.homework22.application;

import com.ludmila.homework22.application.config.Config;
import com.ludmila.homework22.application.config.JavaConfig;
import com.ludmila.homework22.application.factory.ObjectFactory;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass) throws Exception {
        Config config = new JavaConfig(packageToScan, ifc2ImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        return context;
    }
}
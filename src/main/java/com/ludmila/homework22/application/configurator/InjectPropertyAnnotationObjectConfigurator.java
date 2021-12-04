package com.ludmila.homework22.application.configurator;

import com.ludmila.homework22.application.ApplicationContext;
import com.ludmila.homework22.application.injects.InjectProperty;
import com.ludmila.homework22.repository.FileRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 27.11.2021
 * 20. Java IO
 *
 * @author Ludmila Kondratyeva (National Aeronautics and Space Administration)
 * @version v1.0
 */

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private Map<String, String> propertiesMap;

    public InjectPropertyAnnotationObjectConfigurator() throws FileNotFoundException {
        File file = getResources("application.properties");
        Stream<String> lines = new BufferedReader(new FileReader(file)).lines();
        propertiesMap = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));

    }

    @Override
    public void configure(Object t, ApplicationContext context) {
        Class<?> implClass = t.getClass();
        FileRepository fileRepository = implClass.getAnnotation(FileRepository.class);
        String fieldValue = "";
        if(Objects.nonNull(fileRepository)) {
            fieldValue = fileRepository.filePath();
        }
        for (Field field : implClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (Objects.nonNull(annotation)) {
                File resources;
                if(!fieldValue.isEmpty()) {
                    resources = fileRepository.isResourcesFile() ? getResources(fieldValue) : new File(fieldValue);
                } else {
                    resources = fileRepository.isResourcesFile() ? getResources(Optional.ofNullable(propertiesMap.get(field.getName())).orElse("")) : null;
                }
                field.setAccessible(true);
                try {
                    field.set(t, resources);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }

    private File getResources(String resource) {
        try {
            return Paths.get(ClassLoader.getSystemClassLoader().getResource(resource).toURI()).toFile();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
package com.ludmila.homework22.application.injects;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface InjectProperty {
    String value() default "";
}
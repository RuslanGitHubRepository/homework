package models;

import java.util.Arrays;

public enum ModelType {
    CIRCLE("circle"),
    ELLLIPSE("ellipse"),
    RECTANGLE("rectangle"),
    SQUARE("square");
    private final String value;

    ModelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public ModelType getType(String value) {
       return Arrays.stream(ModelType.values())
                .filter(type -> type.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}

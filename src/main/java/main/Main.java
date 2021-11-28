package main;


import model.Car;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    private enum Field {
        NUMBER(1),
        MODEL(2),
        COLOR(3),
        MILEAGE(4),
        COST(5);
        private Integer value;

        Field(Integer value) {
            this.value = value;
        }
    }
    public static void main(String[] args) {
        File carsFile = getResources("cars.txt");
        List<String> records = new ArrayList<>();
        try(InputStream fileInputStream = new FileInputStream(carsFile)) {
            Scanner scanner = new Scanner(fileInputStream);
            scanner = scanner.useDelimiter(Pattern.compile("\n"));
            while (scanner.hasNext()) {
                records.add(scanner.next());
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }

        System.out.println("Получим номера всех автомобилей, имеющих черный цвет или нулевой пробег:");
        Predicate<String> predicate = str -> getParameter(str, Field.COLOR).equals("Black");
        predicate = predicate.or(str -> getParameter(str, Field.MILEAGE).equals("0"));
        List<String> numbers = records.stream()
                .filter(predicate)
                .map(str -> getParameter(str, Field.NUMBER))
                .collect(Collectors.mapping(String::valueOf, Collectors.toList()));
        System.out.println(numbers);

        System.out.println("Получим количество 'уникальных' автомобилей в ценовом диапазоне от 700 до 800 тыс:");
        predicate = str -> Integer.parseInt(getParameter(str, Field.COST)) >= 700000;
        predicate = predicate.and(str -> Integer.parseInt(getParameter(str, Field.COST)) <= 800000);
        List<Car> cars = records.stream()
                .filter(predicate)
                .map(str -> Car.newBuilder()
                        .setColor(getParameter(str, Field.COLOR))
                        .setMileage(Integer.parseInt(getParameter(str, Field.MILEAGE)))
                        .setCost(Integer.parseInt(getParameter(str, Field.COST)))
                        .setModel(getParameter(str, Field.MODEL))
                        .setNumber(getParameter(str, Field.NUMBER))
                        .build())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(cars);
    }

    private static File getResources(String resource) {
        try {
            return Paths.get(ClassLoader.getSystemClassLoader().getResource(resource).toURI()).toFile();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private static <T extends java.io.Serializable & Comparable<T>> T getParameter(String record, Field field) {
        Pattern compile = Pattern.compile("(\\w+)\\|(\\w+)\\|(\\D+)\\|(\\d+)\\|(\\d+)", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = compile.matcher(record);
        if(!matcher.matches()) {
            return null;
        }
        return (T)matcher.group(field.value);
    }
}

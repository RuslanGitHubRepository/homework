package main;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
       final String INPUT_STRING = "За свою карьеру я пропустил более 9000 бросков, проиграл почти 300 игр. " +
               "26 раз мне доверяли сделать финальный победный бросок, и я промахивался. " +
               "Я терпел поражения снова, и снова, и снова. И именно поэтому я добился успеха. Майкл Джордан";

        String[] splitStrings = INPUT_STRING.replaceAll(",|\\.", "").split("\\s");
        Map<String ,Integer> counter = new HashMap<>();
        BiFunction<String, Integer, Integer> remappingFunction = (k, v) -> {
            if(Objects.nonNull(v)) {
                return ++v;
            }
            return 1;
        };
        Arrays.stream(splitStrings).forEach(str -> counter.compute(str, remappingFunction));
        System.out.println("Статистика по количеству вхождений каждого слова во входящей строке:");
        counter.entrySet()
                .stream()
                .sorted((Comparator.comparing(Map.Entry::getKey)))
                .forEach(System.out::println);
    }
}

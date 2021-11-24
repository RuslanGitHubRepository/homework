package com.lusinda.homework16.main;

import com.lusinda.homework16.models.ArrayList;
import com.lusinda.homework16.models.LinkedList;

import java.util.stream.LongStream;
/*
* @author Ludmila Kondratyeva - the future senior java-developer
* @version v1.0
* */

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final byte MASSIVE_SIZE = 20;
        ArrayList<Long> arrayList = new ArrayList();
        LongStream.iterate(0, index -> index + 1)
                .limit(MASSIVE_SIZE)
                .forEach(arrayList::add);
        /*удалим 11-й элемент*/
        System.out.println(arrayList);
        arrayList.removeAt(11);
        System.out.println(arrayList);
        /*удалим элемент за пределами массива, проверим как работает генерация исключений*/
        try {
            arrayList.removeAt(999);
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println(exception.getMessage());
        }

        LinkedList<Long> linkedList = new LinkedList();
        LongStream.iterate(0, index -> index + 1)
                .limit(MASSIVE_SIZE)
                .forEach(linkedList::add);
        /*запросим элемент в 11-й позиции из связного списка*/
        Long element = linkedList.get(11);
        System.out.println(element);
        /*запросим элемент за пределами списка, проверим как работает генерация исключений*/
        try {
            linkedList.get(-150);
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println(exception.getMessage());
        }
    }
}

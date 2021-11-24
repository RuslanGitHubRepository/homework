package com.lusinda.homework16.models;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * 10.11.2021
 * 16. ArrayList
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class ArrayList<T> {
    private static final int DEFAULT_SIZE = 10;

    private T[] elements;
    private int size;
    private Semaphore semaphore;

    public ArrayList() {
        this.elements = (T[])new Object[DEFAULT_SIZE];
        this.size = 0;
        semaphore = new Semaphore(1);
    }

    /**
     * Добавляет элемент в конец списка
     * @param element добавляемый элемент
     */
    public void add(T element) {
        // если массив уже заполнен
        if (isFullArray()) {
            resize();
        }

        this.elements[size] = element;
        size++;
    }

    private void resize() {
        // запоминаем старый массив
        T[] oldElements = this.elements;
        // создаем новый массив, который в полтора раза больше предыдущего
        this.elements = (T[])new Object[oldElements.length + oldElements.length / 2];
        // копируем все элементы из старого массива в новый
        for (int i = 0; i < size; i++) {
            this.elements[i] = oldElements[i];
        }
    }

    private boolean isFullArray() {
        return size == elements.length;
    }

    /**
     * Получить элемент по индексу
     * @param index индекс искомого элемента
     * @return элемент под заданным индексом
     */
    public T get(int index) {
        if (isCorrectIndex(index)) {
            return elements[index];
        } else {
            return null;
        }
    }

    private boolean isCorrectIndex(int index) {
        return index >= 0 && index < size;
    }

    public void clear() {
        this.size = 0;
    }

    /**
     * Удаление элемента по индексу
     *
     * 45, 78, 10, 17, 89, 16, size = 6
     * removeAt(3)
     * 45, 78, 10, 89, 16, size = 5
     * @param includeIndex
     */
    /*Метод потокбезопасен*/
    public void removeAt(int includeIndex) throws ArrayIndexOutOfBoundsException, InterruptedException {
        if(includeIndex < 0 || size == 0 || includeIndex >= size) {
            throw new ArrayIndexOutOfBoundsException(includeIndex);
        }
        if(semaphore.tryAcquire(1, 1, TimeUnit.MILLISECONDS)) {
            this.elements = (T[]) IntStream.range(0, this.elements.length)
                    .filter(o -> o != includeIndex)
                    .mapToObj(o -> elements[o])
                    .toArray();
            size -= 1;
            semaphore.release(1);
        }
    }

    @Override
    public String toString() {
        return "ArrayList{" +
                "elements=" + Arrays.toString(elements) +
                ", size=" + size +
                '}';
    }
}
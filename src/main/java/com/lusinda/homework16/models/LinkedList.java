package com.lusinda.homework16.models;

/**
 * 11.11.2021
 * 17. LinkedList
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class LinkedList<T> {

    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    private Node<T> first;
    private Node<T> last;
    private int size;

    public void add(T element) {
        // создаю новый узел
        Node<T> newNode = new Node<>(element);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    public void addToBegin(T element) {
        Node<T> newNode = new Node<>(element);

        if (size == 0) {
            last = newNode;
        } else {
            newNode.next = first;
        }
        first = newNode;
        size++;
    }

    public int size() {
        return size;
    }

    /*класс потокобезопасен*/
    public T get(int includeIndex) throws ArrayIndexOutOfBoundsException {
        if(includeIndex < 0 || size == 0 || includeIndex >= size) {
            throw new ArrayIndexOutOfBoundsException(includeIndex);
        }
        if(includeIndex == 0) {
            return first.value;
        }
        int count = includeIndex;
        Node<T> next = first.next;
        while (--count > 0) {
            next = next.next;
        }
        return next.value;
    }

}
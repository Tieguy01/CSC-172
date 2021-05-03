package Project3;

import java.util.NoSuchElementException;

public class Queue<T> {

    private class Node {
        T data;
        Node next;
    }

    private Node head, tail;
    private int n = 0;

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return n;
    }

    public void enqueue(T obj) {
        Node newLink = new Node();
        newLink.data = obj;
        if (head == null) {
            head = newLink;
        } else {
            tail.next = newLink;
        } 
        tail = newLink;
        n++;
    }

    public T dequeue() {
        if (head == null) throw new NoSuchElementException();
        T obj = head.data;
        head = head.next;
        n--;
        return obj;
    }
}
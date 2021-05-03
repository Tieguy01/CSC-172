package Project3;

import java.util.NoSuchElementException;

// class for queue data structure
public class Queue<T> {

    // nodes in queue contain their own data and a link to another node
    private class Node {
        T data;
        Node next;
    }

    private Node head, tail; // head node at end of queue, tail node at front of queue
    private int n = 0;

    // returns true if the queue is empty
    public boolean isEmpty() {
        return head == null;
    }

    // returns the size of the queue
    public int size() {
        return n;
    }

    // adds an element to the front of the queue
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

    // deletes and returns an element from the end of the queue
    public T dequeue() {
        if (head == null) throw new NoSuchElementException();
        T obj = head.data;
        head = head.next;
        n--;
        return obj;
    }
}
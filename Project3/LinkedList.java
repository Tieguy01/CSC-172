package Project3;

import java.util.NoSuchElementException;

public class LinkedList<T> {

    private class Node {
        T data;
        Node next;
    }

    private Node head;

    public boolean isEmpty() {
        return head == null;
    }

    public void addToHead(T obj) {
        Node newLink = new Node();
        newLink.data = obj;
        newLink.next = head;
        head = newLink;
    }

    public T removeHead() {
        if (head == null) throw new NoSuchElementException();
        T obj = head.data;
        head = head.next;
        return obj;
    }

    // used to return the data of the head of the list without removing the head from the list
    public T getHead() {
        return head.data;
    }
    
    public void printList() {
        printList(head);
    }

    private void printList(Node front) {
        if (front == null) {
            return;
        } else {
            System.out.print(front.data.toString() + " ");
            printList(front.next);
        }
    }

}


package Lab4;

import java.util.NoSuchElementException;

public class LinkedList<T> {

    // private class Node {
    //     T data;
    //     Node next;
    // }

    private Node<T> head, tail;
    private int n = 0;

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return n;
    }

    public void addToHead(T obj) {
        Node<T> newLink = new Node<>();
        newLink.data = obj;
        newLink.next = head;
        head = newLink;
        n++;
    }

    public void addToTail(T obj) {
        Node<T> newLink = new Node<>();
        newLink.data = obj;
        if (head == null) {
            head = newLink;
        } else {
            tail.next = newLink;
        } 
        tail = newLink;
        n++;
    }

    public T removeHead() {
        if (head == null) throw new NoSuchElementException();
        T obj = head.data;
        head = head.next;
        n--;
        return obj;
    }

    // used to return the data of the head of the list without removing the head from the list
    public Node<T> getHead() {
        return head;
    }
    
    public void printList() {
        printList(head);
    }

    private void printList(Node<T> front) {
        if (front == null) {
            return;
        } else {
            System.out.print(front.data.toString());
            printList(front.next);
        }
    }

    public void printListReverse() {
        printListReverse(head);
    }

    private void printListReverse(Node<T> front) {
        if (front == null) {
            return;
        } else {
            printListReverse(front.next);
            System.out.print(front.data.toString());
        }
    }

}


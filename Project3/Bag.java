package Project3;

import java.util.Iterator;

public class Bag<T> implements Iterable<T> {

    private class Node {
        T data;
        Node next;
    }

    private Node head;
    private int n;

    // adds an element to the Bag
    public void add(T obj) {
        Node newNode = new Node();
        newNode.data = obj;
        newNode.next = head;
        head = newNode;
        n++;
    }

    // returns the current number of elements in the Bag
    public int size() {
        return n;
    }

    // returns an iterator for the Bag
    public Iterator<T> iterator() {
         return new BagIterator();
    }

    // iterator to iterate through the elements in the Bag
    public class BagIterator implements Iterator<T> {
        private Node currNode = head;

        public boolean hasNext() {
            return currNode != null;
        }

        public T next() {
            T obj = currNode.data;
            currNode = currNode.next;
            return obj;
        }
    }
}
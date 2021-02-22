package Project1;

public class Queue<T> {

    private LinkedList<T> theQueue;
    private int n;

    public Queue() {
        theQueue = new LinkedList<>();
    }

    public boolean isEmpty() {
        return theQueue.isEmpty();
    }

    public int size() {
        return n;
    }

    public void enqueue(T obj) {
        theQueue.addToTail(obj);
        n++;
    }

    public T dequeue() {
        n--;
        return theQueue.removeHead();
    }

    public void viewQueue() {
        theQueue.printList();
    }
}
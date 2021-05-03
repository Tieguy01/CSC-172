package Project3;

// heap-based priority queue class 
public class PriorityQueue<T extends Comparable<T>> {

    private T[] pq; // array of all elements in queue
    private int n = 0; // size of queue

    public PriorityQueue(int maxN) {
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Comparable[maxN + 1];
        pq = temp;
    }

    // returns true if the queue is empty
    public boolean isEmpty() {
        return n == 0;
    }

    // returns the size of the queue
    public int size() {
        return n;
    }

    // adds an element to the queue and makes it swim up the heap
    public void enqueue(T obj) {
        pq[++n] = obj;
        swim(n);
    }

    // deletes the minimum element in the queue by exchanging it with the element at the end of the array representing the heap 
    // and making that element sink back down the heap
    public T delMin() {
        T min = pq[1];
        exchange(1, n--);
        pq[n+1] = null;
        sink(1);
        return min;
    }

    // method to make an element travel up a heap as long as it is not the root and is less than its parent element in the heap
    private void swim(int i) {
        while (i > 1 && moreThan(i/2, i)) {
            exchange(i/2, i);
            i = i/2;
        }
    }

    // method to make an element travel down a heap as long as it is more than one of its children
    // the element always switches with its smaller child
    private void sink(int i) {
        while (2 * i <= n) {
            int j = 2 * i;
            if (j < n && moreThan(j, j+1)) j++;
            if (!moreThan(i, j)) break;
            exchange(i, j);
            i = j;
        }
    }

    // returns true if the element at index i is more than the element at index j
    private boolean moreThan(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    // exchanged the positions of two elements in the priority queue
    private void exchange(int i, int j) {
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

}
package Project3;

public class PriorityQueue<T extends Comparable<T>> {

    private T[] pq;
    private int n = 0;

    public PriorityQueue(int maxN) {
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Comparable[maxN + 1];
        pq = temp;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(T obj) {
        pq[++n] = obj;
        swim(n);
    }

    public T delMin() {
        T min = pq[1];
        exchange(1, n--);
        pq[n+1] = null;
        sink(1);
        return min;
    }

    private void swim(int i) {
        while (i > 1 && moreThan(i/2, i)) {
            exchange(i/2, i);
            i = i/2;
        }
    }

    private void sink(int i) {
        while (2 * i <= n) {
            int j = 2 * i;
            if (j < n && moreThan(j, j+1)) j++;
            if (!moreThan(i, j)) break;
            exchange(i, j);
            i = j;
        }
    }

    private boolean moreThan(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    private void exchange(int i, int j) {
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

}
package Project1;

public class Stack<T> {

    private LinkedList<T> theStack;
    private int n;

    public Stack() {
        theStack = new LinkedList<>();
    }

    public boolean isEmpty() {
        return theStack.isEmpty();
    }

    public int size() {
        return n;
    }

    public void push(T obj) {
        theStack.addToHead(obj);
        n++;
    }

    public T pop() {
        n--;
        return theStack.removeHead();
    }
    
    public T getHead() {
        return theStack.getHead();
    }

    public void viewStack() {
        theStack.printListReverse();
    }
}
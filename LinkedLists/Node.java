package LinkedLists;

public class Node <T> {

    private T data;
    private Node<?> next;

    public Node(T data, Node<?> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public Node<?> getNext() {
        return next;
    }
}
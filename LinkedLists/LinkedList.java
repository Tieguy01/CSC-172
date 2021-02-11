package LinkedLists;

public class LinkedList <T> {

    private Node<T> head;

    // add node to list from front
    public void addFirst(T obj) {
        Node<T> myNode = new Node<>(obj, head);
        head = myNode;
    }

    // print list
    public void printList() {
        printList(head);
    }

    private void printList(Node<?> front) {
        while (front != null) {
            System.out.println(front.getData().toString());
            front = front.getNext();
        }
    }
}
package Lab3;

import java.util.ArrayList;
import java.util.Iterator;

public class Lab3Task3 {

    // prints an ArrayList using a basic for loop
    public static void printArrayListBasicLoop(ArrayList<Integer> al) {
        for(int i = 0; i < al.size(); i++) {
            System.out.print(al.get(i) + " ");
        }
    }

    // prints an ArrayList using an enhanced for loop
    public static void printArrayEnhancedLoop(ArrayList<Integer> al) {
        for(Integer i : al) {
            System.out.print(i + " ");
        }
    }

    // prints an ArrayList using a for loop with an Iterator
    public static void printArrayListForLoopListIterator(ArrayList<Integer> al) {
        for(Iterator<Integer> it = al.iterator(); it.hasNext(); ) {
            System.out.print(it.next() + " ");
        }
    }

    // prints an ArrayList using a while loop with an Iterator
    public static void printArrayListWhileLoopListIterator(ArrayList<Integer> al) {
        Iterator<Integer> it = al.iterator();
        while(it.hasNext()) {
            System.out.print(it.next() + " ");
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> al = new ArrayList<>();
        al.add(10);
        al.add(15);
        al.add(30);
        al.add(40);

        System.out.println("Basic for loop: ");
        printArrayListBasicLoop(al);
        System.out.println("\n");

        System.out.println("Enhanced for loop: ");
        printArrayEnhancedLoop(al);
        System.out.println("\n");

        System.out.println("For loop with iterator: ");
        printArrayListForLoopListIterator(al);
        System.out.println("\n");

        System.out.println("While loop with iterator: ");
        printArrayListWhileLoopListIterator(al);
    }
}
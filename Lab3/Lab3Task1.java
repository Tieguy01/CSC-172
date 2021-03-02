package Lab3;

import java.util.ArrayList;
import java.util.Arrays;

public class Lab3Task1 {

    // prints a formatted 2D array
    public static void print2Darray(int[][] array) {
        for(int[] i : array) {
            for(int j : i) {
                System.out.printf("%-13d", j); // formated to give space between elements
            }
            System.out.println("\n");
        }
    }

    // prints a formatted 2D ArrayList
    public static void print2DList(ArrayList<ArrayList<Integer>> list) {
        for(ArrayList<Integer> i : list) {
            for(Integer j : i) {
                System.out.printf("%-13d", j); // formated to give space between elements
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        int[][] array = {{10, 15, 30, 40},{15, 5, 8, 2}, {20, 2, 4, 2},{1, 4, 5, 0}};
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        list.add(new ArrayList<Integer>(Arrays.asList(10, 15, 30, 40)));
        list.add(new ArrayList<Integer>(Arrays.asList(15, 5, 8, 2)));
        list.add(new ArrayList<Integer>(Arrays.asList(20, 2, 4, 2)));
        list.add(new ArrayList<Integer>(Arrays.asList(1, 4, 5, 0)));
        
        System.out.println("Array: ");
        print2Darray(array);
        System.out.println("List: ");
        print2DList(list);        
    }
}
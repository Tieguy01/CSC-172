/**
 * @author Ethan Shahan
 */

package Lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lab2 {

    // print array of Objects
    public static void printArrayNonGen(Object[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    // prints array of Integers
    public static void printArray(Integer[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    // prints array of Doubles
    public static void printArray(Double[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    // prints array of Characters
    public static void printArray(Character[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    // prints array of Strings
    public static void printArray(String[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    // prints array of generic type T
    public static <T> void printArrayGen(T[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    //  returns the max Comparable value in an array (non-generic)
    public static Comparable getMax(Comparable[] arr) {
        Comparable max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max.compareTo(arr[i]) < 0) {
                max = arr[i];
            }
        }
        return max;
    }

    // returns the max value of generic type T in an array
    public static <T extends Comparable<T>> T getMaxGen(T[] arr) {
        T max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max.compareTo(arr[i]) < 0) {
                max = arr[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner scan1 = new Scanner(Lab2.class.getResourceAsStream("00.in")); // scanner getting each line from a file
        
        String tempLine = scan1.nextLine(); // string to store line from scan1
        Scanner scan2 = new Scanner(tempLine); // scanner to scan trough a line from scan1

        // makes array of integers from the first line of the file
        List<Integer> intList = new ArrayList<>();
        while(scan2.hasNextInt()) {
            intList.add(scan2.nextInt());
        }
        Integer[] intArray = intList.toArray(new Integer[0]);

        // reset the second scan2 to the next line in scan1
        scan2.close();
        tempLine = scan1.nextLine();
        scan2 = new Scanner(tempLine);

        // makes array of doubles from the second line of the file
        List<Double> doubList = new ArrayList<>();
        while(scan2.hasNextDouble()) {
            doubList.add(scan2.nextDouble());
        }
        Double[] doubArray = doubList.toArray(new Double[0]);

        // reset the second scan2 to the next line in scan1
        scan2.close();
        tempLine = scan1.nextLine();
        scan2 = new Scanner(tempLine);

        // makes array of characters from the third line of the file
        List<Character> charList = new ArrayList<>();
        while(scan2.hasNext()) {
            charList.add(scan2.next().charAt(0));
        }
        Character[] charArray = charList.toArray(new Character[0]);

        // reset the second scan2 to the next line in scan1
        scan2.close();
        tempLine = scan1.nextLine();
        scan2 = new Scanner(tempLine);

        // makes array of strings from the fourth line of the file
        List<String> strList = new ArrayList<>();
        while(scan2.hasNext()) {
            strList.add(scan2.next());
        }
        String[] strArray = strList.toArray(new String[0]);

        // close both the scanners
        scan2.close();
        scan1.close();

        // print arrays using non generic method
        printArrayNonGen(intArray);
        printArrayNonGen(doubArray);
        printArrayNonGen(charArray);
        printArrayNonGen(strArray);

        // print arrays using overloaded methods
        printArray(intArray);
        printArray(doubArray);
        printArray(charArray);
        printArray(strArray);

        // print arrays using generic method
        printArrayGen(intArray);
        printArrayGen(doubArray);
        printArrayGen(charArray);
        printArrayGen(strArray);

        // print max value of each array using non generic method
        System.out.println(getMax(intArray));
        System.out.println(getMax(doubArray));
        System.out.println(getMax(charArray));
        System.out.println(getMax(strArray));

        // print max value of each array using generic method
        System.out.println(getMaxGen(intArray));
        System.out.println(getMaxGen(doubArray));
        System.out.println(getMaxGen(charArray));
        System.out.println(getMaxGen(strArray));
    }
}
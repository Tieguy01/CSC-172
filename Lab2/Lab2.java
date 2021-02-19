package Lab2;

public class Lab2 {

    public static void printArrayNonGen(Object[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    public static void printArray(Integer[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    public static void printArray(Double[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    public static void printArray(Character[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    public static void printArray(String[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    public static <T> void printArrayGen(T[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.print(arr[arr.length - 1] + "}");
        System.out.println();
    }

    public static Comparable getMax(Comparable[] arr) {
        Comparable max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max.compareTo(arr[i]) < 0) {
                max = arr[i];
            }
        }
        return max;
    }

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
        Integer[] intArray = {1, 2, 3, 4, 5};
        Double[] doubArray = {1.1, 2.2, 3.3, 4.4};
        Character[] charArray = {'H', 'E', 'L', 'L', 'O'};
        String[] strArray = {"once", "upon", "a", "time"};

        printArrayNonGen(intArray);
        printArrayNonGen(doubArray);
        printArrayNonGen(charArray);
        printArrayNonGen(strArray);

        printArray(intArray);
        printArray(doubArray);
        printArray(charArray);
        printArray(strArray);

        printArrayGen(intArray);
        printArrayGen(doubArray);
        printArrayGen(charArray);
        printArrayGen(strArray);

        System.out.println(getMax(intArray));
        System.out.println(getMax(doubArray));
        System.out.println(getMax(charArray));
        System.out.println(getMax(strArray));
        

        System.out.println(getMaxGen(intArray));
        System.out.println(getMaxGen(doubArray));
        System.out.println(getMaxGen(charArray));
        System.out.println(getMaxGen(strArray));
    }
}
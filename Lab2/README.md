Ethan Shahan Lab #2 Java Generics
Lab: 17 Tues/Thursday 4:50-6:05

Contact Info:
    Email: eshahan@u.rochester.edu

Lab Explanation:
    In this lab, contained within the class Lab2, there are six methods for printing arrays of different types: the method printArrayNonGen takes an array of Objects as its parameter, the printArray methods are four overloaded methods for printing arrays of type int, double, char, and String, and the method printArrayGen uses the parameter of an array of generic type T to support all four of these array types. There are also two methods for getting the max value from an array of any type: getMax takes an array of type Comparable, while the type safe version getMaxGen takes an array of generic type T (as long as it extends Comparable).

    All the arrays used in this class are taken from the text file 00.in. In this text file the first line is turned into an array of integers, the second line is turned into an array of doubles, the third line is turned into an array of characters, and the fourth line is turned into an array of Strings. All values are separated by spaces in the the text file.

    All results are printed to the console.

/******************************************************************************
 *  Compilation:  javac Sorting.java
 *  Execution:    java Sorting input.txt AlgorithmUsed
 *  Dependencies: StdOut.java In.java Stopwatch.java
 *  Data files:   http://algs4.cs.princeton.edu/14analysis/1Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/2Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/4Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/8Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/16Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/32Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/1Mints.txt
 *
 *  A program to play with various sorting algorithms. 
 *
 *
 *  Example run:
 *  % java Sorting 2Kints.txt  2
 *
 ******************************************************************************/
package Lab5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Questions for TAs
 * Quicksort StackOverflowError
 * Nearly sorted array
 * Bubble sort algorithm
 * Chart format (log scale x-axis)
 */


public class Sorting {


 /**
     * 
     * Sorts the numbers present in the file based on the algorithm provided.
     * 0 = Arrays.sort() (Java Default)
     * 1 = Bubble Sort
     * 2 = Selection Sort 
     * 3 = Insertion Sort 
     * 4 = Mergesort
     * 5 = Quicksort
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException{ 
        In in = new In(args[0]);   

		// Storing file input in an array
        int[] a = in.readAllInts();
		
		// b contains sorted integers from a (You can use Java Arrays.sort() method)
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
          b[i] = a[i];
        }
        Arrays.sort(b);

		// c contains all integers stored in reverse order 
        int[] c = new int[b.length];
        for (int i = 0; i < b.length; i++) {
          c[i] = b[b.length - i - 1];
        }

		// d contains almost sorted array 
        //(b copied to d, then (0.1 * d.length)  many swaps performed to acheive this.   
        int[] d = new int[b.length];
        for (int i = 0; i < b.length; i++) {
          d[i] = b[i];
        }
        for (int i = 0; i < 0.1 * d.length; i++) {
        	exchange(d, i, d.length - i - 1);
		}


		int algorithmSelected = Integer.parseInt(args[1]);
		char arrayUsed = 'a';

		// Perform sorting on a,b,c,d. Print runtime for each case along with timestamp and record those.
		int[][] arrs = {a, b, c, d};
		for (int i = 0; i < arrs.length; i ++) {
			Stopwatch timer = new Stopwatch();
			sort(arrs[i], algorithmSelected);
			double time = timer.elapsedTimeMillis();

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String netID = "eshahan";

			// Write the resultant array to a file (Each time you run a program 4 output files should be generated. (one for each a,b,c, and d)
			FileWriter writer = new FileWriter(new File(arrayUsed + ".txt"));
			for (int n : arrs[i]) {
				writer.write(n + "");
				writer.write("\n");
			}
			writer.close();

			StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed, arrayUsed, time, timeStamp, netID, args[0]);
			arrayUsed++;
		}
	} 

	static String algorithmUsed = "";
 
	// Second argument and based on input sorting algorithm selected
	//  * 0 = Arrays.sort() (Java Default)
	//  * 1 = Bubble Sort
	//  * 2 = Selection Sort 
	//  * 3 = Insertion Sort 
	//  * 4 = Mergesort
	//  * 5 = Quicksort

	public static void sort(int[] arr, int algorithm) {
		switch(algorithm) {
			case 0: // .sort()
				algorithmUsed = "Default Sort";
				defaultSort(arr);
				break;
			case 1: // bubble
				algorithmUsed = "Bubble Sort";
				bubbleSort(arr);
				break;
			case 2: // selection
				algorithmUsed = "Selection Sort";
				selectionSort(arr);
				break;
			case 3: // insertion
				algorithmUsed = "Insertion Sort";
				insertionSort(arr);
				break;
			case 4: // merge
				algorithmUsed = "Mergesort";
				mergeSort(arr);
				break;
			case 5: // quick
				algorithmUsed = "Quicksort";
				quickSort(arr);
				break;
		}
	}

	public static void exchange(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void defaultSort(int[] arr) {
		Arrays.sort(arr);
	}

	public static void bubbleSort(int[] arr) {
		boolean sorted = false;
		while (!sorted) {
			sorted = true;
			for (int i = 0; i < arr.length - 1; i++) {
				if (arr[i] > arr[i + 1]) {
					sorted = false;
					exchange(arr, i, i + 1);
				}
			}
		}
	}

	public static void selectionSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int min = i;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[min]) {
					min = j;
				}
			}
			exchange(arr, i, min);
		}
	}

	public static void insertionSort(int[] arr) {
		for (int i = 1; i < arr.length;  i++) {
			for (int j = i; j > 0 && (arr[j] < arr[j-1]); j--) {
				exchange(arr, j, j-1);
			}
		}
	}

	static int[] aux;

	public static void merge(int[] arr, int lo, int mid, int hi) {

		int i = lo;
		int j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			aux[k] = arr[k];
		}
		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				arr[k] = aux[j++];
			} else if (j > hi) {
				arr[k] = aux[i++];
			} else if (aux[j] < aux[i]) {
				arr[k] = aux[j++];
			} else {
				arr[k] = aux[i++];
			}
		}
	}

	public static void mergeSort(int[] arr) {
		aux = new int[arr.length];
		mergeSort(arr, 0, arr.length - 1);
	}

	public static void mergeSort(int[] arr, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		mergeSort(arr, lo, mid);
		mergeSort(arr, mid + 1, hi);
		merge(arr, lo, mid, hi);
	}

	public static int partition(int[] arr, int lo, int hi) {
		int mid = lo + (hi - lo) / 2;
		int pivot = arr[mid];

		boolean done = false;
		while (!done) {
			while (arr[lo] < pivot) {
				lo++;
			}

			while (pivot < arr[hi]) {
				hi--;
			}

			if (lo >= hi) {
				done = true;
			} else {
				exchange(arr, lo, hi);
				lo++;
				hi--;
			}
		}
		return hi;
	}

	public static void quickSort(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}

	public static void quickSort(int[] arr, int lo, int hi) {
		if (lo < hi) {
			int j = partition(arr, lo, hi);
			quickSort(arr, lo, j);
			quickSort(arr, j+1, hi);
		}
	}
} 



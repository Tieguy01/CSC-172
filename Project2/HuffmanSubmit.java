package Project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HuffmanSubmit implements Huffman {

	private static class MinPQ<T extends Comparable<T>> {

		private class PQNode implements Comparable<PQNode>{
			T data;
			PQNode next;

			public int compareTo(PQNode that) {
				return this.data.compareTo(that.data);
			}
		}

		private PQNode head, tail;
		private int n;

		public int size() {
			return n;
		}

		public void insert(T obj) {
			PQNode newLink = new PQNode();
			newLink.data = obj;

			if (head == null) {
				head = newLink;
				tail = newLink;
			} else if (newLink.compareTo(tail) > 0) {
				tail.next = newLink;
				tail = newLink;
			} else if (newLink.compareTo(head) <= 0) {
				newLink.next = head;
				head = newLink;
			} else {
				PQNode front = head;
				while (front != null) {
					if (newLink.compareTo(front.next) <= 0) {
						newLink.next = front.next;
						front.next = newLink;
						break;
					} else {
						front = front.next;
					}
				}
			} 
			n++;
		}

		public T delMin() {
			if (head == null) throw new NoSuchElementException();
			T obj = head.data;
			head = head.next;
			n--;
			return obj;
		}

		public void printList() {
			printList(head);
			System.out.println();
		}
	
		private void printList(PQNode front) {
			if (front == null) {
				return;
			} else {
				System.out.print(front.data.toString() + " ");
				printList(front.next);
			}
		}

	}
	
	private static class Node implements Comparable<Node> {

		private char ch;
		private int freq;
		private Node left, right;

		Node(char ch, int freq, Node left, Node right) {
			this.ch = ch;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		public int compareTo(Node that) {
			return this.freq - that.freq;
		}
	}

	private static final int R = 256;

	public static void calcFrequencies(String inputFile, String freqFile) throws IOException{
		BinaryIn in = new BinaryIn(inputFile);
		int[] freq = new int[R];
		FileWriter freqWriter = new FileWriter(new File(freqFile));
		
		while (!in.isEmpty()) {
			freq[in.readChar()]++;
		}
		for (int i = 0; i < freq.length; i++) { 
			if (freq[i] > 0) {
				String charByte = "";
				for (int j = 0; j < 8; j++) {
					charByte = ((((char)i) >> j) & 1) + charByte;
				}
				freqWriter.write(charByte + ":" + freq[i] + "\n");
			}
		}
		freqWriter.close();
	}

	public static Node buildTrie(String freqFile) throws FileNotFoundException{
		Scanner freqScan = new Scanner(new File(freqFile));
		MinPQ<Node> pq = new MinPQ<>();

		while (freqScan.hasNextLine()) {
			String next = freqScan.nextLine();
			char c = (char)Integer.parseInt(next.substring(0, 8), 2);
			pq.insert(new Node(c, Integer.parseInt(next.substring(9)), null, null));
		}
		freqScan.close();

		while (pq.size() > 1) {
			System.out.println("size: " + pq.size());
			Node x = pq.delMin();
			if (x.isLeaf()) System.out.println("x: " + x.ch);
			else System.out.println("x: " + x.freq);
			Node y = pq.delMin();
			if (y.isLeaf()) System.out.println("y: " + y.ch);
			else System.out.println("y: " + y.freq);
			Node parent = new Node('\0', x.freq + y.freq, x, y);
			pq.insert(parent);
		}
		return pq.delMin();
	}

	public static void printTrie(Node root) {
		if (root == null) return;
		if (root.isLeaf()) System.out.print(root.ch + " ");
		printTrie(root.left);
		printTrie(root.right);
	}

	public static void buildCode(String[] st, Node x, String s) {
		if (x.isLeaf()) {
			st[x.ch] = s;
			System.out.println(x.ch + " " + s);
			return;
		}
		buildCode(st, x.left, s + '0');
		buildCode(st, x.right, s + '1');
	}

	public static void write(Node root, String inputFile, String outputFile) {
		String[] st = new String[R];
		buildCode(st, root, "");

		BinaryIn in = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(outputFile);

		while (!in.isEmpty()) {
			String code = st[in.readChar()];
			for (int j = 0; j < code.length(); j++) {
				if (code.charAt(j) == '1') {
					System.out.print(1);
					out.write(true);
				} else {
					System.out.print(0);
					out.write(false);
				}
			}
			System.out.println();
		}
		out.flush();
	}

	public void encode(String inputFile, String outputFile, String freqFile) {
		// calcFrequencies(inputFile, freqFile);
		// Node root = buildTrie(freqFile);
		// write(root, inputFile, outputFile);
	}


	public void decode(String inputFile, String outputFile, String freqFile){
			// TODO: Your code here
	}

   	public static void main(String[] args) throws IOException{
    	Huffman  huffman = new HuffmanSubmit();
		huffman.encode("ur.jpg", "ur.enc", "freq.txt");
		huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");
		// After decoding, both ur.jpg and ur_dec.jpg should be the same. 
		// On linux and mac, you can use `diff' command to check if they are the same.

		// MinPQ<Integer> testPQ = new MinPQ<>();
		// testPQ.insert(2);
		// testPQ.insert(8);
		// testPQ.insert(1);
		// testPQ.insert(7);
		// testPQ.insert(3);
		// testPQ.insert(6);
		// testPQ.insert(4);
		// testPQ.printList();
		// testPQ.delMin();
		// testPQ.delMin();
		// testPQ.printList();

		calcFrequencies("in.txt", "freq.txt");
		Node root = buildTrie("freq.txt");
		printTrie(root);
		System.out.println();
		write(root, "in.txt", "out.txt");
   	}
}

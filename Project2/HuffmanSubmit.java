package Project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HuffmanSubmit implements Huffman {

	private class MinPQ<T extends Comparable<T>> {

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

		public Iterator<T> iterator() {
			return new PQIterator();
		}

		private class PQIterator implements Iterator<T> {
			private int i = n;
			private PQNode currNode = head;

			public boolean hasNext() {
				return i > 0;
			}

			public T next() {
				PQNode rNode = currNode;
				i--;
				currNode = currNode.next;
				return rNode.data;
			}
		}

		public void printList() {
			printList(head);
			System.out.println();
		}
	
		private void printList(PQNode root) {
			if (root == null) {
				return;
			} else {
				System.out.print(root.data.toString() + " ");
				printList(root.next);
			}
		}

	}
	
	private class Node implements Comparable<Node> {

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

		public String toString() {
			return "(" + freq + ")" + Character.toString(ch);
		}
	}

	private static final int R = 256;

	public void calcFrequencies(String inputFile, String freqFile) {
		BinaryIn in = new BinaryIn(inputFile);
		int[] freq = new int[R];

		try {
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

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public Node buildTrie(String freqFile) {
		MinPQ<Node> pq = new MinPQ<>();

		try{
			Scanner freqScan = new Scanner(new File(freqFile));
			
			while (freqScan.hasNextLine()) {
				String next = freqScan.nextLine();
				char c = (char)Integer.parseInt(next.substring(0, 8), 2);
				pq.insert(new Node(c, Integer.parseInt(next.substring(9)), null, null));
			}
			freqScan.close();

			// pq.printList();
			printPQ(pq);

			while (pq.size() > 1) {
				// System.out.println("size: " + pq.size());

				Node x = pq.delMin();
				// pq.printList();

				// if (x.isLeaf()) System.out.println("x: " + x.ch);
				// else System.out.println("x: " + x.freq);

				Node y = pq.delMin();
				// pq.printList();

				// if (y.isLeaf()) System.out.println("y: " + y.ch);
				// else System.out.println("y: " + y.freq);

				Node parent = new Node('\0', x.freq + y.freq, x, y);
				pq.insert(parent);

				printPQ(pq);
			}
			
		} catch (FileNotFoundException e)  {
			System.out.println(e);
		}

		Node min = pq.delMin();
		// printTrie(min);

		return min;
	}

	// public static void printTrie(Node root) {
	// 	if (root == null) return;
	// 	if (root.isLeaf()) System.out.print(root.ch + " ");
	// 	printTrie(root.left);
	// 	printTrie(root.right);
	// }

	public void printPQ(MinPQ<Node> pq) {
		Iterator<Node> pqIterator = pq.iterator();
		while (pqIterator.hasNext()) {
			printTrie(pqIterator.next());
		}
		System.out.println("\n");
	}

	public void printTrie(Node root) {
		StringBuilder sb = new StringBuilder();
		printTrie(sb, "", "", root);
		System.out.print(sb.toString());
	}

	public void printTrie(StringBuilder sb, String padding, String branch, Node node) {
		if (node != null) {
			sb.append("\n");
			sb.append(padding);
			String nodeString = branch + node.toString();
			if (node.left != null || node.right != null) {
				nodeString = nodeString + "_";
			}
			sb.append(nodeString);

			String paddingString = padding;
			for (int i = 0; i < nodeString.length() - 1; i++) {
				paddingString = paddingString + " ";
			}

			printTrie(sb, paddingString + "|", "_/--", node.left);
			printTrie(sb, paddingString + " ", " \\--", node.right);
		}
	}

	public void buildCode(String[] st, Node x, String s) {
		if (x.isLeaf()) {
			st[x.ch] = s;
			// System.out.println(x.ch + " " + s);
			return;
		}
		buildCode(st, x.left, s + '0');
		buildCode(st, x.right, s + '1');
	}

	public void write(Node root, String inputFile, String outputFile) {
		String[] st = new String[R];
		buildCode(st, root, "");

		BinaryIn in = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(outputFile);

		while (!in.isEmpty()) {
			String code = st[in.readChar()];
			for (int j = 0; j < code.length(); j++) {
				if (code.charAt(j) == '1') {
					// System.out.print(1);
					out.write(true);
				} else {
					// System.out.print(0);
					out.write(false);
				}
			}
			// System.out.println();
		}

		out.flush();
	}

	public void expand(Node root, String inputFile, String outputFile) {
		BinaryIn in  = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(outputFile);

		for (int i = 0; i < root.freq; i++) {
			
			Node currNode = root;
			while (!currNode.isLeaf()) {

				if (in.readBoolean()) {
					// System.out.println(1);
					currNode = currNode.right;
				} else {
					// System.out.println(0);
					currNode = currNode.left;
				}
			}
			out.write(currNode.ch);
		}
		out.close();
	}

	public void encode(String inputFile, String outputFile, String freqFile) {
		calcFrequencies(inputFile, freqFile);
		Node root = buildTrie(freqFile);
		write(root, inputFile, outputFile);
	}


	public void decode(String inputFile, String outputFile, String freqFile){
		Node root = buildTrie(freqFile);
		expand(root, inputFile, outputFile);
	}

   	public static void main(String[] args) throws IOException{
    	Huffman  huffman = new HuffmanSubmit();
		// huffman.encode("ur.jpg", "ur.enc", "freq.txt");
		// huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");
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

		// calcFrequencies("in.txt", "freq.txt");
		// Node root = buildTrie("freq.txt");
		// printTrie(root);
		// System.out.println();
		// write(root, "in.txt", "out.txt");

		huffman.encode("in.txt", "out.txt", "freq.txt");
		huffman.decode("out.txt", "in_dec.txt", "freq.txt");

		// huffman.encode("alice30.txt", "alice30.enc", "freq.txt");
		// huffman.decode("alice30.enc", "alice30_dec.txt", "freq.txt");
   	}
}
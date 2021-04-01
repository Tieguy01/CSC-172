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
			PQNode parent;
			PQNode left;
			PQNode right;
			String whichChild;

			public int compareTo(PQNode that) {
				return this.data.compareTo(that.data);
			}
		}

		private PQNode root;
		private int n;

		public int size() {
			return n;
		}

		public void swim(PQNode n) {
			while (n.parent != null && more(n.parent, n)) {
				System.out.println(n.parent.data + " more than " + n.data);
				if(root == n.parent)  root = n;
				exchange(n.parent, n);
			}
		}

		public void sink(PQNode n) {
			while ((n.left != null && more(n, n.left)) || (n.right != null && more(n, n.right))) {
				System.out.println("n: " + n.data); 
				if (n.left != null && n.right == null) {
					exchange(n, n.left);
				} else {
					if (more(n.left, n.right)) {
						System.out.println("n.right: " + n.right.data);
						exchange(n, n.right);
					} else {
						exchange(n, n.left);
					}
				} 
			}
		}

		public PQNode findLastNode(PQNode root) {
			System.out.println("root find: " + root.data);
			if (root.left != null) {
				if (root.right != null) {
					if (root.left.right == null) return findLastNode(root.left);
					else return findLastNode(root.right);
				}
				return root.left;
			} 
			return root;
		}

		public PQNode findEndNode(PQNode root) {
			if (root.left != null) {
				if (root.right != null) return findEndNode(root.right);
				else return findEndNode(root.left);
			}
			return root;
		}

		public void insert(T obj) {
			System.out.println("newNode: " + obj);
			PQNode newNode = new PQNode();
			newNode.data = obj;

			if (root == null) {
				root = newNode;
			} else {
				System.out.println("root: " + root.data);
				PQNode lastNode = findLastNode(root);
				System.out.println("lastNode: " + lastNode.data);
				if (lastNode.whichChild == "left" && lastNode.parent.right == null) {
					lastNode = lastNode.parent;
					lastNode.right = newNode;
					newNode.whichChild = "right";
				} else {
					lastNode.left = newNode;
					newNode.whichChild = "left";
				}
				newNode.parent = lastNode;
				if (root.left != null) System.out.println("root left: " + root.left.data);
				if (root.right != null) System.out.println("root right: " + root.right.data);
				swim(newNode);
			}
		}

		public T delMin() {
			if (root == null) throw new NoSuchElementException();
			T min = root.data;
			PQNode endNode = findEndNode(root);
			System.out.println("endNode: " + endNode.data);
			if (endNode.whichChild == "left") {
				endNode.parent.left = null;
			} else {
				endNode.parent.right = null;
			}
			endNode.left = root.left;
			endNode.right = root.right;
			endNode.parent = null;
			endNode.whichChild = null;
			root = endNode;
			sink(root);
			return min;
		}

		public boolean more(PQNode n, PQNode m) {
			System.out.println("parent: " + n.data);
			System.out.println("more: " + n.compareTo(m));
			return n.compareTo(m) > 0;
		}

		public void exchange(PQNode ogParent, PQNode ogChild) {
			if (ogParent.whichChild == "left" ) {
				ogParent.parent.left = ogChild;
			} else if (ogParent.whichChild == "right") {
				ogParent.parent.right = ogChild;
			} else {
				root = ogChild;
			}
			ogChild.parent = ogParent.parent;
			PQNode leftTemp = ogChild.left;
			PQNode rightTemp = ogChild.right;
			String whichChildTemp = ogChild.whichChild;
			ogChild.whichChild = ogParent.whichChild;
			if (whichChildTemp == "left") {
				ogChild.left = ogParent;
				if (ogParent.right != null) ogParent.right.parent = ogChild;
				ogChild.right = ogParent.right;
			} else {
				ogChild.right = ogParent;
				if (ogParent.left != null) ogParent.left.parent = ogChild;
				ogChild.left = ogParent.left;
			}
			ogParent.left = leftTemp;
			if (ogParent.left != null) ogParent.left.parent = ogParent;
			ogParent.right = rightTemp;
			if (ogParent.right != null)ogParent.right.parent = ogParent;
			ogParent.parent = ogChild;
			ogParent.whichChild = whichChildTemp;

			if (ogParent.parent != null)System.out.println("Parent parent: " + ogParent.parent.data);
			if (ogParent.left != null) System.out.println("Parent left: " + ogParent.left.data);
			if (ogParent.right != null) System.out.println("Parent right: " + ogParent.right.data);
			System.out.println("parent whichChild: " + ogParent.whichChild);
			if (ogChild.parent != null)System.out.println("child parent: " + ogChild.parent.data);
			if (ogChild.left != null) System.out.println("child left: " + ogChild.left.data);
			if (ogChild.right != null) System.out.println("child right: " + ogChild.right.data);
			System.out.println("child whichChild: " + ogChild.whichChild);
		}

		public void printList() {
			printList(root);
			System.out.println();
		}
	
		private void printList(PQNode root) {
			if (root == null) {
				return;
			} else {
				System.out.print(root.data.toString() + " ");
				printList(root.left);
				printList(root.right);
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

	public static void calcFrequencies(String inputFile, String freqFile) {
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

	public static Node buildTrie(String freqFile) {
		MinPQ<Node> pq = new MinPQ<>();

		try{
			Scanner freqScan = new Scanner(new File(freqFile));
			
			while (freqScan.hasNextLine()) {
				String next = freqScan.nextLine();
				char c = (char)Integer.parseInt(next.substring(0, 8), 2);
				pq.insert(new Node(c, Integer.parseInt(next.substring(9)), null, null));
			}
			pq.insert(new Node('\u0000', 0, null, null));
			freqScan.close();

			while (pq.size() > 1) {
				// System.out.println("size: " + pq.size());

				Node x = pq.delMin();

				// if (x.isLeaf()) System.out.println("x: " + x.ch);
				// else System.out.println("x: " + x.freq);

				Node y = pq.delMin();

				// if (y.isLeaf()) System.out.println("y: " + y.ch);
				// else System.out.println("y: " + y.freq);

				Node parent = new Node('\0', x.freq + y.freq, x, y);
				pq.insert(parent);
			}
			
		} catch (FileNotFoundException e)  {
			System.out.println(e);
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
			if (x.ch == '\u0000') System.out.println("null: " + s);
			else System.out.println(x.ch + " " + s);
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
					// System.out.print(1);
					out.write(true);
				} else {
					// System.out.print(0);
					out.write(false);
				}
			}
			// System.out.println();
		}

		String nullCode = st[0];
		for (int i = 0; i < nullCode.length(); i++) {
			if (nullCode.charAt(i) == '1') {
				// System.out.print(1);
				out.write(true);
			} else {
				// System.out.print(0);
				out.write(false);
			}
		}
		// System.out.println();

		out.flush();
	}

	public static void expand(Node root, String inputFile, String outputFile) {
		BinaryIn in  = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(outputFile);

		while (!in.isEmpty()) {
			
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
			if (currNode.ch == '\u0000') break;
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
		// huffman.decode("ur.enc", "ur_dec.txt", "freq.txt");
		// After decoding, both ur.jpg and ur_dec.jpg should be the same. 
		// On linux and mac, you can use `diff' command to check if they are the same.

		MinPQ<Integer> testPQ = new MinPQ<>();
		testPQ.insert(2);
		testPQ.printList();
		testPQ.insert(8);
		testPQ.printList();
		testPQ.insert(1);
		testPQ.printList();
		testPQ.insert(7);
		testPQ.printList();
		testPQ.insert(3);
		testPQ.printList();
		testPQ.insert(6);
		testPQ.printList();
		testPQ.insert(4);
		testPQ.printList();
		testPQ.delMin();
		testPQ.printList();
		testPQ.delMin();
		testPQ.printList();

		// calcFrequencies("in.txt", "freq.txt");
		// Node root = buildTrie("freq.txt");
		// printTrie(root);
		// System.out.println();
		// write(root, "in.txt", "out.txt");

		// huffman.encode("in.txt", "out.txt", "freq.txt");
		// huffman.decode("out.txt", "in_dec.txt", "freq.txt");

		// huffman.encode("alice30.txt", "alice30.enc", "freq.txt");
		// huffman.decode("alice30.enc", "alice30_dec.txt", "freq.txt");
   	}
}
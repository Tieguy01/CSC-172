package Project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HuffmanSubmit implements Huffman {

	// priority queue used to aid in building the Huffman tree by allowing the node with the lowest frequency to be easily retrieved
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

		// returns the size of the priority queue
		public int size() {
			return n;
		}

		// inserts a new node into the PQ
		public void insert(T obj) {
			PQNode newNode = new PQNode();
			newNode.data = obj;

			// if the PQ is empty, assign the head and tail to the new node
			if (head == null) {
				head = newNode;
				tail = newNode;

			// if the new node is larger than the tail, add the node to the end of the PQ and reassign the tail to the new node
			} else if (newNode.compareTo(tail) > 0) { 
				tail.next = newNode;
				tail = newNode;

			// if the new node is smaller than or equal to the head, add the new node to the front of the PQ and reassign the head to the new node
			} else if (newNode.compareTo(head) <= 0) {
				newNode.next = head;
				head = newNode;

			// otherwise, insert the new node at the first position in the PQ at which the next node is greater than or equal to the new node
			} else {
				PQNode currNode = head;
				while (currNode != null) {
					if (newNode.compareTo(currNode.next) <= 0) {
						newNode.next = currNode.next;
						currNode.next = newNode;
						break;
					} else {
						currNode = currNode.next;
					}
				}
			} 
			n++; // increase the size of the PQ
		}

		// deletes and returns the minimum node from the PQ by reassigning the head to the next node after the head
		public T delMin() {
			if (head == null) throw new NoSuchElementException();
			T obj = head.data;
			head = head.next;
			n--;
			return obj;
		}

		// used to iterate through the nodes in the PQ without deleting them (used when printing trees)
		public Iterator<T> iterator() {
			return new PQIterator();
		}

		// iterator to cycle through the nodes in the PQ
		private class PQIterator implements Iterator<T> {
			private int i = n;
			private PQNode currNode = head;

			public boolean hasNext() {
				return i > 0;
			}

			public T next() {
				PQNode tempNode = currNode;
				i--;
				currNode = currNode.next;
				return tempNode.data;
			}
		}

		// prints the PQ to standard out (used for debugging)
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
	
	// node class used to create nodes for use in the huffman trees 
	private class Node implements Comparable<Node> {

		// each node stores the character it represents (if it's a leaf), its frequency, and its left and right branch nodes
		private char ch;
		private int freq;
		private Node left, right;

		Node(char ch, int freq, Node left, Node right) {
			this.ch = ch;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		// returns true if the node is a leaf
		public boolean isLeaf() {
			return left == null && right == null;
		}

		// used to compare node freqencies (returns postive is this > that, 0 if this == that, and negative if this < that)
		public int compareTo(Node that) {
			return this.freq - that.freq;
		}

		// retruns string representation of a node in the format "(freq)character"
		public String toString() {
			return "(" + freq + ")" + Character.toString(ch);
		}
	}

	private static final int R = 256; // number of characters in the ASCII alpahbet

	// used to calculate the number of occurances of every character in an input file and stores those frequencies in a frequency file
	public void calcFrequencies(String inputFile, String freqFile) {
		BinaryIn in = new BinaryIn(inputFile);
		int[] freq = new int[R]; // creates an array the size of the ASCII alphabet to store the frequency of every character in the input file at the position of that character in the alphabet

		try {
			FileWriter freqWriter = new FileWriter(new File(freqFile));

			// increments the frequency of the character postion in the array for every occurance of that character in the input file
			while (!in.isEmpty()) {
				freq[in.readChar()]++;
			}

			// writes the 8-bit binary representation of every character that appears in the input file next to its frequency in the file
			for (int i = 0; i < freq.length; i++) { 
				if (freq[i] > 0) { // only writes a character to the frequency file if its appears in the input file at least once
					
					// creates an 8-bit binary representation of a character 
					String charByte = "";
					for (int j = 0; j < 8; j++) {
						charByte = ((((char)i) >> j) & 1) + charByte;
					}

					// writes a byte representation of a character next to its frequency to the frequency file in the format "byte:freq"
					freqWriter.write(charByte + ":" + freq[i] + "\n");
				}
			}
			freqWriter.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public Node buildTrie(String freqFile) {
		// priority queue to store a node for every character listed in a frequency file
		MinPQ<Node> pq = new MinPQ<>();

		try{
			Scanner freqScan = new Scanner(new File(freqFile)); // scanner to scan through the frequency file
			FileWriter writer = new FileWriter(new File("tree.txt")); // used to write printed trees to a text at every step of their creation
			
			// for every character listed in a frequency file, create a char from the 8-bit representation of the character and insert a node containing that character and its freqency into the PQ
			while (freqScan.hasNextLine()) {
				String next = freqScan.nextLine();
				char c = (char)Integer.parseInt(next.substring(0, 8), 2);
				pq.insert(new Node(c, Integer.parseInt(next.substring(9)), null, null));
			}
			freqScan.close();

			// pq.printList();
			printPQ(pq, writer); // writes the initially creates PQ to the print file

			// creates a huffman tree out of the PQ by repeatedle removing the two smallest nodes from the PQ, combining them under a new parent node, and inserting that parent node back into the PQ
			// until there is only one node left in the PQ, which implies that that node represents a full huffman tree containing all the characters from the frequency file
			while (pq.size() > 1) {
				// comments used for decoding
				// System.out.println("size: " + pq.size());

				Node x = pq.delMin();
				// pq.printList();

				// if (x.isLeaf()) System.out.println("x: " + x.ch);
				// else System.out.println("x: " + x.freq);

				Node y = pq.delMin();
				// pq.printList();

				// if (y.isLeaf()) System.out.println("y: " + y.ch);
				// else System.out.println("y: " + y.freq);

				// new parent node with an empty character, a frequnecy that is a combination of the frequencies of the two smallest nodes, and with the two smallest nodes as its children
				Node parent = new Node('\0', x.freq + y.freq, x, y); 
				pq.insert(parent);

				printPQ(pq, writer); // adds a representation of the current state of the PQ and the trees branching off from it to the print file on each iteration
			}
			writer.close();
			
		} catch (FileNotFoundException e)  {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

		return pq.delMin(); // returns the last value remaining in the PQ, which represents the root of the Huffman tree 
	}

	// writes a PQ and all the trees branching off from it to a print file
	public void printPQ(MinPQ<Node> pq, FileWriter writer) {
		try{
			Iterator<Node> pqIterator = pq.iterator();
			writer.write("Print:\n");

			// for each node in the PQ, write its tree to a print file
			while (pqIterator.hasNext()) {
				writer.write(printTree(pqIterator.next()));
			}
			writer.write("\n\n");
		} catch (IOException e)  {
			System.out.println(e);
		}
	}

	// helper method that returns a string representation of a Huffman tree
	public String printTree(Node root) {
		StringBuilder sb = new StringBuilder();
		printTree(sb, "      ", "", root);
		return sb.toString();
	}

	// method to recusively  traverse through and create a string representation of a Huffman tree
	public void printTree(StringBuilder sb, String padding, String branch, Node node) {
		if (node != null) {
			sb.append(padding);
			String nodeString = branch + node.toString();
			if (node.left != null || node.right != null) {
				nodeString = nodeString + "_";
			}
			sb.append(nodeString);
			sb.append("\n");

			String paddingString = padding;
			for (int i = 0; i < nodeString.length(); i++) {
				paddingString = paddingString + " ";
			}

			printTree(sb, paddingString + "|", "_/--", node.left);
			printTree(sb, paddingString + " ", " \\--", node.right);
		}
	}

	// recusively builds prefix-free codes for characters by traversing a Huffman tree
	public void buildCode(String[] st, Node x, String s) {
		if (x.isLeaf()) {
			st[x.ch] = s; // assigns a created prefix-free code to the position of an ASCII alphabet sized array at which the character that the code represents is located
			// System.out.println(x.ch + " " + s); // for debugging
			return;
		}
		buildCode(st, x.left, s + '0'); // adds a 0 to the prefix-free code when going down a left branch
		buildCode(st, x.right, s + '1'); // adds a 1 to the prefix-free code when going down a right branch
	}

	// writes a compressed version of a given input file to a given output file
	public void write(Node root, String inputFile, String outputFile) {
		String[] codes = new String[R]; // array the size of the ASCII alphabet to store prefix-free codes at the location of their assigned characters in the alphabet
		buildCode(codes, root, ""); // creates prefix-free codes and stores them in an array given the root of a Huffman tree

		BinaryIn in = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(outputFile);

		// for every byte of an input file, convert that byte to a character using BinaryIn, retrieve the prefix-free code for that character from the codes array,
		// and use that code to write a new compressed bit pattern representing that character to an output file
		while (!in.isEmpty()) {

			String code = codes[in.readChar()]; // creates a string for a prefix-free code for a character read from the input file
			for (int j = 0; j < code.length(); j++) {
				if (code.charAt(j) == '1') {
					// System.out.print(1);
					out.write(true); // writes true to the file when a 1 appears in a code
				} else {
					// System.out.print(0);
					out.write(false); // writes false to the file when a 0 appears in a code
				}
			}
			// System.out.println();
		}

		out.flush(); // pads 0s to the end of the output file if the number of bits written is not a multiple of 8
	}

	// expands compressed versions of characters from an encoded file and writes the decompressed characters to an output file
	public void expand(Node root, String inputFile, String outputFile) {
		BinaryIn in  = new BinaryIn(inputFile);
		BinaryOut out = new BinaryOut(outputFile);

		// for every character in an input file (represented by the frequncy of the root node of a Huffman tree), 
		// traverse a Huffman tree using the bits read from a compressed file until a leaf node is found
		// write the character contained in that leaf node to the decoded output file
		for (int i = 0; i < root.freq; i++) {
			
			Node currNode = root;
			while (!currNode.isLeaf()) {

				if (in.readBoolean()) { // if this is tree, the bit read is a 1, otherwise it is a 0
					// System.out.println(1);
					currNode = currNode.right; // if the bit read is a 1 go down the right branch of the tree
				} else {
					// System.out.println(0);
					currNode = currNode.left; // if the bit read is a 0 go down the left branch of the tree
				}
			}
			out.write(currNode.ch); // write the character found at the leaf node to the output file
		}
		out.close();
	}

	// used to compress an input file to an encoded outputfile, writing a frequency file in the process
	public void encode(String inputFile, String outputFile, String freqFile) {
		calcFrequencies(inputFile, freqFile); // writes a frequency file given an input file
		Node root = buildTrie(freqFile); // builds a Huffman tree from a frequency file and returns its root node
		write(root, inputFile, outputFile); // writes a compressed version of an input file to an output file using a Huffman tree
	}

	// used to decode a compressed input file to a decoded output file, given a frequency file
	public void decode(String inputFile, String outputFile, String freqFile){
		Node root = buildTrie(freqFile); // builds a Huffman tree from a frequency file and returns its root node
		expand(root, inputFile, outputFile); // expands a compressed output file and writes the expanded file to an output file
	}

   	public static void main(String[] args) {
		Huffman  huffman = new HuffmanSubmit();

		huffman.encode("in.txt", "out.txt", "freq.txt");
		huffman.decode("out.txt", "in_dec.txt", "freq.txt");

		huffman.encode("alice30.txt", "alice30.enc", "freq.txt");
		huffman.decode("alice30.enc", "alice30_dec.txt", "freq.txt");

		huffman.encode("ur.jpg", "ur.enc", "freq.txt");
		huffman.decode("ur.enc", "ur_dec.jpg", "freq.txt");
   	}
}
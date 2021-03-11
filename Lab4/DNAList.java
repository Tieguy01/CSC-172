package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

enum Type {
    DNA, RNA
}

public class DNAList {

    static Sequence[] sequences;

    // inserts a sequence into a position of the array if the sequence contains all the appropriate letters for the given type
    public static void insert(int pos, String type, String sequence) {
        if (pos >= sequences.length) {
            System.out.println("Sequence index out of bounds");
        } else {
            LinkedList<Character> sequenceList = new LinkedList<>();
            Type sequenceType;

            Pattern DNAchars = Pattern.compile("A|C|G|T"); // pattern for checking whether a char is an appropriate letter for a DNA sequence
            Pattern RNAchars = Pattern.compile("A|C|G|U"); // pattern for checking whether a char is an appropriate letter for an RNA sequence

            if (type.equals("DNA")) {
                sequenceType = Type.DNA;

                // checks if every char in a given sequence string is an appropriate letter for a DNA sequence
                for (int i = sequence.length() - 1; i >= 0; i--) {
                    if (DNAchars.matcher(Character.toString(sequence.charAt(i))).matches()) {
                        sequenceList.addToHead(sequence.charAt(i));
                    } else {
                        System.out.println("Error occured while inserting");
                        return;
                    }
                }
            } else if (type.equals("RNA")) {
                sequenceType = Type.RNA;

                // checks if every char in a given sequence string is an appropriate letter for an RNA sequence
                for (int i = sequence.length() - 1; i >= 0; i--) {
                    if (RNAchars.matcher(Character.toString(sequence.charAt(i))).matches()) {
                        sequenceList.addToHead(sequence.charAt(i));
                    } else {
                        System.out.println("Error occured while inserting");
                        return;
                    }
                }
            } else {
                return;
            }

            sequences[pos] = new Sequence(sequenceType, sequenceList);
        }
    }

    // removes a sequence from the a given position in the array
    public static void remove(int pos) {
        if (pos >= sequences.length) {
            System.out.println("Sequence index out of bounds");
        } else if (sequences[pos] == null) {
            System.out.println("No sequence to remove at specified position");
        } else {
            sequences[pos] = null;
        }
    }

    // prints all the sequences in the array
    public static void print() {
        for (int i = 0; i < sequences.length; i++) {
            if (sequences[i] != null) {
                System.out.print(i + "\t" + sequences[i].getType() + "\t");
                sequences[i].getSequence().printList();
                System.out.println();
            }
        }
    }

    // prints a sequence at a given position in the array
    public static void printPos(int pos) {
        if (pos >= sequences.length) {
            System.out.println("Sequence index out of bounds");
        } else if (sequences[pos] == null) {
            System.out.println("No sequence to print at specified position");
        } else {
            System.out.print(sequences[pos].getType() + "\t");
            sequences[pos].getSequence().printList();
            System.out.println();
        }
    }

    // replaces a sequence with a clipped version of that sequence given a specified start and end of where to clip the sequence (including the start and end in the clipped sequence)
    public static void clip(int pos, int start, int end) {
        if (pos >= sequences.length) {
            System.out.println("Sequence index out of bounds");
        } else if (sequences[pos] == null) {
            System.out.println("No sequence to clip at specified position");
        } else if (start < 0) {
            System.out.println("Invalid start index");
        } else if (start > sequences[pos].getSequence().size()) {
            System.out.println("Start index out of bounds");
        } else if (end > sequences[pos].getSequence().size()) {
            System.out.println("End index out of bounds");
        } else {
            LinkedList<Character> clippedSequence = new LinkedList<>();
            Node<Character> currentNode = sequences[pos].getSequence().getHead();
            for (int i = 0; i <= end; i++) {
                if (i >= start) {
                    clippedSequence.addToTail(currentNode.data); // adds each node for the clipped sequence to the end of the list so that it ends up properly ordered
                }
                currentNode = currentNode.next;
            }
            sequences[pos].setSequence(clippedSequence);
        }
    }

    // copies a sequence at one position in the array to another position in the array
    public static void copy(int pos1, int pos2) {
        if (pos1 >= sequences.length || pos2 >= sequences.length) {
            System.out.println("Sequence index out of bounds");
        } else if (sequences[pos1] == null) {
            System.out.println("No sequence to copy at specified position");
        } else {
            LinkedList<Character> copiedSequence = new LinkedList<>();
            Node<Character> currentNode = sequences[pos1].getSequence().getHead();
            for (int i = 0; i < sequences[pos1].getSequence().size(); i++) {
                copiedSequence.addToTail(currentNode.data); // adds each node for the copied sequence to the end of the list so that it ends up properly ordered
                currentNode = currentNode.next;
            }
            sequences[pos2] = new Sequence(sequences[pos1].getType(), copiedSequence);
        }
    }

    // transcribes a DNA sequence at a given position in the array to an RNA sequence 
    // switches each letter in the sequence to its compliment and reverses the sequence
    public static void transcribe(int pos1) {
        if (pos1 >= sequences.length) {
            System.out.println("Sequence index out of bounds");
        } else if (sequences[pos1] == null) {
            System.out.println("No sequence to transcribe at specific position");
        } else if (sequences[pos1].getType() == Type.RNA) {
            System.out.println("Cannot Transcribe RNA");
        } else {
            LinkedList<Character> transcribedSequence = new LinkedList<>();
            Node<Character> currentNode = sequences[pos1].getSequence().getHead();
            for (int i = 0; i < sequences[pos1].getSequence().size(); i++) {
                
                // adds all tanscribed characters to the beginning of the list so that it ends up in reversed order from the original seqeunce
                switch(currentNode.data) {
                    case 'A':
                        transcribedSequence.addToHead('U');
                        break;
                    case 'C':
                        transcribedSequence.addToHead('G');
                        break;
                    case 'G':
                        transcribedSequence.addToHead('C');
                        break;
                    case 'T':
                        transcribedSequence.addToHead('A');
                        break;
                }

                currentNode = currentNode.next;
            }
            sequences[pos1] = new Sequence(Type.RNA, transcribedSequence);
        }
    }


    public static void main(String[] args) throws FileNotFoundException{
        
        sequences = new Sequence[Integer.parseInt(args[0])];
        Scanner scan = new Scanner(new File(args[1]));

        while (scan.hasNextLine()) {
            Scanner command = new Scanner(scan.nextLine());

            if (command.hasNext()) { // skips the line if the line is blank
                String operation  = command.next();

                // executes a given command from a line of the input file
                switch(operation) {
                    case "insert":
                        insert(command.nextInt(), command.next(), command.next());
                        break;
                    case "remove":
                        remove(command.nextInt());
                        break;
                    case "print":
                        if (command.hasNext()) {
                            printPos(command.nextInt());
                        } else {
                            print();
                        }
                        break;
                    case "clip":
                        clip(command.nextInt(), command.nextInt(), command.nextInt());
                        break;
                    case "copy":
                        copy(command.nextInt(), command.nextInt());
                        break;
                    case "transcribe":
                        transcribe(command.nextInt());
                        break;
                }
            }
            command.close();
        }
        scan.close();
    }
}
package Lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

enum Type {
    DNA, RNA
}

enum Used {
    USED, UNUSED
}

public class DNAList {

    static Sequence[] sequences;

    public static void insert(int pos, String type, String sequence) {
        LinkedList<IndexedChar> sequenceList = new LinkedList<>();
        Type sequenceType;

        Pattern DNAchars = Pattern.compile("A|C|G|T");
        Pattern RNAchars = Pattern.compile("A|C|G|U");

        if (type.equals("DNA")) {
            sequenceType = Type.DNA;
            for (int i = sequence.length() - 1; i >= 0; i--) {
                if (DNAchars.matcher(Character.toString(sequence.charAt(i))).matches()) {
                    sequenceList.addToHead(new IndexedChar(i, sequence.charAt(i)));
                } else {
                    System.out.println("Error occured while inserting");
                    return;
                }
            }
        } else if (type.equals("RNA")) {
            sequenceType = Type.RNA;
            for (int i = sequence.length() - 1; i >= 0; i--) {
                if (RNAchars.matcher(Character.toString(sequence.charAt(i))).matches()) {
                    sequenceList.addToHead(new IndexedChar(i, sequence.charAt(i)));
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

    public static void remove(int pos) {
        if (sequences[pos] != null) {
            sequences[pos] = null;
        } else {
            System.out.println("No sequence to remove at specified position");
        }
    }

    public static void print() {
        for (int i = 0; i < sequences.length; i++) {
            if (sequences[i] != null) {
                System.out.print(i + "\t" + sequences[i].getType() + "\t");
                sequences[i].getSequence().printList();
                System.out.println();
            }
        }
    }

    public static void printPos(int pos) {
        if (sequences[pos] != null) {
            System.out.print(sequences[pos].getType() + "\t");
            sequences[pos].getSequence().printList();
            System.out.println();
        } else {
            System.out.println("No sequence to print at specified position");
        }
    }

    public static void clip(int pos, int start, int end) {
        if (sequences[pos] == null) {
            System.out.println("No sequence to clip at specified position");
        } else if (start < 0) {
            System.out.println("Invalid start index");
        } else if (start > sequences[pos].getSequence().size()) {
            System.out.println("Start index out of bounds");
        } else if (end > sequences[pos].getSequence().size()) {
            System.out.println("End index out of bounds");
        } else {
            LinkedList<IndexedChar> clippedSequence = new LinkedList<>();
            Node<IndexedChar> currentNode = sequences[pos].getSequence().getHead();
            while(currentNode.data.getIndex() <= end) {
                if (currentNode.data.getIndex() >= start) {
                    clippedSequence.addToTail(currentNode.data);
                }

                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                } else {
                    break;
                }
            }
            sequences[pos].setSequence(clippedSequence);
        }
    }

    public static void copy(int pos1, int pos2) {
        if (sequences[pos1] != null) {
            LinkedList<IndexedChar> copiedSequence = new LinkedList<>();
            Node<IndexedChar> currentNode = sequences[pos1].getSequence().getHead();
            while(currentNode.data.getIndex() < sequences[pos1].getSequence().size()) {
                copiedSequence.addToTail(currentNode.data);

                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                } else {
                    break;
                }
            }
            sequences[pos2] = new Sequence(sequences[pos1].getType(), copiedSequence);
        } else {
            System.out.println("No sequence to copy at specified position");
        }
    }

    public static void transcribe(int pos1) {
        if (sequences[pos1] == null) {
            System.out.println("No sequence to transcribe at specific position");
        } else if (sequences[pos1].getType() == Type.RNA) {
            System.out.println("Cannot Transcribe RNA");
        } else {
            LinkedList<IndexedChar> transcribedSequence = new LinkedList<>();
            Node<IndexedChar> currentNode = sequences[pos1].getSequence().getHead();
            while(currentNode.data.getIndex() < sequences[pos1].getSequence().size()) {
                int index = sequences[pos1].getSequence().size() - currentNode.data.getIndex() - 1;
                switch(currentNode.data.getCharacter()) {
                    case 'A':
                        transcribedSequence.addToHead(new IndexedChar(index, 'U'));
                        break;
                    case 'C':
                        transcribedSequence.addToHead(new IndexedChar(index, 'G'));
                        break;
                    case 'G':
                        transcribedSequence.addToHead(new IndexedChar(index, 'C'));
                        break;
                    case 'T':
                        transcribedSequence.addToHead(new IndexedChar(index, 'A'));
                        break;
                }

                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                } else {
                    break;
                }
            }
            sequences[pos1] = new Sequence(Type.RNA, transcribedSequence);
        }
    }


    public static void main(String[] args) throws FileNotFoundException{
        sequences = new Sequence[Integer.parseInt(args[0])];
        Scanner scan = new Scanner(new File(args[1]));

        while (scan.hasNextLine()) {
            Scanner command = new Scanner(scan.nextLine());
            String operation  = command.next();

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

            command.close();
        }
        scan.close();

        // sequences = new Sequence[20];
        // insert(0, "DNA", "AATTCCGGAATTCCGG");
        // // insert(1, "RNA", "UAGACAUGGAUU");
        // print();
        // // copy(0, 1);
        // // clip(0, 2, 15);
        // transcribe(0);
        // print();
    }
}
package Lab4;

public class Sequence {

    private Type type;
    private LinkedList<Character> sequence;

    public Sequence(Type type, LinkedList<Character> sequence) {
        this.type = type;
        this.sequence = sequence;
    }

    public void setType(Type type) {
        this.type = type;
    }
    public void setSequence(LinkedList<Character> sequence) {
        this.sequence = sequence;
    }
    public Type getType() {
        return type;
    }
    public LinkedList<Character> getSequence() {
        return sequence;
    }
}
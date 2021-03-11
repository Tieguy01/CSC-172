package Lab4;

public class Sequence {

    private Type type;
    private LinkedList<IndexedChar> sequence;

    public Sequence(Type type, LinkedList<IndexedChar> sequence) {
        this.type = type;
        this.sequence = sequence;
    }

    public void setType(Type type) {
        this.type = type;
    }
    public void setSequence(LinkedList<IndexedChar> sequence) {
        this.sequence = sequence;
    }
    public Type getType() {
        return type;
    }
    public LinkedList<IndexedChar> getSequence() {
        return sequence;
    }
}
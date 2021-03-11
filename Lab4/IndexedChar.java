package Lab4;

public class IndexedChar {
    int index;
    char character;

    IndexedChar(int index, char character) {
        this.index = index;
        this.character = character;
    }

    public int getIndex() {
        return index;
    }
    public char getCharacter() {
        return character;
    }
    public void setCharacter(char character) {
        this.character = character;
    }

    public String toString() {
        return Character.toString(character);
    }
}
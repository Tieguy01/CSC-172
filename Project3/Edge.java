package Project3;

public class Edge implements Comparable<Edge>{

    private final String id;
    private final Node intersection1;
    private final Node intersection2;
    private final Double weight;

    public Edge (String id, Node int1ID, Node int2ID) {
        this.id = id;
        this.intersection1 = int1ID;
        this.intersection2 = int2ID;

        weight = Math.sqrt(
            Math.pow(Math.abs(int1ID.getLatitude() - int2ID.getLatitude()), 2) +
            Math.pow(Math.abs(int1ID.getLongitude() - int2ID.getLongitude()), 2));
    }

    public String getId() {
        return id;
    }

    public Node getEitherInt() {
        return intersection1;
    }
    
    public Node getOtherInt(Node intersection) {
        if (intersection.equals(intersection1)) return intersection2;
        else return intersection1;
    }

    public Double getWeight() {
        return weight;
    }

    public int compareTo(Edge that) {
        if (this.getWeight() < that.getWeight()) return -1;
        else if (this.getWeight() > that.getWeight()) return 1;
        else return 0;
    }
}
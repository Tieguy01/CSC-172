package Project3;

public class Edge implements Comparable<Edge>{

    private final String id;
    private final int index;
    private final Node intersection1;
    private final Node intersection2;
    private final Double weight;

    public Edge (String id, int index, Node int1ID, Node int2ID) {
        this.id = id;
        this.index = index;
        this.intersection1 = int1ID;
        this.intersection2 = int2ID;

        final double longMilesAtEquator = 69.172; // miles per degree of longitude at the equator
        final double latMilesConstant = 69; // ~ miles per degree of latitude

        double lat1Radians = Math.toRadians(int1ID.getLatitude());
        double long1MilesConstant = Math.cos(lat1Radians) * longMilesAtEquator;

        double lat2Radians = Math.toRadians(int2ID.getLatitude());
        double long2MilesConstant = Math.cos(lat2Radians) * longMilesAtEquator;

        double latDiff = (int1ID.getLatitude() - int2ID.getLatitude()) * latMilesConstant;
        double longDiff = (int1ID.getLongitude() * long1MilesConstant) - (int2ID.getLongitude() * long2MilesConstant);

        weight = Math.sqrt(Math.pow(latDiff, 2) + Math.pow(longDiff, 2));
    }

    public String getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public Node getEitherIntersection() {
        return intersection1;
    }
    
    public Node getOtherIntersection(Node intersection) {
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
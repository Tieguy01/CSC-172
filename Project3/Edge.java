package Project3;

// class representing an edge in a graph
public class Edge implements Comparable<Edge>{

    // each edge has an ID, two vertices that it connects, and a weight that indicates its length in miles
    private final String ID;
    private final Node intersection1;
    private final Node intersection2;
    private final Double weight;

    public Edge (String ID, Node intersection1, Node intersection2) {
        this.ID = ID;
        this.intersection1 = intersection1;
        this.intersection2 = intersection2;

        // calculations to covert latitude and longitude coordinates of an edge's adjacent vertices to the length of the edge in miles
        final double longMilesAtEquator = 69.172; // miles per degree of longitude at the equator
        final double latMilesConstant = 69; // ~ miles per degree of latitude

        double lat1Radians = Math.toRadians(intersection1.getLatitude());
        double long1MilesConstant = Math.cos(lat1Radians) * longMilesAtEquator; // miles constant used for intersection 1 at its given latitude

        double lat2Radians = Math.toRadians(intersection2.getLatitude());
        double long2MilesConstant = Math.cos(lat2Radians) * longMilesAtEquator; // // miles constant used for intersection 2 at its given latitude

        double latDiff = (intersection1.getLatitude() - intersection2.getLatitude()) * latMilesConstant; // difference in latitude between the two intersection
        double longDiff = (intersection1.getLongitude() * long1MilesConstant) - (intersection2.getLongitude() * long2MilesConstant); // difference in longitude between the two intersection

        weight = Math.sqrt(Math.pow(latDiff, 2) + Math.pow(longDiff, 2)); // calculates the length of the edge in miles
    }


    // returns the edges ID
    public String getID() {
        return ID;
    }

    // returns one of the vertices that the edge connects
    public Node getEither() {
        return intersection1;
    }
    
    // returns the other vertex the edge connects given one of the edges vertices
    public Node getOther(Node intersection) {
        if (intersection.equals(intersection1)) return intersection2;
        else return intersection1;
    }

    // returns the length of the edge in miles
    public Double getWeight() {
        return weight;
    }

    // method to compare this edge with another given edge
    // returns -1 if this edge is shorter than the given edge
    // returns 1 if this edge is longer than the given edge
    // returns 0 if the edges are the same length
    public int compareTo(Edge that) {
        if (this.getWeight() < that.getWeight()) return -1;
        else if (this.getWeight() > that.getWeight()) return 1;
        else return 0;
    }
}
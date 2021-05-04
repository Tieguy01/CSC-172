/**
 * @author Ethan Shahan
 */

package Project3;

// class representing a vertex in a graph
public class Node {

    // each vertex node has its own ID, index, latitude and longitude coordinates, and a Bag containing the edges connected to it
    private final String ID;
    private final int index;
    private final double latitude;
    private final double longitude;
    Bag<Edge> adj;

    public Node(String ID, int index, double latitude, double longitude) {
        this.ID = ID;
        this.index = index;
        this.latitude = latitude;
        this.longitude = longitude;
        adj = new Bag<>();
    }

    // adds an edge to the node's Bag of adjecent edges
    public void addAdj(Edge edge) {
        adj.add(edge);
    }

    public String getID() {
        return ID;
    }

    public int getIndex() {
        return index;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Bag<Edge> getAdj() {
        return adj;
    }

}
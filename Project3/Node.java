package Project3;

public class Node {

    private final String ID;
    private final int index;
    private final double latitude;
    private final double longitude;
    Bag<Edge> adj;

    boolean marked;

    public Node(String ID, int index, double latitude, double longitude) {
        this.ID = ID;
        this.index = index;
        // this.latitude = Math.abs(((1000000 * latitude) % 100000));
        // this.longitude = Math.abs(((1000000 * longitude) % 100000));
        this.latitude = latitude;
        this.longitude = longitude;
        adj = new Bag<>();

        marked = false;
    }

    public void addAdj(Edge edge) {
        adj.add(edge);
    }

    public void unmarkEdges() {
        for (Edge e : adj) {
            e.marked = false;
        }
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
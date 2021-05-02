package Project3;

public class ConnectedComponents {

    private boolean[] marked;
    private int[] components;
    private int numComponents;

    public ConnectedComponents(Graph map) {
        marked = new boolean[map.getNumIntersections()];
        components = new int[map.getNumIntersections()];

        for (String s : map.getKeys()) {
            Node v = map.getIntersections().get(s);
            if (!marked[v.getIndex()]) {
                depthFirstSearch(v);
                numComponents++;
            }
        }
    }

    private void depthFirstSearch(Node v) {
        marked[v.getIndex()] = true;
        components[v.getIndex()] = numComponents;
        for (Edge e : v.getAdj()) {
            v = e.getOtherIntersection(v);
            if (!marked[v.getIndex()]) {
                // System.out.println(w.getID());
                depthFirstSearch(v);
            }
        }
    }

    public boolean connected(Node v, Node w) {
        return components[v.getIndex()] == components[w.getIndex()];
    }

    public int getNumComponents() {
        return numComponents;
    }
}
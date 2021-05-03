package Project3;

public class ConnectedComponents {

    private boolean[] marked;
    private int[] components;
    private int numComponents;

    private Queue<Node> queue;

    public ConnectedComponents(Graph map) {
        marked = new boolean[map.getNumIntersections()];
        components = new int[map.getNumIntersections()];

        queue = new Queue<Node>();

        for (String s : map.getKeys()) {
            Node v = map.getIntersections().get(s);
            if (!marked[v.getIndex()]) {
                breadthFirstSearch(v);
                numComponents++;
            }
        }
    }

    private void breadthFirstSearch(Node v) {
        marked[v.getIndex()] = true;
        components[v.getIndex()] = numComponents;
        queue.enqueue(v);

        while (!queue.isEmpty()) {
            Node n = queue.dequeue();
            for (Edge e : n.getAdj()) {
                Node w = e.getOtherIntersection(n);
                if (!marked[w.getIndex()]) {
                    marked[w.getIndex()] = true;
                    components[w.getIndex()] = numComponents;
                    queue.enqueue(w);
                }
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
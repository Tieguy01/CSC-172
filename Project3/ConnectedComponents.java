package Project3;

// class used to assign identify connected compenents in a graph and the nodes contained within them
public class ConnectedComponents {

    private boolean[] marked; // used to keep track of what nodes have been searched
    private int[] components; // used to keep track of which nodes are in which connected components
    private int componentIndex; // used to identify which connected component a vertex is in
    
    private Queue<Node> queue; // used to temporarily store nodes while they are being searched

    public ConnectedComponents(Graph g) {
        marked = new boolean[g.getNumVertices()];
        components = new int[g.getNumVertices()];
        queue = new Queue<Node>();

        // for every intersection in the map, if an intersection has not yet been searched, 
        // search to find all the vertices connected to it and update the current component index
        for (String s : g.getKeys()) {
            Node v = g.getVertices().get(s);
            if (!marked[v.getIndex()]) {
                breadthFirstSearch(v);
                componentIndex++;
            }
        }
    }

    // used to find all the vertices in a connected component
    private void breadthFirstSearch(Node v) {
        marked[v.getIndex()] = true; // marks the initial vertex as searched
        components[v.getIndex()] = componentIndex; // assigns the component of the initial vertex to the current component index
        queue.enqueue(v); // puts the initial vertex in a queue

        // while there are still vertices in the queue, take a vertex out,
        // and for each vertex adjencent to it, if that vertex has not yet been searched,
        // mark it as searched, assign its component to the current component index, and add it to the queue
        while (!queue.isEmpty()) {
            Node n = queue.dequeue();
            for (Edge e : n.getAdj()) {
                Node w = e.getOther(n);
                if (!marked[w.getIndex()]) {
                    marked[w.getIndex()] = true;
                    components[w.getIndex()] = componentIndex;
                    queue.enqueue(w);
                }
            }
        }
        
    }

    // used to determine if two vertices are in the same connected component
    public boolean connected(Node v, Node w) {
        return components[v.getIndex()] == components[w.getIndex()];
    }
}
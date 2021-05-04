/**
 * @author Ethan Shahan
 */

package Project3;

// class used to construct a minimum spanning tree for a given graph, implements Kruskal's MST algorithm
public class MinimumSpanningTree {
 
    // class used to store sets of connected vertices
    private class VertexSet {

        VertexSet set; // used to store what set a certain vertex is in
        private Bag<Node> nodes; // Bag containing all the vertices in the set
        int length; // length of the set

        public VertexSet() {
            nodes = new Bag<>();
            length = 0;
        }

        // adds a vertex node to the set
        public void addNode(Node n) {
            nodes.add(n);
            length++;
        }
    }

    private PriorityQueue<Edge> edgePQ; // priority queue of all the edges in a graph sorted based on their weights
    private VertexSet[] vertexSets; // array of sets for every vertex in a graph
    private Bag<Edge> mst; // bag of edges used to store all the edges in the final mst

    public MinimumSpanningTree(Graph map) {
        edgePQ = new PriorityQueue<Edge>(map.getNumEdges());
        vertexSets = new VertexSet[map.getNumVertices()];
        mst = new Bag<>();

        // puts every edge in the graph into the priority queue
        for (Edge e : map.getEdges()) {
            edgePQ.enqueue(e);
        }

        // creates a new set of vertices for every vertex in the graph, assigns its set to itself, and adds its correspondig vertex to its set
        for (String s : map.getKeys()) {
            Node v = map.getVertices().get(s);
            vertexSets[v.getIndex()] = new VertexSet();
            vertexSets[v.getIndex()].set = vertexSets[v.getIndex()];
            vertexSets[v.getIndex()].addNode(v);
        }

        int vertexSetLength = 1; // used to keep track of the number of vertices in the largest set of vertices

        // loops until there are no edges left in the priority queue or the number of vertices in the largest set of vertices is >= the number of vertices in the graph
        while (!edgePQ.isEmpty() && vertexSetLength < map.getNumVertices()) {
            Edge e = edgePQ.delMin(); // gets the edge with the minimum weight from the priority queue

            // nodes for both the vertices the edge connects
            Node v = e.getEither();
            Node w = e.getOther(v);

            // sets containing each of the vertices the edge connects
            VertexSet vSet = vertexSets[v.getIndex()].set;
            VertexSet wSet = vertexSets[w.getIndex()].set;

            // if the two vertices are not in the same set
            if (!vSet.equals(wSet)) {
                VertexSet parentSet;
                VertexSet lesserSet;

                if (vSet.length > wSet.length) {
                    parentSet = vSet;
                    lesserSet = wSet;
                } else {
                    parentSet = wSet;
                    lesserSet = vSet;
                }

                // every vertex in the smaller set is added to the larger set
                for (Node n : lesserSet.set.nodes) {
                    vertexSets[n.getIndex()].set = parentSet;
                    parentSet.addNode(n);
                }
                lesserSet = null; // smaller set set to null

                // if the length of the larger set is larger than the largest set length, then the largest set length is set to the length of the larger set
                if (parentSet.length > vertexSetLength) vertexSetLength = parentSet.length;

                // the edge is added to the mst
                mst.add(e);
            }
        }
    }

    // returns the Bag of edges representing the minimum spanning tree
    public Bag<Edge> getMST() {
        return mst;
    }

    // prints all the edges in the mst
    public void printMST() {
        System.out.println("Minimum Weight Spanning Tree: ");
        for (Edge e : mst) {
            System.out.println("\t\t\t     " + e.getID());
        }
    }
}
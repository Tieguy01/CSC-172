package Project3;

public class MinimumSpanningTree {

    private class SetNode {

        SetNode set;
        private Node node;

        public void setNode(Node node) {
            this.node = node;
        }

        public Node getNode() {
            return node;
        }
    }

    private PriorityQueue<Edge> edgePQ;
    private SetNode[] vertexSet;
    private Bag<Edge> mst;

    public MinimumSpanningTree(Graph map) {
        edgePQ = new PriorityQueue<Edge>(map.getNumRoads());
        vertexSet = new SetNode[map.getNumIntersections()];
        mst = new Bag<>();

        for (Edge e : map.getRoads()) {
            edgePQ.enqueue(e);
        }

        for (int i = 0; i < map.getNumIntersections(); i++) {
            vertexSet[i] = new SetNode();
            vertexSet[i].set = vertexSet[i];
        }

        while (!edgePQ.isEmpty() && mst.size() < map.getNumIntersections() - 1) {
            Edge e = edgePQ.delMin();
            Node v = e.getEitherIntersection();
            Node w = e.getOtherIntersection(v);
            SetNode vSet = vertexSet[v.getIndex()].set;
            SetNode wSet = vertexSet[w.getIndex()].set;
            if (!vSet.equals(wSet)) {
                vertexSet[w.getIndex()].set = vSet;
                mst.add(e);
            }
        }
    }

    public Bag<Edge> getMST() {
        return mst;
    }

    public void printMST() {
        System.out.println("Minimum Weight Spanning Tree: ");
        for (Edge e : mst) {
            System.out.println("\t\t\t     " + e.getId());
        }
    }
}
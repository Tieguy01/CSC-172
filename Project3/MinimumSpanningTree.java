package Project3;

public class MinimumSpanningTree {

    private class SetNode {

        SetNode set;
        private Bag<Node> nodes;
        int length;

        public SetNode() {
            nodes = new Bag<>();
            length = 1;
        }

        public void addNode(Node n) {
            nodes.add(n);
            length++;
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

        int vertexSetLength = 1;

        while (!edgePQ.isEmpty() && vertexSetLength < map.getNumIntersections()) {
            Edge e = edgePQ.delMin();
            Node v = e.getEitherIntersection();
            Node w = e.getOtherIntersection(v);

            SetNode vSet = vertexSet[v.getIndex()].set;
            SetNode wSet = vertexSet[w.getIndex()].set;

            if (vSet.nodes.size() == 0) vSet.addNode(v);
            if (wSet.nodes.size() == 0) wSet.addNode(w);

            if (!vSet.equals(wSet)) {
                SetNode parentSet;
                SetNode lesserSet;

                if (vSet.length > wSet.length) {
                    parentSet = vSet;
                    lesserSet = wSet;
                } else {
                    parentSet = wSet;
                    lesserSet = vSet;
                }

                for (Node n : lesserSet.set.nodes) {
                    // System.out.println("n: " + n.getID());
                    vertexSet[n.getIndex()].set = parentSet;
                    parentSet.addNode(n);
                }
                lesserSet = null;

                vertexSetLength = parentSet.length;
                // System.out.println("parentSet: " + vertexSetLength);

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
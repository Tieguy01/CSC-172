package Project3;

public class ShortestPath {

    private class DistanceNode implements Comparable<DistanceNode> {

        double distanceTo;
        Node predecessor;
        private Node node;

        public int compareTo(DistanceNode that) {
            if (this.distanceTo < that.distanceTo) return -1;
            else if (this.distanceTo > that.distanceTo) return 1;
            else return 0;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        public Node getNode() {
            return node;
        }
    }


    private DistanceNode[] distanceNodes;
    private boolean[] known;
    private PriorityQueue<DistanceNode> pq;
    private LinkedList<Node> path;
    private double pathDistance;

    public ShortestPath(Graph map, String start, String target) {
        distanceNodes = new DistanceNode[map.getNumIntersections()];
        known = new boolean[map.getNumIntersections()];
        pq = new PriorityQueue<DistanceNode>(map.getNumIntersections());
        path = new LinkedList<Node>();

        Node startIntersection = map.getIntersections().get(start);
        Node targetIntersection = map.getIntersections().get(target);


        for (int i = 0; i < map.getNumIntersections(); i++) {
            distanceNodes[i] = new DistanceNode();
            distanceNodes[i].distanceTo = Double.POSITIVE_INFINITY;
        }

        distanceNodes[startIntersection.getIndex()].distanceTo = 0.0;
        distanceNodes[startIntersection.getIndex()].setNode(startIntersection);
        pq.enqueue(distanceNodes[startIntersection.getIndex()]);
        findShortestPath(targetIntersection);
        createPath(targetIntersection);
        pathDistance = distanceNodes[targetIntersection.getIndex()].distanceTo;
    }

    private void findShortestPath(Node targetIntersection) {
        while (!known[targetIntersection.getIndex()]) {
            Node v = pq.delMin().getNode();
            known[v.getIndex()] = true;
            for (Edge e : v.getAdj()) {
                Node w = e.getOtherInt(v);
                if (!known[w.getIndex()]) {
                    double distanceToV = distanceNodes[v.getIndex()].distanceTo;
                    double distanceToW = distanceNodes[w.getIndex()].distanceTo;
                    if (distanceToV + e.getWeight() < distanceToW) {
                        distanceNodes[w.getIndex()].distanceTo = distanceToV + e.getWeight();
                        distanceNodes[w.getIndex()].predecessor = v;
                        distanceNodes[w.getIndex()].setNode(w); 
                        pq.enqueue(distanceNodes[w.getIndex()]);
                    }
                }
            }
        }
    }

    private void createPath(Node v) {
        if (!(distanceNodes[v.getIndex()].predecessor == null)) {
            path.addToHead(distanceNodes[v.getIndex()].getNode());
            createPath(distanceNodes[v.getIndex()].predecessor);
        } else {
            path.addToHead(distanceNodes[v.getIndex()].getNode());
        }
    }

    public LinkedList<Node> getPath() {
        return path;
    }

    public void printPath() {
        System.out.print("Path: ");
        path.printList();
        System.out.println();
        System.out.println("Distance: " + pathDistance);
    }
}
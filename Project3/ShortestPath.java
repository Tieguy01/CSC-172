package Project3;

import java.util.Iterator;

// class used to construct the shortest path for two given vertices in a graph, implements Dijkstra's shortest paths algorithm
public class ShortestPath {

    // class used to store distance information for a vertex
    private class DistanceNode implements Comparable<DistanceNode> {

        double distanceTo; // current found distance the DistanceNode vertex is away from a certain start vertex
        Node predecessor; // the vertex that currently preceeds the DistanceNode vertex to achieve its current distanceTo
        private Node node; // the vertex the DistanceNode vertex corresponds to 

        // method used to compare two DistanceNodes
        // returns -1 if this DistanceNode has a smaller distanceTo than the given DistanceNode
        // returns 1 if this DistanceNode has a greater distanceTo than the given DistanceNode
        // returns 0 if the DistanceNodes have the same distanceTo
        public int compareTo(DistanceNode that) {
            if (this.distanceTo < that.distanceTo) return -1;
            else if (this.distanceTo > that.distanceTo) return 1;
            else return 0;
        }

        // sets the vertex the DistanceNode corresponds to
        public void setNode(Node node) {
            this.node = node;
        }

        // returns the vertex the DistanceNode corresponds to
        public Node getNode() {
            return node;
        }
    }

    private DistanceNode[] distanceNodes; // array of DistanceNodes for every vertex in the graph
    private boolean[] known; // array that stores whether the shortest path to any given vertex in the graph has been found 
    private PriorityQueue<DistanceNode> pq; // priority queue of all the DistanceNodes sorted based on their current distanceTo the given start vertex
    private Bag<Node> path; // bag of nodes used to store all the vertices in the final shortest path found
    private double pathDistance; // the distance in miles of the shortest path found

    public ShortestPath(Graph map, String start, String target) {
        distanceNodes = new DistanceNode[map.getNumVertices()];
        known = new boolean[map.getNumVertices()];
        pq = new PriorityQueue<DistanceNode>(map.getNumVertices());
        path = new Bag<Node>();

        // nodes of the given start and end vertices
        Node startVertex = map.getVertices().get(start);
        Node targetVertex = map.getVertices().get(target);

        // find what vertices are in each connected component of the given graph
        ConnectedComponents cc = new ConnectedComponents(map);

        // only tries to find a path between the two vertices if they are in the same connected component
        if (cc.connected(startVertex, targetVertex)) {

            // for every vertex in the graph, a new DistanceNode is created, its distanceTo the start vertex is set to infinity, and its node it set to its corresponding vertex
            for (String s : map.getKeys()) {
                Node v = map.getVertices().get(s);
                distanceNodes[v.getIndex()] = new DistanceNode();
                distanceNodes[v.getIndex()].distanceTo = Double.POSITIVE_INFINITY;
                distanceNodes[v.getIndex()].setNode(v);
            }

            // the distanceTo for the start vertex's DistanceNode is set to 0 and the Distance Node is added to the priority queue
            distanceNodes[startVertex.getIndex()].distanceTo = 0.0; 
            pq.enqueue(distanceNodes[startVertex.getIndex()]);

            findShortestPath(targetVertex); // the shortest path between the start and end index is found
            createPath(targetVertex); // the path is creates into a bag of vetices in the correct order
            pathDistance = distanceNodes[targetVertex.getIndex()].distanceTo; // the distance of the found path is set the the final found distanceTo of the end vertex
        
        } else {
            // if the two given vertices are not in the same connected components, the path is set to null and the path's distance is set to -1
            path = null;
            pathDistance = -1;
        }
    }

    // method to find the shortest path from a start vertex to a target vertex
    private void findShortestPath(Node targetIntersection) {

        // loops until the shortest path from the start vertex to the target vertex is found
        while (!known[targetIntersection.getIndex()]) {
            
            // sets vertex v to the vertex with the smallest distanceTo the start vertex in the priority queue
            Node v = null;
            if (!pq.isEmpty()) v = pq.delMin().getNode();
            else {
                System.out.println("target not found");
                break;
            }

            // marks the shortest path from the start vertex to the current vertex as known
            known[v.getIndex()] = true;

            // for every vertex adjacent to the current vertex, if that vertex is not known,
            // if the current vertex's distanceTo the start vertex plus its distance to its adjacent vertex is less than its adjacent vertex's current distanceTo the start vertex,
            // set the adjacent vertex's distanceTo the start vertex to the current vertex's distanceTo the start vertex plus its distance to its adjacent vertex,
            // set the predecessor of the adjacent vertex to the current vertex,
            // and add the adjacent vertex to the priority queue
            for (Edge e : v.getAdj()) {
                Node w = e.getOther(v);
                if (!known[w.getIndex()]) {
                    double distanceToV = distanceNodes[v.getIndex()].distanceTo;
                    double distanceToW = distanceNodes[w.getIndex()].distanceTo;
                    if (distanceToV + e.getWeight() < distanceToW) {
                        distanceNodes[w.getIndex()].distanceTo = distanceToV + e.getWeight();
                        distanceNodes[w.getIndex()].predecessor = v;
                        pq.enqueue(distanceNodes[w.getIndex()]);
                    }
                }
            }
        }
    }

    // method that creates a path from the found vertices that lead from the start vertex to the target vertex in the smallest distance
    private void createPath(Node v) {
        // given a vertex v, add v to the path
        path.add(distanceNodes[v.getIndex()].getNode());

        // if v had a predecessor, recursively add it to the path too
        if (!(distanceNodes[v.getIndex()].predecessor == null)) {
            createPath(distanceNodes[v.getIndex()].predecessor);
        } 
    }

    // returns a Bag of vertices representing the shortest path
    public Bag<Node> getPath() {
        return path;
    }

    // prints all the vertices in the path and the distance of the path,
    // unless no path was able to be created, in which case an error is printed
    public void printPath() {
        if (path != null) {
            Iterator<Node> it = path.iterator();
            System.out.print("start: ");
            while (it.hasNext()) {
                Node v = it.next();
                if (!it.hasNext()) System.out.print("  end:  ");
                else System.out.print("\t");
                System.out.println(v.getID());
            }
            System.out.println("Distance: " + String.format("%.3f", pathDistance) + " miles");
        } else {
            System.out.println("Intersections not connected");
        }
    }
}
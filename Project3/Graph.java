/**
 * @author Ethan Shahan
 */

package Project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

// class to create a store a graph from a given map file
public class Graph {

    private Hashtable<String, Node> vertices; // stores vertices in the graph where the key in an intersection identifier and the value is an intersection node
    private Bag<Edge> edges; // stores the edges of the graph 

    // used tp keep track of the minimum and maximum coordinates for vertices in the graph
    private double minLat;
    private double minLong;
    private double maxLat;
    private double maxLong;

    public Graph(String graphFile) throws FileNotFoundException {
        vertices = new Hashtable<>();
        edges = new Bag<>();

        minLat = Integer.MAX_VALUE;
        minLong = Integer.MAX_VALUE;
        maxLat = Integer.MIN_VALUE;
        maxLong = Integer.MIN_VALUE;

        Scanner scan = new Scanner(new File(graphFile)); // scanner to scan through an entire map file

        int i = 0; // used to assign indices to the vertices

        // while loop constructs a graph based on the data from a given map file
        while (scan.hasNextLine()) {
            String nextLine = scan.nextLine();

            // scanner to scan through a single line on a map file delimited with tabs
            Scanner lineScan = new Scanner(nextLine);
            lineScan.useDelimiter("\t");

            String type = lineScan.next(); // stores whether a given line of a file includes data for a road or an intersection

            // for every intersection listed in the file, a new vertex is created and added to the Hashtable and the min/max values are updated
            if (type.equals("i")) {
                String intersectionID = lineScan.next();
                Double latitude = Double.parseDouble(lineScan.next());
                Double longitude = Double.parseDouble(lineScan.next());
                vertices.put(intersectionID, new Node(intersectionID, i, latitude, longitude));

                if (latitude < minLat) minLat = latitude;
                if (longitude < minLong) minLong = longitude;
                if (latitude > maxLat) maxLat = latitude;
                if (longitude > maxLong) maxLong = longitude;
                i++;
            }

            // for every road listed in the file, a new edge is created, added to the Bag,
            // and added to the adjecency lists of each vertex it connects
            if (type.equals("r")) {
                String roadID = lineScan.next();
                String intersection1ID = lineScan.next();
                String intersection2ID  = lineScan.next();

                Edge newEdge = new Edge(roadID, vertices.get(intersection1ID), vertices.get(intersection2ID));
                edges.add(newEdge);
                vertices.get(intersection1ID).addAdj(newEdge);
                vertices.get(intersection2ID).addAdj(newEdge);
            }
            lineScan.close();
        }

        // used for debugging
        // System.out.println("minLat: " + minLat + "    maxLat: " + maxLat);
        // System.out.println("minLong: " + minLong + "    maxLong: " + maxLong);

        scan.close();
    }

    // returns the number of vertices
    public int getNumVertices() {
        return vertices.size();
    }

    // returns the number of edges
    public int getNumEdges() {
        return edges.size();
    }

    // returns a set of the keys for the vertices in order to be able to iterate throught the Hashtable
    public Iterable<String> getKeys() {
        return vertices.keySet();
    }

    // returns the Hashtable storing the vertices
    public Hashtable<String, Node> getVertices() {
        return vertices;
    }

    // returns the Bag containing the edges
    public Bag<Edge> getEdges() {
        return edges;
    }

    // methods to return the min/max vertex coordinates
    public double getMinLat() {
        return minLat;
    }
    public double getMinLong() {
        return minLong;
    }
    public double getMaxLat() {
        return maxLat;
    }
    public double getMaxlong() {
        return maxLong;
    }

    // prints an adjencency list of the vertices, used for debugging
    public void printGraph() {
        for (String s : vertices.keySet()) {
            System.out.print(s + ": ");
            for (Edge e : vertices.get(s).getAdj()) {
                System.out.print(e.getOther(vertices.get(s)).getID() + " ");
            }
            System.out.println();
        }
    }

    // prints all the data for each vertex in a graph, used for debugging
    public void printIntersections() {
        for (String s : vertices.keySet()) {
            Node v = vertices.get(s);
            System.out.println(s + " " + v.getIndex() + " " + v.getLatitude() + " " + v.getLongitude());
        }
    }
}
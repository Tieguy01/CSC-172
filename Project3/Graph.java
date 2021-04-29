package Project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Graph {

    // private Hashtable<Integer, String> adj;
    private Hashtable<String, Node> intersections;

    private String head;

    private double minLat;
    private double minLong;
    private double maxLat;
    private double maxLong;

    private int numEdges;

    public Graph(String mapFile) throws FileNotFoundException {
        // adj = new Hashtable<>();
        intersections = new Hashtable<>();
        Scanner scan = new Scanner(new File(mapFile));

        minLat = Integer.MAX_VALUE;
        minLong = Integer.MAX_VALUE;
        maxLat = Integer.MIN_VALUE;
        maxLong = Integer.MIN_VALUE;

        int i = 0;
        int j = 0;
        boolean firstLine = true;
        while (scan.hasNextLine()) {
            String nextLine = scan.nextLine();
            Scanner lineScan = new Scanner(nextLine);
            lineScan.useDelimiter("\t");
            String type = lineScan.next();

            if (type.equals("i")) {
                String intersectionID = lineScan.next();
                if (firstLine) {
                    head = intersectionID;
                    firstLine = false;
                }
                Double latitude = Double.parseDouble(lineScan.next());
                Double longitude = Double.parseDouble(lineScan.next());
                intersections.put(intersectionID, new Node(intersectionID, i, latitude, longitude));
                // adj.put(i, intersectionID);
                if (latitude < minLat) minLat = latitude;
                if (longitude < minLong) minLong = longitude;
                if (latitude > maxLat) maxLat = latitude;
                if (longitude > maxLong) maxLong = longitude;
                i++;
            }

            if (type.equals("r")) {
                String rId = lineScan.next();
                String int1ID = lineScan.next();
                String int2ID  = lineScan.next();

                Edge newEdge = new Edge(rId, j, intersections.get(int1ID), intersections.get(int2ID));
                intersections.get(int1ID).addAdj(newEdge);
                intersections.get(int2ID).addAdj(newEdge);
                j++;
            }
            lineScan.close();
        }
        numEdges = j;

        // System.out.println("minLat: " + minLat + "    maxLat: " + maxLat);
        // System.out.println("minLong: " + minLong + "    maxLong: " + maxLong);

        scan.close();
    }

    public int getNumIntersections() {
        return intersections.size();
    }

    public int getNumEdges() {
        return numEdges;
    }

    // public Hashtable<Integer, String> getAdj() {
    //     return adj;
    // }

    public Hashtable<String, Node> getIntersections() {
        return intersections;
    }

    // public Iterable<Edge> getAdjRoads(int intersectionID) {
    //     return getAdjRoads(adj.get(intersectionID));
    // }

    public Iterable<Edge> getAdjRoads(String intersectionID) {
        return intersections.get(intersectionID).getAdj();
    }

    // public Node getNodeFromIndex(int index) {
    //     return intersections.get(adj.get(index));
    // }

    // public void unmarkIntersections() {
    //     for (String s : intersections.keySet()) {
    //         intersections.get(s).marked = false;
    //         intersections.get(s).unmarkEdges();
    //     }
    // }

    public void printGraph() {
        for (String s : intersections.keySet()) {
            System.out.print(s + ": ");
            for (Edge e : getAdjRoads(s)) {
                System.out.print(e.getOtherInt(intersections.get(s)).getID() + " ");
            }
            System.out.println();
        }
    }

    public void printIntersections() {
        for (String s : intersections.keySet()) {
            Node intersection = intersections.get(s);
            System.out.println(s + " " + intersection.getIndex() + " " + intersection.getLatitude() + " " + intersection.getLongitude());
        }
    }

    public Node getHeadNode() {
        return intersections.get(head);
    }

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
}
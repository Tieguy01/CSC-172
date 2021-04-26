package Project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Graph {

    private Hashtable<Integer, String> adj;
    private Hashtable<String, Node> intersections;

    private double minLat;
    private double minLong;
    private double maxLat;
    private double maxLong;

    public Graph(String mapFile) throws FileNotFoundException {
        adj = new Hashtable<>();
        intersections = new Hashtable<>();
        Scanner scan = new Scanner(new File(mapFile));

        int i = 0;
        minLat = Double.MAX_VALUE;
        minLong = Double.MAX_VALUE;
        maxLat = Double.MIN_VALUE;
        maxLong = Double.MIN_VALUE;
        while (scan.hasNextLine()) {
            String nextLine = scan.nextLine();
            Scanner lineScan = new Scanner(nextLine);
            lineScan.useDelimiter("\t");
            String type = lineScan.next();

            if (type.equals("i")) {
                String intersectionID = lineScan.next();
                intersections.put(intersectionID, new Node(intersectionID, i, Double.parseDouble(lineScan.next()), Double.parseDouble(lineScan.next())));
                adj.put(i, intersectionID);
                if (getNodeFromIndex(i).getLatitude() < minLat) minLat = getNodeFromIndex(i).getLatitude();
                if (getNodeFromIndex(i).getLongitude() < minLong) minLong = getNodeFromIndex(i).getLongitude();
                if (getNodeFromIndex(i).getLatitude() > maxLat) maxLat = getNodeFromIndex(i).getLatitude();
                if (getNodeFromIndex(i).getLongitude() > maxLong) maxLong = getNodeFromIndex(i).getLongitude();
                i++;
            }

            if (type.equals("r")) {
                String rId = lineScan.next();
                String int1ID = lineScan.next();
                String int2ID  = lineScan.next();

                Edge newEdge = new Edge(rId, intersections.get(int1ID), intersections.get(int2ID));
                intersections.get(int1ID).addAdj(newEdge);
                intersections.get(int2ID).addAdj(newEdge);
            }
            lineScan.close();
        }
        scan.close();
    }

    public int getNumIntersections() {
        return intersections.size();
    }

    public Hashtable<Integer, String> getAdj() {
        return adj;
    }

    public Hashtable<String, Node> getIntersections() {
        return intersections;
    }

    public Iterable<Edge> getAdjRoads(int intersectionID) {
        return getAdjRoads(adj.get(intersectionID));
    }

    public Iterable<Edge> getAdjRoads(String intersectionID) {
        return intersections.get(intersectionID).getAdj();
    }

    public Node getNodeFromIndex(int index) {
        return intersections.get(adj.get(index));
    }

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
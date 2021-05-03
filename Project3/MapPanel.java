package Project3;

import java.awt.*;
import java.awt.geom.*;
import java.util.Iterator;

import javax.swing.JPanel;

// Panel component used to contain map drawings
public class MapPanel extends JPanel {

    private static final long serialVersionUID = 0; // auto generated for JPanel

    private Graphics2D g2D;

    private Graph map;
    private Bag<Node> path; // left null if constructor without path used
    private Bag<Edge> mst; // left null if constructor without mst used
    
    // constructor used for just drawing a map
    public MapPanel(Graph map) {
        this.map = map;
    }

    // constructor used for drawing a map and a path connecting two points on the map
    public MapPanel(Graph map, Bag<Node> path) {
        this.map = map;
        this.path = path;
    }

    // constructor used for drawing a map and a minimum spanning tree of the map
    public MapPanel(Graph map, Bag<Edge> mst, boolean isMST) {
        this.map = map;
        this.mst = mst;
    }

    // method called automatically when panel created or changed
    // used to draw the map and any other specified features
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);

        drawEdgeSet(map.getEdges(), 3); // draws a map on the panel from the given graph

        if (path != null) drawPath(path); // draws path if a path was given in the constructor
        if (mst != null) drawMST(mst); // draws a minimum spanning tree if an mst was given in the constructor
    }

    // method used to draw a path on the panel from a given path between two points
    private void drawPath(Bag<Node> path) {
        // only draws the path if it actually exists
        if (path != null) {

            // graphics set to different color and stroke width to differentiate the path from the rest of the map
            g2D.setColor(Color.RED);
            g2D.setStroke(new BasicStroke(2));

            Iterator<Node> it = path.iterator(); // iterator used to iterate through the vertices connecting the path

            Node v = it.next(); // intial vertice on the path

            // for each vertex in the path, 
            // the coordinates of the vertex are calculated, the vertex is drawn on the map,
            // and if there is another vertex after it, 
            // the coordinates for that vertex are calculate, and a line is drawn between them
            while (true) {
                Point.Double vPoint = calcCoordinates(v);
                double vX = vPoint.getX();
                double vY = vPoint.getY();

                g2D.fill(new Ellipse2D.Double(vX - 2.5, vY - 2.5, 5, 5));

                if (it.hasNext()) {
                    Node w = it.next();
                    Point.Double wPoint = calcCoordinates(w);
                    double wX = wPoint.getX();
                    double wY = wPoint.getY();

                    g2D.draw(new Line2D.Double(vX, vY, wX, wY));
                    v = w;

                } else {
                    break;
                }
            }
        }
    }

    // method used to draw a minimum spanning tree on the panel from a given minimum spanning tree of the graph
    private void drawMST(Bag<Edge> mst) {
        // graphics set to different color and stroke width to differentiate the mst from the rest of the map
        g2D.setColor(Color.MAGENTA);
        g2D.setStroke(new BasicStroke(2));

        drawEdgeSet(mst, 5);
    }

    // method used to draw a set of edges and the vertices they connect
    private void drawEdgeSet(Bag<Edge> edgeSet, double pointSize) {
        // used for debugging, used to determine the size of the fonts used for string elements drawn on the map
        // g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize() * .85F));

        boolean[] marked = new boolean[map.getNumVertices()]; // used to keep track of which vertices have been drawn
        
        // for every edge in a given edgeSet,
        // the coordinates for a point are calculated for each vertex it connects
        // if either of the vertices it connects have not yet been drawn, it draws them and marks them as drawn
        // and finally a line is drawn between the two vertices to represent the edge connecting them
        for (Edge e : edgeSet) {
            Node v = e.getEither();
            Point.Double vPoint = calcCoordinates(v);
            double vX = vPoint.getX();
            double vY = vPoint.getY();

            Node w = e.getOther(v);
            Point.Double wPoint = calcCoordinates(w);
            double wX = wPoint.getX();
            double wY = wPoint.getY();

            if (!marked[v.getIndex()]) {
                g2D.fill(new Ellipse2D.Double(vX - (pointSize/2), vY - (pointSize/2), pointSize, pointSize));
                marked[v.getIndex()] = true;
                // g2D.drawString(v.getID(), (float) vX - 5, (float) vY - 3); // used for debugging, used to draw labels for each intersection
            }

            if (!marked[w.getIndex()]) {
                g2D.fill(new Ellipse2D.Double(wX - (pointSize/2), wY - (pointSize/2), pointSize, pointSize));
                marked[v.getIndex()] = true;
                // g2D.drawString(v.getID(), (float) wX - 5, (float) wY - 5); // used for debugging, used to draw labels for each intersection
            }

            g2D.draw(new Line2D.Double(vX, vY, wX, wY));

            // used for debugging, used to draw the length in miles of each road next to the road
            // g2D.drawString(String.format("%.2f", e.getWeight()), (float) (Math.min(x, wX) + (Math.abs(x - wX) / 2)), (float) (Math.min(y, wY) + (Math.abs(y - wY) / 2)));
        }
    }

    // method used to calculate a point with x and y pixel coordinates given a vertex of a graph
    private Point.Double calcCoordinates(Node v) {
        // calculates the width and height of the map from its minimum/maximum vertex coordinates in longitude and latitude
        double mapWidth = map.getMaxlong() - map.getMinLong();
        double mapHeight = map.getMaxLat() - map.getMinLat();

        // stores the current width and height of the frame
        double frameWidth = getWidth();
        double frameHeight = getHeight();

        // constants used to convert longitude/latitude coordinates to coordinates relative to the size of the frame
        double scaleX = frameWidth / mapWidth;
        double scaleY = frameHeight / mapHeight;

        // used for debugging
        // System.out.println("mapWidth: " + mapWidth + "    mapHeight: " + mapHeight);
        // System.out.println("scaleX: " + scaleX + "   scaleY: " + scaleY);

        double scaleFactor = Math.min(scaleX, scaleY); // the smaller scale constant is used so the map always fits in the smallest dimension of the frame
        
        // calculates x and y pixel coordinates relative to the size of the frame based on the original longitude/latitude of the vertex
        double pointX = ((v.getLongitude() - map.getMinLong()) * scaleFactor);
        double pointY = (mapHeight - (v.getLatitude() - map.getMinLat())) * scaleFactor;

        return new Point.Double(pointX, pointY);
    }
}
package Project3;

import java.awt.*;
import java.awt.geom.*;
import java.util.Iterator;

import javax.swing.JPanel;

public class MapPanel extends JPanel {

    private static final long serialVersionUID = 0;

    private Graph map;
    private boolean[] markedNodes;
    private boolean[] markedEdges;

    private Bag<Node> path;
    private Bag<Edge> mst;
    
    public MapPanel(Graph map) {
        this.map = map;
    }

    public MapPanel(Graph map, Bag<Node> path) {
        this.map = map;
        this.path = path;
    }

    public MapPanel(Graph map, Bag<Edge> mst, boolean isMST) {
        this.map = map;
        this.mst = mst;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);

        markedNodes = new boolean[map.getNumIntersections()];
        markedEdges = new boolean[map.getNumRoads()];
        depthFirstDraw(g2D, map.getHeadNode());

        if (path != null) drawPath(g2D, path);
        if (mst != null) drawMST(g2D, mst);
    }

    private void depthFirstDraw(Graphics2D g2D, Node v) {
        Point.Double nodePoint = calcCoordinates(v);
        double x = nodePoint.getX();
        double y = nodePoint.getY();

        g2D.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));

        markedNodes[v.getIndex()] = true;
        for (Edge e : map.getAdjRoads(v.getID())) {

            Node w = e.getOtherIntersection(v);
            Point.Double wPoint = calcCoordinates(w);
            double wX = wPoint.getX();
            double wY = wPoint.getY();

            if (!markedEdges[e.getIndex()]) {
                markedEdges[e.getIndex()] = true;
                g2D.draw(new Line2D.Double(x, y, wX, wY));
                g2D.drawString(String.format("%.2f", e.getWeight()), (float) (Math.min(x, wX) + (Math.abs(x - wX) / 2)), (float) (Math.min(y, wY) + (Math.abs(y - wY) / 2)));
            }

            if (!markedNodes[w.getIndex()]) {
                depthFirstDraw(g2D, w);
            }
        }

        g2D.drawString(v.getID(), (float) x - 5, (float) y - 5);
    }

    private void drawPath(Graphics2D g2D, Bag<Node> path) {
        g2D.setColor(Color.RED);
        g2D.setStroke(new BasicStroke(2));

        Iterator<Node> it = path.iterator();
        Node start = it.next();
        drawPath(g2D, start, it);
    }

    private void drawPath(Graphics2D g2D, Node v, Iterator<Node> it) {
        Point.Double nodePoint = calcCoordinates(v);
        double x = nodePoint.getX();
        double y = nodePoint.getY();

        g2D.fill(new Ellipse2D.Double(x - 4, y - 4, 8, 8));

        if (it.hasNext()) {
            Node w = it.next();
            Point.Double wPoint = calcCoordinates(w);
            g2D.draw(new Line2D.Double(x, y, wPoint.getX(), wPoint.getY()));
            drawPath(g2D, w, it);
        }
    }


    private void drawMST(Graphics2D g2D, Bag<Edge> mst) {
        g2D.setColor(Color.MAGENTA);
        g2D.setStroke(new BasicStroke(2));

        boolean[] drawnNodes = new boolean[map.getNumIntersections()];

        for (Edge e : mst) {
            Node v = e.getEitherIntersection();
            Point.Double vPoint = calcCoordinates(v);
            double vX = vPoint.getX();
            double vY = vPoint.getY();

            Node w = e.getOtherIntersection(v);
            Point.Double wPoint = calcCoordinates(w);
            double wX = wPoint.getX();
            double wY = wPoint.getY();

            if (!drawnNodes[v.getIndex()]) {
                g2D.fill(new Ellipse2D.Double(vX - 4, vY - 4, 8, 8));
                drawnNodes[v.getIndex()] = true;
            }

            if (!drawnNodes[w.getIndex()]) {
                g2D.fill(new Ellipse2D.Double(wX - 4, wY - 4, 8, 8));
                drawnNodes[v.getIndex()] = false;
            }

            g2D.draw(new Line2D.Double(vX, vY, wX, wY));
        }
    }

    private Point.Double calcCoordinates(Node intersection) {
        double mapWidth = map.getMaxlong() - map.getMinLong();
        double mapHeight = map.getMaxLat() - map.getMinLat();
        double frameWidth = getWidth();
        double frameHeight = getHeight();
        double scaleX = frameWidth / mapWidth;
        double scaleY = frameHeight / mapHeight;
        // System.out.println("mapWidth: " + mapWidth + "    mapHeight: " + mapHeight);
        // System.out.println("scaleX: " + scaleX + "   scaleY: " + scaleY);

        double scaleFactor = Math.min(scaleX, scaleY);
        double pointX = ((intersection.getLongitude() - map.getMinLong()) * scaleFactor);
        double pointY = (mapHeight - (intersection.getLatitude() - map.getMinLat())) * scaleFactor;

        return new Point.Double(pointX, pointY);
    }
}
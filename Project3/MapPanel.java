package Project3;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    private static final long serialVersionUID = 0;

    private Graph map;
    private boolean[] markedNodes;
    private boolean[] markedEdges;
    
    public MapPanel(Graph map) {
        this.map = map;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);

        markedNodes = new boolean[map.getNumIntersections()];
        markedEdges = new boolean[map.getNumEdges()];
        depthFirstDraw(g2D, map.getHeadNode());
    }

    private void depthFirstDraw(Graphics2D g2D, Node v) {
        Point.Double nodePoint = calcCoordinates(v);
        double x = nodePoint.getX();
        double y = nodePoint.getY();

        g2D.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));

        markedNodes[v.getIndex()] = true;
        for (Edge e : map.getAdjRoads(v.getID())) {

            Node w = e.getOtherInt(v);
            Point.Double wPoint = calcCoordinates(w);

            if (!markedEdges[e.getIndex()]) {
                markedEdges[e.getIndex()] = true;
                g2D.draw(new Line2D.Double(x, y, wPoint.getX(), wPoint.getY()));
            }

            if (!markedNodes[w.getIndex()]) {
                depthFirstDraw(g2D, w);
            }
        }
    }

    public Point.Double calcCoordinates(Node intersection) {
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
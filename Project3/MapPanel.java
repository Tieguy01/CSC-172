package Project3;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    private static final long serialVersionUID = 0;

    private Graph map;
    private boolean[] marked;

    private double minX;
    private double minY;
    private double width;
    private double height;

    private AffineTransform at;
    
    public MapPanel(Graph map) {
        this.map = map;
        minX = map.getMinLat() - .0001;
        minY = map.getMinLong() - .0001;
        System.out.println("minX: " + minX);
        System.out.println("minY: " + minY);
        width = map.getMaxLat() - minX;
        height = map.getMaxlong() - minY;
        System.out.println("map width: " + width);
        System.out.println("map height: " + height);
        // setPreferredSize(new Dimension((int) width, (int) height));
        at = AffineTransform.getScaleInstance(100000, 100000);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        // g2D.scale(1000, 1000);
        g2D.setColor(Color.BLACK);
        // g2D.fillRect(0, 0, 250, 250);

        // g2D.fill(at.createTransformedShape(new Rectangle2D.Double(0, 0, 250.5, 250)));
        // g2D.fill(new Rectangle2D.Double(0, 0, 250.5, 250));
   
        System.out.println(getSize());

        marked = new boolean[map.getNumIntersections()];
        depthFirstDraw(g2D, map.getNodeFromIndex(0));
    }

    private void depthFirstDraw(Graphics2D g2D, Node v) {
        System.out.println("v: " + v.getID());
        // int latitude = v.getLatitude() - minX;
        // int longitude = v.getLongitude() - minY;
        double latitude = v.getLatitude() - minX;
        double longitude = v.getLongitude() - minY;
        // double latitude = v.getLatitude();
        // double longitude = v.getLongitude();
        System.out.println("latitude: "  + latitude);
        System.out.println("longitude: " + longitude);
        
        g2D.setColor(Color.BLACK);
        g2D.fill(at.createTransformedShape(new Ellipse2D.Double(latitude - .00001, longitude - .00001, .00002, .00002)));
        // g2D.fill(at.createTransformedShape(new Ellipse2D.Double(v.getIndex(), v.getIndex(), 50, 50)));
        // g2D.fill(at.createTransformedShape(new Rectangle2D.Double(10, 10, 80, 80)));
        // g2D.fill(new Ellipse2D.Double(latitude, longitude, 50, 50));
        // g2D.fillOval(latitude, longitude, 2, 2);
        // g2D.fillOval(minX, minY, 50, 50);
        marked[v.getIndex()] = true;
        for (Edge e : map.getAdjRoads(v.getIndex())) {
            Node w = e.getOtherInt(v);
            if (!marked[w.getIndex()]) {
                // g2D.draw(new Line2D.Double(latitude, longitude, w.getLatitude() - minX, w.getLongitude() - minY));
                // g2D.draw(new Line2D.Double(latitude, longitude, w.getLatitude(), w.getLongitude()));
                g2D.draw(at.createTransformedShape(new Line2D.Double(latitude, longitude, w.getLatitude() - minX, w.getLongitude() - minY)));
                // g2D.drawLine(latitude, longitude, w.getLatitude() - minX, w.getLongitude() - minY);
                depthFirstDraw(g2D, w);
            }
        }
        // System.out.println("width: " + width);
        // System.out.println("height: " + height);
    }
}
/**
 * @author Ethan Shahan
 */

package Project3;

import java.awt.*;
import javax.swing.*;

// class for executing all the graphics operations for a given graph
public class GraphGraphics {

    private JFrame frame; // the frame in which the graphs are drawn
    private Graph map;

    public GraphGraphics(Graph map, String title) {
        this.map = map;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes the frame exit when closed
        frame.setLocationRelativeTo(null); // sets the location of the frame relative to nothing

        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout); // sets the layout of the frame to a BorderLayout
        
        // adds empty panels to each position of the BorderLayout except the center to create padding
        frame.add(new JPanel(), BorderLayout.PAGE_START);
        frame.add(new JPanel(), BorderLayout.PAGE_END);
        frame.add(new JPanel(), BorderLayout.LINE_START);
        frame.add(new JPanel(), BorderLayout.LINE_END);
        
        frame.setSize(710, 700); // sets the size of the frame
        frame.setTitle(title.substring(0, title.length() - 4) + " map"); // sets the title of the frame based on a given map file name
    }

    // just draws the map represented by a graph
    public void drawMap() {
        MapPanel mapPanel = new MapPanel(map);
        frame.add(mapPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // draws the map represented by a graph along with a path between two points
    public void drawPath(Bag<Node> path) {
        MapPanel mapPanel = new MapPanel(map, path);
        frame.add(mapPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // draws the map represented by a graph alonf with the map's minimum spanning tree
    public void drawMST(Bag<Edge> mst) {
        MapPanel mapPanel = new MapPanel(map, mst, true);
        frame.add(mapPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}
package Project3;

import java.awt.*;
import javax.swing.*;

public class GraphGraphics {

    JFrame frame;

    public void initialize(String title) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        // frame.pack();
        frame.setSize(750, 750);
        frame.setTitle(title);
        frame.setVisible(true);
    }

    public void drawMap(Graph map, String title) {
        initialize(title);
        MapPanel mapPanel = new MapPanel(map);
        frame.add(mapPanel, BorderLayout.CENTER);
        // frame.pack();
        System.out.println("Frame size: " + frame.getSize());
    }
}
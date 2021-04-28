package Project3;

import java.awt.*;
import javax.swing.*;

public class GraphGraphics {

    JFrame frame;
    Graph map;

    public GraphGraphics(Graph map, String title) {
        this.map = map;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);
        frame.add(new JPanel(), BorderLayout.PAGE_START);
        frame.add(new JPanel(), BorderLayout.PAGE_END);
        frame.add(new JPanel(), BorderLayout.LINE_START);
        frame.add(new JPanel(), BorderLayout.LINE_END);
        
        frame.setSize(710, 700);
        frame.setTitle(title);
    }

    public void drawMap() {
        MapPanel mapPanel = new MapPanel(map);
        frame.add(mapPanel, BorderLayout.CENTER);
        System.out.println("Frame size: " + frame.getSize());
        frame.setVisible(true);
    }

}
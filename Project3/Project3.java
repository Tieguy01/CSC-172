package Project3;

import java.io.FileNotFoundException;

public class Project3 {


    public static void main(String[] args) throws FileNotFoundException {
        Graph map = new Graph(args[0]);
        // map.printGraph();
        // map.printIntersections();

        GraphGraphics graphics = null;
        ShortestPath shortestPath = null;
        MinimumSpanningTree mst = null;
        boolean displayMap = false;
        boolean displayDirections = false;
        boolean displayMeridian = false;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-show":
                    graphics = new GraphGraphics(map, args[0]);
                    displayMap = true;
                    break;
                case "-directions":
                    shortestPath = new ShortestPath(map, args[++i], args[++i]);
                    shortestPath.printPath();
                    displayDirections = true;
                    break;
                case "-meridianmap":
                    mst = new MinimumSpanningTree(map);
                    mst.printMST();
                    displayMeridian = true;
                    break;
            }
        }

        if (displayMap) {
            if (displayDirections) graphics.drawPath(shortestPath.getPath());
            else if (displayMeridian) graphics.drawMST(mst.getMST());
            else graphics.drawMap();
        }

        // GraphGraphics graphics = new GraphGraphics(map, args[0]);
        // graphics.drawMap();
        // ShortestPath shortestPath = new ShortestPath(map, "SPURRIER", "WILMOT");
        // shortestPath.printPath();
        // graphics.drawPath(shortestPath.getPath());
        // MinimumSpanningTree mst = new MinimumSpanningTree(map);
        // mst.printMST();
    }
}
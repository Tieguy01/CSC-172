package Project3;

import java.io.FileNotFoundException;

public class Project3 {


    public static void main(String[] args) throws FileNotFoundException {
        Graph map = new Graph(args[0]);
        // map.printGraph();
        // map.printIntersections();
        GraphGraphics graphics = new GraphGraphics(map, args[0]);
        // graphics.drawMap();
        ShortestPath shortestPath = new ShortestPath(map, "SPURRIER", "WILMOT");
        shortestPath.printPath();
        graphics.drawPath(shortestPath.getPath());
    }
}
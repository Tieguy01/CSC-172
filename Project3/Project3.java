package Project3;

import java.io.FileNotFoundException;

// main class that runs code
public class Project3 {
    public static void main(String[] args) throws FileNotFoundException {
        Graph map = new Graph(args[0]); // creates a graph from a given file name in the first argument

        // used for debugging
        // map.printGraph();
        // map.printIntersections();

        GraphGraphics graphics = null; // becomes non-null if -show flag given
        ShortestPath shortestPath = null; // becomes non-null if -directions flag given
        MinimumSpanningTree mst = null; // becomes non-null if -meridianmap flag given

        boolean displayMap = false; // represents whether the map should be drawn on the screen
        boolean displayDirections = false; // represents whether a path on the map should be drawn
        boolean displayMeridian = false; // represents whether the map's mst should be drawn

        // traverses the flags and arguments in the command line
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {

                // if -show flag found, GraphGraphics object created and displayMap flag set to true
                case "-show":
                    graphics = new GraphGraphics(map, args[0]);
                    displayMap = true;
                    break;

                // if -dirctions flag found, ShortestPath object created, intersections in path printed, and displayDirections flag set to true
                case "-directions":
                    shortestPath = new ShortestPath(map, args[++i], args[++i]);
                    shortestPath.printPath();
                    displayDirections = true;
                    break;

                // if -meridianmap flag found, MinimumSpanningTree object created, edges in mst printed, and displayMeridian flag set to true
                case "-meridianmap":
                    mst = new MinimumSpanningTree(map);
                    mst.printMST();
                    displayMeridian = true;
                    break;
            }
        }

        // if just the displayMap flag is true, just the map will be drawn,
        // if the displayDirections flag is also true, the map and a path on the map will be drawn
        // if the displayMeridian flag is also true, the map and the map's mst will be drawn
        if (displayMap) {
            if (displayDirections) graphics.drawPath(shortestPath.getPath());
            else if (displayMeridian) graphics.drawMST(mst.getMST());
            else graphics.drawMap();
        }
    }
}
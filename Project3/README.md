https://www.java2novice.com/java-collections-and-util/hashtable/iterate/

https://stackoverflow.com/questions/27439225/how-do-you-use-re-size-all-graphic2d

https://www3.ntu.edu.sg/home/ehchua/programming/java/J4b_CustomGraphics.html

https://stackoverflow.com/questions/30792089/java-graphics2d-translate-and-scale

https://gis.stackexchange.com/questions/142326/calculating-longitude-length-in-miles

https://www.codejava.net/java-se/swing/jframe-basic-tutorial-and-examples

Ethan Shahan Project 3 Street Mapping
Lab: 17 Tues/Thursday 4:50-6:05

Contact Info:
    Email: eshahan@u.rochester.edu

Code Explanation:
    The main function of this code is to create graphs from given files containing data about intersections and roads. Using these graphs shortest paths between any two intersections and minimum spanning trees connecting all the intersections in the map can be found. The maps, paths, and msts can all be displayed on a window on the sceen using the -show flag.

    To create a representation of the map as a graph, every intersection is stored in a Hashtable, using its ID as a key, and with its value being a Node containing all its information given in the original file, and every Edge is stored in a Bag and also added to an adjacency list contained within the Nodes for both intersections it connects.

    To find the shortest path between any two intersections in the map, Dijksta's shortest path algorithm was used to systematically determine the shortest path from the start intersections to every other intersection in the map until the shortest path from the start intersection to the target intersection is found. Before any of this is occurs, a breadth first search is performed to find all the connected compoenents of the graph and determine if the two intersections are even connected in the first place, if not an error message is printed.

    To find the minimum spanning tree of the map, Kruskal's algorithm is used to systematically find each of the shortest edges in the graph and combine their vertices into sets of connected components until all the vertices are in one set. 

    The biggest challenge with the project was figuring out how to use Java Graphics as I had previously never used it before. The thing that took the longest to figure out was how to properly scale the map to fit the window, but once I started to make the map's size relative to the size of the window it became a lot easier.

Files Included in Submission:
    Project3.java   // main class that runs code
    Graph.java  // class to create a store a graph from a given map file
    Node.java   // class representing a vertex in a graph
    Edge.java   // class representing an edge in a graph
    ShortestPath.java   // class used to construct the shortest path for two given vertices in a graph, implements Dijkstra's shortest paths algorithm
    MinimumSpanningTee.java // class used to construct a minimum spanning tree for a given graph, implements Kruskal's MST algorithm
    ConnectedComponents.java    // class used to identify connected compenents in a graph and the nodes contained within them
    GraphGraphics.java  // class for executing all the graphics operations for a given graph
    MapPanel.java   // Panel component used to contain map drawings
    PriorityQueue.java  // heap-based priority queue class 
    Bag.java    // iterable bag class using linked nodes
    Queue.java  // class for queue data structure

Runtime Analysis:
    

Compile and Run Information:
    From the commandline, inside the directory of the project:
        ...\Project3> javac *.java
        ...\Project3> java Project3.Project3 map.txt
        or 
        ...\Project3> java Project3.Project3 map.txt -show
        or
        ...\Project3> java Project3.Project3 map.txt -show -directions startIntersection endIntersection
        or
        ...\Project3> java Project3.Project3 map.txt -directions startIntersection endIntersection -show
        or
        ...\Project3> java Project3.Project3 map.txt -show -meridianmap
        or
        ...\Project3> java Project3.Project3 map.txt -meridianmap -show
        // this assumes that the set classpath extends to the directory that precedes the package Project3
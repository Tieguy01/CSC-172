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

    I also faced a challenge with the larger maps giving stackOverflowErrors when attempting to find shortest paths within them or plot them. I discovered this problem likely occured due to my use of recusion when finding the shortest paths and plotting the maps, so to fix it I recoded them to use non-recursive methods instead.

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
    Plotting the map:
        To plot the map, the drawEdgeSet method within the map panel class. Within this method, an array is created to mark all the vertices in the graph, which takes time proportional to the number of vetices in the graph, so O(V), and a for loop is run for every edge in the graph, and since every operation within this loop runs in constant time, including the method calls, the whole loop takes time proportional to the number of edges in the graph, so O(E). Thus, overall, the time complexity for plotting a map is O(V + E).

    Finding the Shortest Path:
        To find the shortest path between two intersection, we turn to the ShortestPath class. To create a shortest path, the constructor first creates two arrays and a priority queue, all the length of the number of vertices and thus all together take time proportional to 3*V. Next the ConnectedComponents class is used to find all the connected components in the graph, starting by creating two more arrays the length the number of vertices (adding another 2*V), then it proceeds to do a breadth first search on every vertex that hasn't been marked yet, so in the worst case this process takes time proportional to V*(V+E). Next, coming back to the ShortestPaths class, if the intersections are indeed connected, for every vertex distance node is created, adding another V. After that the method findShortestPaths is called, this method employs Dijkstra's algorithm, and as a result of using a heap based priority queue, in the worst case it takes time proportional to E log V. Lastly, the createPath method is recursively called to create a bag containing the path, which, in the worst case if the path contains every vertex in the map, would take time proportional to V. So all together we have 3*V + CC(2*V + V*(V+E)) + V + ELogV + V, which we can simplify down to O(V^2 + VE + ELogV).

    Generating Minimum Weigtht Spanning Tree:
        To find the minimum weight spanning tree, the MinimumSpanningTree class is used. The entore minimum weight spanning tree is created within the constructor of this class, starting by initializing a priority queue the length of the number of edges and an array the lengthof the number of vertices, thus taking time proportional to E+V. Next every edge in the graph is added to the priority queue and a VertexSet is created for every vertex, together taking time proportional to E+V as well. Finally, Kruskal's algorithm is used to construct the mst using a heap based priority queue full of edges and thus taking time proportional to ELogE. All together this comes to E + V + E + V + ELogE, or, simplified, O(E + V + ELogE).

    With larger data sets, plotting the map scales in linear time, while finding the shortest path scales closer to exponential time generating the minimum weight spanning tree scales closer to linearithmic time. So, as the maps gets bigger, plotting always takes time close to proportional with the size of the data, the time to generate a minimum weight spanning tree slowly starts to increase more as the data gets larger, and a faster increase is seen in the time it takes to find the shortest path between two points.


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
        // startIntersection and endIntersection should be written just as their ID's are listed in the map files
            Ex: SPURRIER WILMOT
                i83 i4025

Additional Resources:
    // Used to get general idea of how to code classes PriorityQueue and ConnectedComponents, and used for Dijkstra's algorithm, Kruskal's algorithm, and breadth-first search algorithm
    Algorithms, by Robert Sedgewick and Kevin Daniel Wayne, Fourth Edition ed., Addison-Wesley, 2011.

    // Used to figure out the basics of Java Graphics
    Hock-Chuan, Chua. “Java Programming Tutorial.” Custom Graphics Programming - Java Programming Tutorial, Apr. 2016, www3.ntu.edu.sg/home/ehchua/programming/java/J4b_CustomGraphics.html.  

    // Used to figure out how to use a JFrame
    Minh, Nam Ha. JFrame Basic Tutorial and Examples, CodeJava.net, 6 July 2019, www.codejava.net/java-se/swing/jframe-basic-tutorial-and-examples. 

    // Used to help convert longitude and latitude to miles
    Qayum, Abdullah, and Alex Tereshenkov. “Calculating Longitude Length in Miles.” Geographic Information Systems Stack Exchange, Stack Exchange Inc., 12 Apr. 2015, gis.stackexchange.com/questions/142326/calculating-longitude-length-in-miles. 

    I also talked about the project with my fellow classmate Matthew Daly
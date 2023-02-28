# GraphApp
This program allows the user to do different operations with graphs, which looks like "squared paper", are directed and weighted. We can generate graphs in three ways:
a) graph that contains all edges between adjacent vertexes
b) graph that is connected (this type of generation uses DFS algorithm which is made to check connections for each vertex, due to the time complexity we can generate only graph that contains up to 1000 vertexes)
c) graph with randomly generated edges
To generate graph we should mark options that we are interested in and specify number of columns, rows and range of weights and click the "Generate" button.
Generated graph we can save to the file and also we can load the graph from file.
To check if graph is connected we can use:
a) BFS algorithm - it is recommended when every path from one vertex to adjacent vertex is in both ways
b) DFS algorithm - it is recommended in every other cases, but time complexity allows us to check if graph is connected when it has up to 1000 vertexes as mentioned above
We can also find the shortest path between two vertexes using Dijkstra algorithm and we can do that in two ways:
a) type start and end vertex in the adequate field and press the button
b) select two vertexes on the user interface by clicking on the middle of the each vertex
Selected path should appear on the panel on the right side. We can find a few different paths from different vertexes and change the current path by clicking "Select This Path" button on the panel on the right side. We can also delete any path and check the details about the path.


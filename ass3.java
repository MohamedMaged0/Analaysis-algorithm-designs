import java.util.*;

public class GraphTraversal {

    private Map<Integer, List<Integer>> graph;
    private Set<Integer> visited;

    public GraphTraversal(Map<Integer, List<Integer>> graph) {
        this.graph = graph;
        this.visited = new HashSet<>();
    }

    public void dfs(int startNode, boolean printLeft) {
        visited.clear();
        System.out.println("DFS:");
        dfsHelper(startNode, printLeft);
        System.out.println();
    }

    private void dfsHelper(int node, boolean printLeft) {
        if (!visited.contains(node)) {
            visited.add(node);
            System.out.print(node + " ");

            List<Integer> neighbors = graph.get(node);
            if (neighbors != null) {
                neighbors.sort(Comparator.naturalOrder());
                for (int neighbor : neighbors) {
                    if (printLeft) {
                        dfsHelper(neighbor, printLeft);
                    }
                }
                for (int neighbor : neighbors) {
                    if (!printLeft) {
                        dfsHelper(neighbor, printLeft);
                    }
                }
            }
        }
    }

    public void bfs(int startNode, boolean printLeft) {
        visited.clear();
        System.out.println("BFS:");
        bfsHelper(startNode, printLeft);
        System.out.println();
    }

    private void bfsHelper(int startNode, boolean printLeft) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            System.out.print(currentNode + " ");

            List<Integer> neighbors = graph.get(currentNode);
            if (neighbors != null) {
                neighbors.sort(Comparator.naturalOrder());
                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }
    }

    public boolean isBipartite() {
        Set<Integer> colorMap = new HashMap<>();

        for (int node : graph.keySet()) {
            if (!visited.contains(node)) {
                if (!bfsColor(node, colorMap)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean bfsColor(int startNode, Map<Integer, Integer> colorMap) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        visited.add(startNode);
        colorMap.put(startNode, 0);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            int currentColor = colorMap.get(currentNode);

            List<Integer> neighbors = graph.get(currentNode);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                        colorMap.put(neighbor, 1 - currentColor); // Alternate colors
                    } else if (colorMap.get(neighbor) == currentColor) {
                        // If adjacent nodes have the same color, not bipartite
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(3, 4));
        graph.put(2, Arrays.asList(1, 3));
        graph.put(3, Arrays.asList(4));
        graph.put(4, Arrays.asList(1, 2));

        GraphTraversal traversal = new GraphTraversal(graph);
        traversal.dfs(1, true);
        traversal.bfs(1, true);

        if (traversal.isBipartite()) {
            System.out.println("The graph is bipartite.");
        } else {
            System.out.println("The graph is not bipartite.");
        }
    }
}




//To determine whether an undirected graph is a tree or not, you can use the following properties:

// Connectedness: A tree is a connected graph, meaning that there is a path between every pair of vertices. You can perform a depth-first search (DFS) or breadth-first search (BFS) starting from any node. If all nodes are visited, and there are no disconnected components, the graph is connected.
//Acyclic: A tree is an acyclic graph, meaning that it has no cycles. You can check for cycles using DFS or BFS. During the traversal, if you encounter a visited node that is not the parent of the current node, then there is a cycle in the graph.
//Number of Edges: A tree with n nodes has exactly n−1 edges. You can count the number of edges in the graph and check if it satisfies this property.
//running time = O(V+E)
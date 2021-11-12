import java.util.Arrays;

public class Graph {
    /** Adjacency matrix of the graph */
    int[][] G;
    /** List of components present in graph */
    int[] component;

    /** Constructor */
    public Graph(int[][] G) {
        this.G = G;
        /** Initialize the list of components
         *  where each vertex is its own component */
        component = new int[G.length];
        for (int i = 0; i < G.length; i++) {
            component[i] = i;
        }
    }

    /** Method to determine the minimum spanning tree of a graph using Boruvka's algorithm
     *
     * Initialize an empty adjacency matrix F
     * count the distinct component present in the graph (will be the same as the number of vertices present in the graph)
     * while the count of distinct components is greater than one add safe edges between components
     *
     * @param G
     * @return
     */
    public int[][] Boruvka (int[][] G) {
        int __ = Integer.MAX_VALUE;
        int[][] F = new int[G.length][G.length];
        for (int[] row : F) {
            Arrays.fill(row, __);
        }

        int count = countDistinctComponents();

        while (count > 1) {
            addAllSafeEdges(F, count);
            count = countDistinctComponents();
        }
        return F;
    }

    /** Method that counts and returns the amount of distinct components present in the graph */
    public int countDistinctComponents() {
        int count = 0;

        for (int i = 0; i < component.length; i++)
        {
            int j = 0;
            for (j = 0; j < i; j++)
                if (component[i] == component[j])
                    break;

            if (i == j) {
                count++;
            }
        }
        return count;
    }

    /** Method to add the safest edge for each component to F[][]
     *
     *  For each distinct component present in the graph,
     *  look at every adjacent edge belong to each vertex present in that component and
     *  find the edge with the least weight whose vertex is present in a different component and add it to F
     *  Once all safe edges have been added call labelComponent() to label each vertex with its corresponding component.
     *
     *  @param F
     *  @param count
     *  */
    public void addAllSafeEdges (int[][] F, int count) {
        for (int i = 0; i < count; i++) {
            int safestU = Integer.MAX_VALUE;
            int safestV = Integer.MAX_VALUE;
            int safestEdgeWeight = Integer.MAX_VALUE;

            for (int u = 0; u < G.length; u++) {
                for (int v = 0; v < G.length; v++) {
                    if (component[u] != component[v]) {
                        if (G[u][v] < safestEdgeWeight) {
                            safestEdgeWeight = G[u][v];
                            safestU = u;
                            safestV = v;
                        }
                    }
                }
            }

            F[safestU][safestV] = G[safestU][safestV];
            F[safestV][safestU] = G[safestV][safestU];
        }
        labelComponents(F);
    }

    /** Method to label each vertex that are connected by an edge as belonging to the same component
     *
     * Visit each edge present in the Adjacency Matrix F
     * each vertex that is visited should be marked true and have its component set to the count
     * once all connected vertices have been visited increment the count and move onto the following component
     *
     * @param F
     */
    public int labelComponents(int[][] F) {
        boolean[] visited = new boolean[F.length];
        int count = 1;

        for (int v = 0; v < F.length; v++) {
            if (!visited[v]) {
                DFSUtil(F, v, visited, count);
                count++;
            }
        }
        return count;
    }

    /** Method to recursively mark each connected vertex to the same component */
    void DFSUtil (int[][] F, int v, boolean[] visited, int count) {
        visited[v] = true;
        component[v] = count;

        for (int u = 0; u < G.length; u++) {
            if (!visited[u] && F[v][u] != Integer.MAX_VALUE) {
                DFSUtil(F, u, visited, count);
            }
        }
    }

    /** Driver Method */
    public static void main (String [] args) {
        int __ = Integer.MAX_VALUE;
        int[][] G = {
                { __,  1, 20, 30 },
                { 1,  __,  2, 10 },
                {20,  2,  __,  3 },
                {30, 10,  3,  __ }
        };

        Graph g = new Graph(G);

        int[][] answer = g.Boruvka(G);
        for (int i = 0; i < answer.length; i++) {
            System.out.println(Arrays.toString(answer[i]));
        }
    }
}

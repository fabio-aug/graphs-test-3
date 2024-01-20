import java.util.ArrayList;
import java.util.Arrays;

public class FordFulkerson {
    private final int numberOfVertices;
    private final int start;
    private final int end;
    private ArrayList<int[]>[] edges;
    private ArrayList<int[]>[] rotulation;
    private boolean[] visited;

    @SuppressWarnings("unchecked")
    public FordFulkerson(int numberOfVertices, int start, int end) {
        this.numberOfVertices = numberOfVertices;
        this.start = start;
        this.end = end;

        this.edges = new ArrayList[numberOfVertices];
        this.rotulation = new ArrayList[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            this.edges[i] = new ArrayList<>();
            this.rotulation[i] = new ArrayList<>();
        }

        this.visited = new boolean[numberOfVertices];
    }

    public void addEdge(int from, int to, int capacity) {
        this.edges[from].add(new int[] { to, capacity });
        this.rotulation[to].add(new int[] { from, 0 });
    }

    private int dfs(int currentNode, int minCapacity) {
        this.visited[currentNode] = true;

        if (currentNode == this.end)
            return minCapacity;

        for (int[] neighbor : this.edges[currentNode]) {
            int to = neighbor[0];
            int capacity = neighbor[1];

            if (!this.visited[to] && capacity > 0) {
                int currentPathFlow = dfs(to, Math.min(minCapacity, capacity));

                if (currentPathFlow > 0) {
                    for (int[] reverseNeighbor : this.rotulation[to]) {
                        if (reverseNeighbor[0] == currentNode) {
                            neighbor[1] -= currentPathFlow;
                            reverseNeighbor[1] += currentPathFlow;
                            break;
                        }
                    }

                    return currentPathFlow;
                }
            }
        }

        return 0;
    }

    public String formatArray(ArrayList<int[]>[] array, String name) {
        String aux = "    " + name + " = [\n";
        for (int i = 0; i < this.numberOfVertices; i++) {
            aux += "        index: " + i + " -> ";
            for (int[] item : array[i]) {
                aux += Arrays.toString(item) + " ";
            }
            if (i != this.numberOfVertices - 1) {
                aux += "\n";
            } else {
                aux += "\n    ],";
            }
        }
        return aux;
    }

    public void showResult(int maxFlow) {
        String aux = "Fluxo máximo: " + maxFlow + ",\n";
        aux += "FordFulkerson: { \n";
        aux += "    numberOfVertices = " + this.numberOfVertices + " (0 - " + (this.numberOfVertices - 1) + "),\n";
        aux += "    start = " + this.start + ",\n";
        aux += "    end = " + this.end + ",\n";
        aux += formatArray(this.edges, "edges");
        aux += "\n";
        aux += formatArray(this.rotulation, "rotulation");
        aux += "\n};";
        System.out.println(aux);
    }

    public void maxFlow() {
        int maxFlow = 0;

        while (true) {
            Arrays.fill(this.visited, false);
            int pathFlow = dfs(this.start, Integer.MAX_VALUE);
            if (pathFlow == 0) {
                break;
            }
            maxFlow += pathFlow;
        }

        showResult(maxFlow);
    }

    public static void main(String[] args) {
        FordFulkerson ff = new FordFulkerson(6, 0, 5);
        // S / 0
        ff.addEdge(0, 1, 10);
        ff.addEdge(0, 3, 10);

        // A / 1
        ff.addEdge(1, 2, 4);
        ff.addEdge(1, 3, 2);
        ff.addEdge(1, 4, 8);

        // B / 2
        ff.addEdge(2, 5, 10);

        // C / 3
        ff.addEdge(3, 4, 9);

        // D / 4
        ff.addEdge(4, 2, 6);
        ff.addEdge(4, 5, 10);

        ff.maxFlow();
    }
}

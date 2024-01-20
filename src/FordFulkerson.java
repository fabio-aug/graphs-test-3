import java.util.ArrayList;
import java.util.Arrays;

public class FordFulkerson {
    private final int end;
    private final int start;
    private boolean[] visited;
    private ArrayList<int[]>[] edges;
    private final int numberOfVertices;

    @SuppressWarnings("unchecked")
    public FordFulkerson(int numberOfVertices, int start, int end) {
        this.end = end;
        this.start = start;
        this.numberOfVertices = numberOfVertices;
        this.visited = new boolean[numberOfVertices];

        this.edges = new ArrayList[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++)
            this.edges[i] = new ArrayList<>();
    }

    public void addEdge(int from, int to, int capacity) {
        this.edges[from].add(new int[] { to, capacity, 0 });
    }

    public int walk(int currentNode, int minCapacity) {
        this.visited[currentNode] = true;

        if (currentNode == this.end)
            return minCapacity;

        for (int[] neighbor : this.edges[currentNode]) {
            int to = neighbor[0];
            int capacity = neighbor[1];

            if (!this.visited[to] && capacity > 0) {
                int currentPathFlow = walk(to, Math.min(minCapacity, capacity));

                if (currentPathFlow > 0) {
                    neighbor[1] -= currentPathFlow;
                    neighbor[2] += currentPathFlow;

                    return currentPathFlow;
                }
            }
        }

        return 0;
    }

    public void showResult(int maxFlow) {
        String aux = "Fluxo máximo: " + maxFlow + ",\n";
        aux += "FordFulkerson: { \n";
        aux += "    numberOfVertices = " + this.numberOfVertices + " (0 - " + (this.numberOfVertices - 1) + "),\n";
        aux += "    start = " + this.start + ",\n";
        aux += "    end = " + this.end + ",\n";
        aux += "    edges = [\n";
        for (int i = 0; i < this.numberOfVertices; i++) {
            aux += "        index: " + i + " -> ";
            for (int[] item : this.edges[i]) {
                aux += Arrays.toString(item) + " ";
            }
            if (i != this.numberOfVertices - 1) {
                aux += "\n";
            } else {
                aux += "\n    ],";
            }
        }
        aux += "\n};";
        System.out.print(aux);
    }

    public void maxFlow() {
        int maxFlow = 0;

        while (true) {
            Arrays.fill(this.visited, false);
            int pathFlow = walk(this.start, Integer.MAX_VALUE);
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

import java.util.ArrayList;

public class NearestNeighbor {
    private ArrayList<int[]>[] edges;
    private final int numberOfVertices;

    @SuppressWarnings("unchecked")
    public NearestNeighbor(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;

        this.edges = new ArrayList[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++)
            this.edges[i] = new ArrayList<>();
    }

    public void addEdge(int from, int to, int capacity) {
        this.edges[from].add(new int[] { to, capacity });
        this.edges[to].add(new int[] { from, capacity });
    }

    public void nearestNeighbor(int start) {
        int cost = 0;
        String tour = "";
        boolean[] visited = new boolean[this.numberOfVertices];

        int currentVertex = start;
        visited[currentVertex] = true;
        tour += currentVertex + 1;

        int index = 1;
        while (index < (this.numberOfVertices + 1)) {
            int nextVertex = 0;
            int minDistance = Integer.MAX_VALUE;

            for (int[] edge : this.edges[currentVertex]) {
                int neighbor = edge[0];
                int distance = edge[1];

                if (!visited[neighbor] && distance < minDistance) {
                    minDistance = distance;
                    nextVertex = neighbor;
                }
            }

            visited[nextVertex] = true;
            currentVertex = nextVertex;
            cost += minDistance;
            tour += " - " + (nextVertex + 1);

            index++;
            if (index == this.numberOfVertices) {
                visited[start] = false;
            }
        }

        System.out.println("Custo: " + cost);
        System.out.println("Rota: " + tour);
    }

    public static void main(String[] args) {
        NearestNeighbor nn = new NearestNeighbor(6);

        nn.addEdge(0, 1, 2);
        nn.addEdge(0, 2, 10);
        nn.addEdge(0, 3, 1);
        nn.addEdge(0, 4, 8);
        nn.addEdge(0, 5, 25);
        nn.addEdge(1, 2, 15);
        nn.addEdge(1, 3, 14);
        nn.addEdge(1, 4, 1);
        nn.addEdge(1, 5, 4);
        nn.addEdge(2, 3, 14);
        nn.addEdge(2, 4, 16);
        nn.addEdge(2, 5, 5);
        nn.addEdge(3, 4, 7);
        nn.addEdge(3, 5, 3);
        nn.addEdge(4, 5, 20);

        nn.nearestNeighbor(0);
    }
}

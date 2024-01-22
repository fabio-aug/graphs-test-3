import java.util.ArrayList;

public class Shift {
    private ArrayList<int[]>[] edges;
    private final int numberOfVertices;

    @SuppressWarnings("unchecked")
    public Shift(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;

        this.edges = new ArrayList[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++)
            this.edges[i] = new ArrayList<>();
    }

    public void addEdge(int from, int to, int capacity) {
        this.edges[from].add(new int[] { to, capacity });
        this.edges[to].add(new int[] { from, capacity });
    }

    public void shift() {
        int[] tour = generateInitialTour();

        int initialDistance = calculateTourDistance(tour);

        int index = 0;
        while (index < this.numberOfVertices) {
            for (int i = 0; i < this.numberOfVertices - 2; i++) {
                int j = i + 1;
                int[] newTour = tour.clone();
                int aux = newTour[i];
                newTour[i] = newTour[j];
                newTour[j] = aux;
    
                int newDistance = calculateTourDistance(newTour);
    
                if (newDistance < initialDistance) {
                    tour = newTour;
                    initialDistance = newDistance;
                }
            }
            index++;
        }
        

        System.out.println("Custo: " + calculateTourDistance(tour));
        System.out.print("Rota: ");
        for (int vertex : tour) {
            System.out.print((vertex + 1) + " - ");
        }
        System.out.print((tour[0] + 1));
    }

    private int[] generateInitialTour() {
        return new int[] { 0, 1, 2, 3, 4, 5};
    }

    private int calculateTourDistance(int[] tour) {
        int distance = 0;

        for (int i = 0; i < this.numberOfVertices - 1; i++) {
            distance += getDistanceBetween(tour[i], tour[i + 1]);
        }

        distance += getDistanceBetween(tour[this.numberOfVertices - 1], tour[0]);

        return distance;
    }

    private int getDistanceBetween(int vertex1, int vertex2) {
        for (int[] edge : this.edges[vertex1]) {
            if (edge[0] == vertex2) {
                return edge[1];
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Shift s = new Shift(6);

        s.addEdge(0, 1, 2);
        s.addEdge(0, 2, 10);
        s.addEdge(0, 3, 1);
        s.addEdge(0, 4, 8);
        s.addEdge(0, 5, 25);
        s.addEdge(1, 2, 15);
        s.addEdge(1, 3, 14);
        s.addEdge(1, 4, 1);
        s.addEdge(1, 5, 4);
        s.addEdge(2, 3, 14);
        s.addEdge(2, 4, 16);
        s.addEdge(2, 5, 5);
        s.addEdge(3, 4, 7);
        s.addEdge(3, 5, 3);
        s.addEdge(4, 5, 20);

        s.shift();
    }
}

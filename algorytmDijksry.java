import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Tworzenie grafu
        Graph graph = new Graph();

        // Tworzenie wierzchołków
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);

        // Dodawanie wierzchołków do grafu
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        // Dodawanie krawędzi do grafu
        graph.addEdge(v1, v2, 2);
        graph.addEdge(v1, v3, 5);
        graph.addEdge(v2, v3, 1);
        graph.addEdge(v2, v4, 6);
        graph.addEdge(v3, v4, 3);

        // Wybieranie losowego wierzchołka docelowego
        Random random = new Random();
        Vertex randomEndVertex = graph.vertices.get(random.nextInt(graph.vertices.size()));

        // Znajdowanie najkrótszej drogi między wierzchołkiem 1 a losowym wierzchołkiem docelowym
        List<Vertex> shortestPath = graph.shortestPath(v1, randomEndVertex);

        // Wyświetlanie najkrótszej drogi
        System.out.println("Najkrótsza droga:");
        for (Vertex vertex : shortestPath) {
            System.out.print(vertex.id + " -> ");
        }
    }
}

class Vertex {
    int id;
    List<Edge> edges;

    public Vertex(int id) {
        this.id = id;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
}

class Edge {
    Vertex source;
    Vertex destination;
    int weight;

    public Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

class Graph {
    List<Vertex> vertices;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addEdge(Vertex source, Vertex destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        source.addEdge(edge);
        destination.addEdge(edge);
    }

    // Metoda do znajdowania najkrótszej drogi pomiędzy wierzchołkiem początkowym a losowym wierzchołkiem docelowym
    public List<Vertex> shortestPath(Vertex start, Vertex randomEndVertex) {
        // Inicjalizacja
        Map<Vertex, Integer> distances = new HashMap<>();
        Map<Vertex, Vertex> previousVertices = new HashMap<>();
        PriorityQueue<Vertex> queue = new PriorityQueue<>((v1, v2) -> distances.get(v1) - distances.get(v2));

        for (Vertex vertex : vertices) {
            distances.put(vertex, vertex == start ? 0 : Integer.MAX_VALUE);
            queue.add(vertex);
        }

        // Algorytm Dijkstry
        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            if (current == randomEndVertex) break;
            if (distances.get(current) == Integer.MAX_VALUE) break;

            for (Edge edge : current.edges) {
                Vertex neighbor = edge.source == current ? edge.destination : edge.source;
                int newDistance = distances.get(current) + edge.weight;
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousVertices.put(neighbor, current);
                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Rekonstrukcja najkrótszej ścieżki
        List<Vertex> shortestPath = new ArrayList<>();
        Vertex current = randomEndVertex;
        while (current != null) {
            shortestPath.add(current);
            current = previousVertices.get(current);
        }
        Collections.reverse(shortestPath);

        return shortestPath;
    }
}

package java_learnings.CollectionFrameworks;
import java.util.ArrayList;

// Creating Graphs with the help of adjacency list---
    // This is a undirectional and weghted graph---
public class CreatingGraph_2 {
    static class Edge {
        int source;
        int destination;
        int weight;
        public Edge(int src , int dest, int w){ 
            this.destination = dest;
            this.source = src;
            this.weight = w;
        }
    }
    public static void createGraph( ArrayList<Edge> graph[]){
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<Edge>();   // Important step
        }
        graph[0].add(new Edge(0, 2,2));

        graph[1].add(new Edge(1, 2,10));
        graph[1].add(new Edge(1, 3,0));

        graph[2].add(new Edge(2, 0,2));
        graph[2].add(new Edge(2, 1,10));
        graph[2].add(new Edge(2, 3,-1));

        graph[3].add(new Edge(3, 1,0));
        graph[3].add(new Edge(3, 2,-1));
    }
    public static void main(String[] args) {
        int v =4;

    
   ArrayList<Edge> graph[] = new ArrayList[v];
    createGraph(graph);
        
    // print 2's neighbours
    for (int i = 0; i < graph[2].size(); i++) {
        Edge  e = graph[2].get(i);
        System.out.println(e.destination+" , "+ e.weight);
    }

  }
}

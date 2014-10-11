import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Graph {
   Node[] vertices;
   
   private static class Node {
     private int data;
     private List<Edge> incoming = new ArrayList<Edge>();
     private List<Edge> outgoing = new ArrayList<Edge>();
     
     public Node(int data) {
       this.data = data;
     }
     
     private void addEdge(Node to) {
       Edge e = new Edge(this, to);
       outgoing.add(e);
       to.incoming.add(e);
     }
   }
  
   private static class Edge {
     private Node from;
     private Node to;
     
     public Edge(Node from, Node to){
       this.from = from;
       this.to = to;
     }    
   }
   
   private void topologicalSort() {
     List<Node> sorted = new ArrayList<Graph.Node>();
     HashSet<Node> set = new HashSet<Graph.Node>();
     for(Node n : vertices) {
       if(n.incoming.size() == 0) {
         set.add(n);
       }
     }
     
     while(!set.isEmpty()) {
       Node n = set.iterator().next();
       set.remove(n);
       for(Edge e : n.outgoing) {
         
         Node m = e.to;
         n.outgoing.remove(e);
         m.incoming.remove(e);
         if(m.incoming.isEmpty()) {
           sorted.add(m);
         }
       }
     }
     
     boolean cycle = false;
     for(Node n : vertices) {
       if(!n.incoming.isEmpty()) {
         cycle = true;
         break;
       }
     }
     
   }
   
  public static void main(String[] args) {
    Node a = new Node(0);
    Node b = new Node(1);
    a.addEdge(b);
  }

}

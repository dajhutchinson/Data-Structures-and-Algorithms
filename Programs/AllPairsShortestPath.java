import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class AllPairsShortestPath {

  // map from node to map to all other nodes
  public static Map<Node<String>,Map<Node<String>,Double>> johnson(Graph<String> g) {
    Map<Node<String>,Map<Node<String>,Double>> dist=new HashMap<>();

    // copy graph
    Graph<String> copy=new LinkedGraph<>();
    Set<Node<String>> vertices=g.vertices();
    Set<Edge<String>> edges=g.edges();
    for (Node<String> n:vertices) {
      copy.addVertex(n);
    }
    for (Edge<String> e:edges) {
      copy.addEdge(e);
    }

    // add new vertex
    Node<String> s=new Node<>("NEW");
    copy.addVertex(s);

    // add zero weighted edge from s to all other vertices
    vertices=copy.vertices();
    for (Node<String> v:vertices) {
      copy.addEdge(s, v, 0.0);
    }

    // run bellman ford with s as source
    Map<Node<String>,Double> bellman=SingleSourceShortestPath.bellmanFord(copy, s);
    if (bellman==null) {return null;}

    // reweight all edges
    edges=copy.edges();
    for (Edge<String> edge:edges) {
      Node<String> u=edge.start;
      Node<String> v=edge.end;
      Double weight=edge.weight;
      edge.weight=edge.weight+bellman.get(v)-bellman.get(u);
    }

    // remove s
    copy.removeVertex(s);

    // run dijkstra's with each edge as origin
    for (Node<String> u:vertices) {
      Map<Node<String>,Double> dijk=SingleSourceShortestPath.dijkstra(copy, u);
      dist.put(u, dijk);
    }

    // deweight
    for (Node<String> u:vertices) {
      for (Node<String> v:vertices) {
        Double reweight=dist.get(u).get(v)+bellman.get(v)-bellman.get(u);
      }
    }

    return dist;
  }

  public static void print(Map<Node<String>,Map<Node<String>,Double>> map) {
    if (map==null) {System.out.println("null"); return;}
    Set<Node<String>> vertices=map.keySet();

    for (Node<String> vertex:vertices) {
      System.out.printf("Source-%s\n", vertex.data);
      SingleSourceShortestPath.print(map.get(vertex));
    }
  }

}

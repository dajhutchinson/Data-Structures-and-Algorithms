import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

public class SingleSourceShortestPath {

  public static Map<Node<String>,Integer> breadthFirstSearch(Graph<String> g, Node<String> s) {
    Set<Node<String>> met=new HashSet<>();

    Map<Node<String>,Integer> dist=new HashMap<>();
    for (Node<String> n:g.vertices()) {
      dist.put(n,Integer.MAX_VALUE);
    }
    dist.put(s,0);

    Queue<Node<String>> queue=new Queue<>();
    queue.add(s);
    while (!queue.empty()) {
      Node<String> u=queue.poll();
      if (!met.contains(u)) {
        met.add(u);
        for (Edge<String> edge:g.edges(u)) {
          queue.add(edge.end);
          if (dist.get(edge.end).equals(Integer.MAX_VALUE)) {
            dist.put(edge.end, dist.get(u)+1);
          }
        }
      }
    }
    return dist;
  }

  // non-negative weights
  public static Map<Node<String>,Double> dijkstra(Graph<String> g, Node<String> s) {
    Map<Node<String>,Double> dist=new HashMap<>();
    PriorityQueue<Node<String>> queue=new BinaryHeapPriorityQueue<>();

    Set<Node<String>> vertices=g.vertices();
    for (Node<String> v:vertices) {
      dist.put(v,Double.MAX_VALUE);
      queue.insert(v,dist.get(v).intValue());
    }
    dist.put(s,0.0);
    queue.decreaseKey(s,0);

    while(!queue.empty()) {
      Node<String> u=queue.extractMin();
      for (Edge<String> edge:g.edges(u)) {
        Node<String> end=edge.end;
        Double weight=edge.weight;

        if (dist.get(end)>dist.get(u)+weight) {
          dist.put(end, dist.get(u)+weight);
          queue.decreaseKey(end, dist.get(end).intValue());
        }
      }
    }
    return dist;
  }

  // negative & non-negative weights
  public static Map<Node<String>,Double> bellmanFord(Graph<String> g, Node<String> s) {
    Map<Node<String>,Double> dist=new HashMap<>();

    Set<Node<String>> vertices=g.vertices();
    Set<Edge<String>> edges=g.edges();
    for (Node<String> v:vertices) {
      dist.put(v,Double.MAX_VALUE);
    }
    dist.put(s,0.0);

    // gemerate distances
    for (int i=0; i<vertices.size(); i++) {
      for (Edge<String> edge:edges) {
        Node<String> start=edge.start;
        Node<String> end=edge.end;
        Double weight=edge.weight;
        if (dist.get(end)>dist.get(start)+weight) {
          dist.put(end,dist.get(start)+weight);
    }}}

    for (Edge<String> edge:edges) {
      Node<String> start=edge.start;
      Node<String> end=edge.end;
      Double weight=edge.weight;
      if (dist.get(end)>dist.get(start)+weight) {
        return null; // negative weight cycle
      }
    }
    return dist;
  }

  public static void print(Map<Node<String>,Double> map) {
    if (map==null) {System.out.println("null"); return;}
    Set<Node<String>> keys=map.keySet();
    Collection<Double> values=map.values();

    String[] keyArray=new String[keys.size()];
    Double[] valueArray=new Double[values.size()];
    int index=0;
    for (Node<String> s:keys) {keyArray[index++]=s.data;}
    index=0;
    for (Double s:values) {valueArray[index++]=s;}

    String[] valueStrings=new String[values.size()];
    for (int i=0; i<values.size(); i++) {
      valueStrings[i] = valueArray[i]==Double.MAX_VALUE ? "null" : valueArray[i].toString();
    }

    // Find length of longest names
    int longKey=0;
    int longValue=0;
    for (int i=0; i<keyArray.length; i++) {
      int klen=keyArray[i].length();
      if (klen>longKey)  {longKey=klen;}
      int vlen=valueStrings[i].length();
      if (vlen>longValue) {longValue=vlen;}
    }

    // Make horizontal line
    String line="+";
    for (int i=0;i<longKey;i++) {line+="-";}
    line+="+";
    for (int i=0;i<longValue;i++) {line+="-";}
    line+="+";

    // Print table
    System.out.println(line);
    for (Node<String> key:keys) {
      String k=key.data;
      for (int i=k.length(); i<longKey; i++) {k+=" ";}
      Double v=map.get(key);
      if (v==Double.MAX_VALUE) {v=null;}
      System.out.printf("|%s|%f|\n",k,v);
    }
    System.out.println(line);
  }

}

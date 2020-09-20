import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.ListIterator;

class LinkedGraph<T> extends Graph<T> {

  private Map<Node<T>,LinkedList<Tuple<Node<T>,Double>>> linkedList;

  public LinkedGraph() {
    linkedList=new HashMap<>();
  }

  // all vertices
  @Override
  public Set<Node<T>> vertices() {
    return linkedList.keySet();
  }

  // all edges
  @Override
  public Set<Edge<T>> edges() {
    Set<Node<T>> vertices=vertices();
    Set<Edge<T>> edges=new HashSet<>();

    for (Node<T> start:vertices) {
      LinkedList<Tuple<Node<T>,Double>> vertexEdges=linkedList.get(start);

      for (Tuple<Node<T>,Double> tuple:vertexEdges) {
        Node<T> end=tuple.first;
        Double weight=tuple.second;
        Edge<T> edge=new Edge<>(start, end, weight);
        edges.add(edge);
      }
    }

    return edges;
  }

  // all edges from s
  @Override
  public Set<Edge<T>> edges(Node<T> s) {
    Set<Edge<T>> edges=new HashSet<>();

    if (!vertices().contains(s)) {
      return null;
    }

    LinkedList<Tuple<Node<T>,Double>> vertexEdges=linkedList.get(s);

    for (Tuple<Node<T>,Double> tuple:vertexEdges) {
      Node<T> end=tuple.first;
      Double weight=tuple.second;
      Edge<T> edge=new Edge<>(s, end, weight);
      edges.add(edge);
    }

    return edges;
  }

  // returns edge from s to e or null
  @Override
  public Edge<T> edge(Node<T> s, Node<T> e) {
    Edge<T> edge=null;

    LinkedList<Tuple<Node<T>,Double>> edges=linkedList.get(s);

    for (Tuple<Node<T>,Double> tuple:edges) {
      if (tuple.first==e) {
        edge=new Edge<>(s,e,tuple.second);
      }
    }

    return edge;
  }

  // add vertex to graph
  @Override
  public Boolean addVertex(Node<T> v) {
    LinkedList<Tuple<Node<T>,Double>> list=new LinkedList<>();
    if (vertices().contains(v)) {
      return false;
    }
    linkedList.put(v, list);

    return true;
  }

  // add edge to graph
  @Override
  public Boolean addEdge(Edge<T> e) {
    if (e==null) {
      return false;
    }

    Node<T> start=e.start;
    Node<T> end=e.end;

    if (!vertices().contains(start) || !vertices().contains(end)) {
      return false;
    }

    LinkedList<Tuple<Node<T>,Double>> list=linkedList.get(start);

    Tuple<Node<T>,Double> tuple=new Tuple<>(end, e.weight);

    return list.add(tuple);
  }

  // add edge from s to e with weight w to graph
  @Override
  public Boolean addEdge(Node<T> s, Node<T> e, Double w) {
    if (!vertices().contains(s) || !vertices().contains(e)||w==null) {
      return false;
    }

    LinkedList<Tuple<Node<T>,Double>> list=linkedList.get(s);
    Tuple<Node<T>,Double> tuple=new Tuple<>(e,w);

    return list.add(tuple);
  }

  // remove vertex from graph
  @Override
  public void removeVertex(Node<T> v) {
    for (Node<T> start:vertices()) {
      removeEdge(start, v);
    }

    linkedList.remove(v);
  }

  // remove edge from graph
  @Override
  public Boolean removeEdge(Edge<T> e) {
    Node<T> start=e.start;
    Node<T> end=e.end;
    Double weight=e.weight;

    if (!vertices().contains(start)||!vertices().contains(end)) {
      return false;
    }

    LinkedList<Tuple<Node<T>,Double>> list=linkedList.get(start);
    ListIterator<Tuple<Node<T>,Double>> iterator=list.listIterator();

    while (iterator.hasNext()) {
      Tuple<Node<T>,Double> tuple=iterator.next();
      if (tuple.first==end&&tuple.second.equals(weight)) {iterator.remove();}
    }

    return true;
  }

  // removes all edges from s to e
  @Override
  public Boolean removeEdge(Node<T> s, Node<T> e) {
    if (!vertices().contains(s)||!vertices().contains(e)) {
      return false;
    }

    LinkedList<Tuple<Node<T>,Double>> list=linkedList.get(s);
    ListIterator<Tuple<Node<T>,Double>> iterator=list.listIterator();

    while (iterator.hasNext()) {
      Tuple<Node<T>,Double> tuple=iterator.next();
      if (tuple.first==e) {iterator.remove();}
    }

    return true;
  }

  @Override
  public void clear() {
    linkedList.clear();
  }
}

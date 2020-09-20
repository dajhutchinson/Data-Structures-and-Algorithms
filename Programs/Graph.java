import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public abstract class Graph<T> {
  public abstract Set<Node<T>> vertices(); // all vertices
  public abstract Set<Edge<T>> edges(); // all edges
  public abstract Set<Edge<T>> edges(Node<T> s); // all edges from s
  public abstract Edge<T> edge(Node<T> s, Node<T> e); // returns edge from s to e or null
  public abstract Boolean addVertex(Node<T> v); // add vertex to graph
  public abstract Boolean addEdge(Edge<T> e); // add edge to graph
  public abstract Boolean addEdge(Node<T> s, Node<T> e, Double w); // add edge from s to e with weight w to graph
  public abstract void removeVertex(Node<T> v); // remove vertex from graph
  public abstract Boolean removeEdge(Edge<T> e); // remove edge from graph
  public abstract Boolean removeEdge(Node<T> s, Node<T> e); // remove edge from graph
  public abstract void clear(); // clear graph

  public static void breadthFirstSearch(Graph<String> g, Node<String> n) {
    Set<Node<String>> vertices=new HashSet<>();

    Queue<Node<String>> bag=new Queue<>();
    bag.add(n);

    while (!bag.empty()) {
      Node<String> u=bag.poll();
      if (!vertices.contains(u)) {
        vertices.add(u);
        System.out.println(u.data);
        for (Edge<String> edge:g.edges(u)) {
          bag.add(edge.end);
        }
      }
    }
  }

  public static void depthFirstSearch(Graph<String> g, Node<String> n) {
    Set<Node<String>> vertices=new HashSet<>();

    Stack<Node<String>> bag=new Stack<>();
    bag.push(n);

    while (!bag.empty()) {
      Node<String> u=bag.pop();
      if (!vertices.contains(u)) {
        vertices.add(u);
        System.out.println(u.data);
        for (Edge<String> edge:g.edges(u)) {
          bag.push(edge.end);
        }
      }
    }
  }

  public static <E> Graph<E> kruskal(Graph<E> g) {
    // store index of vertex in list
    DisjointSets sets=new ReverseTreeDisjointSets();
    Graph<E> tree=new LinkedGraph<>();

    Set<Edge<E>> es=g.edges();
    Set<Node<E>> vs=g.vertices();
    List<Node<E>> vertices=new ArrayList<>();
    List<Edge<E>> edges=new ArrayList<>();
    for (Node<E> v:vs) {
      vertices.add(v);
      tree.addVertex(v);
    }

    for (int i=0; i<vertices.size(); i++) {sets.makeSet(i);}

    // Sort edges
    for (Edge<E> e:es) {
      edges.add(e);
      int j=edges.indexOf(e)-1;
      while (j>-1 && e.weight.compareTo(edges.get(j).weight)<0) {
        edges.set(j+1, edges.get(j));
        j--;
      }
      edges.set(j+1,e);
    }

    for (Edge<E> e:edges) {
      int u=vertices.indexOf(e.start);
      int v=vertices.indexOf(e.end);

      if (sets.findSet(u)!=sets.findSet(v)) {
        sets.union(u,v);
        tree.addEdge(e);
      }
    }

    return tree;

  }

}

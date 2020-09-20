import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// NOTE can only have one edge between two vertices, the most recently added is used
// NOTE can't haev zero weighted edges

public class MatrixGraph<T> extends Graph<T> {

  private double[][] weights; // weights[start][end]
  private List<Node<T>> vertices;

  public MatrixGraph () {
    weights=new double[0][0];
    vertices=new ArrayList<>();
  }

  // decrease matrix dimension by 1
  private double[][] expand() {
    int len=weights.length;
    double[][] newWeights=new double[len+1][len+1];

    // copy existing data
    for (int i=0; i<len; i++) {
      for (int j=0; j<len; j++) {
        newWeights[i][j]=weights[i][j];
    }}

    // final column is zeroes
    for (int i=0; i<len+1; i++) {
      newWeights[i][len]=0;
    }

    for (int j=0; j<len; j++) {
      newWeights[len][j]=0;
    }

    return newWeights;
  }

  // decrease matrix dimension by 1 and remove col/row n
  private double[][] contract(int n) {
    int len=weights.length;
    double[][] newWeights=new double[len-1][len-1];

    // copy items inside col/row n
    for (int i=0; i<n; i++) {
      for (int j=0; j<n; j++) {
        newWeights[i][j]=weights[i][j];
    }}

    // copy item ouside col/row n
    for (int i=n; i<len-1; i++) {
      for (int j=n; j<len-1; j++) {
        newWeights[i][j]=weights[i+1][j+1];
    }}

    return newWeights;
  }

  // all vertices
  @Override
  public Set<Node<T>> vertices() {
    return new HashSet<>(vertices);
  }

  // all edges
  @Override
  public Set<Edge<T>> edges() {
    int len=weights.length;
    Set<Edge<T>> edges=new HashSet<>();

    for (int i=0; i<len; i++) {
      for (int j=0; j<len; j++) {
        double weight=weights[i][j];
        if (weight!=0.0) {
          Edge<T> edge=new Edge<>(vertices.get(i), vertices.get(j), weight);
          edges.add(edge);
        }
      }
    }

    return edges;
  }

  // all edges from s
  @Override
  public Set<Edge<T>> edges(Node<T> s) {
    Set<Edge<T>> edges=new HashSet<>();

    int len=weights.length;
    int index=vertices.indexOf(s);
    for (int j=0; j<len; j++) {
      double weight=weights[index][j];
      if (weight!=0.0) {
        Node<T> end=vertices.get(j);
        edges.add(new Edge<T>(s, end, weight));
      }
    }

    return edges;
  }

  // returns edge from s to e or null
  @Override
  public Edge<T> edge(Node<T> s, Node<T> e) {
    Edge<T> edge=null;

    int i=vertices.indexOf(s);
    int j=vertices.indexOf(e);

    if (weights[i][j]!=0.0) {
      edge=new Edge<>(s,e, weights[i][j]);
    }

    return edge;
  }

  // add vertex to graph
  @Override
  public Boolean addVertex(Node<T> v) {
    if (vertices.contains(v)) {
      return false;
    }
    weights=expand();
    return vertices.add(v);
  }

  // add edge to graph
  @Override
  public Boolean addEdge(Edge<T> e) {
    Node<T> start=e.start;
    Node<T> end=e.end;

    if (!vertices.contains(start)||!vertices.contains(end)) {
      return false;
    }

    int i=vertices.indexOf(start);
    int j=vertices.indexOf(end);
    weights[i][j]=e.weight;

    return true;
  }

  // add edge from s to e with weight w to graph
  @Override
  public Boolean addEdge(Node<T> s, Node<T> e, Double w) {
    if (!vertices.contains(s)||!vertices.contains(e)) {
      return false;
    }

    int i=vertices.indexOf(s);
    int j=vertices.indexOf(e);
    weights[i][j]=w;

    return true;
  }

  // remove vertex from graph
  @Override
  public void removeVertex(Node<T> v) {
    int n=vertices.indexOf(v);
    weights=contract(n);
    vertices.remove(v);
  }

  // remove edge from graph
  @Override
  public Boolean removeEdge(Edge<T> e) {
    Node<T> start=e.start;
    Node<T> end=e.end;

    if (!vertices.contains(start)||!vertices.contains(end)) {
      return false;
    }

    int i=vertices.indexOf(start);
    int j=vertices.indexOf(end);

    weights[i][j]=0.0;

    return false;
  }

  // remove edge from graph
  @Override
  public Boolean removeEdge(Node<T> s, Node<T> e) {
    if (!vertices.contains(s)||!vertices.contains(e)) {
      return false;
    }

    int i=vertices.indexOf(s);
    int j=vertices.indexOf(e);

    weights[i][j]=0.0;

    return false;
  }

  // clear graph
  @Override
  public void clear() {
    weights=new double[0][0];
    vertices.clear();
  }
}

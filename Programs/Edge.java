public class Edge<T> {
  public Node<T> start;
  public Node<T> end;
  public Double weight;

  public Edge(Node<T> s, Node<T> e, Double w) {
    this.start=s;
    this.end=e;
    this.weight=w;
  }
}

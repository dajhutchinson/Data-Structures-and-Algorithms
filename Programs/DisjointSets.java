public interface DisjointSets {
  public boolean makeSet(int x); // Makes new set containg x
  public boolean union(int x, int y); // Merges the sets containing x & y
  public int findSet(int x); // returns identity of set containg x
}

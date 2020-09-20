public class ReverseTreeDisjointSets implements DisjointSets {

  // NOTE -1 = tree index (top of tree)
  // NOTE -2 = not assigned

  int[] trees;
  int[] heights;

  public ReverseTreeDisjointSets() {
    trees=new int[0];
    heights=new int[0];
  }

  // NOTE if x is already assigned, ignore
  // Makes new set containg x
  public boolean makeSet(int x) {
    if (x>=trees.length) { // x outside current tree range
      trees=expand(x);
      trees[x]=-1;
      heights[x]=1;
    } else { // x inside current tree range
      if (trees[x]==-2) {
        trees[x]=-1;
        heights[x]=1;
      } else { // already in set
        return false;
      }
    }
    return true;
  }

  // TODO
  // Merges the sets containing x & y
  public boolean union(int x, int y) {
    int xi=findSet(x);
    int yi=findSet(y);

    if (xi==yi) {return false;}
    if (xi==-1||yi==-1) {return false;}

    int xh=heights[xi];
    int yh=heights[yi];

    if (xh<yh) {
      trees[xi]=yi;
      heights[xi]=yh;
    } else if (yh<xh) {
      trees[yi]=xi;
      heights[yi]=xh;
    } else {// same height
      trees[xi]=yi;
      heights[xi]=xh+1;
      heights[yi]=yh+1;
    }

    return true;
  }

  // NOTE -1 = not in any set
  // returns identity of set containg x
  public int findSet(int x) {
    if (x<0||x>=trees.length||trees[x]==-2) {return -1;} // x is unassigned

    while (trees[x]!=-1) {
      x=trees[x];
    }

    return x;
  }

  // expands existing array to size n
  private int[] expand(int n) {
    int[] newTrees=new int[n+1];
    int[] newHeights=new int[n+1];
    int i=0;

    while (i<trees.length) { // copy existing elements
      newTrees[i]=trees[i];
      newHeights[i]=heights[i];
      i++;
    }

    while (i<n+1) { // set to new elements to non-assigned
      newTrees[i]=-2;
      newHeights[i]=0;
      i++;
    }

    heights=newHeights;
    return newTrees;
  }

}

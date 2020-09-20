import java.util.List;
import java.util.ArrayList;

public class Node234 {
  public int size;
  public int[] keys; // data
  public List<Node234> children;
  public Node234 parent;

  // make 2 node with given key
  public Node234(int key) {
    this.size=2;
    keys=new int[1];
    keys[0]=key;
    children=new ArrayList<>();
    this.parent=null;
    children.add(null); children.add(null);
  }

  // always add child when addign key
  public void addKey(int key) {
    int[] newKeys=new int[size];
    // merge
    int i=0;
    while (i<size-1) {
      if (keys[i]<key) {
        newKeys[i]=keys[i];
        i++;
      } else {break;}
    }

    // new child
    Node234 displace=children.get(i);
    if (displace==null) {
      children.add(i,null);
    } else if (displace.keys[displace.size-2]<key) {
      children.add(i+1,null);
    } else {
      children.add(i,null);
    }

    newKeys[i]=key;
    i++;
    while (i<size) {
      newKeys[i]=keys[i-1];
      i++;
    }
    size++;
    keys=newKeys;

  }

  public void removeKey(int key) {
    int[] newKeys=new int[size-2];
    int i=0;
    while (i<size-1) {
      if (keys[i]!=key) {
        newKeys[i]=keys[i];
        i++;
      } else {break;}
    }
    i++;
    while (i<size-1) {
      newKeys[i-1]=keys[i];
      i++;
    }
    keys=newKeys;
    size--;
  }

  // add key before child
  public boolean addChild(Node234 child) {
    if (child==null) {return true;}
    int leastKey=child.keys[0];
    int greatestKey=child.keys[child.size-2];

    if (greatestKey<keys[0]) { // leftmost child
      if (children.get(0)==null) {
        children.set(0,child);
        child.parent=this;
        return true;
      } else { // already has child
        return false;
      }
    }

    if (size==2) {
      if (leastKey>keys[0]) { // leftmost child
        if (children.get(1)==null) {
          children.set(1,child);
          child.parent=this;
          return true;
        } else { // already has child
          return false;
        }
      }
    }

    if (size>2) {
      if (leastKey>keys[0]&&greatestKey<keys[1]) {
        if (children.get(1)==null) { // middle child
          children.set(1,child);
          child.parent=this;
          return true;
        } else {
          return false;
        }
      }
    }

    if (size==3) {
      if (children.get(2)==null) { // rightmost child
        children.set(2,child);
        child.parent=this;
        return true;
      } else { // already has child
        return false;
      }
    }

    // size ==4
    if (leastKey>keys[1]&&greatestKey<keys[2]) { // middle right
      if (children.get(2)==null) {
        children.set(2,child);
        child.parent=this;
        return true;
      } else {
        return false;
      }
    }

    if (children.get(3)==null) {
      children.set(3,child);
      child.parent=this;
      return true;
    } else {
      return false;
    }
  }

  public boolean removeChild(Node234 child) {
    Integer index=children.indexOf(child);
    if (index==null) {return false;}
    children.set(index,null);
    child.parent=null;
    return true;
  }

  public boolean adjacent(Node234 n1, Node234 n2) {
    int i1=children.indexOf(n1);
    int i2=children.indexOf(n2);

    if (Math.abs(i1-i2)==1) {return true;}
    return false;
  }

}

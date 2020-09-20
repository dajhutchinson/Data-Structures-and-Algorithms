import java.util.Set;
import java.util.HashSet;

public class Tree234 {

  public Node234 root;

  // NOTE only 2Node can have no children
  public Tree234(int root) {
    this.root=new Node234(root);
    //map=new HashMap<>();
  }

  // insert key to tree
  // returns false if element is already in tree
  public boolean insert(int key) {
    if (root==null) { // make element root if there is no root
      root=new Node234(key);
      return true;
    }

    // find node to place in
    Node234 toCheck=root;
    while (true) {
      if (toCheck.size==4) {toCheck=split(toCheck);} // split all four nodes

      // check if we are at the bottom of the tree
      boolean leaf=true;
      for (Node234 child:toCheck.children) {if (child!=null) {leaf=false;}}

      // if at bottom add key
      if (leaf) {
        toCheck.addKey(key);
        return true;
      }

      // otherwise, follow correct child down
      int i=0;
      while (i<toCheck.size-1) {
        if (toCheck.keys[i]==key) {return false;} // already in
        if (toCheck.keys[i]>key)  {break;}
        i++;
      }

      toCheck=toCheck.children.get(i); // next node to check
    }
  }

  // find key in tree
  public Integer find(int key) {
    Node234 toCheck=root; // element to check

    while (true) {
      if (toCheck==null) {return null;} // reached end of tree
      if (toCheck.size==4) {toCheck=split(toCheck);} // split all encountered 4 nodes

      int i=0;
      while (i<toCheck.size-1) { // compare to keys
        if (toCheck.keys[i]==key) {return key;} // element found
        if (toCheck.keys[i]>key)  {break;} // found new upper limit
        i++; // next key
      }
      toCheck=toCheck.children.get(i); // follow appropriate child
    }
  }

  // remove key from tree
  public boolean delete(int key) {
    // if there are no elements
    if (root==null) {return false;}

    Node234 toCheck=root;
    while (true) { // find element in tree

      // delete leaves
      boolean leaf=true; // are we at bottom of tree
      for (Node234 child:toCheck.children) {if (child!=null) {leaf=false;}}

      if (leaf&&toCheck.size>2) {
        toCheck.removeKey(key);
        return true;
      } else if (leaf&&toCheck==root) { // tree is only 2 node
        root=null;
        return true;
      } else if (leaf) { // fuse or transfer 2 nodes and then delete, if fuse go up one level
        Node234 parent=toCheck.parent;
        Node234 leftNeighour=null;
        Node234 rightNeighour=null;
        int pos=parent.children.indexOf(toCheck);

        if (pos==0) {
          rightNeighour=parent.children.get(1);
        } else if (pos==parent.size-1) {
          leftNeighour=parent.children.get(pos-1);
        } else {
          leftNeighour=parent.children.get(pos-1);
          rightNeighour=parent.children.get(pos+1);
        }

        Node234 next=null;
        if (leftNeighour!=null) {
          next=fuse(toCheck, leftNeighour);
          if (next!=null) {
            toCheck=next;
            continue;
          }
          next=transfer(toCheck, leftNeighour);
          if (next!=null) {
            toCheck=next;
            continue;
          }
        }

        if (rightNeighour!=null) {
          next=fuse(toCheck, rightNeighour);
          if (next!=null) {
            toCheck=next;
            continue;
          }
          next=transfer(toCheck, rightNeighour);
          if (next!=null) {
            toCheck=next;
            continue;
          }
        }

        return false;
      }

      // find correct child
      boolean here=false;
      int i=0;
      while (i<toCheck.size-1) {
        if (toCheck.keys[i]==key) {here=true; break;} // already in
        if (toCheck.keys[i]>key)  {break;}
        i++;
      }

      if (here) { // delete key from !leaf
        int pred=predecessor(key);
        delete(pred);
        for (i=0; i<toCheck.size-1; i++) {
          if (toCheck.keys[i]==key) {
            toCheck.keys[i]=pred;
            return true;
          }
        }
        return false;
      }

      toCheck=toCheck.children.get(i);

      // not leaves

    }
  }

  // find greatest k st k<key
  public Integer predecessor (int key) {
    Node234 toCheck=root;
    while (true) {
      if (toCheck==null) {return null;}
      if (toCheck.size==4) {toCheck=split(toCheck);}
      int i=0;
      while (i<toCheck.size-1) {
        if (toCheck.keys[i]==key) { // go left then always right
          return max(toCheck.children.get(i));
        }
        if (toCheck.keys[i]>key)  {break;}
        i++;
      }
      toCheck=toCheck.children.get(i);
    }
  }

  // return max value of children, or given if leaf
  private Integer max(Node234 node) {
    boolean leaf=true;
    for (Node234 child:node.children) {
      if (child!=null) {leaf=false;}
    }

    // if leaf
    if (leaf) {
      return node.keys[node.size-2];
    }

    // if not
    return max(node.children.get(node.size-1));
  }

  public Set<Integer> rangeFind(int min, int max) {
    Set<Integer> set=new HashSet<>();

    // this is really naive
    for (int i=min; i<max+1; i++) {
      if (find(i)!=null) {set.add(i);}
    }

    return set;
  }

  // returns parent
  private Node234 split(Node234 node) {
    if (node.size!=4) {return node;}

    if (node==this.root) {
      return splitRoot();
    }

    Node234 parent=node.parent;

    Node234 left =new Node234(node.keys[0]);
    left.addChild(node.children.get(0));
    left.addChild(node.children.get(1));
    Node234 right=new Node234(node.keys[2]);
    right.addChild(node.children.get(2));
    right.addChild(node.children.get(3));

    int middle=node.keys[1];
    node.removeKey(middle);
    parent.removeChild(node);
    parent.addKey(middle);
    parent.addChild(left);
    parent.addChild(right);

    return parent;
  }

  private Node234 splitRoot() {
    Node234 left =new Node234(root.keys[0]);
    left.addChild(root.children.get(0));
    left.addChild(root.children.get(1));
    Node234 right=new Node234(root.keys[2]);
    right.addChild(root.children.get(2));
    right.addChild(root.children.get(3));

    int middle=root.keys[1];
    Node234 newRoot=new Node234(middle);
    newRoot.addChild(left);
    newRoot.addChild(right);
    root=newRoot;

    return root;
  }

  // returns parent
  private Node234 fuse(Node234 n1, Node234 n2) {
    if (n1==n2) {return null;}
    if (n1.size!=2||n2.size!=2) {return null;}
    if (n1.parent!=n2.parent) {return null;}

    Node234 parent=n1.parent;

    if (!parent.adjacent(n1, n2)) {return null;}

    int n1key=n1.keys[0];
    int n2key=n2.keys[0];
    Node234 min=n1key<n2key ? n1 : n2;
    Node234 max=n1key<n2key ? n2 : n1;
    int minKey=min.keys[0];
    int maxKey=max.keys[0];

    // find key that forms boundary of n1, n2
    if (parent==this.root&&root.size==2) {return fuseRoot();}

    Integer key=null;
    for (int k:parent.keys) {
      if (k>minKey&&k<maxKey) {key=k;break;}
    }
    if (key==null) {return null;}

    parent.removeKey(key);

    Node234 newNode=new Node234(minKey);
    newNode.addKey(key);
    newNode.addKey(maxKey);
    newNode.addChild(n1.children.get(0));
    newNode.addChild(n1.children.get(1));
    newNode.addChild(n2.children.get(0));
    newNode.addChild(n2.children.get(1));

    parent.removeChild(n1);
    parent.removeChild(n2);

    parent.addChild(newNode);

    return parent;
  }

  private Node234 fuseRoot() {
    Node234 left=root.children.get(0);
    Node234 right=root.children.get(1);

    Node234 newNode=new Node234(left.keys[0]);
    newNode.addKey(root.keys[0]);
    newNode.addKey(right.keys[0]);

    newNode.addChild(left.children.get(0));
    newNode.addChild(left.children.get(1));
    newNode.addChild(right.children.get(0));
    newNode.addChild(right.children.get(1));

    root=newNode;
    return root;
  }

  // returns new version of n1
  private Node234 transfer(Node234 n1, Node234 n2) {
    if (n1==null||n2==null) {return null;}
    if (n1.parent!=n2.parent) {return null;}
    Node234 parent=n1.parent;
    if (!parent.adjacent(n1,n2)) {return null;}

    // find lesser
    int n1min=n1.keys[0];
    int n2min=n2.keys[0];

    Node234 left =n1min<n2min ? n1 : n2;
    Node234 right=n1min<n2min ? n2 : n1;

    // left is 2Node
    if (left.size==2) {
      // find key to move
      int lmax=left.keys[0];
      int rmin=right.keys[0];

      Integer mid=null;
      for (int k:parent.keys) {
        if (lmax<k&&k<rmin) {mid=k; break;}
      }
      if (mid==null) {return null;}

      // update parent keys
      for (int i=0; i<parent.size-1; i++) {
        if (parent.keys[i]==mid) {
          parent.keys[i]=rmin;
      }}
      left.addKey(mid);
      right.removeKey(rmin);

      Node234 child=right.children.get(0);
      if (child!=null) {
        right.removeChild(child);
        right.children.remove(0);
        left.addChild(child);
        return n1min<n2min ? left : right;
      }
      return n1min<n2min ? left : right;

    } else { // left is 3Node
      int lmax=left.keys[1];
      int rmin=right.keys[0];

      Integer mid=null;
      for (int k:parent.keys) {
        if (lmax<k&&k<rmin) {mid=k; break;}
      }
      if (mid==null) {
        return null;
      }

      for (int i=0; i<parent.size-1; i++) {
        if (parent.keys[i]==mid) {
          parent.keys[i]=lmax;
      }}
      right.addKey(mid);
      left.removeKey(lmax);

      Node234 child=left.children.get(2);
      left.removeChild(child);
      left.children.remove(2);
      right.addChild(child);
      return n1min<n2min ? left : right;
    }
  }

}

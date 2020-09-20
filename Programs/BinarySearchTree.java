import java.util.HashSet;
import java.util.Set;

public class BinarySearchTree<E extends Comparable<? super E>> implements DynamicSearchStructures<E> {

  private BinarySearchTreeNode<E> root;

  public BinarySearchTree(E e) {
    this.root=new BinarySearchTreeNode<E>(e);
  }
  // TODO rotations
  @Override
  public boolean delete(E e) {
    BinarySearchTreeNode<E> toCheck=root; // element to check
    BinarySearchTreeNode<E> parent=null; // last element checked

    while (true) {
      if (toCheck.data.equals(e)) { // found node
        if (toCheck.right==null&&toCheck.left==null) { // no children
          if (toCheck==root) { // delete root
            root=null;
            return true;
          }
          return parent.delete(toCheck); // remove element from parent/tree
        } else if (toCheck.right==null) { // only left child
          if (toCheck==root) { // make left child  root
            root=toCheck.left;
            return true;
          }
          return parent.replace(toCheck,toCheck.left); // move left child up
        } else if (toCheck.left==null) { // only right child
          if (toCheck==root) { // make right child root
            root=toCheck.right;
            return true;
          }
          return parent.replace(toCheck,toCheck.right); // move right child up
        } else { // two children
          E pre=predecessor(e); // find predecessor
          delete(pre); // delete predecessor
          toCheck.data=pre; // replace element with predecessor
          return true;
        }
      } else if (e.compareTo(toCheck.data)>0) { // follow right subtree
        if (toCheck.right==null) {return false;} // no subtree
        parent=toCheck; // next element
        toCheck=toCheck.right;
      } else {
        if (toCheck.left==null) {return false;} // no subtree
        parent=toCheck; // next element
        toCheck=toCheck.left;
      }
    }
  }

  @Override
  public E find(E e) {
    BinarySearchTreeNode<E> toCheck=root;

    while (true) {
      if (toCheck==null) {return null;} // reached leaf
      if (e.equals(toCheck.data)) {return e;} // element found
      if (e.compareTo(toCheck.data)>0) { // right subtree
        if (toCheck.right==null) {return null;}
        toCheck=toCheck.right; // progress
      } else { // left
        if (toCheck.left==null) {return null;}
        toCheck=toCheck.left; // progress
      }
    }
  }

  @Override
  public boolean insert(E e) {
    if (root==null) { // Make root if no root
      root=new BinarySearchTreeNode<>(e);
    }

    BinarySearchTreeNode<E> toCheck=root;
    while (true) {
      if (e.compareTo(toCheck.data)>0) { // follow right subtree
        if (toCheck.right==null) { // no child, insert element
          toCheck.right=new BinarySearchTreeNode<>(e);
          return true;
        }
        toCheck=toCheck.right;
      } else if (e.compareTo(toCheck.data)<0){ // follow left subtree
        if (toCheck.left==null) { // no subtree, insert element
          toCheck.left=new BinarySearchTreeNode<>(e);
          return true;
        }
        toCheck=toCheck.left;
      } else { // key is not unique
        return false;
      }
    }
  }

  // null if e not in tree
  @Override
  public E predecessor(E e) {
    BinarySearchTreeNode<E> toCheck=root;
    BinarySearchTreeNode<E> parent=null;

    while (true) {
      if (toCheck==null) {return null;}
      if (e.equals(toCheck.data)) {
        if (toCheck.left==null&&toCheck.right==null) {
          return parent.data;
        } else {
          parent=toCheck;
          toCheck=toCheck.left;
          while (toCheck!=null) {
            parent=toCheck;
            toCheck=toCheck.right;
          }
          return parent.data;
        }
      } else if (e.compareTo(toCheck.data)>0) {
        parent=toCheck;
        toCheck=toCheck.right;
      } else {
        parent=toCheck;
        toCheck=toCheck.left;
      }
    }
  }

  // TODO
  @Override
  public Set<E> rangeFind(E min, E max) {
    return null;
  }
}

class BinarySearchTreeNode<E extends Comparable<? super E>> {
  public E data;
  public BinarySearchTreeNode<E> left;
  public BinarySearchTreeNode<E> right;

  public BinarySearchTreeNode(E data) {
    this.data=data;
    this.left=null;
    this.right=null;
  }

  protected boolean delete(BinarySearchTreeNode<E> node) {
    if (left==node) {
      left=null;
      return true;
    } else if (right==node) {
      right=null;
      return true;
    }
    return false;
  }

  protected boolean replace(BinarySearchTreeNode<E> remove, BinarySearchTreeNode<E> add) {
    if (left==remove) {
      left=add;
      return true;
    } else if (right==remove) {
      right=add;
      return true;
    } else {
      return false;
    }
  }

}

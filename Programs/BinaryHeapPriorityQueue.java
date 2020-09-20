import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class BinaryHeapPriorityQueue<E> implements PriorityQueue<E> {

  List<Tuple<Integer,E>> heap;

  public BinaryHeapPriorityQueue() {
    heap=new ArrayList<>();
  }

  public boolean insert(E e, int key) {
      Tuple<Integer,E> tuple=new Tuple<>(key, e);
      boolean succ=heap.add(tuple); // bottom of heap
      int pos=heap.indexOf(tuple);

      while (pos!=0) { // not at top
        Tuple<Integer,E> parent=heap.get((pos-1)/2);
        if (parent.first>key) { // parent has greater key, swap
          succ&=swap(parent, tuple);
          pos=(pos-1)/2;
        } else { // parent has lesser key, done
          return succ;
        }
      }
      // at top
      return succ;
  }

  public boolean decreaseKey(E e, int key) {
      // if key < parent.key swap
      // else stop
    ListIterator<Tuple<Integer,E>> iterator=heap.listIterator();
    Tuple<Integer,E> toUpdate=null;

    // find element
    while (toUpdate==null&&iterator.hasNext()) {
      Tuple<Integer,E> t=iterator.next();
      if (t.second.equals(e)) {
        toUpdate=t;
    }}

    if (toUpdate==null) {
      return false;
    } // element not in queue

    // update key
    if (toUpdate.first<=key) {return false;} // must decrease key
    toUpdate.first=key;
    int pos=heap.indexOf(toUpdate);

    // key comparing to parent until top
    while (pos!=0) {
      Tuple<Integer,E> parent=heap.get((pos-1)/2);
      if (parent.first>toUpdate.first) { // parent has greater key value, swap
        swap(parent, toUpdate);
        pos=(pos-1)/2;
      } else { // parent has lesser key value, done
        return true;
      }
    }

    // top of heap
    return true;
  }

  public E extractMin() {
    Tuple<Integer,E> first=heap.get(0); // to extract
    Tuple<Integer,E> last=heap.get(heap.size()-1);

    swap(first, last);
    heap.remove(first);
    int pos=0;

    while((2*pos)+1<heap.size()) { // While has children
      Tuple<Integer,E> left=heap.get((2*pos)+1);
      Tuple<Integer,E> right=null;

      if ((2*pos)+2<heap.size()) {right=heap.get((2*pos)+2);}

      if (right!=null) {
        if (left.first<right.first) {
          if (left.first<last.first) {
            swap(left, last);
            pos=(2*pos)+1;
          } else {
            return first.second;
          }
        } else {
          if (right.first<last.first) {
            swap(right, last);
            pos=(2*pos)+2;
          } else {
            return first.second;
          }
        }
      } else {
        if (left.first<last.first) {
          swap(left, last);
          pos=(2*pos)+1;
        } else {
          return first.second;
        }
      }
    }

    return first.second;
  }

  public boolean swap(Tuple<Integer,E> x, Tuple<Integer,E> y) {
    int xpos=heap.indexOf(x);
    int ypos=heap.indexOf(y);

    heap.set(xpos, y);
    heap.set(ypos, x);

    return true;
  }

  public void test() {
    int i=0;
    for (Tuple<Integer,E> tuple:heap) {
      System.out.printf("<%d>key=%d\n",i,tuple.first);
      i++;
    }
  }

  @Override
  public boolean empty() {
    return heap.size()==0;
  }

}

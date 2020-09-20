import java.util.LinkedList;
import java.util.ListIterator;

public class SortedLinkedPriorityQueue<E> implements PriorityQueue<E> {

  private LinkedList<Tuple<Integer,E>> list;

  public SortedLinkedPriorityQueue() {
    list=new LinkedList<>();
  }

  public boolean insert(E e, int key) {
    int i=0;
    Tuple<Integer,E> t=new Tuple<>(key, e);
    for (Tuple<Integer,E> tuple:list) {
      if (tuple.first>=key) {
        list.add(i, t);
        return true;
      }
      i++;
    }
    list.add(i, t);
    return true;
  }

  public boolean decreaseKey(E e, int key) {
    ListIterator<Tuple<Integer,E>> iterator=list.listIterator(0);
    Tuple<Integer,E> tuple; //tuple to update

    while (true) {
      tuple=iterator.next();
      if (tuple.second==e&&key<tuple.first) {
        list.remove(tuple);
        return insert(e,key);
      } else if (!iterator.hasNext()) {return false;}
    }
  }

  public E extractMin() {
    E e=list.get(0).second;
    list.removeFirst();
    return e;
  }

  @Override
  public boolean empty() {
    return list.size()==0;
  }

}

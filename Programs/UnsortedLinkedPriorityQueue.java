import java.util.LinkedList;

public class UnsortedLinkedPriorityQueue<E> implements PriorityQueue<E> {

  private LinkedList<Tuple<Integer,E>> list;

  public UnsortedLinkedPriorityQueue() {
    list=new LinkedList<>();
  }

  public boolean insert(E e, int key) {
    Tuple<Integer,E> tuple=new Tuple<>(new Integer(key), e);
    return list.add(tuple);
  }

  public boolean decreaseKey(E e, int key) {
    for (Tuple<Integer, E> tuple:list) {
      if (tuple.second==e) {
        if (tuple.first<=key) { // can only decrease key
          return false;
        }
        tuple.first=new Integer(key);
        return true;
    }}
    return false;
  }

  public E extractMin() {
    int min=Integer.MAX_VALUE;
    E  emin=null;
    Tuple<Integer,E> t=null;
    for (Tuple<Integer,E> tuple:list) {
      if (tuple.first<min) {
        min=tuple.first;
        emin=tuple.second;
        t=tuple;
      }
    }
    list.remove(t);
    return emin;
  }

  @Override
  public boolean empty() {
    return list.size()==0;
  }

}

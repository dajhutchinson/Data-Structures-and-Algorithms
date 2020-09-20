import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Queue<E> implements Iterable<E> {

  private List<E> list;

  public Queue() {
    list=new ArrayList<>();
  }

  public boolean add(E e) {
    return list.add(e);
  }

  public E element() {
    return list.get(0);
  }

  public boolean empty() {
    return list.size()==0;
  }

  public boolean offer(E e) {
    return list.add(e);
  }

  public E peek() {
    if (empty()) {return null;}
    return element();
  }

  public E poll() {
    if (empty()) {return null;}
    E e=element();
    list.remove(e);
    return e;
  }

  public E remove() {
    E e=element();
    list.remove(e);
    return e;
  }

  public Iterator<E> iterator() {
    Iterator<E> iterator=new Iterator<E>() {
      private int currentIndex=0;

      @Override
      public boolean hasNext() {
        return currentIndex<list.size()&&list.get(currentIndex)!=null;
      }

      @Override
      public E next() {
        E e=list.get(currentIndex);
        currentIndex++;
        return e;
      }

      @Override
      public void remove() {
        list.remove(currentIndex-1);
      }
    };

    return iterator;
  }

}

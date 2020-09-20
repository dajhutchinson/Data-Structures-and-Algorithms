import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Stack<E> implements Iterable<E> {

  private List<E> list;

  public Stack() {
    list=new ArrayList<>();
  }

  public boolean empty() {
    return list.size()==0;
  }

  public E peek() {
    if (empty()) {return null;}
    return list.get(list.size()-1);
  }

  public E pop() {
    E e=peek();
    list.remove(e);
    return e;
  }

  public E push(E e) {
    list.add(e);
    return e;
  }

  public int search(E e) {
    return list.size()-list.indexOf(e);
  }

  public Iterator<E> iterator() {
    Iterator<E> iterator=new Iterator<E>() {
      private int currentIndex=list.size()-1;

      @Override
      public boolean hasNext() {
        return currentIndex>-1&&list.get(currentIndex)!=null;
      }

      @Override
      public E next() {
        E e=list.get(currentIndex);
        currentIndex--;
        return e;
      }

      @Override
      public void remove() {
        list.remove(currentIndex+1);
      }
    };

    return iterator;
  }

}

public interface PriorityQueue<E> {
  public boolean insert(E e, int key);
  public boolean decreaseKey(E e, int key);
  public E extractMin();
  public boolean empty();
}

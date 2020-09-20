import java.util.Set;

public interface DynamicSearchStructures<E> {
  public boolean delete(E e);
  public E find(E e);
  public boolean insert(E e);
  public E predecessor(E e);
  public Set<E> rangeFind(E min, E max);
}

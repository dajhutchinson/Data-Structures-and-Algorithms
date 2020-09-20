import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class SkipList<E extends Comparable<? super E>> implements DynamicSearchStructures<E> {

  private SkipListNode<E> initial;
  private SkipListNode<E> terminal;

  public SkipList(E initial, E terminal) {
    this.initial=new SkipListNode<>(initial);
    this.terminal=new SkipListNode<>(terminal);
    addNode(this.initial, this.terminal, null);
  }

  private void addNode(SkipListNode<E> before, SkipListNode<E> n, SkipListNode<E> next) {
    before.next(n);
    n.next(next);
  }

  private void deleteNode(SkipListNode<E> before, SkipListNode<E> n) {
    before.next(n.next());
    n.below(null);
  }

  @Override
  public boolean delete(E element) {
    if (element.compareTo(terminal.data())>=0||element.compareTo(initial.data())<=0) {return false;} // out of range

    // find first occurence (in highest row), not element before
    // delete occurence & connect before to next
    // go below before, go right as far as possible repeat

    SkipListNode<E> toCheck=initial;
    boolean found=false;

    while (toCheck!=null) {
      if (toCheck.next().data().equals(element)) {
        found=true;
        deleteNode(toCheck, toCheck.next());
        toCheck=toCheck.below();
      } else if (toCheck.next().data().compareTo(element)>0) {
        toCheck=toCheck.below();
      } else {
        toCheck=toCheck.next();
      }
    }

    // delete excess rows
    while (initial.below()!=null) {
      if (initial.below().next()==terminal.below()) {
        initial=initial.below();
        terminal=terminal.below();
      } else {
        break;
      }
    }

    return found;
  }

  @Override
  public E find(E element) {
    if (element.compareTo(terminal.data())>0||element.compareTo(initial.data())<0) {return null;} // out of range

    SkipListNode<E> toCheck=initial;

    // not on bottom row
    while (toCheck.below()!=null) {
      while (toCheck.next().data().compareTo(element)<0) { // go right as far as possible
        toCheck=toCheck.next();
      } // turn found
      toCheck=toCheck.below(); // go down a level
    }

    while (true) {
      if (toCheck.next()==null) {break;}
      if (toCheck.next().data().compareTo(element)<=0) {
        toCheck=toCheck.next();
      } else {
        break;
      }
    }

    if (toCheck.data().equals(element)) {return toCheck.data();}
    return null;
  }

  @Override
  public boolean insert(E data) {
    if (data.compareTo(terminal.data())>=0||data.compareTo(initial.data())<=0) {return false;} // out of range

    List<SkipListNode<E>> turns=new ArrayList<>(); // nodes before new Node in each row
    int rows=1;
    SkipListNode<E> toCheck=initial;

    // not on bottom row
    while (toCheck.below()!=null) {
      while (toCheck.next().data().compareTo(data)<0) { // go right as far as possible
        toCheck=toCheck.next();
      } // turn found
      turns.add(0,toCheck); // save turning point
      toCheck=toCheck.below(); // go down a level
      rows++;
    }

    // on bottom row
    while (toCheck.next().data().compareTo(data)<0) {
      toCheck=toCheck.next();
    }

    turns.add(0,toCheck); // not necessary?

    //SkipListNode<E> newNode=new SkipListNode<>(data);
    //this.addNode(toCheck, newNode, toCheck.next());
    //SkipListNode<E> below=newNode;

    Random r=new Random();
    int flip=r.nextInt(2);
    SkipListNode<E> below=null;

    do {
      // if not top row
      SkipListNode<E> node=new SkipListNode<>(data);
      SkipListNode<E> before=initial; // if new row
      if (turns.size()!=0) {
        before=turns.get(0);
        turns.remove(before);
      }
      addNode(before, node, before.next());
      node.below(below);
      below=node;
      if (rows==1) { // if top row, make new row
        SkipListNode<E> newInitial=new SkipListNode<>(initial.data());
        SkipListNode<E> newTerminal=new SkipListNode<>(terminal.data());
        newInitial.below(initial);
        newInitial.next(newTerminal);
        newTerminal.below(terminal);
        this.initial=newInitial;
        this.terminal=newTerminal;
      } else {
        rows--;
      }
      flip=r.nextInt(2);
    } while (flip==0);
    return true;
  }

  @Override
  public E predecessor(E element) {
    if (element.compareTo(terminal.data())>0||element.compareTo(initial.data())<0) {return null;} // out of range

    SkipListNode<E> toCheck=initial;

    // not on bottom row
    while (toCheck.below()!=null) {
      while (toCheck.next().data().compareTo(element)<0) { // go right as far as possible
        toCheck=toCheck.next();
      } // turn found
      toCheck=toCheck.below(); // go down a level
    }

    while (true) {
      if (toCheck.next().data().equals(element)) {return toCheck.data();}
      else if (toCheck.next().data().compareTo(element)<=0) {toCheck=toCheck.next();}
      else {return null;}
    }

  }

  @Override
  public Set<E> rangeFind(E min, E max) {
    if (min.compareTo(max)>0) {return null;}
    if (max.compareTo(terminal.data())>0||min.compareTo(initial.data())<0) {return null;} // out of range

    SkipListNode<E> toCheck=initial;

    // not on bottom row
    while (toCheck.below()!=null) {
      while (toCheck.next().data().compareTo(min)<0) { // go right as far as possible
        toCheck=toCheck.next();
      } // turn found
      toCheck=toCheck.below(); // go down a level
    }

    while (true) {
      if (toCheck.next()==null) {break;}
      if (toCheck.next().data().compareTo(min)<=0) {
        toCheck=toCheck.next();
      } else {
        break;
      }
    }

    Set<E> set=new HashSet<>();

    while (toCheck.data().compareTo(max)<=0) {
      set.add(toCheck.data());
      toCheck=toCheck.next();
      if (toCheck==null) {return set;}
    }

    return set;
  }

  public int height() {
    int i=1;
    SkipListNode<E> node=initial;
    while (node.below()!=null) {
      i++;
      node=node.below();
    }
    return i;
  }

  public void print() {
    SkipListNode<E> node=initial;
    do {
      SkipListNode<E> start=node;
      while (node.next()!=null) {
        System.out.print(node.data());
        if (!node.data().equals(terminal.data())) {System.out.print("-");}
        node=node.next();
      }
      System.out.println(node.data());
      node=start.below();
    } while (node!=null);
  }

}

class SkipListNode<E extends Comparable<? super E>> {

  private E data;
  private SkipListNode<E> next;
  private SkipListNode<E> below;

  public SkipListNode (E data) {
    this.data=data;
    this.next=null;
    this.below=null;
  }

  public SkipListNode<E> next() {
    return next;
  }

  public void next(SkipListNode<E> next) {
    this.next=next;
  }

  public SkipListNode<E> below() {
    return below;
  }

  public void below(SkipListNode<E> below) {
    this.below=below;
  }

  public E data() {
    return data;
  }

}

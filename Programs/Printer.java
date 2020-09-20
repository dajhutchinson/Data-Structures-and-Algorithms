import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Printer {

  // print List
  public static void print(List<Integer> c) {
    int n=c.size();
    if (n==0) {
      System.out.printf("[]");
    } else {
      System.out.printf("[");
      for (int i=0; i<n-1; i++) {
        print(c.get(i));
        System.out.printf(",");
      }
      print(c.get(n-1));
      System.out.printf("]");
    }
  }

  public static void print(Set<Integer> c) {
    int[] ints=new int[c.size()];
    int i=0;
    for (Integer e:c) {
      ints[i]=e;
      i++;
    }
    Printer.print(ints);
  }

  // print List
  public static void print(int[] c) {
    int n=c.length;
    if (n==0) {
      System.out.printf("[]");
    } else {
      System.out.printf("[");
      for (int i=0; i<n-1; i++) {
        System.out.printf("%d,",c[i]);
      }
      print(c[n-1]);
      System.out.printf("]");
    }
  }

  public static void print(double[] c) {
    int n=c.length;
    if (n==0) {
      System.out.printf("[]");
    } else {
      System.out.printf("[");
      for (int i=0; i<n-1; i++) {
        System.out.printf("%f,",c[i]);
      }
      System.out.print(c[n-1]);
      System.out.printf("]");
    }
  }

  public static void print(int[] c, int left, int right) {
    if (right-left<1) {
      System.out.printf("[]");
    } else {
      System.out.printf("[");
      for (int i=left; i<right; i++) {
        System.out.printf("%d,",c[i]);
      }
      print(c[right]);
      System.out.printf("]");
    }
  }

  public static void print(List<Integer> c, int left, int right) {
    if (right-left<0) {
      System.out.printf("[]");
    } else {
      System.out.printf("[");
      for (int i=left; i<right; i++) {
        System.out.printf("%d,",c.get(i));
      }
      print(c.get(right));
      System.out.printf("]");
    }
  }

  public static <E,F> void printEdgeSet(Set<Edge<E>> set, Class<F> c) {
    Iterator<Edge<E>> iterator=set.iterator();

    System.out.print("[");
    while(iterator.hasNext()) {
      Edge<E> edge=iterator.next();
      Printer.print(edge, c);
      if (iterator.hasNext()) {System.out.print(", ");}
    }
    System.out.print("]");
  }

  public static <E,F> void printNodeSet(Set<Node<E>> set, Class<F> c) {
    Iterator<Node<E>> iterator=set.iterator();

    System.out.print("[");
    while(iterator.hasNext()) {
      Node<E> node=iterator.next();
      Printer.print(node, c);
      if (iterator.hasNext()) {System.out.print(", ");}
    }
    System.out.print("]");
  }

  public static void print(BadMatchTable table, String s) {
    // Get unique characters
    Set<Character> chars=new HashSet<>();
    for (Character c:s.toCharArray()) {chars.add(c);}
    chars.add('*');

    // Create line to seperate
    String line="";
    for (int i=0; i<(2+(2*chars.size())-1); i++) {line+="-";}

    // Print table
    System.out.println(line);
    System.out.printf("|");
    for (Character c:chars) { // Print characters
      System.out.printf("%c|",c);
    }
    System.out.printf("\n%s\n|",line);
    for (Character c:chars) { // Print values
      System.out.printf("%d|",table.get(c));
    }
    System.out.printf("\n%s\n",line);
  }

  public static void print(GaleShapely gs, Map<String,List<String>> boys, Map<String,List<String>> girls) {
    Set<String> bNames=boys.keySet();
    Set<String> gNames=girls.keySet();

    String[] boyNames =new String[bNames.size()];
    String[] girlNames=new String[gNames.size()];
    int index=0;
    for (String s:bNames) {boyNames[index++]=s;}
    index=0;
    for (String s:gNames) {girlNames[index++]=s;}

    // Find length of longest names
    int longBoy=0;
    int longGirl=0;
    for (int i=0; i<boyNames.length; i++) {
      int blen=boyNames[i].length();
      if (blen>longBoy)  {longBoy=blen;}
      int glen=girlNames[i].length();
      if (glen>longGirl) {longGirl=glen;}
    }

    // Make horizontal line
    String line="+";
    for (int i=0;i<longGirl;i++) {line+="-";}
    line+="+";
    for (int i=0;i<longBoy;i++) {line+="-";}
    line+="+";

    // Print table
    System.out.println(line);
    for (String g:gNames) {
      for (int i=g.length(); i<longGirl; i++) {g +=" ";}
      String b=gs.get(g);
      for (int i=b.length(); i<longBoy; i++)  {b +=" ";}
      System.out.printf("|%s|%s|\n",g,b);
    }
    System.out.println(line);
  }

  public static void print(Map<String,String> map) {
    Set<String> keys=map.keySet();
    Collection<String> values=map.values();

    String[] keyArray=new String[keys.size()];
    String[] valueArray=new String[values.size()];
    int index=0;
    for (String s:keys) {keyArray[index++]=s;}
    index=0;
    for (String s:values) {valueArray[index++]=s;}

    // Find length of longest names
    int longKey=0;
    int longValue=0;
    for (int i=0; i<keyArray.length; i++) {
      int klen=keyArray[i].length();
      if (klen>longKey)  {longKey=klen;}
      if (valueArray[i]!=null) {
        int vlen=valueArray[i].length();
        if (vlen>longValue) {longValue=vlen;}
      }
    }

    // Make horizontal line
    String line="+";
    for (int i=0;i<longKey;i++) {line+="-";}
    line+="+";
    for (int i=0;i<longValue;i++) {line+="-";}
    line+="+";

    // Print table
    System.out.println(line);
    for (String k:keyArray) {
      for (int i=k.length(); i<longKey; i++) {k+=" ";}
      String v=map.get(k);
      if (v==null) {v="";}
      for (int i=v.length(); i<longValue; i++)  {v+=" ";}
      System.out.printf("|%s|%s|\n",k,v);
    }
    System.out.println(line);
  }

  public static void print(Integer i) {
    System.out.print(i);
  }

  public static void print(String text, String pattern, int offset) {
    System.out.println(text);
    for (int i=0; i<offset; i++) {pattern=" "+pattern;}
    System.out.println(pattern);
  }

  public static void print(String pattern, PrefixTable table) {
    System.out.printf("Prefix Table for %s\n", pattern);
    for (int i=0; i<pattern.length(); i++) {
      int v=table.get(i);
      System.out.printf("%s, %d (%s)\n", pattern.substring(0, i+1), v, pattern.substring(0,v));
    }
  }

  public static void print(String s) {
    System.out.print(s);
  }

  public static <E,F> void print(Graph<E> graph, Class<F> c) {
    Set<Node<E>> vertices=graph.vertices();

    for (Node<E> vertex:vertices) {
      Printer.print(vertex, c);
      System.out.println();
      Set<Edge<E>> edges=graph.edges(vertex);
      for (Edge<E> edge:edges) {
        Printer.print(edge, c);
        System.out.println();
      }
      System.out.println("-----------");
    }
  }

  public static <E,F> void print(Node<E> node, Class<F> c) {
    if (c.equals(String.class)) {
      System.out.printf("Vertex %s", (String) node.data);
    } else if (c.equals(Integer.class)) {
      System.out.printf("Vertex %d", (Integer) node.data);
    }
  }

  public static <E,F> void print(Edge<E> edge, Class<F> c) {
    if (c.equals(String.class)) {
      System.out.printf("Edge %s->%s (%f)", (String) edge.start.data, (String) edge.end.data, edge.weight);
    } else if (c.equals(Integer.class)) {
      System.out.printf("Edge %d->%d (%f)", (Integer) edge.start.data, (Integer) edge.end.data, edge.weight);
    }
  }

  public static void print(Node234 node, int n) {
    String indent="";
    for (int i=0;i<n;i++) {indent+="  ";}
    if (node==null) {System.out.println(indent+"NULL"); return;}
    System.out.printf("%s%d-Node\n", indent, node.size);
    System.out.printf("%sKeys ", indent);
    for (int key:node.keys) {
      System.out.printf("%d ", key);
    }
    System.out.printf("\n%sChildren\n", indent);
    for (Node234 child:node.children) {
        Printer.print(child, n+1);
    }
  }

  public static void print(Tree234 tree) {
    print(tree.root, 0);
  }


  // Prnt for all methods without own defined method
  public static <E> void print(E e) {
    System.out.printf("ERROR-Unkown datatype");
  }

  public static String imaginary(Tuple<Double,Double> t) {
    String s=t.first.toString();
    s+=t.second<0 ? "" : "+";
    s+=t.second.toString();
    s+="i";
    return s;
  }

}

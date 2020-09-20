import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

  public static void main(String[] args) {
    graphSearch();
  }

  private static void badMatchTable() {
    BadMatchTable table = new BadMatchTable("bb");
    Printer.print(table, "bb");
    table = new BadMatchTable("tooth");
    Printer.print(table, "tooth");
    table = new BadMatchTable("aaabbb");
    Printer.print(table, "aaabbb");
  }

  private static void insertionSort() {
    Integer[] array={9,8,7,6,5,4,3,2,1};
    List<Integer> list=new ArrayList<>(Arrays.asList(array));
    Sort.insertionSortExplained(list);
  }

  private static void mergeSort() {
    Integer[] array={9,8,7,6,5,4,3,2,1};
    List<Integer> list=new ArrayList<>(Arrays.asList(array));
    Sort.mergeSortExplained(list);
  }

  private static void galeShapely() {
    Map<String,List<String>> boys=new HashMap<>();
    Map<String,List<String>> girls=new HashMap<>();

    List<String> frank=new ArrayList<>();
    frank.add("Kate");
    frank.add("Mary");
    frank.add("Rhea");
    frank.add("Jill");

    List<String> dennis=new ArrayList<>();
    dennis.add("Mary");
    dennis.add("Jill");
    dennis.add("Rhea");
    dennis.add("Kate");

    List<String> mac=new ArrayList<>();
    mac.add("Kate");
    mac.add("Rhea");
    mac.add("Jill");
    mac.add("Mary");

    List<String> charlie=new ArrayList<>();
    charlie.add("Rhea");
    charlie.add("Mary");
    charlie.add("Kate");
    charlie.add("Jill");

    boys.put("Frank",frank);
    boys.put("Dennis",dennis);
    boys.put("Mac",mac);
    boys.put("Charlie",charlie);

    List<String> rhea=new ArrayList<>();
    rhea.add("Frank");
    rhea.add("Mac");
    rhea.add("Dennis");
    rhea.add("Charlie");

    List<String> mary=new ArrayList<>();
    mary.add("Mac");
    mary.add("Charlie");
    mary.add("Dennis");
    mary.add("Frank");

    List<String> kate=new ArrayList<>();
    kate.add("Dennis");
    kate.add("Mac");
    kate.add("Charlie");
    kate.add("Frank");

    List<String> jill=new ArrayList<>();
    jill.add("Charlie");
    jill.add("Dennis");
    jill.add("Frank");
    jill.add("Mac");

    girls.put("Rhea",rhea);
    girls.put("Mary",mary);
    girls.put("Kate",kate);
    girls.put("Jill",jill);

    GaleShapely gs=new GaleShapely(boys, girls, false);
  }

  private static void partition() {
    int[] array={9,8,7,6,5,4,3,2,1};
    OrderStatistics.partitionExplained(array, 0, array.length);
  }

  private static void randomSelect() {
    Integer[] array={3,8,1,7,4,6,2,5,9,15,14,13,12,11,10,16,17,18,19,20};
    List<Integer> list=new ArrayList<>(Arrays.asList(array));
    /*

    int max=list.size();
    int[] fail=new int[max];

    for (int i=0; i<max; i++) {
      int c=0;
      for (int j=0; j<5000; j++) {
        List<Integer> copy=new ArrayList<>(list);
        int k=OrderStatistics.randomSelect(copy, 0, list.size()-1, i);
        if (k!=i+1) {c++;}
      }
      fail[i]=c;
    }

    System.out.printf("\nfail=");
    Printer.print(fail);
    */
    System.out.println(OrderStatistics.randomSelectExplained(list, 0, list.size()-1, 10));
  }

  private static void bfprtSelect() {
    Integer[] array={3,8,1,7,4,6,2,5,9,15,14,13,12,11,10,16,17,18,20,19};
    List<Integer> list=new ArrayList<>(Arrays.asList(array));

    /*
    int min=0;
    int max=list.size();
    int[] fail=new int[max-min];

    for (int i=min; i<max; i++) {
      int c=0;
      for (int j=0; j<100; j++) {
        int k=OrderStatistics.bfprtSelect(list, 0, list.size()-1, i);
        if (k!=i+1) {c++;}
      }
      fail[i]=c;
    }
    Printer.print(fail);
    */

    System.out.println(OrderStatistics.bfprtSelect(list, 0, list.size()-1, 0));
  }

  private static void naivePatternMatcher() {
    System.out.println(PatternMatcher.naiveExplained("aaabbb", "bbb"));
  }

  private static void prefixTable() {
    String pattern="aaabbb";
    PrefixTable table=new PrefixTable(pattern);
    Printer.print(pattern, table);
  }

  private static void kmp() {
    System.out.println(PatternMatcher.kmpExplained("aabbaabaabb", "bb"));
  }

  private static void bmh() {
    System.out.println(PatternMatcher.bmh("aabbaabaabb", "bb"));
  }

  private static void finiteStateMachine() {
    // NOTE issue when text shorter than regex expression
    System.out.println(PatternMatcher.finiteStateMachine("abbaaa", "ab+a")); // aa invalid, abba valid aba valid
    System.out.println(PatternMatcher.finiteStateMachine("abbaaa", "ab*a"));
    System.out.println(PatternMatcher.finiteStateMachine("a", "ab?a"));

  }

  private static void graph(Graph<String> g) {
    System.out.println("Checking Cannot Add Multiple Vertices");
    Node<String> one=new Node<>("One");
    g.addVertex(one);
    g.addVertex(one);
    g.addVertex(one);
    Printer.printNodeSet(g.vertices(), String.class);

    System.out.println("\n\nAdding Vertices");
    Node<String> two=new Node<>("Two");
    Node<String> three=new Node<>("Three");
    Node<String> four=new Node<>("Four");
    g.addVertex(two);
    g.addVertex(three);
    Printer.printNodeSet(g.vertices(), String.class);

    System.out.println("\n\nAdding Vertices");
    Edge<String> e1=new Edge<>(one, two, 1.0);
    Edge<String> e2=new Edge<>(two, three, 3.5);
    g.addEdge(e1);
    g.addEdge(e2);
    g.addEdge(one, three, 2.0);
    g.addEdge(one, four, 2.0);
    g.addEdge(three, two, 1.5);
    g.addEdge(three, one, 1.5);
    Printer.printEdgeSet(g.edges(), String.class);
    System.out.println("\n\nVertices from One");
    Printer.printEdgeSet(g.edges(one), String.class);

    System.out.println("\n\nRemoving Edges");
    g.removeEdge(e2);
    g.removeEdge(one, three);
    Printer.printEdgeSet(g.edges(), String.class);

    System.out.println("\n\nRemoving Vertex");
    g.removeVertex(one);
    Printer.printNodeSet(g.vertices(), String.class);
    System.out.println();
    Printer.printEdgeSet(g.edges(), String.class);
  }

  private static void stack() {
    Stack<Integer> stack=new Stack<>();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    System.out.printf("Empty=%s\n", stack.empty() ? true:false);

    for (Integer i:stack) {
      System.out.println(i);
    }
    System.out.printf("Empty=%s\n", stack.empty() ? true:false);

    while (!stack.empty()) {
      System.out.println(stack.pop());
    }
    System.out.printf("Empty=%s\n", stack.empty() ? true:false);
  }

  private static void queue() {
    Queue<Integer> queue=new Queue<>();
    queue.add(1);
    queue.add(2);
    queue.add(3);

    System.out.printf("Empty=%s\n", queue.empty() ? true:false);

    for (Integer i:queue) {
      System.out.println(i);
    }
    System.out.printf("Empty=%s\n", queue.empty() ? true:false);

    while (!queue.empty()) {
      System.out.println(queue.poll());
    }
    System.out.printf("Empty=%s\n", queue.empty() ? true:false);
  }

  private static void priorityQueue(PriorityQueue<String> queue) {
    String hello="Hello";
    String world="World";
    queue.insert(hello,2);
    queue.insert(world,3);
    queue.insert("Dom",1);
    System.out.println(queue.extractMin()); // Dom
    System.out.println(queue.extractMin()); // Hello
    System.out.println(queue.extractMin()); // World

    queue.insert(hello,2);
    queue.insert(world,3);
    queue.insert("Dom",0);
    queue.decreaseKey(hello,-2);
    queue.decreaseKey(world,-1);
    System.out.println(queue.extractMin()); // Hello
    System.out.println(queue.extractMin()); // World
    System.out.println(queue.extractMin()); // Dom
  }

  private static void priorityQueueX(PriorityQueue<Integer> queue, int n) {
    List<Integer> elements=new ArrayList<>();

    for (int i=0; i<n; i++) {
      Integer in=new Integer(i);
      elements.add(in,i);
      queue.insert(in,i);
    }

    for (int i=0; i<n; i++) {
      Integer in=queue.extractMin();
      if (in!=i) {System.out.printf("%d FAILURE\n", in); return; }
      System.out.println(in);
    }

    for (int i=0; i<n; i++) {
      queue.insert(elements.get(i), i);
    }

    for (int i=0; i<n; i++) {
      boolean succ=queue.decreaseKey(elements.get(i), -i);
      if (!succ) {
        System.out.printf("UPDATE FAILED %d\n", elements.get(i));
      }
    }

    for (int i=0; i<n; i++) {
      Integer in=queue.extractMin();
      if (in!=n-(i+1)) {System.out.printf("%d FAILURE\n", in); return; }
      System.out.println(in);
    }

  }

  private static void disjointSets(DisjointSets sets) {
    System.out.println("Checking makeSet()");
    sets.makeSet(0);
    System.out.println(sets.findSet(0)); // 0
    System.out.println("Checking makeSet() extending range");
    sets.makeSet(5);
    System.out.println(sets.findSet(0)); // 0
    System.out.println(sets.findSet(5)); // 5
    System.out.println(sets.findSet(1)); // -1
    System.out.println(sets.findSet(-1)); // -1
    System.out.println(sets.findSet(6)); // -1
    System.out.println("Checking merge()");
    sets.makeSet(2);
    System.out.println("*");
    System.out.println(sets.findSet(0)); // 0
    System.out.println(sets.findSet(2)); // 2
    System.out.println(sets.findSet(5)); // 5
    sets.union(2,5);
    System.out.println("*");
    System.out.println(sets.findSet(0)); // 0
    System.out.println(sets.findSet(2)); // 5
    System.out.println(sets.findSet(5)); // 5
    sets.union(5,0);
    System.out.println("*");
    System.out.println(sets.findSet(0)); // 0
    System.out.println(sets.findSet(2)); // 0
    System.out.println(sets.findSet(5)); // 0
  }

  private static void horner() {
    //y=1/2+(3/4)x+x^2-x^3
    //1/2+3/4+1-1=5/4
    double[] coeff=new double[4];
    coeff[0]=0.5; coeff[1]=0.75; coeff[2]=1.0; coeff[3]=-1.0;
    System.out.println(FourierTransform.horner(coeff, 4, 1));
    //1/2+6/4+4-8=-2
    System.out.println(FourierTransform.horner(coeff, 4, 2));
  }

  private static void rootsOfUnity(int n) {
    for (int i=0; i<n+1; i++) {
      Tuple<Double,Double> t=FourierTransform.rootOfUnity(n,i);
      System.out.println(Printer.imaginary(t));
    }
  }

  private static void dft() {
    double[] coef=new double[4];
    coef[0]=0; coef[1]=0; coef[2]=1; coef[3]=-1;
    List<Tuple<Double,Double>> y=FourierTransform.dft(coef, 4);
    for (Tuple<Double,Double> a:y) {
      System.out.println(Printer.imaginary(a));
    }
  }

  private static void fft() {
    List<Tuple<Double,Double>> coef=new ArrayList<>();
    coef.add(new Tuple<>(0.0,0.0));
    coef.add(new Tuple<>(0.0,0.0));
    coef.add(new Tuple<>(1.0,0.0));
    coef.add(new Tuple<>(-1.0,0.0));
    List<Tuple<Double,Double>> y=FourierTransform.fft(coef, 4);
    for (Tuple<Double,Double> a:y) {
      System.out.println(Printer.imaginary(a));
    }
  }

  private static void graphSearch() {
    Graph<String> g=new LinkedGraph<>();

    Node<String> v1=new Node<>("v1");
    Node<String> v2=new Node<>("v2");
    Node<String> v3=new Node<>("v3");
    Node<String> v4=new Node<>("v4");

    g.addVertex(v1);
    g.addVertex(v2);
    g.addVertex(v3);
    g.addVertex(v4);

    g.addEdge(v1,v3,0.0);
    g.addEdge(v2,v1,3.0);
    g.addEdge(v2,v3,4.0);
    g.addEdge(v3,v4,0.0);
    g.addEdge(v4,v2,0.0);

    SingleSourceShortestPath.print(SingleSourceShortestPath.dijkstra(g, v1));
    SingleSourceShortestPath.print(SingleSourceShortestPath.dijkstra(g, v2));
    SingleSourceShortestPath.print(SingleSourceShortestPath.dijkstra(g, v3));
    SingleSourceShortestPath.print(SingleSourceShortestPath.dijkstra(g, v4));
    /*
    +----------+-+
    |Bristol   | 0|
    |Bath      | 1|
    |Cardiff   | 2|
    |Gloucester| 5|
    |Cheltenham| 6|
    |Swindon   |14|
    +----------+--+
    */
  }

  private static void kruskal() {
    Graph<String> g=new LinkedGraph<>();

    Node<String> bath=new Node<>("Bath"); // 1
    Node<String> bristol=new Node<>("Bristol"); // 0
    Node<String> cardiff=new Node<>("Cardiff"); // 2
    Node<String> cheltenham=new Node<>("Cheltenham"); //4
    Node<String> gloucester=new Node<>("Gloucester"); // 3
    Node<String> swindon=new Node<>("Swindon"); // 5

    g.addVertex(bath);
    g.addVertex(bristol);
    g.addVertex(cardiff);
    g.addVertex(cheltenham);
    g.addVertex(gloucester);
    g.addVertex(swindon);

    g.addEdge(bath,bristol,1.0);
    g.addEdge(bath,cardiff,3.0);
    g.addEdge(bath,cheltenham,7.0);
    g.addEdge(bath,gloucester,4.0);

    g.addEdge(bristol,bath,1.0);
    g.addEdge(bristol,cardiff,2.0);
    g.addEdge(bristol,cheltenham,6.0);

    g.addEdge(cardiff,cheltenham,4.0);
    g.addEdge(cardiff,bath,3.0);
    g.addEdge(cardiff,bristol,2.0);

    g.addEdge(cheltenham,bath,7.0);
    g.addEdge(cheltenham,bristol,6.0);
    g.addEdge(cheltenham,swindon,8.0);
    g.addEdge(cheltenham,gloucester,5.0);

    g.addEdge(gloucester,bath,4.0);
    g.addEdge(gloucester,cheltenham,5.0);

    g.addEdge(swindon,cheltenham,8.0);

    //Printer.print(g,String.class);
    Printer.print(Graph.kruskal(g), String.class);
  }

  public static void tree234Find() {
    Tree234 tree=new Tree234(41);
    tree.root.addKey(63);
    tree.root.addKey(86);

    Node234 threeTwo=new Node234(32);
    Node234 fiveOne=new Node234(51);
    Node234 sevenOne=new Node234(71);
    Node234 sixEight=new Node234(68);
    Node234 sevenFour=new Node234(74);
    Node234 nineOne=new Node234(91);
    nineOne.addKey(97);
    nineOne.addKey(101);
    Node234 eightEight=new Node234(88);
    Node234 nineThree=new Node234(93);
    Node234 oneZeroZero=new Node234(100);
    Node234 oneZeroFour=new Node234(104);

    sevenOne.addChild(sixEight);
    sevenOne.addChild(sevenFour);

    nineOne.addChild(eightEight);
    nineOne.addChild(nineThree);
    nineOne.addChild(oneZeroZero);
    nineOne.addChild(oneZeroFour);

    tree.root.addChild(threeTwo);
    tree.root.addChild(fiveOne);
    tree.root.addChild(sevenOne);
    tree.root.addChild(nineOne);

    System.out.println(tree.find(91));
    System.out.println("---------");
    Printer.print(tree);
  }

  public static void tree234Add() {
    Tree234 tree=new Tree234(50);
    tree.insert(25);
    tree.insert(75);
    tree.insert(100);
    tree.insert(0);
    tree.insert(10);
    tree.insert(5);
    tree.insert(1);
    tree.insert(2);
    Printer.print(tree);
  }

  public static void tree234Delete() {
    Tree234 tree=new Tree234(50);
    tree.insert(25);
    tree.insert(75);
    tree.insert(100);
    tree.insert(10);
    Printer.print(tree);
    System.out.println("------------");
    tree.delete(50);
    Printer.print(tree);
    System.out.println("------------");
    tree.delete(25);
    Printer.print(tree);
  }

  public static void tree234RangeFind() {
    Tree234 tree=new Tree234(50);
    tree.insert(25);
    tree.insert(75);
    tree.insert(100);
    tree.insert(0);
    tree.insert(10);
    tree.insert(5);
    tree.insert(1);
    tree.insert(2);
    Printer.print(tree);
    System.out.println("------------");
    Printer.print(tree.rangeFind(10, 60));
  }

  private static void skipList() {
    int n=10;
    SkipList<Integer> list=new SkipList<>(0, n);
    for (int i=1; i<n; i++) {
      list.insert(i);
    }
    list.print();
    for (int i=0; i<n+1; i++) {
      System.out.println(list.find(i));
    }
    for (int i=0; i<n+1; i++) {
      System.out.println(list.predecessor(i));
    }
    Printer.print(list.rangeFind(0,10));
    System.out.println();
    for (int i=1; i<n; i++) {
      list.delete(i);
    }
    list.print();
  }

  private static void binarySearchTree() {
    int n=10;
    BinarySearchTree<Integer> tree=new BinarySearchTree<>(n/2);
    for (int i=0; i<n; i++) {
      tree.insert(i);
    }
    for (int i=0; i<n; i++) {
      System.out.println(tree.find(i));
    }
    for (int i=0; i<n; i++) {
      tree.delete(i);
    }
    for (int i=0; i<n; i++) {
      System.out.println(tree.find(i));
    }
    tree=new BinarySearchTree<>(n/2);
    for (int i=0; i<n; i++) {
      tree.insert(i);
    }
    for (int i=0; i<n; i++) {
      System.out.println(tree.find(i));
    }
    for (int i=0; i<n; i++) {
      tree.delete(i);
    }
    for (int i=0; i<n; i++) {
      System.out.println(tree.find(i));
    }

  }

  public static void bellmanFord() {
    Graph<String> g=new LinkedGraph<>();

    Node<String> s=new Node<>("s");
    Node<String> v1=new Node<>("v1");
    Node<String> v2=new Node<>("v2");
    Node<String> v3=new Node<>("v3");
    Node<String> v4=new Node<>("v4");


    g.addVertex(s);
    g.addVertex(v1);
    g.addVertex(v2);
    g.addVertex(v3);
    g.addVertex(v4);

    g.addEdge(s, v1, 0.0);
    g.addEdge(s, v2, 0.0);
    g.addEdge(s, v3, 0.0);
    g.addEdge(s, v4, 0.0);
    g.addEdge(v1, v3, -2.0);
    g.addEdge(v2, v1, 4.0);
    g.addEdge(v2, v3, 3.0);
    g.addEdge(v3, v4, 2.0);
    g.addEdge(v4, v1, -1.0);

    SingleSourceShortestPath.print(SingleSourceShortestPath.bellmanFord(g, s));
  }

  public static void johnson() {
    Graph<String> g=new LinkedGraph<>();

    Node<String> bath=new Node<>("Bath"); // 1
    Node<String> bristol=new Node<>("Bristol"); // 0
    Node<String> cardiff=new Node<>("Cardiff"); // 2
    Node<String> cheltenham=new Node<>("Cheltenham"); //4
    Node<String> gloucester=new Node<>("Gloucester"); // 3
    Node<String> swindon=new Node<>("Swindon"); // 5

    g.addVertex(bath);
    g.addVertex(bristol);
    g.addVertex(cardiff);
    g.addVertex(cheltenham);
    g.addVertex(gloucester);
    g.addVertex(swindon);

    g.addEdge(bath,cardiff,3.0);
    g.addEdge(bath,gloucester,4.0);
    g.addEdge(bristol,bath,1.0);
    g.addEdge(bristol,cardiff,2.0);
    g.addEdge(cardiff,cheltenham,4.0);
    g.addEdge(cheltenham,bath,7.0);
    g.addEdge(cheltenham,bristol,6.0);
    g.addEdge(cheltenham,swindon,8.0);
    g.addEdge(gloucester,cheltenham,5.0);

    AllPairsShortestPath.print(AllPairsShortestPath.johnson(g));
    /*
    BATH
    +----------+-+
    |Bath      | 0|
    |Bristol   |13|
    |Cardiff   | 3|
    |Cheltenham| 7|
    |Gloucester| 4|
    |Swindon   |15|
    +----------+--+
    BRISTOL
    +----------+-+
    |Bath      | 1|
    |Bristol   | 0|
    |Cardiff   | 2|
    |Cheltenham| 6|
    |Gloucester| 5|
    |Swindon   |14|
    +----------+--+
    CARDIFF
    +----------+-+
    |Bath      |11|
    |Bristol   |10|
    |Cardiff   | 0|
    |Cheltenham| 4|
    |Gloucester|15|
    |Swindon   |12|
    +----------+--+
    CHELTENHAM
    +----------+-+
    |Bath      | 7|
    |Bristol   | 6|
    |Cardiff   | 8|
    |Cheltenham| 0|
    |Gloucester|11|
    |Swindon   | 8|
    +----------+--+
    GLOUCESTER
    +----------+-+
    |Bath      |12|
    |Bristol   |11|
    |Cardiff   |13|
    |Cheltenham| 5|
    |Gloucester| 0|
    |Swindon   |13|
    +----------+--+
    BATH
    +----------+-+
    |Bath      |NA|
    |Bristol   |NA|
    |Cardiff   |NA|
    |Cheltenham|NA|
    |Gloucester|NA|
    |Swindon   | 0|
    +----------+--+
    */
  }

  public static void linearProgramStandardForm() {
    // w x y z
    // x+y>=400
    double[] coef=new double[4];
    coef[0]=0; coef[1]=1; coef[2]=1; coef[3]=0;
    LinearInequality li1=new LinearInequality(coef, 400, true, false, false);

    // w+z>=400
    coef=new double[4];
    coef[0]=1; coef[1]=0; coef[2]=0; coef[3]=1;
    LinearInequality li2=new LinearInequality(coef, 600, true, false, false);

    // x+w<=700
    coef=new double[4];
    coef[0]=1; coef[1]=1; coef[2]=0; coef[3]=0;
    LinearInequality li3=new LinearInequality(coef, 700, false, false, false);

    // y+z<=700
    coef=new double[4];
    coef[0]=0; coef[1]=0; coef[2]=1; coef[3]=1;
    LinearInequality li4=new LinearInequality(coef, 800, false, false, false);

    // w>=0
    coef=new double[4];
    coef[0]=1; coef[1]=0; coef[2]=0; coef[3]=0;
    LinearInequality li5=new LinearInequality(coef, 0, true, false, false);

    // x>=0
    coef=new double[4];
    coef[0]=0; coef[1]=1; coef[2]=0; coef[3]=0;
    LinearInequality li6=new LinearInequality(coef, 0, true, false, false);

    // y>=0
    coef=new double[4];
    coef[0]=0; coef[1]=0; coef[2]=1; coef[3]=0;
    LinearInequality li7=new LinearInequality(coef, 0, true, false, false);

    // z>=0
    coef=new double[4];
    coef[0]=0; coef[1]=0; coef[2]=0; coef[3]=1;
    LinearInequality li8=new LinearInequality(coef, 0, true, false, false);

    List<LinearInequality> constraints=new ArrayList<>();
    constraints.add(li1);
    constraints.add(li2);
    constraints.add(li3);
    constraints.add(li4);

    List<LinearInequality> variableConstraints=new ArrayList<>();
    variableConstraints.add(li5);
    variableConstraints.add(li6);
    variableConstraints.add(li7);
    variableConstraints.add(li8);

    coef=new double[4];
    coef[0]=5; coef[1]=10; coef[2]=4; coef[3]=8;
    LinearProgram nonStandard=new LinearProgram(coef, false, constraints, variableConstraints);
    System.out.println(nonStandard.standardForm());

    // -2 -y <= 400
    coef=new double[4];
    coef[0]=0; coef[1]=-1; coef[2]=1; coef[3]=0;
    LinearInequality li9=new LinearInequality(coef, -400, false, false, false);

    coef=new double[4];
    coef[0]=-1; coef[1]=0; coef[2]=0; coef[3]=-1;
    LinearInequality li10=new LinearInequality(coef, -600, false, false, false);

    coef=new double[4];
    coef[0]=1; coef[1]=1; coef[2]=0; coef[3]=0;
    LinearInequality li11=new LinearInequality(coef, 700, false, false, false);

    coef=new double[4];
    coef[0]=0; coef[1]=0; coef[2]=1; coef[3]=1;
    LinearInequality li12=new LinearInequality(coef, 800, false, false, false);

    constraints=new ArrayList<>();
    constraints.add(li9);
    constraints.add(li10);
    constraints.add(li11);
    constraints.add(li12);

    coef=new double[4];
    coef[0]=-5; coef[1]=-10; coef[2]=-4; coef[3]=8;
    LinearProgram standard=new LinearProgram(coef, true, constraints, variableConstraints);
    System.out.println(standard.standardForm());
  }

}

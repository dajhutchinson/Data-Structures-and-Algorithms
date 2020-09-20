

public class PatternMatcher {

  public static Integer naive(String t, String p) {
    // pattern too long
    if (p.length()>t.length()) {
      return -1;
    }

    int count=0; // number of occurences of pattern
    Boolean match=true;
    for (int i=0; i+p.length()<=t.length(); i++) { // shift pattern along text
      match=true;

      int j=0;
      while(j<p.length() && match) { // check until find mismatch
        if (p.charAt(j)!=t.charAt(i+j)) {match=false;}
        j++;
      }

      if (match) {count++;} // if no mismatch found
    }
    return count;
  }

  public static Integer naiveExplained(String t, String p) {
    // pattern too long
    if (p.length()>t.length()) {
      System.out.println("Error - pattern longer than text.");
      return -1;
    }

    int count=0; // number of occurences of pattern
    Boolean match=true;
    for (int i=0; i+p.length()<=t.length(); i++) { // shift pattern along text
      Printer.print(t,p,i);
      match=true;

      int j=0;
      while(j<p.length() && match) { // check until find mismatch
        System.out.printf("Comparing - %c & %c\n", p.charAt(j), t.charAt(i+j));
        if (p.charAt(j)!=t.charAt(i+j)) {System.out.println("Mismatch Found"); match=false;}
        j++;
      }

      if (match) {System.out.println("Pattern Occurence Found"); count++;} // if no mismatch found
      System.out.println();
    }
    return count;
  }

  public static Integer kmp(String t, String p) {
    PrefixTable table = new PrefixTable(p);
    int q=-1; // 1 before character checking in pattern
    int c=0; // number of occurences

    for (int i=0; i<t.length(); i++) {

      while ((q>0) && (p.charAt(q+1)!=t.charAt(i))) { // while mismatch (or first character)
        q=table.get(q);
      }

      if (p.charAt(q+1)==t.charAt(i)) { // if aligned characters match
        q++;
      }

      if (q==p.length()-1) { // if full pattern matches
        c++;
        q=table.get(q-1)-1;
      }
    }

    return c;
  }

  public static Integer kmpExplained(String t, String p) {
    PrefixTable table = new PrefixTable(p);
    Printer.print(p, table);
    int q=-1; // 1 before character checking in pattern
    int c=0; // number of occurences

    for (int i=0; i<t.length(); i++) {
      System.out.println("\nNEW PASS");
      Printer.print(t, p, i-q-1);
      System.out.printf("Comparing %c & %c\n", p.charAt(q+1), t.charAt(i));
      while ((q>0) && (p.charAt(q+1)!=t.charAt(i))) { // while mismatch (or first character)
        System.out.printf("Mismatch %c & %c\n", p.charAt(q+1), t.charAt(i));
        q=table.get(q);
        System.out.printf("Comparing %c & %c\n", p.charAt(q+1), t.charAt(i));
      }

      if (p.charAt(q+1)==t.charAt(i)) { // if aligned characters match
        System.out.printf("Match %c & %c\n", p.charAt(q+1), t.charAt(i));
        q++;
      } else if (q==-1) {
        System.out.printf("Mismatch %c & %c\n", p.charAt(q+1), t.charAt(i));
      }

      if (q==p.length()-1) { // if full pattern matches
        System.out.printf("Occurence Found.\n");
        System.out.println();
        c++;
        q=table.get(q-1)-1;
      }
    }

    return c;
  }

  public static Integer bmh(String t, String p) {
    BadMatchTable table = new BadMatchTable(p);
    Printer.print(table, p);
    int i=0; // first character alligned to
    int j=p.length()-1; // character checking in pattern
    int c=0; // number of occurents

    while (i+j<t.length()) {
      System.out.println();
      Printer.print(t, p, i);

      if (p.charAt(j)==t.charAt(i+j)) { // match

        if (j==0) { // occurence found
          c++;
          int step=j-table.get(t.charAt(i+j)); // align next occurence in pattern of text character
          if (step<1) {step=1;}
          i+=step;
          j=p.length()-1; // restart pattern
        } else {
          j--;
        }
      } else { // mismatch
        int step=j-table.get(t.charAt(i+j)); // align next occurence in pattern of text character
        if (step<1) {step=1;}
        i+=step;
        j=p.length()-1; // restart pattern
      }
    }

    return c;
  }

  public static Boolean finiteStateMachine(String t, String p) {
    int c=0; // number of occurences

    FiniteStateMachine machine=new FiniteStateMachine(p);
    String state="";

    for (int i=0; i<t.length(); i++) {
      state=machine.transititionFunction(state, t.charAt(i));
      if (state.equals(p)) {
        System.out.printf("%s %c\n", t.substring(0,i+1), t.charAt(i));
        return true;
      }
    }
    return false;
  }

}

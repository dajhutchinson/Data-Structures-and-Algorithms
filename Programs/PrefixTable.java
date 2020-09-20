import java.util.List;
import java.util.ArrayList;

public class PrefixTable {

  private List<Integer> prefixTable;
  private String string;

  public PrefixTable(String s) {
    string=s;
    prefixTable=new ArrayList<>();

    // check prefix against suffix
    for (int i=1; i<s.length()+1; i++) {
      int c=0;
      for (int j=1; j<i; j++) {
        String prefix=s.substring(0,j);
        String suffix=s.substring(i-j,i);
        if (prefix.equals(suffix)) {c=j;}
      }
      prefixTable.add(c);
    }

  }

  public PrefixTable(String s, Boolean explain) {
    string=s;
    prefixTable=new ArrayList<>();

    // check prefix against suffix
    for (int i=1; i<s.length()+1; i++) {
      if (explain) {System.out.printf("\nAnalysing - %s\n", s.substring(0,i));}
      int c=0;
      for (int j=1; j<i; j++) {
        String prefix=s.substring(0,j);
        String suffix=s.substring(i-j,i);
        if (explain) {System.out.printf("Comparing %s & %s\n", prefix, suffix);}
        if (prefix.equals(suffix)) {
          c=j;
          if (explain) {System.out.printf("New Longest Preffix, %d\n", j);}
        }
      }
      prefixTable.add(c);
    }

  }

  public Integer get(int i) {
    if (i>=prefixTable.size()) {
      return null;
    }
    return prefixTable.get(i);
  }

}

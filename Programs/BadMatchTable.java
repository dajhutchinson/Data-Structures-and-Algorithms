import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BadMatchTable {

  public Map<Character, Integer> table;
  int length;

  public BadMatchTable(String s) {
    length=s.length();
    table=new HashMap<>();

    for (int i=0; i<length; i++) {
        Character c=Character.toLowerCase(s.charAt(i));

        if (table.containsKey(c)) { // update
          table.replace(c,i);
        } else {                          // add
          table.put(c,i);
        }
    }
  }

  public Integer get(Character c) {
    c=Character.toLowerCase(c);
    if (!table.containsKey(c)) {return -1;}
    return table.get(c);
  }

}

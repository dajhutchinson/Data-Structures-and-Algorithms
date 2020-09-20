import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

// TODO verify pattern
public class FiniteStateMachine {

  // accepting state = final state
  private String acceptingState;
  private Set<Character> alphabet;
  private List<String> states;
  private Map<Integer,Map<Character,Integer>> transitions;

  public FiniteStateMachine(String p) {

    // generate states
    // NOTE add (|), . and [] tokens
    states=tokenise(p);
    alphabet=generateAlphabet(p);
    print(states);
    System.out.println();

    transitions=generateTransitions(states, alphabet);

    acceptingState=p;
    System.out.println("\n-----------------------");

  }

  // returns new state
  // p=last confirmed state c=next character
  public String transititionFunction (String p, Character c) {

    if (!alphabet.contains(c)) {
      return "";
    }

    List<String> tokens=tokenise(p); // convert pattern into tokens
    int lastn=tokens.size()-1; // current state token

    Map<Character,Integer> possibleTransitions=transitions.get(lastn); // map for current state
    Integer dest=possibleTransitions.get(c); // new state

    return current(dest);
  }

  // appends n tokens to form pattern
  private String current(int n) {
    String s="";
    for (int i=0; i<n+1; i++) {
      s+=states.get(i);
    }
    return s;
  }

  // returns how far the single character c can get from the initial state, at most to current state
  private int forwards(Character c, int current) {
    int i=0; // state to check
    Boolean fail=false;
    int minLen=0; // minimum number of chars required NOTE c cannot do more than 1

    while (i<current && !fail && minLen<2) {

      String state=states.get(i); // state token to check
      if (state.length()==1) { // token is character only
        minLen++;
      } else if (state.length()==2) { // token has condition
        Character condition=state.charAt(1);
        if (condition.equals('+')) {minLen++;} // + requires at least one char
      }

      if (!valid(states.get(i),c)) {
        fail=true; // end
      } else {
        i++; // check next state
      }
    }
    i--;
    return i;
  }

  // returns how far the single character c can get from the current state, at most to accepting state
  private int beyond (Character c, int current) {
    int i=current+1; // state to check
    Boolean fail=false;
    int minLen=0; // minimum number of chars required NOTE c cannot do more than 1

    while (i<states.size() && !fail && minLen<2) {

      String state=states.get(i); // state token to check
      if (state.length()==1) { // token is character only
        minLen++; // checks if valid later
      } else if (state.length()==2) {
        Character condition=state.charAt(1);
        if (condition.equals('+')) {minLen++;} // + requires at least one char
      }

      if (!valid(states.get(i),c)) {
        fail=true; // end
      } else {
        i++; // check next state
      }

      if (minLen>1) {i--;}
    }
    i--;
    return i;
  }

  // turn pattern into tokens
  // NOTE extend
  private List<String> tokenise(String p) {
    List<String> tokens=new ArrayList<String>();

    tokens.add(""); // initial state

    for (int i=0; i<p.length(); i++) { // tokenise string
      Character c=p.charAt(i); // character
      Character suf=null; // possible condition
      if (i+1<p.length()) {suf=p.charAt(i+1);} // if not at end of string
      if (i+1!=p.length() && (suf.equals('+') || suf.equals('*') || suf.equals('?'))) { // if not at end and valid suffix
        tokens.add(String.valueOf(c)+String.valueOf(suf));
        i++; // skip past suffix
      } else {
        tokens.add(String.valueOf(c));
      }
    }
    return tokens;
  }

  // generate alphabet of pattern
  // NOTE extend
  private Set<Character> generateAlphabet(String p) {
    Set<Character> a=new HashSet<>();

    for (Character c:p.toCharArray()) { // add every character in pattern
      a.add(c);
    }
    a.remove('+'); a.remove('*'); a.remove('?'); // remove conditions

    return a;
  }

  // generate transition map
  // NOTE extend
  private Map<Integer,Map<Character,Integer>> generateTransitions (List<String> s, Set<Character> a) {

    Map<Integer,Map<Character,Integer>> map=new HashMap<>();

    for (int i=0; i<s.size(); i++) { // generate for every state
      String state=s.get(i);

      // explain
      if (i==0) { // initial state
        System.out.println("Generating transitions for initial state!");
      } else {
        System.out.printf("Generating transitions from state (%s,%d)!\n", state, i);
      }

      Map<Character,Integer> transition=new HashMap<>();

      for (Character c:a) { // generate for every character in alphabet

        if (state.length()==0) { // initial state

          if (s.size()==1) { // if inital state is only state
            System.out.printf("%c transitions to state %d\n", c, 0);
            transition.put(c,0);
          } else {
            String next=s.get(i+1); // state after initial
            if (valid(next, c)) {
              int be=beyond(c,i); // progress forward
              System.out.printf("%c transitions to state %d\n", c, be);;
              transition.put(c,be);
            } else { // stay at initial
              System.out.printf("%c transitions to state %d\n", c, 0);
              transition.put(c,0);
            }
          }

        } else { // not initial state

          Character target=state.charAt(0); // target character

          if (state.length()==1) { // character only state

            if (i==s.size()-1) { // accepting state
              int n=forwards(c, i); // return to start and progress
              System.out.printf("%c transitions to state %d\n", c, n);
              transition.put(c, n);
            } else { // not last item
              String next=s.get(i+1); // next state
              if (valid(next, c)) {
                int be=beyond(c,i); // progress forward
                System.out.printf("%c transitions to state %d\n", c, be);;
                transition.put(c,be);
              } else {
                int n=forwards(c,i+1); // return to start and progress
                System.out.printf("%c transitions to state %d\n", c, n);
                transition.put(c,n);
              }
            }

          } else if (state.length()==2) { // state has condition

            Character condition=state.charAt(1);

            if (condition.equals('*')) { // can always progress past (min = 0)
              int be=beyond(c,i);
              System.out.printf("%c transitions to state %d\n", c, be);
              transition.put(c,be);
            }

            if (condition.equals('+')) { // since we are in state we can always leave
              int be=beyond(c,i);
              System.out.printf("%c transitions to state %d\n", c, be);
              transition.put(c,be);
            }

            if (condition.equals('?')) {
              if (c.equals(target)) { // if two in a row
                int n=forwards(c,i+1); // return to start, progress
                System.out.printf("%c transitions to state %d\n", c, n);
                transition.put(c,n);
              } else {
                int be=beyond(c,i); // progress forward
                System.out.printf("%c transitions to state %d\n", c, be);
                transition.put(c,be);
              }
            }
      }}}
      map.put(i, transition);


    }

    return map;

  }

  // is it valid for the character c to ENTER state s
  // NOTE extend
  private Boolean valid(String s, Character c) {

    if (s.length()==0) { // initial state
      return true;
    } else {
      Character target=s.charAt(0); // target character

      if (s.length()==1) { // character only state
        return target.equals(c);
      }

      if (s.length()==2) { // character and condition state
        Character condition=s.charAt(1); // condition

        if (condition.equals('*')) {
          return true;
        }

        if (condition.equals('+')) {
          return target.equals(c);
        }

        if (condition.equals('?')) {
          return true;
        }

      }

    }

    return false; // invalid state

  }

  // print list of strings
  private void print(List<String> list) {
    System.out.print("[");
    for (int i=0; i<list.size()-1; i++) {
      System.out.printf("%s, ", list.get(i));
    }
    System.out.printf("%s]", list.get(list.size()-1));
  }

}

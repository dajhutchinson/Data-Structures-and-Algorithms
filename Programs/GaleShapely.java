import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GaleShapely {

  // map from girl to partner(boy)
  private Map<String, String> pairs;

  public GaleShapely(Map<String,List<String>> boys, Map<String,List<String>> girls, Boolean explain)  {
    if (explain) {System.out.println("GALE-SHAPELY ALGORITHM");}
    if (!verify(boys, girls, explain)) { // invalid input
      pairs=null;
    } else {  // valid input
      Map<String,Queue<String>> boyQueues=convertBoys(boys); // Convert boys' preference lists to queues
      preparePairs(girls); // set up pairs map
      do{                  // run cycle
        if (explain) {System.out.println("\n*NEW CYCLE*");}
        Set<String> prop=proposing(boys, explain); // boys without partners
        Map<String,Collection<String>> propositions=preparePropositions(girls); // empty map from girl to proposals recieved
        if (explain) {System.out.println("<PROPOSALS>");}
        for (String s:prop) { // every boy without partner makes proposal to current preference
          String proposeTo=boyQueues.get(s).remove(); // pop girl from queue
          propositions.get(proposeTo).add(s);         // record girl recieving proposal
          if (explain) {System.out.printf("%s proposes to %s\n", s, proposeTo);}
        }
        analyse(propositions, girls, explain);               // girls analyse propositions
      } while (incomplete()); // check if everyone has a partner
    }
  }

  // Return partner (for boy or girl)
  public String get(String s) {
    if (pairs.containsKey(s)) {
      return pairs.get(s);
    }

    if (pairs.containsValue(s)) {
      String[] keys=(String[]) pairs.keySet().toArray();
      String[] values=(String[]) pairs.values().toArray();

      for (int i=0; i<values.length; i++) {
        if (values[i]==s) {return keys[i];}
      }
    }
    return null;
  }

  // verify input data
  private Boolean verify(Map<String,List<String>> boys, Map<String,List<String>> girls, Boolean explain) {
    Set<String> boyNames=boys.keySet();   // set of boys names
    Set<String> girlNames=girls.keySet(); // set of girls names
    int b=boyNames.size();
    int g=girlNames.size();

    for (String s:boyNames) { // check every boy's preference list
      if (boys.get(s).size()!=g) {  // correct number of girls
        if (explain) {System.out.printf("ERROR - Invalid Input\n%s's preference list is wrong size.\n", s);}
        return false;
      }
      Set<String> girlsDone=new HashSet<>();
      for (String gs:boys.get(s)) {               // every girl appears once
        if (!girlNames.contains(gs)) { // valid girls name
          if (explain) {System.out.printf("ERROR - Invalid Input\n%s's preference list missing %s.\n" ,s, gs);}
          return false;
        }
        if (girlsDone.contains(gs)) { // girl not already named
          if (explain) {System.out.printf("ERROR - Invalid Input\n%s's preference list contains %s multiple times.\n", s, gs);}
          return false;
        }
        girlsDone.add(gs);
      }
    }

    for (String s:girlNames) { // check every girl's preference list
      if (girls.get(s).size()!=b) { // correct number of boys
        if (explain) {System.out.printf("ERROR - Invalid Input\n%s's preference list is wrong size.\n", s);}
        return false;
      }
      Set<String> boysDone=new HashSet<>();
      for (String bs:girls.get(s)) {              // every boy appears once
        if (!boyNames.contains(bs)) { // valid boys name
          if (explain) {System.out.printf("ERROR - Invalid Input\n%s's preference list missing %s.\n" ,s, bs);}
          return false;
        }
        if (boysDone.contains(bs)) { // boy not already named
          if (explain) {System.out.printf("ERROR - Invalid Input\n%s's preference list contains %s multiple times.\n", s, bs);}
          return false;
        }
        boysDone.add(bs);
      }
    }

    if (explain) {System.out.println("Input is valid.");}
    return true;
  }

  // Convert boys' preference lists to queues
  private Map<String,Queue<String>> convertBoys(Map<String,List<String>> boys) {
    Map<String,Queue<String>> converted=new HashMap<>(); // empty converted map
    for (String boy:boys.keySet()) {  // For each boy
      List<String> preferences=boys.get(boy);           // preference list
      Queue<String> preferenceQueue=new LinkedList<>(); // empty preference queue
      for (int i=0; i<preferences.size(); i++) {        // add girls in preference order
        preferenceQueue.add(preferences.get(i));
      }
      converted.put(boy,preferenceQueue); // add to converted map
    }
    return converted;
  }

  // create pairs map with correct keys and null values
  private void preparePairs(Map<String,List<String>> girls) {
    Map<String,String> prep=new HashMap<>();
    for (String girl:girls.keySet()) {
      prep.put(girl,null);  // add each girl with empty value
    }
    pairs=prep;
  }

  // boys without partners
  private Set<String> proposing(Map<String,List<String>> boys, Boolean explain) {
    Set<String> prop=new HashSet<>();
    Collection<String> paired=pairs.values();

    for (String boy:boys.keySet()) { // add boys without partners
      if (!paired.contains(boy)) {prop.add(boy);}
    }

    if (explain) {
      System.out.print("Proposing this cycle:\n");
      for (String s:prop) {System.out.printf(" - %s\n", s);}
    }

    return prop;
  }

  // empty map from girl to proposals recieved
  private Map<String,Collection<String>> preparePropositions(Map<String,List<String>> girls) {
    Map<String,Collection<String>> propositions=new HashMap<>();
    for (String s:girls.keySet()) {
      propositions.put(s,new ArrayList<>());
    }
    return propositions;
  }

  private void analyse(Map<String,Collection<String>> propositions, Map<String,List<String>> girls, Boolean explain) {

    // have map from girls to offers recieved & map of boys to paired girl
    if (explain) {System.out.println("<ANALYSE PROPOSALS>");}
    for (String girl:girls.keySet()) {
      // find preference from propositions
      String preferredProp=null;
      Integer position=Integer.MAX_VALUE;
      for (String offer:propositions.get(girl)) {
        Integer i=girls.get(girl).indexOf(offer);
        if (i<position) { // if this proposal is more preferable than current best update
          preferredProp=offer;
          position=i;
        }
      }

      // compare best proposition to current offer
      String current=this.get(girl);
      if (preferredProp!=null) {  // if proposal recieved
        if (current==null) {      // if no current partner
          pairs.put(girl, preferredProp);
          if (explain) {System.out.printf("%s accepts %s - They have no partner.\n", girl, preferredProp);}
        } else {                  // if girl has partner
          Integer i=girls.get(girl).indexOf(current);
          if (position<i) { // if proposal is preferable update
            pairs.put(girl, preferredProp);
            if (explain) {System.out.printf("%s accepts %s - Dropping %s.\n", girl, preferredProp, current);}
          } else {
            if (explain) {System.out.printf("%s stays with %s - Rejecting %s.\n", girl, current,  preferredProp);}
          }
      }}
    }

    if (explain) {System.out.println("<TABLE>");Printer.print(pairs);}

  }

  // check if every has a partner
  private Boolean incomplete() {
    if (pairs.values().contains(null)) {return true;}
    return false; // everyone has a partner
  }

}

import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import java.util.Arrays;

public class LinearProgram {

  private boolean valid;
  private int size;
  private double[] coef;
  private boolean maximise;
  private Set<LinearInequality> constraints;
  private Set<LinearInequality> variableConstraints;

  public LinearProgram(double[] coef, boolean maximise, Collection<LinearInequality> constraints, Collection<LinearInequality> variableConstraints) {
    valid=true;
    int size=coef.length;
    // verift variable constraints
    // NOTE single constraint per variable [0,...,1,...,0]
    valid=verifyVariableConstraints(size, variableConstraints);
    if (!valid) {
      System.out.println("Variable Constraints are bad!");
    }
    this.size=size;
    this.coef=coef;
    this.maximise=maximise;
    this.constraints=new HashSet<>(constraints);
    this.variableConstraints=new HashSet<>(variableConstraints);
  }

  private boolean verifyVariableConstraints(int size, Collection<LinearInequality> constraints) {
    int[] constraintOccurences=new int[size]; // check each variable ionly checked once

    if (constraints.size()!=size) {return false;} // wrong number of constraints
    Arrays.fill(constraintOccurences,0); // set to zero occurences

    for (LinearInequality li:constraints) { // check each inequality
      if (li.equality) {return false;}
      if (li.coef.length!=size) {return false;} // inequality has wrong number of coefficients
      // check only one coef is 1/-1 and the rest are zero
      for (int i=0; i<li.coef.length; i++) {
        double c=li.coef[i];
        if (c!=0) {
          li.coef[i]=c/c;
          constraintOccurences[i]=constraintOccurences[i]+1;
          if (constraintOccurences[i]>1) {return false;}
        }
      }
    }

    // check each variable occured once
    for (int i=0; i<size; i++) {
      if (constraintOccurences[i]!=1) {return false;}
    }

    return true;
  }

  public boolean standardForm() {
    if (!valid) {return false;}
    if (this.maximise!=true) {return false;} // aim must be to maximise
    System.out.println("Aim to maximise");

    // variable constraints are non-negative
    for (LinearInequality li:variableConstraints) {
      if (li.equality) {return false;}
      double c=0;
      for (int i=0; i<size; i++) {
        if (li.coef[i]!=0) {
          c=li.coef[i];
          break;
      }}
      if (c==0) {return false;}
      if (c>0&&!li.greater) {return false;}
      if (c<0&&li.greater) {return false;}
    }

    // no equality
    for (LinearInequality li:constraints) {
      if (li.equality) {return false;}
      if (li.greater) {return false;}
    }

    return true;

  }

  // TODO
  public void convertToStandardForm() {
    // make maximise
  }

}

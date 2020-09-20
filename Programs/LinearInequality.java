// NOTE set coef to zero for variables not in linear LinearInequality
public class LinearInequality {

  public int size;
  public double target;
  public double[] coef;
  public boolean greater;
  public boolean strictly;
  public boolean equality;

  public LinearInequality(double[] coef, double target, boolean greater, boolean strictly, boolean equality) {
    this.size=coef.length;
    this.target=target;
    this.coef=coef;
    this.greater=greater;
    this.strictly=strictly;
    this.equality=equality;
  }

  public Double calculate(double[] values) {
    if (values.length!=size) {return null;}
    Double result=0.0;
    for (int i=0; i<size; i++) {
      result+=values[i]*coef[i];
    }
    return result;
  }

}

import java.util.List;
import java.util.ArrayList;

public class FourierTransform {

  public static double horner(double[] coef, int n, double x) {
    double t=0;
    for (int i=n-1; i>-1; i--) {
      t=(t*x)+coef[i];
    }
    return t;
  }

  public static List<Tuple<Double,Double>> dft(double[] coef, int n) {
    List<Tuple<Double,Double>> y=new ArrayList<>();

    for (int i=0; i<n; i++) {
      Tuple<Double,Double> tup=eval(coef, rootOfUnity(n,i), n);
      y.add(tup);
    }

    return y;
  }

  // n must be even
  public static List<Tuple<Double,Double>> fft(List<Tuple<Double,Double>> coef, int n) {
    if (n==1) {return coef;}

    Tuple<Double,Double> rootN=rootOfUnity(n,1);
    Tuple<Double,Double> root=new Tuple<>(1.0,0.0);

    List<Tuple<Double,Double>> coef0=new ArrayList<>();
    List<Tuple<Double,Double>> coef1=new ArrayList<>();
    for (int i=0; i<n; i++) {
      if (i%2==0) {
        coef0.add(coef.get(i));
      } else {
        coef1.add(coef.get(i));
      }
    }

    List<Tuple<Double,Double>> y =new ArrayList<>();
    for (int i=0; i<n; i++) {y.add(new Tuple<>(0.0,0.0));}

    List<Tuple<Double,Double>> y0=fft(coef0, n/2);
    List<Tuple<Double,Double>> y1=fft(coef1, n/2);

    for (int k=0; k<n/2; k++) {
      Tuple<Double,Double> y0k=y0.get(k);
      Tuple<Double,Double> y1k=y1.get(k);

      Tuple<Double,Double> combined0=new Tuple<>(0.0,0.0);
      combined0.first=y0k.first+y1k.first*root.first-y1k.second*root.second;
      combined0.second=y0k.second+y1k.first*root.second+y1k.second*root.first;
      y.set(k, combined0);

      Tuple<Double,Double> combined1=new Tuple<>(0.0,0.0);
      combined1.first=y0k.first-y1k.first*root.first+y1k.second*root.second;
      combined1.second=y0k.second-y1k.first*root.second-y1k.second*root.first;
      y.set(k+(n/2), combined1);

      Double first=root.first*rootN.first-root.second*rootN.second;
      Double second=root.first*rootN.second+root.second*rootN.first;
      root.first=first;
      root.second=second;
    }

    return y;
  }

  // Tuple<Real,Imaginary>
  private static Tuple<Double,Double> eval(double[] coef, Tuple<Double,Double> x, int n) {
    Tuple<Double,Double> t=new Tuple<>(0.0,0.0);
    // equiv to Horner's method
    for (int i=n-1; i>-1; i--) {
      // x3+(x1+iy1)*(x2+iy2) = (x1x2-y1y2+x3)+i(x1y2+x2y1)
      // x1=t.first, y1=t.second, x2=x.first, y2=x.second, x3=coeff[i]
      double first =t.first*x.first-t.second*x.second+coef[i];
      double second=t.first*x.second+x.first*t.second;
      t.first=first;
      t.second=second;
    }

    return t;
  }

  // n = degree, k=pow
  // tuple<real,imaginary>
  public static Tuple<Double,Double> rootOfUnity(int n, int k) {
    if (n<1) {return null;}

    double rads=2*Math.PI*((double)k/(double)n);

    double real=Math.cos(rads);
    double img=Math.sin(rads);
    if (Math.abs(real)<0.001) {real=0.0;}
    if (Math.abs(img)<0.001) {img=0.0;}
    return new Tuple<Double,Double>(real,img);
  }

}

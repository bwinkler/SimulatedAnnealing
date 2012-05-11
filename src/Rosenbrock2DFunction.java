public final class Rosenbrock2DFunction{
  public static double C = 100;
  public static Function getFunction(){
    return new Function(){
      public double eval( double[] x ){
        double x1sq = x[0] * x[0];
        double t1sq = (x[1] - x1sq) * (x[1] - x1sq);
        return (C*t1sq) + ( 1 - x[0]) * (1 - x[0]);
      }
    };

  };
}


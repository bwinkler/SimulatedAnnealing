public final class RosenbrockNDFunction{
  public static double C = 100;
  public static Function getFunction( final int N ){
    return new Function(){
      public double eval( double[] x ){
        double result = 0;
        for( int k = 0; k < N - 1; k++ ){
          double xksq = x[k] * x[k];
          double t1sq = (x[ k + 1] - xksq) * (x[ k + 1 ] - xksq);
          result += (C*t1sq) + ( ( 1 - x[k]) * (1 - x[k]) );
        }
        return result;
      }
    };

  };
}


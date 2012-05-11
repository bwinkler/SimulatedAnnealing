import java.util.Random;

public final class Example1Seq {

  private static final int DIM = 2;
  private static final double[] LB = {-3, -3};
  private static final double[] UB = {3, 3};

  private static final int NUM_POINTS = 1000;

  public static void main( final String [] args ){

    AnnealerFactory.RATE = 0.85;

    long tStart = System.currentTimeMillis();
      Function f = new Function(){
        public double eval( double[] x ){
          return - (Math.sin( x[0] * x[0] + x[1] * x[1]) 
              / 0.1*(x[0] * x[0] +x[1] * x[1])) 
                - (55*x[1] + 60 * x[0] * x[0]);

        }
      };

      AbstractAnnealer sa 
        = new SequentialAnnealer( NUM_POINTS, DIM, LB, UB, f );
      sa.run();

      double[] xOpt = sa.getSolution();
      double fOpt = sa.getValue();
    long tEnd = System.currentTimeMillis();
    System.out.printf("(%f,%f), %f\n", xOpt[0], xOpt[1],  fOpt );
    System.out.printf("%d msec\n", tEnd - tStart ); 

  }

  /** Private constructor to prevent auto-generated. */
  private Example1Seq() { }
}



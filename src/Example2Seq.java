import java.util.Random;

public final class Example2Seq {

  private static final int DIM = 4;
  private static final double[] LB = {-3, -3, -3, -3};
  private static final double[] UB = {3, 3, 3, 3};

  private static final int NUM_POINTS = 100;

  public static void main( final String [] args ){

    long tStart = System.currentTimeMillis();
      Function f = new Function(){
        public double eval( double[] x ){
          return Math.sin( 2 * x[0] ) + Math.cos( x[1] ) + 0.5* x[2] - 0.25 * x[3];
        }
      };

      AbstractAnnealer sa 
        = new SequentialAnnealer( NUM_POINTS, DIM, LB, UB, f );
      sa.run();

      double[] xOpt = sa.getSolution();
      double fOpt = sa.getValue();
    long tEnd = System.currentTimeMillis();
    System.out.printf("(%f,%f, %f, %f), %f\n", xOpt[0], xOpt[1], xOpt[2], xOpt[3],  fOpt );
    System.out.printf("%d msec\n", tEnd - tStart ); 

  }

  /** Private constructor to prevent auto-generated. */
  private Example2Seq() { }
}



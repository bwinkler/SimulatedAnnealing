
public final class Rosenbrock2DExampleSeq {

  private static final int DIM = 2;
  private static final double[] LB = {-2, -2};
  private static final double[] UB = {2, 2};

  private static final int NUM_POINTS = 1000;

  public static void main( final String [] args ){

    AnnealerFactory.RATE = 0.85;

    long tStart = System.currentTimeMillis();


    AbstractAnnealer sa 
      = new SequentialAnnealer( NUM_POINTS, 
                                DIM, 
                                LB, UB, 
                                Rosenbrock2DFunction.getFunction() );
    sa.run();

    double[] xOpt = sa.getSolution();
    double fOpt = sa.getValue();
    long tEnd = System.currentTimeMillis();
    System.out.printf("(%f,%f), %f\n", xOpt[0], xOpt[1],  fOpt );
    System.out.printf("%d msec\n", tEnd - tStart ); 

  }

  /** Private constructor to prevent auto-generated. */
  private Rosenbrock2DExampleSeq() { }
}



public final class Rosenbrock4DExampleSmp2step {

  private static final int DIM = 4;
  private static final double[] LB = {-2, -2, -2, -2};
  private static final double[] UB = {2, 2, 2, 2};

  private static final int NUM_POINTS = 50;

  public static void main( final String [] args ){

    AnnealerFactory.RATE = 0.85;

    long tStart = System.currentTimeMillis();


    AbstractAnnealer sa 
      = new Parallel2StepAnnealer(   NUM_POINTS, 
                                DIM, 
                                LB, UB, 
                                RosenbrockNDFunction.getFunction( DIM ) );
    sa.run();

    double[] xOpt = sa.getSolution();
    double fOpt = sa.getValue();
    long tEnd = System.currentTimeMillis();
    System.out.printf("(%f,%f,%f,%f), %f\n", xOpt[0], xOpt[1], xOpt[2], xOpt[3], fOpt );
    System.out.printf("%d msec\n", tEnd - tStart ); 

  }

  /** Private constructor to prevent auto-generated. */
  private Rosenbrock4DExampleSmp2step() { }
}


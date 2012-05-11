
public final class Rosenbrock16DExampleSmp {

  private static final int DIM = 16;
  private static final double[] LB = new double[ DIM ];
  private static final double[] UB = new double[DIM];

  private static final int NUM_POINTS = 100;

  public static void main( final String [] args ){

    for( int i = 0; i < DIM; i++ ){
      LB[i] = -2;
      UB[i] = 2;
    }

    AnnealerFactory.RATE = 0.85;

    long tStart = System.currentTimeMillis();


    AbstractAnnealer sa 
      = new ParallelAnnealer( NUM_POINTS, 
                                DIM, 
                                LB, UB, 
                                RosenbrockNDFunction.getFunction( DIM ) );
    sa.run();

    double[] xOpt = sa.getSolution();
    double fOpt = sa.getValue();
    long tEnd = System.currentTimeMillis();

    System.out.print("(");
    for( int i = 0; i < DIM; i++ ){
      System.out.printf("%f", xOpt[i] );
      if( i != DIM - 1)
        System.out.print(",");
    }
    System.out.printf("), %f\n", fOpt );
    System.out.printf("%d msec\n", tEnd - tStart ); 

  }

  /** Private constructor to prevent auto-generated. */
  private Rosenbrock16DExampleSmp() { }
}


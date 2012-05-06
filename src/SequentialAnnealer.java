import java.util.Random;

public class SequentialAnnealer{
  private int numPoints;
  private double[] lb;
  private double[] ub;
  private int dim; 
  private Function f;

  public static int SEED = 123;

  private double[][] xOptHist;
  private double[]   fOptHist;

  private double[] xOpt;
  private double fOpt;

  private Random prng;

  public SequentialAnnealer( final int numPoints,
                             final int dim,
                             final double[] lb,
                             final double[] ub,
                             final Function f ){
    this.numPoints = numPoints;
    this.dim = dim;
    this.lb = lb;
    this.ub = ub;
    this.f = f;
    this.prng = new Random( SEED );

    this.xOptHist = new double[ numPoints ][ dim ];
    this.fOptHist = new double[ numPoints ];
  }

  public void run(){
    for( int i = 0; i < numPoints; i++ ){
      double[] x0 = new double[ dim ];

      for( int j = 0; j < dim; j++ ){
        x0[j] = boundedRandom( lb[j], ub[j] );
      }

      ObjectiveFunction obj = new ObjectiveFunction( x0, lb, ub, f );
      SimulatedAnnealer sa 
        = AnnealerFactory.getAnnealer( dim, lb, ub, x0, obj );
      sa.run();
      xOptHist[i] = sa.getSolution();
      fOptHist[i] = sa.getValue();

      if( fOptHist[i] < fOpt ){
        fOpt = fOptHist[i];
        xOpt = xOptHist[i];
      }
    }
  } 
  private double boundedRandom( double min, double max ){
    return min + ( max - min ) * prng.nextDouble();
  }

  public double[] getSolution(){
    return xOpt;
  }
  public double getValue(){
    return fOpt;
  }

                 
}

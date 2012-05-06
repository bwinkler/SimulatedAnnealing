import java.util.Random;

public class SequentialAnnealer extends AbstractAnnealer {
  public SequentialAnnealer( final int numPoints,
                             final int dim,
                             final double[] lb,
                             final double[] ub,
                             final Function f ){
    super( numPoints, dim, lb, ub, f );
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
                 
}

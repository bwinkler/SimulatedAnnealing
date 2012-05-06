import edu.rit.pj.Comm;
import edu.rit.util.Random;

import edu.rit.pj.IntegerForLoop;
import edu.rit.pj.ParallelRegion;
import edu.rit.pj.ParallelTeam;
import edu.rit.pj.reduction.SharedInteger;

public class ParallelAnnealer extends AbstractAnnealer{

  private int numThreads = 4;

  public ParallelAnnealer(  
      final int numPoints,
      final int dim,
      final double[] lb,
      final double[] ub,
      final Function f,
      final int numThreads ){
    super( numPoints, dim, lb, ub, f );
    this.numThreads = numThreads;
  }
  public ParallelAnnealer(  
      final int numPoints,
      final int dim,
      final double[] lb,
      final double[] ub,
      final Function f ){
    super( numPoints, dim, lb, ub, f );
  }

  public void run(){

    try{
      new ParallelTeam( numThreads ).execute( new ParallelRegion(){
        public void run() throws Exception{
          execute(0, numPoints - 1, new IntegerForLoop(){
            public void run( int first, int last ){

              for( int i = first; i <= last; i++ ){
                double[] x0 = new double[ dim ];
                for( int j = 0; j < dim; j++ ){
                  x0[j] = boundedRandom( lb[j], ub[j] );
                }
                ObjectiveFunction obj 
                  = new ObjectiveFunction( x0, lb, ub, f );
                SimulatedAnnealer sa 
                  = AnnealerFactory.getAnnealer( dim, lb, ub, x0, obj );
                sa.run();
                xOptHist[i] = sa.getSolution();
                fOptHist[i] = sa.getValue();
              }
            }
          } );
        }
      } );
    } 
    catch( Exception e ){

    }

    fOpt = fOptHist[0];
    xOpt = xOptHist[0];
    for( int i = 0; i < numPoints; i++ ){
      if( fOptHist[i] < fOpt ){
        fOpt = fOptHist[i];
        xOpt = xOptHist[i];
      }
    }
  } 
  protected double boundedRandom( double min, double max ){
    return min + ( max - min ) * prng.nextDouble( numThreads );
  }



}

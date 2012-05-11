import edu.rit.pj.Comm;
import edu.rit.util.Random;

import edu.rit.pj.IntegerForLoop;
import edu.rit.pj.ParallelSection;
import edu.rit.pj.ParallelRegion;
import edu.rit.pj.ParallelTeam;
import edu.rit.pj.reduction.SharedDouble;
import edu.rit.pj.reduction.SharedDoubleArray;

public class ParallelAnnealer extends AbstractAnnealer{

  public ParallelAnnealer(  
      final int numPoints,
      final int dim,
      final double[] lb,
      final double[] ub,
      final Function f,
      final int numThreads ){
    super( numPoints, dim, lb, ub, f );
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
      new ParallelTeam( ).execute( new ParallelRegion(){
        public void run() throws Exception{
          execute(0, numPoints - 1, new IntegerForLoop(){
            double myfOpt = fOpt;
            double[] myxOpt = new double[ xOpt.length ];
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
                if( fOptHist[i] < myfOpt ){
                  myfOpt = fOptHist[i];
                  myxOpt = xOptHist[i];
                }
              }
            }
            public void finish() throws Exception{ 
              critical( new ParallelSection(){
                public void run(){
                  if( myfOpt < fOpt ){
                    fOpt = myfOpt;
                    xOpt = myxOpt;
                  }
                }
              } );

            }
          } );
        }
      } );
    } 
    catch( Exception e ){

    }

  } 

}

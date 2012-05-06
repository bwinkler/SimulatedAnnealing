import java.util.Random;

public final class SimulatedAnnealer {

  /** The dimension (the n in R^n ) of the space that the optimization problem lives in. */
  private int dim;

  /** Maximum number of overall iterations to run. */
  private int maxIter;

  /** Maximum number of steps before attempting to reduce step size. */
  private int maxStep;

  /** Maximum number of iterations before reducing temperature. */
  private int maxTemp;

  /** How many last steps to consider when evaluating a step threshold
   * below the desired epsilon. */
  private int lastSteps;

  /** The stopping criteria. If the last N steps are all below this
   * threshold then either the algorithm has reached a minimum or is
   * stagnating. Either way we want to stop. */
  private double eps;

  /** The initial guess for a solution. */
  private double[] x0;

  /** The initial temperature. */
  private double T0;

  /** The current temperature. */
  private double T;

  /** The pseudo random number generator. */
  private Random prng;
  
  /** The step vector v. */
  private double[] v;

  /** The upper bound of the active solution set. */
  private double[] ub;

  /** The lower bound of the active solution set. */
  private double[] lb;

  /** The cooling rate. */
  private double coolingRate;

  /** The current optimal solution. */
  private double[] xOpt;

  /** The value at the current optimal solution. */
  private double fOpt;

  /** The value history for evaluating the stopping criteria. */
  private double[] fHist;

  /** The objective function we're trying to minimize. */
  private ObjectiveFunction obj;

  public SimulatedAnnealer( final int dim,
                            final int maxIter,
                            final int maxStep,
                            final int maxTemp,
                            final int lastSteps,
                            final double eps,
                            final int seed,
                            final double[] x0,
                            final double[] lb,
                            final double[] ub,
                            final double[] v, 
                            final double T0,
                            final double coolingRate,
                            final ObjectiveFunction obj ){
    this.dim         = dim;
    this.maxIter     = maxIter;
    this.maxTemp     = maxTemp;
    this.maxStep     = maxStep;
    this.lastSteps   = lastSteps;
    this.eps         = eps;
    this.prng        = new Random( seed );
    this.x0          = x0;
    this.lb          = lb;
    this.ub          = ub;
    this.v           = v;
    this.T0          = T0;
    this.coolingRate = coolingRate;
    this.obj         = obj;

    this.xOpt = this.x0;
    this.fOpt = obj.eval();
    this.T    = T0;

    // Initialize the first few steps to the initial value of f.
    fHist = new double[ maxIter ];
    for( int i = 0; i < lastSteps; i++)
      fHist[i] = fOpt;

  }

  public void run(){

    for( int k = lastSteps + 1; k < maxIter; k++){
      for( int m = 0; m < maxTemp; m++ ){
        for( int j = 0; j < maxStep; j++ ) {
          for( int h = 0; h < dim; h++){
            // Step in a random direction for each dimension.
            double f = obj.eval();
            double r = randomStepScale();
            while( ! obj.inBounds(h, r * v[h] ) ){
              r = randomStepScale();
            }
            obj.step( h, r * v[h] );
            double fp = obj.eval();
            decide( f, fp, h);
          }
          // This is where we need to update the step vector.
          obj.reset();
        }

        reduceTemp();
      }
      //Update value history and check for termination
      fHist[ k ] = obj.eval();
      if( hasConverged( k ) )
        break;
      else
        obj.set( xOpt );
    }
  }

  private void decide( final double f, final double fp , final int currentDim){
    if( fp <= f ){
      obj.accept( currentDim );
      if( fp < fOpt ){
        xOpt = obj.getAccepted();
        fOpt = fp;
      }
    }
    else {
      double coinToss = prng.nextDouble();
      double probAccept = Math.exp( (f - fp) / T );

      if( coinToss < probAccept )
        obj.accept( currentDim );
      else
        obj.reject();
    }
  }

  private double randomStepScale() {
    return prng.nextDouble() * 2 - 1;
  }

  private void reduceTemp(){
    // Very simple cooling schedule.
    T = coolingRate * T;
  }

  private boolean hasConverged( final int currentStep ){
    boolean terminate = true;
    for( int i = 1; i <= lastSteps; i++ )
      terminate = terminate && (Math.abs( fHist[ currentStep ] - fHist[ currentStep - i] ) <= eps);
    terminate = terminate && ( Math.abs( fHist[ currentStep ] - fOpt ) <= eps );
    return terminate;
  }

  public double[] getSolution(){
    return xOpt;
  }
  public double getValue(){
    return fOpt;
  }

}



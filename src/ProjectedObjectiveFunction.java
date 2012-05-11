public class ProjectedObjectiveFunction extends ObjectiveFunction{

  public ProjectedObjectiveFunction( final double[] x0, 
      final double[] lower, 
      final double[] upper, 
      final Function f ){
    super( x0, lower, upper, f );
  }

  public void step( final int dir, final double step ){
    double candidateStep = Math.max( xc[dir] + step, lower[dir] );
    xc[dir] = Math.min( candidateStep, upper[dir]);
  }

  public boolean inBounds( final int dir, 
      final double step ){
    return true;

  }

}

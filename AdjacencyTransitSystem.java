import linalg.*;
import java.util.ArrayList;

public class AdjacencyTransitSystem extends TransitSystem{

  private int noPaths;

  public AdjacencyTransitSystem(String[] lns){
    super(lns);
  }

  public AdjacencyTransitSystem(String[] lns, int noPaths){
    super(lns);
    this.noPaths = noPaths;
  }

  public boolean isAdjacency(){
    return true;
  }

  public int getNoLines(){
    return noPaths;
  }

}

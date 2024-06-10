import linalg.*;
import java.util.ArrayList;

public class AdjacencyTransitSystem extends TransitSystem{

  private int noPaths;

  public AdjacencyTransitSystem(){
    super();
  }

  public AdjacencyTransitSystem(int noPaths){
    super();
    this.noPaths = noPaths;
  }

  public boolean isAdjacency(){
    return true;
  }

  public int getNoLines(){
    return noPaths;
  }

}

import linalg.*;
import java.util.ArrayList;

public class TransitSystem extends Network<Stop>{

  public TransitSystem(){
    nodes = new ArrayList<Stop>();
  }

  public Matrix coVariance(double[] weights){
    if (weights.length != nodes.size()){
      throw new IllegalArgumentException("wights");
    }
    ArrayList<Vector> cols = new ArrayList<Vector>();
    Vector col;
    for (int s = 0; s < nodes.size(); s++){
      col = nodes.get(s).getLinesVector();
      col.scale(weights[s]);
      cols.add(col);
    }
    Matrix data = new Matrix(cols);
    return data.coVarianceMatrix();
  }

  public String deepToString(){
    return "Transit System" + super.deepToString().substring(7);
  }

  public String sumToString(){
    return "Transit System" + super.sumToString().substring(7);
  }

  public void addLine(String[] nodeNames, String lineName){
    Stop newStop;
    int index;
    for (int n = 0; n < nodeNames.length; n++){
      index = indexOfNode(nodeNames[n]);
      if (index == -1){
        newStop = new Stop(nodeNames[n], aryAdjacents(nodeNames, n), lineName);
        addNode(newStop);
      }else{
        nodes.get(index).addLine(lineName);
        nodes.get(index).addNabrs(aryAdjacents(nodeNames, n));
      }
    }
  }

  public void addLine(String[] nodeNames){
    Stop newStop;
    int index;
    for (int n = 0; n < nodeNames.length; n++){
      index = indexOfNode(nodeNames[n]);
      if (index == -1){
        throw new IllegalStateException("cannot create walking connections between new Stops");
      }else{
        nodes.get(index).addNabrs(aryAdjacents(nodeNames, n));
        updateNode(index);
      }
    }
  }

  public static String[] aryAdjacents(String[] ary, int index){
    if (ary.length <= 1){
      return new String[] {};
    }
    if (index == 0){
      return new String[] {ary[1]};
    }
    if (index == ary.length-1){
      return new String[] {ary[index-1]};
    }
    return new String[] {ary[index-1], ary[index+1]};
  }

}

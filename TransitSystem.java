import linalg.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TransitSystem extends Network<Stop>{

  protected String[] lines;
  protected MarkovChain mc;

  public TransitSystem(String[] lns){
    nodes = new ArrayList<Stop>();
    this.lines = lns;
    mc = new MarkovChain(this);
  }

  public double[] getStopWeights(){
    return this.mc.getSteadyState();
  }

  public String stopWeightsToString(){
    return this.mc.steadyStateToString();
  }

  public Matrix stationLineData(){
    double[] weights = getStopWeights();
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
    return data;
  }

  public String[] getLineNames(){
    return this.lines;
  }

  public Vector linesStringsToVector(String[] strLines){
    int[] ary = new int[this.lines.length];
    for (int i = 0; i < ary.length; i++){
      if (Matrix.aryContains(strLines, this.lines[i])){
        ary[i] = 1;
      }else{
        ary[i] = 0;
      }
    }
    if (numberOfOnesInArray(ary) < strLines.length){
      throw new IllegalArgumentException
        ("one or more of the line names in "+Arrays.toString(strLines)+" are not lines included in this"
        +" TransitSystem");
    }
    return new Vector(ary);
  }

  public void addLine(String[] nodeNames, String lineName){
    if (! Matrix.aryContains(this.lines, lineName)){
      throw new IllegalArgumentException(lineName+" is not a line in the TransitSystem");
    }
    Stop newStop;
    int index;
    for (int n = 0; n < nodeNames.length; n++){
      index = indexOfNode(nodeNames[n]);
      if (index == -1){
        newStop = new Stop(nodeNames[n], aryAdjacents(nodeNames, n), new String[] {lineName}, this);
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

  public String deepToString(){
    return "Transit System" + super.deepToString().substring(7);
  }

  public String sumToString(){
    return "Transit System" + super.sumToString().substring(7);
  }

  private static String[] aryAdjacents(String[] ary, int index){
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

  private static int numberOfOnesInArray(int[] ary){
    int no = 0;
    for (int i = 0; i < ary.length; i++){
      if (ary[i] == 1){
        no++;
      }
    }
    return no;
  }

}

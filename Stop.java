import java.util.ArrayList;
import java.util.Arrays;
import linalg.*;

public class Stop extends Node{

  private Vector lines;
  private String[] strLines;
  private TransitSystem system;

  public Stop(String name, String[] neibrs, String[] strAryLines, TransitSystem ts){
    super(name, neibrs);
    system = ts;
    lines = ts.linesStringsToVector(strAryLines);
    this.strLines = strAryLines;
    terminology = new String[] {"Stop", "Transit System", "adjacent Stops", "adjacent Stops ArrayList",
      "paths", "path weights"};
  }

  public Vector getLinesVector(){
    return lines;
  }

  public void addLine(String newLine){
    if (lines.get(aryIndexOf(system.getLineNames(), newLine)) == 0){
      this.strLines = Matrix.aryAppend(strLines, newLine);
      lines = system.linesStringsToVector(strLines);
    }
  }

  public void addNabr(String newNabr){
    if (Node.aryIndexOf(nabrs, newNabr) == -1){
      this.nabrs = Node.aryAppend(this.nabrs, newNabr);
      neighbors.add(null);
      edges.add(null);
    }
  }

  public void addNabrs(String[] newNabrs){
    for (int n = 0; n < newNabrs.length; n++){
      addNabr(newNabrs[n]);
    }
  }

}

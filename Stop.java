import java.util.ArrayList;
import java.util.Arrays;
import linalg.*;

public class Stop extends Node{

  private Vector lines;
  private String strLines;

  public Stop(String name, String[] neibrs, String strLines){
    super(name, neibrs);
    this.strLines = strLines;
    lines = mtaLines(strLines);
    terminology = new String[] {"Stop", "Transit System", "adjacent Stops", "adjacent Stops ArrayList",
      "paths", "path weights"};
  }

  public Stop(String name, String[] neibrs, String[] strAryLines){
    super(name, neibrs);
    lines = mtaLines(strAryLines);
    this.strLines = "";
    for (int i = 0; i < strAryLines.length; i++){
      strLines += strAryLines[i];
    }
    terminology = new String[] {"Stop", "Transit System", "adjacent Stops", "adjacent Stops ArrayList",
      "paths", "path weights"};
  }

  public void addLine(String newLine){
    if (lines.get(aryIndexOf(Mta.LINES, newLine)) == 0){
      this.strLines += newLine;
      lines = mtaLines(strLines);
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

  private static Vector mtaLines(String[] strLines){
    double[] aryLines = new double[Mta.LINES.length];
    for (int i = 0; i < aryLines.length; i++){
      for (int j = 0; j < strLines.length; j++){
        if (strLines[j].equals(Mta.LINES[i])){
          aryLines[i] = 1;
        }else{
          aryLines[i] = 0;
        }
      }
    }
    return new Vector(aryLines);
  }

  private static Vector mtaLines(String strLines){
    double[] aryLines = new double[Mta.LINES.length];
    boolean s42 = false;
    if (strLines.indexOf("S42") != -1){
      int s42Index = strLines.indexOf("S42");
      strLines = strLines.substring(0,s42Index) + strLines.substring(s42Index+3);
      s42 = true;
    }
    for (int i = 0; i < aryLines.length; i++){
      if (strLines.indexOf(Mta.LINES[i]) != -1){
        aryLines[i] = 1;
      }else{
        aryLines[i] = 0;
      }
    }
    if (s42){
      aryLines[Node.aryIndexOf(Mta.LINES, "S42")] = 1;
    }
    return new Vector(aryLines);
  }

}

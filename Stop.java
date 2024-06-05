import java.util.ArrayList;
import linalg.*;

public class Stop extends Node{

  private Vector lines;

  public Stop(String name, String[] neibrs, String[] strLines, double[] vals){
    super(name, neibrs, vals);
    lines = mtaLines(strLines);
  }

  public Stop(String name, String[] neibrs, String strLines, double[] vals){
    super(name, neibrs, vals);
    lines = mtaLines(strLines);
  }

  public Stop(String name, String[] neibrs, String[] strLines){
    super(name, neibrs);
    lines = mtaLines(strLines);
  }

  public Stop(String name, String[] neibrs, String strLines){
    super(name, neibrs);
    lines = mtaLines(strLines);
  }

  private static Vector mtaLines(String[] strLines){
    double[] aryLines = new double[MTA.lines.length];
    for (int i = 0; i < aryLines.length; i++){
      for (int j = 0; j < strLines.length; j++){
        if (strLines[j].equals(MTA.lines[i])){
          aryLines[i] = 1;
        }else{
          aryLines[i] = 0;
        }
      }
    }
    return new Vector(aryLines);
  }

  private static Vector mtaLines(String strLines){
    double[] aryLines = new double[MTA.lines.length];
    for (int i = 0; i < aryLines.length; i++){
      if (strLines.indexOf(MTA.lines[i]) != -1){
        aryLines[i] = 1;
      }else{
        aryLines[i] = 0;
      }
    }
  }

}

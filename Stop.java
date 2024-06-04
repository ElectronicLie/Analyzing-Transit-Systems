import java.util.ArrayList;
import linalg.*;

public class Stop extends Node{

  private String[] lines;

  public Stop(String name, String[] neibrs, String[] lines, double[] vals){
    super(name, neibrs, vals);
    lines = lines;
  }

  public Stop(String name, String[] neibrs, String[] lines){
    super(name, neibrs);
    lines = lines;
  }
}

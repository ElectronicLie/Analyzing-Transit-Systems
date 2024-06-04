import linalg.*;

public class System{

  private ArrayList<Stop> stops;
  private String[] lines;

  public System(ArrayList<Line> linesAL, ArrayList<Stop> stops){
    for (int i = 0; i < linesAL.size(); i++){
      lines[i] = linesAL.get(i).getName();
    }
    stops = stops;
    for (int i = 0; i < stops.size(); i++){
      this.stops.get(i).setSystem(this);
    }
  }

  private void linesToStrings(ArrayList<Line> stop){

  }

}

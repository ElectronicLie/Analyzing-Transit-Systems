import java.util.ArrayList;

public class Line{

  private ArrayList<Stop> stops;
  private String name;

  public Line(String name){
    name = name;
    stops = new ArrayList<Stop>();
  }

  public String getName(){
    return name;
  }

}

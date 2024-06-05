import linalg.*;

public class MTA{

  public static final String[] lines = new String[]
    {"A","B","C","D","E","F","G","J","L","M","N","Q","R","S42","Srp","Sfa","W","Z","1","2","3","4","5","6","7"};

  public static void main(String[] args){
    Network intxns = new Network(); //only contsains stops where two or more lines intersect
    intxns.add(new Stop("Times Sq",
      new String[] {"49 St","50 St red","PABT","Penn red","Bryant Park","Herald Sq"},
      "NQRSW1237"));
    intxns.add(new Stop(""))
  }

}

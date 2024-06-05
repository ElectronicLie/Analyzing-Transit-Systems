import linalg.*;

public class Main{

  public static final String[] lines = new String[]
    {"A","B","C","D","E","F","G","J","L","M","N","Q","R","S42","Srp","Sfa","W","Z","1","2","3","4","5","6","7"};

  public static main(String[] args){
    ArrayList<Node> intxns = new ArrayList<Node>(); //only contsains stops where two or more lines intersect
    intxns.add(new Stop("Times Sq",
      new String[] {"49 St","50 St red","PABT","Penn red","Bryant Park","Herald Sq"}),
      "NQRSW1237");
    intxns.add(new Stop(""))
  }

  public static Matrix mtaStops(){
    //double[] STOPNAME = new double[]
    //  {A,B,C,D,E,F,G,J,L,M,N,Q,R,S42,Srp,Sfa,W,Z,1,2,3,4,5,6,7};

    int[][] m = new int[][lines.length];
    m[0] = mtaStop("2"); //Wakefield-241St
    bulk("25",m,1,18); //233St - 3Av149St
    bulk("5",m,19,5); //EastchesterDyreAv - MorrisPark
    bulk("6",m,24,18); //PelhamBayPark - 3Av149St
    bulk("")

  }

  public static int[] mtaStop(String L){
    int[] result = new int[lines.length];
    for (int i = 0; i < lines.length; i++){
      if (L.indexOf("A") != -1){
        result[i] = 1;
      }else{
        result[i] = 0;
      }
    }
    return result;
  }

  public static void bulk(String L, int[][] ary, int startInclusive, int quantity){
    for (int i = startInclusive; i < startInclusive+quantity; i++){
      ary[i] = mtaStop(L);
    }
  }

  public static StochasticMatrix mtaNetwork(){

  }

}

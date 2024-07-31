import linalg.*;

public class Main{

  public static void main(String[] args){
    TransitSystem mta = new TransitSystem(Mta.LINES);
    Mta.addIntxnsAndTerminals(mta);
    mta.doneAddingLines();
    // System.out.println(mta.sumToString()+"\nend of network.toString()");
    // System.out.println("markov chain:\n"+mta.getMarkovChain().toString());
    System.out.println(mta.sortedStopWeightsToString());
    System.out.println(mta.stationLineData().weightedAverageOfPrincipalComponents());
  }

}

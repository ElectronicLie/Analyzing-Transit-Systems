import linalg.*;

public class Main{

  public static void main(String[] args){
    Mta stations = new Mta("intxns", "station");
    Vector mtaStopWeights = stations.getStationWeights();
    // System.out.println(stations.sortedStationWeightsToString());
    Matrix data = stations.getSystem().stationLineData(mtaStopWeights.getVals());
    System.out.println(data.weightedAverageOfPrincipalComponents());
  }

}

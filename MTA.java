import linalg.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Mta{

  public static final String[] LINES = new String[]
  {"A","B","C","D","E","F","G","J","L","M","N","Q","R","S42","Srp","Sfr","W","Z","1","2","3","4","5","6","7"};
  public static double[] stopWeights;

  public static void main(String[] args){

    TransitSystem mta = new TransitSystem();

    // Model 3:
    // Assumes no new or disembarking passengers - passengers are only moving between stops
    // Assumes it takes the same amount of time (one time step) to travel from any stop to any other
    // Only uses stops where two or more services intersect
    //   - includes collections of stops with tunnel/walking transfers
    // Two stops are connected if they are the closest 'intersection' stops on the corresponding service
    //   - this considers local and express services distinct
    // */

    if (args.length > 0){

      if (args[0].equals("1")){

        /*
        Model 1:
        Uses all stops
        Stops are connected if they are one stop away from each other on any service (local or express)
        */

        mta = new EvenTransitSystem();
        addAllStops(mta);

      }

      if (args[0].equals("2")){

        /*
        Model 2:
        Assumes no new or disembarking passengers - passengers are only moving between stops
        Assumes it takes the same amount of time (one time step) to travel from any stop to any other
        Only uses stops where two or more services intersect
        - includes collections of stops with tunnel/walking transfers (denoted by a &)
        Two stops are connected if they are the closest 'intersection' stops on the corresponding lines
        */

        mta = new EvenTransitSystem();
        addIntxns(mta);

      }

      if (args[0].equals("3")){

        /*
        Model 3:
        uses adjacency matrix and time steps to evalute the overall quality/"connectedness"
        */

        mta = new AdjacencyTransitSystem();
        addAllStops(mta);
      }

      if (args[0].equals("4")){

        /* Model 4: Model 3 but only intersections */

        mta = new AdjacencyTransitSystem();
        addIntxns(mta);
        addQueensLink(mta);
      }

      if (args[0].equals("5")){

        /* PCA */

        // mta = new EvenTransitSystem();
        // addIntxns(mta);
        //
        // MarkovChain passengers = new MarkovChain(mta);
        // stopWeights = passengers.steadyState().getVals();
        // // System.out.println(Arrays.toString(stopWeights));
        //
        // Matrix coV = mta.coVariance(stopWeights);
        // coV.scale(Math.pow(10,3));
        // System.out.println(coV.toString(5));

        double[] eigenvals = new double[] {
          1.191,1.386,1.722,2.102,2.687,3.368,4.772,5.391,11.007
        };
        Vector eigenvalsV = new Vector(eigenvals);
        ArrayList<Vector> eigenvectors = new ArrayList<Vector>();
        eigenvectors.add(new Vector(new double[] {
          3.244,-3.248,2.039,-3.704,-14.382,12.856,6.575,-1.852,-3.418,-5.385,-2.729,4.045,1.255,
          0.534,0.058,0.222,-2.693,-1.852,-2.211,0.439,-0.021,1.295,1.653,-1.279,1
        }));
        eigenvectors.add(new Vector(new double[] {
          0.024,0.380,-0.035,0.363,-0.34,-0.021,0.036,0.185,0.035,0.122,-0.171,0.006,-0.686,0.699,
          0.024,0.001,0.114,0.185,0.538,-0.367,-0.32,-0.075,-0.121,0.598,1
        }));
        eigenvectors.add(new Vector(new double[] {
          0.435,-0.554,0.497,-0.642,1.75,0.72,0.391,-2.188,-0.244,0.56,-0.712,-0.825,0.114,
          0.789,-0.067,0.02,-0.166,-2.188,0.633,-0.073,-0.182,0.603,0.625,0.857,1
        }));
        eigenvectors.add(new Vector(new double[] {
          -2.119,-2.213,-1.997,-2.68,1.356,2.906,0.01,2.105,0.382,3.59,-1.295,-0.172,-0.744,
          0.884,-0.092,-0.079,-0.223,2.105,0.12,1.729,1.717,-0.53,-0.311,-0.077,1
        }));
        eigenvectors.add(new Vector(new double[] {
          1.445,-3.759,1.485,-3.929,0.283,-0.518,0.396,1.535,2.95,-0.705,2.024,-0.626,3.015,
          0.595,-0.131,0.042,3.301,1.535,-1.164,-3.467,-3.323,-0.471,-0.353,2.391,1
        }));
        eigenvectors.add(new Vector(new double[] {
          1.839,-1.021,1.803,-1.319,0.501,-1.25,0.161,-0.474,-1.156,-1.516,0.053,1.004,0.112,
          0.845,0,0.042,0.656,-0.474,3.203,1.517,1.84,-4.15,-3.999,-3.042,1
        }));
        eigenvectors.add(new Vector(new double[] {
          -0.814,-1.858,-0.831,-2.018,-1.136,-3.347,-0.25,0.142,-0.276,-2.881,-0.225,-0.554,
          -0.955,0.928,-0.039,-0.013,-0.171,0.142,0.591,2.075,1.925,1.842,1.89,0.896,1
        }));
        eigenvectors.add(new Vector(new double[] {
          -5.838,-0.159,-5.734,-0.073,-1.732,-0.344,-0.34,-3.017,-1.233,-0.276,2.802,2.276,
          2.264,0.815,0.03,-0.083,2.474,-3.017,-0.284,-1.167,-1.054,-1.767,-1.755,-0.387,1
        }));
        eigenvectors.add(new Vector(new double[] {
          0.795,1.087,0.808,1.2,0.324,0.903,0.086,0.386,0.42,0.702,2.36,1.931,2.556,0.917,
          0.021,0.005,1.793,0.386,1.119,1.897,1.849,1.347,1.335,0.512,1
        }));

        Vector average = new Vector(LINES.length);

        for (int i = 0; i < eigenvectors.size(); i++){
          Vector e = eigenvectors.get(i);
          if (eigenvectors.get(i).dim() != LINES.length){
            throw new IllegalArgumentException("PCAAAA: " + i);
          }
          e.stochasticize();
          e.scale(eigenvals[i]/eigenvalsV.sum());
          average.addTo(e);
        }

        System.out.println(average);
      }

      if (args[0].equals("test")){ //test as needed

      }

      if (mta.isEven() && ! args[0].equals("5")){

        // System.out.println(mta.sumToString()+"\n");
        MarkovChain passengers = new MarkovChain(mta);
        // System.out.println(passengers);
        System.out.println(passengers.sortedSteadyStateToString(5));

      }

      if (mta.isAdjacency() && ! args[0].equals("5")){

        AdjacencyChain passengers = new AdjacencyChain(mta);
        // System.out.println(passengers);
        // Matrix chain = passengers.sumChain();?
        // System.out.println(passengers.get("Times Sq", "Inwood"));
        System.out.println(passengers.qualityIndex((double)(LINES.length)));

      }

    }
  }

  private static void addAllStops(TransitSystem mta){
    String[] one,two,three,four,five,fiveRushHour,six,sixRushHour,seven,sevenRushHour;
    String[] aOzone,aFR,sfr,c,b,e,dOffPeak,dRushHour,d,f,m,l,n,q,r,w,j,z,jOffPeak,zRushHour,zOffPeak;
    String[] g,s42,srp;

    one = new String[] {
      "Van Cortlandt Park", "238", "231", "Marble Hill", "215", "207", "Dyckman<ST.NICH>", "191",
      "181<ST.NICH>", "168", "145<BDWY>", "137-City College", "125<BDWY>", "116-Columbia",
      "Cathedral Pkwy<BDWY>", "103<BDWY>", "96<BDWY>", "79", "72<BDWY>", "66-Lincoln Center",
      "Columbus Circle", "50<BDWY>", "Times Sq", "Penn Stn", "28<7TH>", "23<7TH>", "18", "14<7TH>",
      "Sheridan Sq", "Houston", "Canal<VARICK>", "Franklin", "Chambers<VARICK>", "WTC Cortlandt",
      "Rector<VARICK>", "South Ferry"
    };
    mta.addLine(one, "1");

    two = new String[] {
      "Wakefield", "Nereid", "233", "225", "219", "Gun Hill Rd<WTPLNS>", "Burke", "Allerton",
      "Pelham Pkwy<WTPLNS>", "Bronx Park East", "E 180", "West Farms Sq", "174", "Freeman", "Simpson",
      "Intervale", "Prospect Av<WTPLNS>", "Jackson", "3 Av-149", "149-Grand Concourse", "135<LENOX>",
      "125<LENOX>", "116<LENOX>", "CP North", "96<BDWY>", "72<BDWY>", "Times Sq", "Penn Stn",
      "14<7TH>", "Chambers<VARICK>", "Park Place", "Fulton", "Wall St<WATER>", "Clark", "Borough Hall",
      "Hoyt", "Nevins", "Barclays Ctr", "Bergen<FLTBSH>", "Grand Army Plaza", "Eastern Pkwy", "Franklin Evers College",
      "President Evers College", "Sterling", "Winthrop", "Church Av<NOSTRN>", "Beverly<NOSTRN>", "Little Haiti",
      "Flatbush"
    };
    mta.addLine(two, "2");

    three = new String[] {
      "Harlem 148", "135<LENOX>", "125<LENOX>", "116<LENOX>", "CP North", "96<BDWY>", "72<BDWY>",
      "Times Sq", "Penn Stn",
      "14<7TH>", "Chambers<VARICK>", "Park Place", "Fulton", "Wall St<WATER>", "Clark", "Borough Hall",
      "Hoyt", "Nevins", "Barclays Ctr", "Bergen<FLTBSH>", "Grand Army Plaza", "Eastern Pkwy", "Franklin Evers College",
      "Nostrand<EPKW>", "Kingston", "Crown Hts", "Sutter-Rutland", "Saratoga", "Rockaway Av<LIV>", "Junius",
      "Penn Av", "Van Siclen<LIV>", "New Lots<LIV>"
    };
    mta.addLine(three, "3");

    four = new String[] {
      "Woodlawn", "Mosholu Pkwy", "Lehman College", "Kingsbridge<JEROME>", "Fordham<JEROME>", "183", "Burnside",
      "176", "Mt Eden", "170<JEROME>", "167<JEROME>", "Yankee Stadium", "149-Grand Concourse",
      "138 Grand Concourse", "125<LEX>", "86<LEX>", "59<LEX>", "Grand Central", "Union Sq", "Wall St<BDWY>",
      "Bowling Green", "Borough Hall", "Nevins", "Barclays Ctr", "Franklin Evers College",
      "Crown Hts"
    };
    mta.addLine(four, "4");

    five = new String[] {
      "Eastchester", "Baychester", "Gun Hill Rd<ESPL>", "Pelham Pkwy<ESPL>", "Morris Park",
      "E 180", "West Farms Sq", "174", "Freeman", "Simpson", "Intervale", "Prospect Av<WTPLNS>",
      "Jackson", "3 Av-149", "149-Grand Concourse", "138-Grand Concourse",
      "125<LEX>", "86<LEX>", "59<LEX>", "Grand Central", "Union Sq",
      "Wall St<BDWY>", "Bowling Green", "Borough Hall", "Nevins", "Barclays Ctr", "Franklin Evers College",
      "President Evers College", "Sterling", "Winthrop", "Church Av<NOSTRN>", "Beverly<NOSTRN>", "Little Haiti",
      "Flatbush"
    };
    fiveRushHour = new String[] {
      "E 180", "3 Av-149"
    };
    mta.addLine(five, "5");

    six = new String[] {
      "Pelham Bay Park", "Buhre", "Middletown", "Westchester Sq", "Zerega", "Castle Hill", "Parkchester",
      "St Lawrence", "Morrison", "Elder", "Whitlock", "Hunts Point", "Longwood", "E 149", "E 143",
      "Cypress", "Brook", "3 Av-138", "125<LEX>", "116<LEX>", "110<LEX>", "103<LEX>", "96<LEX>",
      "86<LEX>", "77<LEX>", "Hunter College", "59<LEX>", "51", "Grand Central", "33", "28<PARK>",
      "23<PARK>", "Union Sq", "Astor", "Bleecker", "Spring<LFYT>", "Canal<LFYT>",
      "Brooklyn Bridge City Hall"
    };
    sixRushHour = new String[] {
      "Parkchester", "Hunts Point", "3 Av-138"
    };
    mta.addLine(six, "6");

    seven = new String[] {
      "Hudson Yards", "Times Sq", "5 Av", "Grand Central", "Vernon", "Hunters Point", "Court Sq",
      "Queensboro Plaza", "33-Rawson", "40-Lowery", "46-Bliss", "52", "61-Woodside", "69",
      "74-Broadway", "82-Jackson Hts", "90-Elmhurst", "Junction", "103-Corona Plaza", "111<ROSVLT>",
      "Mets-Willets", "Flushing"
    };
    sevenRushHour = new String[] {
      "74-Broadway", "Junction", "Mets-Willets"
    };
    mta.addLine(seven, "7");

    aOzone = new String[] {
      "Inwood", "Dyckman<BDWY>", "190", "181<BDWY>", "175", "168", "145<STNIC>", "125<STNIC>",
      "Columbus Circle", "42/PABT", "Penn Stn", "14<8TH>", "W 4", "Canal<6TH>", "Chambers<CHURCH>",
      "Fulton", "High", "Jay St MetroTech", "Hoyt Schermerhorn", "Nostrand<FULT>", "Utica", "Broadway Junction",
      "Euclid", "Grant", "80", "88", "Rockaway Blvd", "104", "111<LIBTY>", "Ozone Park-Lefferts Blvd",
    };
    aFR = new String[] {
      "Rockaway Blvd", "Aqueduct Racetrack", "Aqueduct N Conduit", "Howard Beach JFK", "Broad Channel",
      "Beach 67", "Beach 60", "Beack 44", "Beach 36", "Beach 25", "Far Rockaway-Mott Av",
    };
    mta.addLine(aOzone, "A");
    mta.addLine(aFR, "A");

    sfr = new String[] {
      "Broad Channel", "Beach 90", "Beach 98", "Beach 105", "Rockaway Park"
    };
    mta.addLine(sfr, "Sfr");

    c = new String[] {
      "168", "163", "155<STNIC>", "145<STNIC>", "135<STNIC>", "125<STNIC>",
      "116<STNIC>", "Cathedral Pkwy<STNIC>", "103<CPW>", "96<CPW>", "86<CPW>", "81-Museum",
      "72<CPW>", "Columbus Circle", "50<8TH>", "42/PABT", "Penn Stn", "23<8TH>", "14<8TH>", "W 4",
      "Spring<6TH>", "Canal<6TH>", "Chambers<CHURCH>", "Fulton", "High", "Jay St MetroTech",
      "Hoyt Schermerhorn", "Lafayette", "Clinton Washington Avs", "Franklin", "Nostrand<FULT>",
      "Kingston Throop Avs", "Utica", "Ralph", "Rockaway<FULT>", "Broadway Junction", "Liberty",
      "Van Siclen<PITKIN>", "Shepherd", "Euclid"
    };
    mta.addLine(c, "C");

    e = new String[] {
      "WTC", "Canal<6TH>", "Spring<6TH>", "W 4", "14<8TH>", "23<8TH>", "Penn Stn", "42/PABT", "50<8TH>",
      "7 Av<53ST>", "5 Av/53", "Lexington/53", "Court Sq-23", "Queens Plaza", "Jackson Hts-Roosevelt", "Forest Hills-71",
      "75 Av", "Kew Gardens Union Tpke", "Briarwood", "Jamaica Van Wyck", "Sutphin-Archer",
      "Jamaica Center"
    };
    mta.addLine(e, "E");

    dOffPeak = new String[] {
      "Norwood<GC>", "Bedford Pk", "Kingsbridge<GC>", "Fordham<GC>", "182-183", "Tremont", "174-175",
      "170<GC>", "167<GC>", "Yankee Stadium", "155<FD>", "145<STNIC>", "125<STNIC>",
      "Columbus Circle", "7 Av<53ST>", "47-50-Rockefeller", "Bryant Pk", "Herald Sq", "Bdwy-Lafayette",
      "Grand", "Barclays Ctr", "36<4TH>", "9 Av", "Fort Hamilton Pkwy", "50<WEL>", "55", "62",
      "71", "79", "18 Av<UTRCT>", "20 Av<UTRCT>", "Bay Pkwy<UTRCT>", "25 Av", "Bay 50",
      "Coney Island"
    };
    dRushHour = new String[] {
      "Fordham<GC>", "Tremont", "145<STNIC>"
    };
    mta.addLine(dOffPeak, "D");

    b = new String[] {
      "145<STNIC>", "135<STNIC>", "125<STNIC>", "116<STNIC>", "Cathedral Pkwy<STNIC>", "103<CPW>",
      "96<CPW>", "86<CPW>", "81-Museum", "72<CPW>", "Columbus Circle", "7 Av<53ST>", "47-50-Rockefeller",
      "Bryant Pk", "Herald Sq", "Bdwy-Lafayette", "Grand", "Barclays Ctr", "7 Av<FLTBSH>",
      "Prospect Park", "Church Av<E15ST>", "Newkirk Plaza", "Kings Hwy<BRITON>", "Sheepshead Bay",
      "Brighton Beach"
    };
    mta.addLine(b, "B");

    f = new String[] {
      "Jamaica-179", "169", "Parsons", "Sutphin", "Briarwood", "Kew Gardens Union Tpke", "75 Av",
      "Forest Hills-71", "Jackson Hts-Roosevelt", "21-Queensbridge", "Roosevelt Island",
      "Lexington/63", "57", "47-50-Rockefeller", "Bryant Park", "Herald Sq", "23<6TH>", "14<6TH>",
      "W 4", "Bdwy-Lafayette", "2 Av", "Delancey-Essex", "E Broadway", "York", "Jay St MetroTech",
      "Bergen<SMITH>", "Carroll", "Smith-9", "4 Av-9", "7 Av<9TH>", "15-Prospect Park",
      "Fort Hamilton Pkwy<9TH>", "Church Av<MCDON>", "Ditmas", "18 Av<MCDON>", "Avenue I",
      "Bay Pkwy<MCDON>", "Avenue N", "Avenue P", "Kings Hwy<MCDON>", "Avenue U<MCDON>",
      "Avenue X", "Neptune", "W 8-Aquarium", "Coney Island"
    };
    mta.addLine(f, "F");

    m = new String[] {
      "Middle Village", "Fresh Pond", "Forest", "Seneca", "Myrtle-Wyckoff", "Knickerbocker",
      "Central", "Myrtle", "Flushing Av", "Lorimer<BDWY>", "Hewes", "Marcy", "Delancey-Essex",
      "Bdwy-Lafayette", "W 4", "14<6TH>", "23<6TH>", "Herald Sq", "Bryant Park",
      "47-50-Rockefeller", "5 Av/53", "Lexington/53", "Court Sq-23", "Queens Plaza", "36<BDWY>",
      "Steinway", "46", "Northern", "65", "Jackson Hts-Roosevelt", "Elmhurst", "Grand-Newtown",
      "Woodhaven", "63 Dr-Rego Park", "67 Av", "Forest Hills-71"
    };
    mta.addLine(m, "M");

    l = new String[] {
      "14<8TH>", "14<6TH>", "Union Sq", "3 Av", "1 Av", "Bedford", "Lorimer<BSHWCK>", "Graham",
      "Grand<BSHWCK>", "Montrose", "Morgan", "Jefferson", "DeKalb<WYCK>", "Myrtle-Wyckoff", "Halsey",
      "Wilson", "Bushwick-Aberdeen", "Broadway Junction", "Atlantic", "Sutter", "Livonia",
      "New Lots<VANSIN>", "E 105", "Canarsie"
    };
    mta.addLine(l, "L");

    n = new String[] {
      "Astoria-Ditmars", "Astoria", "30 Av", "Broadway<31ST>", "36 Av", "39 Av", "Queensboro Plaza",
      "Lexington/59", "5 Av/59", "57-7 Av", "49", "Times Sq", "Herald Sq", "Union Sq", "Canal<BDWY>",
      "DeKalb<FLTBSH>", "Barclays Ctr", "36<4TH>", "59<4TH>", "8 Av", "Fort Hamilton Pkwy<BEACH>",
      "New Utrecht", "18 Av<BEACH>", "Bay Pkwy<W8ST>", "Kings Hwy<W8ST>", "Avenue U<W8ST>",
      "86<W8ST>", "Coney Island"
    };
    mta.addLine(n, "N");

    q = new String[] {
      "96<2ND>", "86<2ND>", "72<2ND>", "Lexington/63", "57-7 Av", "Times Sq", "Herald Sq", "Canal<BDWY>",
      "DeKalb<FLTBSH>", "Barclays Ctr", "7 Av<FLTBSH>", "Prospect Park", "Parkside", "Church Av<E15ST>",
      "Beverly<E15ST>", "Cortelyou", "Newkirk Plaza", "Avenue H", "Avenue J", "Avenue M",
      "Kings Hwy<BRITON>", "Avenue U<BRITON>", "Neck", "Sheepshead Bay", "Brighton Beach", "Ocean Pkwy",
      "W 8-Aquarium", "Coney Island"
    };
    mta.addLine(q, "Q");

    r = new String[] {
      "Forest Hills-71", "67 Av", "63 Dr-Rego Park", "Woodhaven", "Grand-Newtown", "Elmhurst",
      "Jackson Hts-Roosevelt", "65", "Northern", "46", "Steinway", "36", "Queens Plaza",
      "Lexington/59", "5 Av/59", "57-7 Av", "49", "Times Sq", "Herald Sq", "28<BDWY>",
      "23<BDWY>", "Union Sq", "8-NYU", "Prince", "Canal<BDWY>", "City Hall", "Cortlandt",
      "Rector<BDWY>", "Whitehall", "Court", "Jay St MetroTech", "DeKalb<FLTBSH>", "Barclays Ctr",
      "Union", "4 Av-9", "Prospect Av<4TH>", "25", "45", "53", "59<4TH>", "Bay Ridge", "77<4TH>", "86<4TH>",
      "Bay Ridge-95"
    };
    mta.addLine(r, "R");

    w = new String[] { //the W does not add any new connections

    };
    mta.addLine(w, "W");

    jOffPeak = new String[] {
      "Broad", "Fulton", "Chambers<LFYT>", "Canal<LFYT>", "Bowery", "Delancey-Essex", "Marcy",
      "Hewes", "Lorimer<BDWY>", "Flushing Av", "Myrtle", "Kosciuszko", "Gates", "Halsey",
      "Broadway Junction", "Alabama", "Van Siclen<FULT>", "Cleveland", "Norwood<FULT>", "Crescent",
      "Cypress Hills", "75-Elderts", "Woodhaven<JAM>", "104<JAM>", "111<JAM>", "121",
      "Sutphin-Archer", "Jamaica Center"
    };
    mta.addLine(jOffPeak, "J");

    zOffPeak = new String[] {
      "Sutphin-Archer", "Woodhaven<JAM>", "Crescent", "Alabama", "Broadway Junction", "Myrtle"
    };
    zRushHour = new String[] {
      "Myrtle", "Marcy"
    };
    mta.addLine(zOffPeak, "Z");
    mta.addLine(zRushHour, "Z");

    g = new String[] {
      "Court Sq", "21", "Greenpoint", "Nassau", "Metropolitan", "Broadway", "Flushing Av<UNION>",
      "Myrtle-Willoughby", "Bedford-Nostrand", "Classon", "Clinton-Washington", "Fulton",
      "Hoyt Schermerhorn", "Bergen<SMITH>", "Carroll", "Smith-9", "4 Av-9", "7 Av<9TH>",
      "15-Prospect Park", "Fort Hamilton Pkwy<9TH>", "Church Av<MCDON>"
    };
    mta.addLine(g, "G");

    s42 = new String[] {
      "Times Sq", "Grand Central"
    };
    mta.addLine(s42, "S42");

    srp = new String[] {
      "Franklin Av", "Park", "Botanic Garden", "Prospect Park"
    };
    mta.addLine(srp, "Srp");

    //walking connections
    mta.addLine(new String[] {"Cortlandt", "WTC", "Park Place", "Chambers<CHURCH>"});
    mta.addLine(new String[] {"Canal<BDWY>", "Canal<LFYT>"});
    mta.addLine(new String[] {"Bleecker", "Bdwy-Lafayette"});
    mta.addLine(new String[] {"14<7TH>", "14<6TH>"});
    mta.addLine(new String[] {"42/PABT", "Times Sq", "Bryant Park", "5 Av"});
    mta.addLine(new String[] {"51", "Lexington/53"});
    mta.addLine(new String[] {"Lexington/63", "59<LEX>", "Lexington/59"});
    mta.addLine(new String[] {"74-Broadway", "Jackson Hts-Roosevelt"});
    mta.addLine(new String[] {"Junius", "Livonia"});
    mta.addLine(new String[] {"Court", "Borough Hall"});
    mta.addLine(new String[] {"Botanic Garden", "Franklin Evers College"});
    mta.addLine(new String[] {"62", "New Utrecht"});
    mta.addLine(new String[] {"Court Sq", "Court Sq-23"});
    mta.addLine(new String[] {"South Ferry", "Whitehall"});
  }

  private static void addIntxns(TransitSystem mta){
    String[] one,two,three,four,five,fiveRushHour,six,sixRushHour,seven,sevenRushHour;
    String[] aOzone,aFR,sfr,c,b,e,dOffPeak,dRushHour,d,f,m,l,n,q,r,w,j,z,jOffPeak,zRushHour,zOffPeak;
    String[] g,s42,srp;

    one = new String[] {
      "Van Cortlandt Park", "168", "96<BDWY>", "Columbus Circle", "Times Sq", "Penn Stn",
      "14<7TH>", "Chambers<VARICK>", "South Ferry"
    };
    mta.addLine(one, "1");

    two = new String[] {
      "Wakefield", "E 180", "149-Grand Concourse", "135<LENOX>",
      "96<BDWY>", "Times Sq", "Penn Stn",
      "14<7TH>", "Chambers<VARICK>", "Park Place", "Fulton", "Borough Hall",
      "Barclays Ctr", "Franklin Evers College", "Flatbush"
    };
    mta.addLine(two, "2");

    three = new String[] {
      "Harlem 148", "135<LENOX>", "96<BDWY>", "Times Sq", "Penn Stn",
      "14<7TH>", "Chambers<VARICK>", "Park Place", "Fulton", "Borough Hall",
      "Barclays Ctr", "Franklin Evers College", "Crown Hts", "Junius", "New Lots<LIV>"
    };
    mta.addLine(three, "3");

    four = new String[] {
      "Woodlawn", "Yankee Stadium", "149-Grand Concourse",
      "125<LEX>", "59<LEX>", "Grand Central", "Union Sq", "Brooklyn Bridge-City Hall", "Fulton",
      "Borough Hall", "Barclays Ctr", "Franklin Evers College", "Crown Hts"
    };
    mta.addLine(four, "4");

    five = new String[] {
      "Eastchester", "E 180", "149-Grand Concourse",
      "125<LEX>", "59<LEX>", "Grand Central", "Union Sq", "Brooklyn Bridge-City Hall", "Fulton",
      "Borough Hall", "Barclays Ctr", "Franklin Evers College", "Flatbush"
    };
    fiveRushHour = new String[] {
      "E 180", "3 Av-149"
    };
    mta.addLine(five, "5");

    six = new String[] {
      "Pelham Bay Park", "125<LEX>", "59<LEX>", "51", "Grand Central", "Union Sq", "Bleecker", "Canal<LFYT>",
      "Brooklyn Bridge-City Hall"
    };
    sixRushHour = new String[] {
      "Parkchester", "Hunts Point", "3 Av-138"
    };
    mta.addLine(six, "6");

    seven = new String[] {
      "Hudson Yards", "Times Sq", "5 Av", "Grand Central", "Court Sq",
      "Queensboro Plaza", "74-Broadway", "Flushing"
    };
    sevenRushHour = new String[] {
      "74-Broadway", "Junction", "Mets-Willets"
    };
    mta.addLine(seven, "7");

    aOzone = new String[] {
      "Inwood", "168", "145<STNIC>",
      "Columbus Circle", "42/PABT", "Penn Stn", "14<8TH>", "W 4", "Canal<6TH>", "Chambers<CHURCH>",
      "Fulton", "Jay St MetroTech", "Hoyt Schermerhorn", "Broadway Junction",
      "Euclid", "Rockaway Blvd", "104<LIB>", "Ozone Park-Lefferts Blvd",
    };
    aFR = new String[] {
      "Rockaway Blvd", "Broad Channel", "Far Rockaway-Mott Av",
    };
    mta.addLine(aOzone, "A");
    mta.addLine(aFR, "A");

    // sfr = new String[] {
    //   "Broad Channel", "Rockaway Park"
    // };
    // mta.addLine(sfr, "Sfr");

    c = new String[] {
      "168", "145<STNIC>", "Columbus Circle", "50<8TH>", "42/PABT", "Penn Stn", "14<8TH>", "W 4",
      "Canal<6TH>", "Chambers<CHURCH>", "Fulton", "Jay St MetroTech",
      "Hoyt Schermerhorn", "Franklin", "Broadway Junction", "Euclid"
    };
    mta.addLine(c, "C");

    e = new String[] {
      "WTC", "Canal<6TH>", "W 4", "14<8TH>", "Penn Stn", "42/PABT", "50<8TH>",
      "7 Av<53ST>", "5 Av/53", "Lexington/53", "Court Sq-23", "Queens Plaza", "Jackson Hts-Roosevelt", "Forest Hills-71",
      "Briarwood", "Sutphin-Archer", "Jamaica Center"
    };
    mta.addLine(e, "E");

    dOffPeak = new String[] {
      "Norwood<GC>", "Yankee Stadium", "145<STNIC>",
      "Columbus Circle", "7 Av<53ST>", "47-50-Rockefeller", "Bryant Pk", "Herald Sq", "Bdwy-Lafayette",
      "Barclays Ctr", "36<4TH>", "62", "Coney Island"
    };
    dRushHour = new String[] {
      "Fordham<GC>", "Tremont", "145<STNIC>"
    };
    mta.addLine(dOffPeak, "D");

    b = new String[] {
      "145<STNIC>", "Columbus Circle", "7 Av<53ST>", "47-50-Rockefeller",
      "Bryant Pk", "Herald Sq", "Bdwy-Lafayette", "Barclays Ctr", "Prospect Park", "Brighton Beach"
    };
    mta.addLine(b, "B");

    f = new String[] {
      "Jamaica-179", "Briarwood", "Forest Hills-71", "Jackson Hts-Roosevelt",
      "Lexington/63", "47-50-Rockefeller", "Bryant Park", "Herald Sq", "14<6TH>",
      "W 4", "Bdwy-Lafayette", "Delancey-Essex", "Jay St MetroTech",
      "Bergen<SMITH>", "4 Av-9", "Church Av<MCDON>", "W 8-Aquarium", "Coney Island"
    };
    mta.addLine(f, "F");

    m = new String[] {
      "Middle Village", "Myrtle-Wyckoff", "Myrtle", "Delancey-Essex",
      "Bdwy-Lafayette", "W 4", "14<6TH>", "Herald Sq", "Bryant Park",
      "47-50-Rockefeller", "5 Av/53", "Lexington/53", "Court Sq-23", "Queens Plaza",
      "Jackson Hts-Roosevelt", "Forest Hills-71"
    };
    mta.addLine(m, "M");

    l = new String[] {
      "14<8TH>", "14<6TH>", "Union Sq", "Lorimer<BSHWCK>", "Myrtle-Wyckoff", "Broadway Junction", "Livonia",
      "Canarsie"
    };
    mta.addLine(l, "L");

    n = new String[] {
      "Astoria-Ditmars", "Queensboro Plaza",
      "Lexington/59", "57-7 Av", "Times Sq", "Herald Sq", "Union Sq", "Canal<BDWY>",
      "DeKalb<FLTBSH>", "Barclays Ctr", "36<4TH>", "59<4TH>", "New Utrecht", "Coney Island"
    };
    mta.addLine(n, "N");

    q = new String[] {
      "96<2ND>", "Lexington/63", "57-7 Av", "Times Sq", "Herald Sq", "Canal<BDWY>",
      "DeKalb<FLTBSH>", "Barclays Ctr", "Prospect Park", "Brighton Beach", "W 8-Aquarium", "Coney Island"
    };
    mta.addLine(q, "Q");

    r = new String[] {
      "Forest Hills-71", "Jackson Hts-Roosevelt", "Queens Plaza",
      "Lexington/59", "57-7 Av", "Times Sq", "Herald Sq",
      "Union Sq", "Canal<BDWY>", "Cortlandt",
      "Whitehall", "Court", "Jay St MetroTech", "DeKalb<FLTBSH>", "Barclays Ctr",
      "4 Av-9", "36<4TH>", "59<4TH>", "Bay Ridge-95"
    };
    mta.addLine(r, "R");

    w = new String[] { //the W does not add any new connections
      "Astoria-Ditmars", "Queensboro Plaza", "Lexington/59", "57-7 Av", "Times Sq", "Herald Sq",
      "Union Sq", "Canal<BDWY>", "Cortlandt", "Whitehall"
    };
    mta.addLine(w, "W");

    jOffPeak = new String[] {
      "Broad", "Fulton", "Chambers<LFYT>", "Canal<LFYT>", "Bowery", "Delancey-Essex",
      "Myrtle", "Broadway Junction", "104<JAM>","Sutphin-Archer", "Jamaica Center"
    };
    mta.addLine(jOffPeak, "J");

    zOffPeak = new String[] {
      "Broad", "Fulton", "Chambers<LFYT>", "Canal<LFYT>", "Bowery", "Delancey-Essex",
      "Myrtle", "Broadway Junction", "104<JAM>", "Sutphin-Archer", "Jamaica Center"
    };
    zRushHour = new String[] {
      "Myrtle", "Marcy"
    };
    mta.addLine(zOffPeak, "Z");
    //mta.addLine(zRushHour, "Z");

    g = new String[] {
      "Court Sq", "Metropolitan", "Hoyt Schermerhorn", "Bergen<SMITH>", "4 Av-9", "Church Av<MCDON>"
    };
    mta.addLine(g, "G");

    s42 = new String[] {
      "Times Sq", "Grand Central"
    };
    mta.addLine(s42, "S42");

    srp = new String[] {
      "Franklin Av", "Botanic Garden", "Prospect Park"
    };
    mta.addLine(srp, "Srp");

    //walking connections
    mta.addLine(new String[] {"Cortlandt", "WTC", "Park Place", "Chambers<CHURCH>"});
    mta.addLine(new String[] {"Canal<BDWY>", "Canal<LFYT>"});
    mta.addLine(new String[] {"Bleecker", "Bdwy-Lafayette"});
    mta.addLine(new String[] {"14<7TH>", "14<6TH>"});
    mta.addLine(new String[] {"42/PABT", "Times Sq", "Bryant Park", "5 Av"});
    mta.addLine(new String[] {"51", "Lexington/53"});
    mta.addLine(new String[] {"Lexington/63", "59<LEX>", "Lexington/59"});
    mta.addLine(new String[] {"74-Broadway", "Jackson Hts-Roosevelt"});
    mta.addLine(new String[] {"Junius", "Livonia"});
    mta.addLine(new String[] {"Court", "Borough Hall"});
    mta.addLine(new String[] {"Botanic Garden", "Franklin Evers College"});
    mta.addLine(new String[] {"62", "New Utrecht"});
    mta.addLine(new String[] {"Court Sq", "Court Sq-23"});
    mta.addLine(new String[] {"South Ferry", "Whitehall"});
  }

  private static void addQueensLink(TransitSystem mta){
    mta.addLine(new String[] {"Forest Hills-71", "104<JAM>", "104<LIB>", "Broad Channel", "Rockaway Park"}, "M");
  }

  private static void addOneLongLine(TransitSystem mta){

  }

}

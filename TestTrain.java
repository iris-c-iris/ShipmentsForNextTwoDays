/** 
 * This class tests the Train class
 * @author Project4, Train: Iris Carrigg, TestTrain: Professor
 * @version 10-10-2022
 */ 
import java.util.Arrays;
public class TestTrain {
    public static void main( String[] args ) {
        Train t1 = new Train( "Charleston", 5, 12, 115 );
        t1.loadCargo( "broom", 5, 0.3 );
        t1.loadCargo( "carpets", 55, 10.3 );
        t1.loadCargo( "knives", 50, 1.1 );
        assert t1.getOrigin().equals( "Charleston" ) : "second constructor fail (origin)";
        assert t1.getArrivalTime() == 400 : "second constructor fail (arrivalTime)";
        assert t1.getDepartureTime() == 5 : "second constructor fail (departureTime)";
        assert t1.getSameDayArrival() == true  : "second constructor fail (sameDayArrival)";
        assert t1.getWeightOfTrainCargo() - 623.0 < 0.0001  : "second constructor fail (weightOfTrainCargo)";
        assert t1.getCargo().equals( "5 broom and 55 carpets and 50 knives") : "second constructor fail (cargo)";
        
        Train t2 = new Train( "Summerville", 2300, 10, 245 );
        t2.loadCargo( "broom", 5, 0.3 );
        t2.loadCargo( "carpets", 55, 10.3 );
        t2.loadCargo( "knives", 50, 1.1 );
        assert t2.getOrigin().equals( "Summerville" ) : "second constructor fail (origin)";
        assert t2.getArrivalTime() == 445 : "second constructor fail (arrivalTime)";
        assert t2.getDepartureTime() == 2300 : "second constructor fail (departureTime)";
        assert t2.getSameDayArrival() == false  : "second constructor fail (sameDayArrival)";
        assert t2.getWeightOfTrainCargo() - 623.0 < 0.0001  : "second constructor fail (weightOfTrainCargo)";
        assert t2.getCargo().equals( "5 broom and 55 carpets and 50 knives") : "second constructor fail (cargo)";
        
        assert Train.getWeightOfAllCargo() - 1246.0 < 0.0001  : "second constructor fail (weightOfAllCargo)";
        
        System.out.println( "All tests passed" );
    }
}

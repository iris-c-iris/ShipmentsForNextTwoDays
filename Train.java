/**
 * manipulates data about cargo needing to be loaded onto trains
 * takes data provided by Project4 class to make calculations
 * @author Iris Carrigg
 * @version 10-10-2022
 */
public class Train
{
    //declare instance variables
    private String origin;
    private int departureTime;
    private int arrivalTime;
    private boolean sameDayArrival;
    private String cargo;
    private double weightOfTrainCargo;
    private static double weightOfAllCargo;

    /**
     * standard constructor for objects of class Train
     * initializes instance variables, creates dummy object with no data
     * @param no parameters
     * @return nothing
     */
    public Train()
    {
        //initialize instance variables
        this.origin = "";
        this.departureTime = 0;
        this.arrivalTime = 0;
        this.sameDayArrival = false;
        this.cargo = "";
        this.weightOfTrainCargo = 0.0;
        weightOfAllCargo = getWeightOfAllCargo();  //static variable, call static method
    }

    /**
     * constructor for objects of class Train
     * initializes, set instance variables
     * calls methods within class Train 
     * @param String origin, int departure, int stops, int duration
     * @return nothing
     */
    public Train(String origin, int departure, int stops, int duration)
    {
        //initialize instance variables
        this.origin = origin;
        this.departureTime = departure;
        this.sameDayArrival = false;
        
        //calculateArrivalTime() returns void, call it to make updated arrivalTime accessible 
        calculateArrivalTime( stops, duration );
        
        this.weightOfTrainCargo = 0.0;
        weightOfAllCargo = getWeightOfAllCargo(); //static variable, call static method
        this.cargo = "";

    }

    /**
     * calculates train arrival time based on number of stops and trip duration 
     * @param int stops, int duration
     * @return void
     */
    public void calculateArrivalTime( int stops, int duration )
    {
        //declare and initialize local variables
        int totalTripTime = duration + stops * 10; //trip time in minutes
        int departureTimeMinutes = ( this.departureTime / 100 ) * 60 + this.departureTime % 100; //departure time converted to minutes
        
        //check if train will arrive after or at midnight
        if( ( departureTimeMinutes + totalTripTime ) >= 1440 )
        {
            //convert train arrival time to military time
            this.arrivalTime = ( departureTimeMinutes + totalTripTime - 1440 ) / 60 * 100 + ( departureTimeMinutes + totalTripTime - 1440 ) % 60;
            this.sameDayArrival = false; //next day arrival because train will arrive >= midnight 
        }
        //check if train will arrive before midnight
        else if( ( departureTimeMinutes + totalTripTime ) < 1440 )
        {
            //calculate arrival time to military time
            this.arrivalTime = ( departureTimeMinutes + totalTripTime ) / 60 * 100 + ( departureTimeMinutes + totalTripTime ) % 60;
            this.sameDayArrival = true; //same day arrival because train will arrive < midnight 
        }
    }

    /**
     * gets the value of variable this.origin 
     * @param no parameters
     * @return String
     */
    public String getOrigin()
    {
        return this.origin;
    }

    /**
     * gets the value of variable this.arrivalTime 
     * @param no parameters
     * @return int
     */
    public int getArrivalTime()
    {
        return this.arrivalTime;
    }

    /**
     * gets the value of variable this.departureTime
     * @param no parameters
     * @return int
     */
    public int getDepartureTime()
    {
        return this.departureTime;
    }

    /**
     * gets the value of variable this.sameDayArrival
     * @param no parameters
     * @return boolean
     */
    public boolean getSameDayArrival()
    {
        return this.sameDayArrival;
    }

    /**
     * gets the value of variable this.cargo
     * @param no parameters
     * @return String
     */
    public String getCargo()
    {
        return this.cargo;
    }

    /**
     * gets the value of variable this.weightOfTrainCargo
     * @param no parameters
     * @return double
     */
    public double getWeightOfTrainCargo()
    {
        return this.weightOfTrainCargo;
    }

    /**
     * gets the value of static variable weightOfAllCargo
     * @param no parameters
     * @return double
     */
    public static double getWeightOfAllCargo()
    {
        return weightOfAllCargo;
    }

    /**
     * loads cargo onto train
     * calculates this.weightOfTrainCargo, adds to weightOfAllCargo, builds this.cargoType String
     * @param String cargoType, int units, double weightPerItem
     * @return void
     */
    public void loadCargo( String cargoType, int units, double weightPerItem )
    {
        this.weightOfTrainCargo += ( weightPerItem * units ); //calculate weightOfTrainCargo
        weightOfAllCargo += ( weightPerItem * units ); //add weightOfTrainCargo value to weightOfAllCargo
        
        String andSeparator = " and "; //initialize local variable
        
        //check if this.cargo does not contain the andSeparator and is empty
        if( this.cargo.indexOf( andSeparator ) == -1 && this.cargo.length() == 0 )
            this.cargo = this.cargo.concat( "" + units + " " + cargoType ); //concat what is now the first cargo in this.cargo
        else
            this.cargo = this.cargo.concat( andSeparator + "" + units + " " + cargoType ); //concat additional cargo to existing

    }
}

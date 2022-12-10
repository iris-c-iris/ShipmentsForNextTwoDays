/**
 * prints data reports about arriving trains and their cargo --arrivals in the next 48 hour period
 * reads file containing data, manipulates data, and writes out a report 
 * @author Iris Carrigg
 * @version 10-10-2022
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Arrays;

public class Project4 {
    //WHEN DID YOU FIRST START WORKING ON THIS ASSIGNMENT (date and time): <---------------
    //ANSWER: 10/10/2022 8:44AM<---------------

    //DO NOT ALTER THE MAIN METHOD
    public static void main( String[] args ) {
        Train[] train = readFile( "departures2.txt" );

        PrintWriter outStream = null;
        try { outStream = new PrintWriter( "arrivals.txt" ); }
        catch ( FileNotFoundException fnf ) { System.exit( -2 ); }

        //each trip will arive by the end of next day
        outStream.printf( "%d trains arriving today and tomorrow with total cargo %.2f lbs%n", train.length, Train.getWeightOfAllCargo() );
        writeReport(    0,  759, true, train, outStream );
        writeReport(  800, 1559, true, train, outStream );
        writeReport( 1600, 2359, true, train, outStream );

        writeReport(    0,  759, false, train, outStream );
        writeReport(  800, 1559, false, train, outStream );
        writeReport( 1600, 2359, false, train, outStream );

        outStream.close();
    }//DO NOT ALTER THE MAIN METHOD

    /**
     * reads a file into a Train[] array
     * @param String inputFileName --the name of a .txt file
     * @return Train[]
     */
    public static Train[] readFile( String inputFileName ) {

        //initialize input stream
        Scanner in = null;

        //try to open files
        try
        {
            //create a new File object to represent the file with the name from above
            //TRY to create a new stream attached to that file
            in = new Scanner( new File( inputFileName ) );
        }
        catch ( FileNotFoundException e ){
            //if the file is not found, a FileNotFoundException will be thrown
            System.out.println( "File not found" );
        }

        //read first int of file into numberOfTrains
        int numberOfTrains = in.nextInt();

        in.nextLine(); //move through next line
        
        //initialize local variables
        int cargoTypeAmount = 0;
        int cargoAmount = 0;
        double cargoWeightPer = 0.0; 
        String cargoType = "";
        String origin = "";
        int departure = 0;
        int stops = 0;
        int duration = 0;

        //create Train[] type array, size numberOfTrains
        Train[] trains = new Train[numberOfTrains];
        
        //create object of Train class
        Train choochyMcChoochFace = new Train( origin, departure, stops, duration );
        
        //read the file
        while( in.hasNext() ) //is there more to read?
        {
            //read through file for length of array trains
            for( int i = 0; i < trains.length; i++ )
            {
                //intialize variables 
                cargoTypeAmount = 0;
                origin = "";
                departure = 0;
                stops = 0;
                duration = 0;

                //check types of values in file, assign to variables
                if( in.hasNext() ) //peek into file: has a String?
                    origin = in.next();
                if( in.hasNextInt() ) //peek into file: has an int?
                    departure = in.nextInt();
                if( in.hasNextInt() ) //peek into file: has an int?
                    stops = in.nextInt();
                if( in.hasNextInt() ) //peek into file: has an int?
                    duration = in.nextInt(); 

                if( in.hasNextInt() ) //peek into file: has an int?
                    cargoTypeAmount = in.nextInt();

                //while the line in file starts with an int, it contains cargo unit weight, quantity, and type data 
                while( in.hasNextInt() && in.hasNextLine() ) //check for int and lines to read
                {
                    //assign values to variables
                    cargoAmount = in.nextInt();
                    cargoWeightPer = in.nextDouble();
                    cargoType = cargoType.concat( (in.nextLine()).trim() );

                    //call Train class method .loadCargo() on object at index i of t[]
                    choochyMcChoochFace.loadCargo( cargoType, cargoAmount, cargoWeightPer );

                    //re-initialize variables
                    cargoAmount = 0;
                    cargoWeightPer = 0.0;
                    cargoType = "";
                }
                //assign object to index i of trains[]
                trains[i] = choochyMcChoochFace;
            }
            
        }

        in.close(); //close in stream

        return trains;
    }

    /**
     * writes a report of train arrival data to an out .txt file
     * manipulates data
     * @param int start, int end, boolean today, Train[] t, PrintWriter out
     * @return void
     */
    public static void writeReport( int start, int end, boolean today, Train[] t, PrintWriter out ) {

        //declare, initialize local variables
        int i = 0;
        int crewsNeeded = 0;
        double weightOfTrainCargo = 0.0;
        int trains;

        //if boolean today is true, search array for trains arriving today
        if( today == true )
        {
            //initialize variables
            weightOfTrainCargo = 0.0;
            trains = t.length;

            out.printf( "%nArriving today %04d-%04d:%n", start, end );

            //loop through t to find trains arriving today
            for( i = 0; i < t.length; i++ )
            {
                if( t[i].getSameDayArrival() == true )
                {
                    // check if train arrives within timeframe of start, end values
                    if( t[i].getArrivalTime() <= end && t[i].getArrivalTime() >= start ) 
                    {
                        out.printf( "   %04d: The %04d train from %s:%n         %.2f lbs of %s%n", t[i].getArrivalTime(), t[i].getDepartureTime(), t[i].getOrigin(),t[i].getWeightOfTrainCargo(), t[i].getCargo() );
                        weightOfTrainCargo += t[i].getWeightOfTrainCargo(); //update weightOfTrainCargo with value accessed by object
                    }
                    else {trains -= 1;continue;} //if object arrival out of range, decrement number of trains for this time range
                }
                else
                {trains -= 1;continue;}//if object arrival out of range, decrement number of trains for this time range

            }

            if( trains == 0 ) //if no trains arriving within start, end timeframe
                out.println( "No trains." );

            crewsNeeded = (int)( Math.ceil( weightOfTrainCargo / 500 ) ); //calculate crews needed to unload

            if( crewsNeeded != 0 ) //only print if there are crews needed
                out.printf( "Unloading crews needed: %d%n", crewsNeeded );
        }

        //if boolean today is false, search array for trains arriving tomorrow
        if( today == false )
        {
            //initialize variables
            weightOfTrainCargo = 0.0;
            trains = t.length;

            out.printf( "%nArriving tomorrow %04d-%04d:%n", start, end );

            //loop through t to find trains arriving tomorrow
            for( i = 0; i < t.length; i++ )
            {
                if( t[i].getSameDayArrival() == false )
                {
                    // check if train arrives within timeframe of start, end values
                    if(t[i].getArrivalTime() <= end && t[i].getArrivalTime() >= start)
                    {
                        out.printf( "   %04d: The %04d train from %s:%n         %.2f lbs of %s%n", t[i].getArrivalTime(), t[i].getDepartureTime(), t[i].getOrigin(),t[i].getWeightOfTrainCargo(), t[i].getCargo() );
                        weightOfTrainCargo += t[i].getWeightOfTrainCargo(); //update weightOfTrainCargo with value accessed by object
                    }
                    else{trains -= 1;continue;}//if object arrival out of range, decrement number of trains for this time range
                }
                else
                {trains -= 1;continue;}//if object arrival out of range, decrement number of trains for this time range
            }

            if( trains == 0 ) //if no trains arriving within start, end timeframe
                out.println( "No trains." );

            crewsNeeded = (int)( Math.ceil( weightOfTrainCargo / 500 ) ); //calculate crews needed to unload

            if( crewsNeeded != 0 ) //only print if there are crews needed
                out.printf( "Unloading crews needed: %d%n", crewsNeeded );
        }

    }
}

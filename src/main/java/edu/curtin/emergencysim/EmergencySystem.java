package edu.curtin.emergencysim;

import java.util.logging.Logger;
import edu.curtin.emergencysim.display.EmergencyGenerator;
import edu.curtin.emergencysim.display.RandomNumberGenerator;
import edu.curtin.emergencysim.exceptions.ArgumentException;
import edu.curtin.emergencysim.observers.ObservingEmergency;
import edu.curtin.emergencysim.read.*;
import edu.curtin.emergencysim.responders.ResponderCommImpl;
import edu.curtin.emergencysim.types.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("PMD.DoNotTerminateVM")
/*Used System.exit(1) to end the program incase of some exceptions so i had to suppress PMD.DoNotTerminateVM which does not allow the use of System.exit(1)*/

public class EmergencySystem
{
    private static Logger logger = Logger.getLogger(EmergencySystem.class.getName());
    private static List<String> inputData = new ArrayList<>();
    private static List<Emergencies> emergencyData = new ArrayList<>();
    private static ObservingEmergency oE = new ObservingEmergency();
    private static RandomNumberGenerator rNG = new RandomNumberGenerator();
    private static ResponderCommImpl responder = new ResponderCommImpl();
    private static FileReader fRead;
    private static EmergencyGenerator eGen;

    
    public static void main(String[] args)
    {
        try 
        {
            checkArguments(args); //checks the arguments and throws an exception if incorrect argument is send 
        } 
        catch (ArgumentException e) 
        {
            System.out.println(e);
        }
        
        fRead = new FileReader(args[0], logger);
        fRead.read(inputData, emergencyData, oE); //reads the input file and creates the emergencies
        
        eGen = new EmergencyGenerator(responder, oE, emergencyData, rNG, logger); //Genarates the emmergencies and simulates them
        eGen.displayEmergency();
    }

    private static void checkArguments(String[] args)throws ArgumentException
    {
        try 
        {
            if (args.length == 0) 
            {
                throw new ArgumentException("\nInput file name must be provided");
            }
            else if(args.length > 1)
            {
                throw new ArgumentException("\nMore than 1 argument is entered!\nPlease enter only one argument");
            }     

        } catch (ArgumentException e) 
        {
            System.out.println(e);
            System.exit(1);
        }
        
    }

}


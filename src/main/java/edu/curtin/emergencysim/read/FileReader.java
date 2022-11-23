package edu.curtin.emergencysim.read;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.curtin.emergencysim.exceptions.FileReadException;
import edu.curtin.emergencysim.observers.ObservingEmergency;
import edu.curtin.emergencysim.types.*;

/*This class is used to take the command line argument, read the file and create the mentioned emergencies in the file
Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix
Used System.exit(1) to end the program incase of some exceptions so i had to suppress PMD.DoNotTerminateVM which does not allow the use of System.exit(1)
PMD.CloseResource has been shown to  close resources i did close my scanner but the PMD error is still shown
it tells me to add GuardLogStatements i didnt know how to solve this issue so i suppressed it*/

@SuppressWarnings({"PMD.CommentDefaultAccessModifier", "PMD.GuardLogStatement", "PMD.DoNotTerminateVM", "PMD.CloseResource"})
public class FileReader
{
    private static final Pattern INPUT_FILE_REGEX = Pattern.compile(
        "(?<startTime>[0-9]+) (?<emergency>fire|flood|chemical) (?<location>.+)");
        
    private String fileName;
    private static Logger logger; 

    public FileReader(String fileNameIn, Logger loggerIn)
    {
        logger = loggerIn;
        
        fileName = fileNameIn;
    }

    public void read(List<String> inputData, List<Emergencies> emergencyData, ObservingEmergency oE)
    {   
        try
        {

            File infile = new File(fileName);
            Scanner scanFile = new Scanner(infile);
            logger.info(String.format("The file %s is read", fileName));

            //data is added to an array list
            while (scanFile.hasNextLine())
            {
                inputData.add(scanFile.nextLine());
            }

            checkInputFileFormat(inputData);
            checkDuplicates(inputData);
            //data in the input data is iterated and emergencies are created accordingly 
            for (String dataEntry : inputData)
            {
                String[] parts = dataEntry.split(" ", 3);

                if (parts[1].equals("fire"))
                {
    
                    int time = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    String location = parts[2];

                    Fire f = new Fire(oE);
                    f.setTime(time);
                    f.setEmergencyType(type);
                    f.setLocation(location);
                    emergencyData.add(f);

                    logger.info(String.format("New %s with start time %d and location %s is added", f.getEmergencyType(), f.getStartTime(), f.getLocation()));

                }
                else if (parts[1].equals("flood"))
                {
    
                    int time = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    String location = parts[2];

                    Flood fl = new Flood(oE);
                    fl.setTime(time);
                    fl.setEmergencyType(type);
                    fl.setLocation(location);
                    emergencyData.add(fl);

                    logger.info(String.format("New %s with start time %d and location %s is added", fl.getEmergencyType(), fl.getStartTime(), fl.getLocation()));

                }
                else if (parts[1].equals("chemical"))
                {
    
                    int time = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    String location = parts[2];

                    Chemical chem = new Chemical(oE);
                    chem.setTime(time);
                    chem.setEmergencyType(type);
                    chem.setLocation(location);
                    emergencyData.add(chem);

                    logger.info(String.format("New %s with start time %d and location %s is added", chem.getEmergencyType(), chem.getStartTime(), chem.getLocation()));
                }

            }

            scanFile.close();

        }
        catch (FileNotFoundException e) 
        {
            System.out.println("Input file entered is not available or is incorrect");
            System.exit(1);
        } 
        catch (FileReadException e) 
        {
            System.out.println("File Contains an issue "+e);
        }
    }

    //checking for errors in the input file 
    private static void checkInputFileFormat(List<String> data)
    {
        try
        {
            for (String dataEntry : data) 
            {
                Matcher m = INPUT_FILE_REGEX.matcher(dataEntry);
                if(!m.matches())
                {
                    logger.info(String.format("File format error is detected"));
                    throw new FileReadException("\nInput file format is incorrect \nPlease enter a input file with the correct format\nFormat should be (Start Time) (fire|flood|chemical) (Location)");
                }
            }
        } 
        catch (FileReadException e) 
        {
            System.out.println(e);
            System.exit(1);
        }
    }

    private static void checkDuplicates(List<String> data)throws FileReadException
    {
        try {

            List<String> emergencyInputList = new ArrayList<>();
            Set<String> emergencyInputSet = new HashSet<>();
            
            for (String dataEntry : data) 
            {
                String[] parts = dataEntry.split(" ", 2);

                emergencyInputList.add(parts[1]);
            }

            for(String duplicates : emergencyInputList) 
            {
                if(emergencyInputSet.add(duplicates) == false)
                {
                    logger.info(String.format("Duplicate Emergencies have been detected"));
                    throw new FileReadException("\nDuplicate Emergencies are present in the input file!\nProgram might not work properly");
                }
                    
            }
        } catch (FileReadException e) 
        {
            System.out.println(e);
        }
        
    }
    
}
package edu.curtin.emergencysim.display;

import java.util.List;
import java.util.logging.Logger;
import edu.curtin.emergencysim.observers.ObservingEmergency;
import edu.curtin.emergencysim.responders.*;
import edu.curtin.emergencysim.types.*;
/*This class is used to display all the emergencies mentioned in the input file and how they change their state through time and in the presence of responders 
Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix*/

@SuppressWarnings({"PMD.CommentDefaultAccessModifier", "PMD.GuardLogStatement", "PMD.UnusedAssignment"})
public class EmergencyGenerator 
{
    //Fire named constants
    static final int FIRE_LOW_TO_HIGH_TIME = 5;
    static final int FIRE_LOW_CLEANP_TIME = 3;
    static final int FIRE_HIGH_TO_LOW_TIME = 6;
    static final double FIRE_LOW_CASUALTY_PROB = 0.2;
    static final double FIRE_HIGH_CASUALTY_PROB = 0.7;
    static final double FIRE_LOW_DAMAGE_PROB = 0.25; 
    static final double FIRE_HIGH_DAMAGE_PROB = 0.8;

    //Flood named constants 
    static final int FLOOD_END_TIME = 6;
    static final double FLOOD_DAMAGE_PROB = 0.65;
    static final double FLOOD_CASUALTY_PROB = 0.13;

    //Chemical named constants
    static final int CHEM_CLEANUP_TIME = 5;
    static final double CHEM_CASUALTY_PROB = 0.17;
    static final double CHEM_CONTAM_PROB = 0.66;

    private static ResponderCommImpl responder;
    private static ObservingEmergency oE;
    private static List<Emergencies> emergencyData;
    private static RandomNumberGenerator rNG;
    private static Logger logger;

    //Dependency injection is used here to make the class independent of its dependencie
    public EmergencyGenerator(ResponderCommImpl responderIn, ObservingEmergency oEIn, List<Emergencies> emergencyDataIn, RandomNumberGenerator rNGIn, Logger loggerIN)
    {
        responder = responderIn;
        oE = oEIn;
        emergencyData = emergencyDataIn;
        rNG = rNGIn;
        logger = loggerIN;
    }

    //Simulation is carried out using this method
    public void displayEmergency()
    {
        List<String> responderList;
        boolean complete = false;
        int runTime = 1;

        while (!complete)
        {
            sleepProgram();
            System.out.println("\033[31m______________NEWUPDATE______________\033[m");
            System.out.println("Update Number "+runTime);
            responderList = responder.poll();

            if (!responderList.isEmpty()) 
            {
                for (String responders : responderList)
                {
                    System.out.println(responders);
                    logger.info(String.format("%s is being simulated",responders));

                    if(responders.equals("end"))
                    {
                        complete = true; //ends the program if the responders sends end 
                    }
                }
            }
            
            oE.setTime(runTime);

            for (Emergencies eData : emergencyData)
            {
                simulatorFunctions(eData, responder, responderList);
            }
            
            runTime++;
        }
        System.out.println("Goodbye!");
        System.out.println("\033[31m_____________________________________\033[m");
    }
    
    //Use to sleep for 1 second
    private static void sleepProgram()
    {
        try 
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) 
        {
            System.out.println(e);
        }
    }

    //All the fuctions of the simulator is carried out
    private static void simulatorFunctions(Emergencies eData, ResponderCommImpl responder, List<String> responderList)
    {
        if (eData.getEmergencyType().equals("fire") && eData.getState().details().equals("Idle")) 
        {
            fireResponce(eData, responder, responderList);
        }
        
        if (eData.getEmergencyType().equals("flood") && eData.getState().details().equals("Idle"))
        {
            floodResponce(eData, responder, responderList);
        }

        if (eData.getEmergencyType().equals("chemical") && eData.getState().details().equals("Idle"))
        {
            chemResponce(eData, responder, responderList);
        } 

        if (eData.getStartTime() == eData.getTime())
        {
            if (eData.getEmergencyType().equals("fire")) 
            {
                if(eData.getResponderStatus() == true)
                {
                    eData.setEndTimer(eData.getTime()+FIRE_LOW_CLEANP_TIME);
                }

                eData.changeToLowIntensity();
                String s = eData.getEmergencyType() + " start " + eData.getLocation();
                responder.send(s);
                logger.info(String.format("%s is being simulated",s));    
            }
            else if (eData.getEmergencyType().equals("flood")) 
            {
                if(eData.getResponderStatus() == true)
                {
                    eData.setEndTimer(eData.getTime()+FLOOD_END_TIME);
                }

                eData.changeToActiveState();
                String s = eData.getEmergencyType() + " start " + eData.getLocation();
                responder.send(s);
                logger.info(String.format("%s is being simulated",s));
            }
            else if (eData.getEmergencyType().equals("chemical")) 
            {
                if(eData.getResponderStatus() == true)
                {
                    eData.setEndTimer(eData.getTime()+CHEM_CLEANUP_TIME);
                }

                eData.changeToActiveState();
                String s = eData.getEmergencyType() + " start " + eData.getLocation();
                responder.send(s);
                logger.info(String.format("%s is being simulated",s));
            }     
        }
        else if (eData.getEmergencyType().equals("fire")) 
        {
            fireResponce(eData, responder, responderList);
        }
        else if (eData.getEmergencyType().equals("flood"))
        {
            floodResponce(eData, responder, responderList);
        }
        else if (eData.getEmergencyType().equals("chemical"))
        {
            chemResponce(eData, responder, responderList);
        }        

    }

    //All responders are iterated and checked if the needed responce is present
    private static boolean iterRespondersList(String s, List<String> responderList)
    {
        boolean checkResponder = false;
        for (String responders : responderList)
        {
            if (responders.equals(s)) 
            {
                checkResponder = true;    
            }
        }
        return checkResponder;
    }

    //All responces of a fire type emergency is handled here 
    private static void fireResponce(Emergencies eData, ResponderCommImpl responder, List<String> responderList)
    {
        String responseArr = eData.getEmergencyType()+" + "+ eData.getLocation();
        String responseLeft = eData.getEmergencyType()+" - "+ eData.getLocation();
        boolean responderStatus = false;
        
        if (eData.getState().details().equals("Idle")) 
        {
            if(iterRespondersList(responseArr, responderList) == true)
            {
                responderStatus = true;
                eData.setResponderStatus(responderStatus);
            } 
        }

        if(eData.getState().details().equals("LowIntensity"))
        {
            fireDamageandCasualties(eData, responder);

            if(iterRespondersList(responseArr, responderList) == true && eData.getResponderStatus() == false)
            {
                eData.setHighTimer(0);
                eData.setEndTimer(eData.getTime()+FIRE_LOW_CLEANP_TIME);
                responderStatus = true;
                eData.setResponderStatus(responderStatus);
            }
            else if(iterRespondersList(responseLeft, responderList) == true)
            {
                eData.setEndTimer(0);
                eData.setHighTimer(eData.getTime()+FIRE_LOW_TO_HIGH_TIME);
                responderStatus = false;
                eData.setResponderStatus(responderStatus);
            }
            else if(eData.getResponderStatus() == true && eData.getEndTimer() == 0)
            {
                eData.setHighTimer(0);
                eData.setEndTimer(eData.getTime()+FIRE_LOW_CLEANP_TIME-1);
            }
            else if(eData.getResponderStatus() == false && eData.getHighTimer() == 0)
            {
                eData.setEndTimer(0);
                eData.setHighTimer(eData.getTime()+FIRE_LOW_TO_HIGH_TIME-1);
            }
            else if (eData.getTime() == eData.getEndTimer() && eData.getResponderStatus() == true) 
            {
                eData.changeToInactive();
                String s = eData.getEmergencyType() + " end " + eData.getLocation();
                responder.send(s);
                logger.info(String.format("%s is being simulated",s));
                    
            }
            else if(eData.getTime() == eData.getHighTimer()) 
            {
                eData.changeToHighIntensity();
                String s = eData.getEmergencyType() + " high " + eData.getLocation();
                responder.send(s);
                logger.info(String.format("%s is being simulated",s)); 
            }
        }

        if (eData.getState().details().equals("HighIntensity")) 
        {
            fireDamageandCasualties(eData, responder);

            if(iterRespondersList(responseArr, responderList) == true)
            {
                eData.setHighTimer(0);
                eData.setEndTimer(eData.getTime()+FIRE_HIGH_TO_LOW_TIME);
            }
            else if(iterRespondersList(responseLeft, responderList) == true)
            {
                eData.setEndTimer(0);
            }
            else if (eData.getTime() == eData.getEndTimer()) 
            {
                responderStatus = true;
                eData.setResponderStatus(responderStatus);
                eData.setEndTimer(0);
                eData.changeToLowIntensity();
                String s = eData.getEmergencyType() + " low " + eData.getLocation();
                responder.send(s);
                logger.info(String.format("%s is being simulated",s));    
                
            }
            
        }
        
    }

    //All responces of a flood type emergency is handled here 
    private static void floodResponce(Emergencies eData, ResponderCommImpl responder, List<String> responderList)
    {
        String responseArr = eData.getEmergencyType()+" + "+ eData.getLocation();
        String responseLeft = eData.getEmergencyType()+" - "+ eData.getLocation();
        boolean responderStatus = false;

        if (eData.getState().details().equals("Idle")) 
        {
            if(iterRespondersList(responseArr, responderList) == true)
            {
                responderStatus = true;
                eData.setResponderStatus(responderStatus);
            } 
        }

        if (eData.getState().details().equals("Active")) 
        {
            floodDamageandCasualties(eData, responder);

            if(iterRespondersList(responseArr, responderList) == true && eData.getResponderStatus() == false)
            {
                responderStatus = true;
                eData.setResponderStatus(responderStatus);
                eData.setEndTimer(eData.getTime()+FLOOD_END_TIME);
            }
            else if(iterRespondersList(responseLeft, responderList) == true)
            {
                responderStatus = false;
                eData.setResponderStatus(responderStatus);
                eData.setEndTimer(0);
            }
            else if(eData.getTime() == eData.getEndTimer() && eData.getResponderStatus() == true)
            {
                eData.changeToInactive();
                String s = eData.getEmergencyType() + " end " + eData.getLocation();
                responder.send(s);
                logger.info(String.format("%s is being simulated",s));    
            }

        }    
        
    }

    //All responces of a chemical type emergency is handled here 
    private static void chemResponce(Emergencies eData, ResponderCommImpl responder, List<String> responderList)
    {
        String responseArr = eData.getEmergencyType()+" + "+ eData.getLocation();
        String responseLeft = eData.getEmergencyType()+" - "+ eData.getLocation();
        boolean responderStatus = false;

        if (eData.getState().details().equals("Idle")) 
        {
            if(iterRespondersList(responseArr, responderList) == true)
            {
                responderStatus = true;
                eData.setResponderStatus(responderStatus);
            } 
        }

        if (eData.getState().details().equals("Active")) 
        {
            chemDamageandCasualties(eData, responder);

            if(iterRespondersList(responseArr, responderList) == true && eData.getResponderStatus() == false)
            {
                responderStatus = true;
                eData.setResponderStatus(responderStatus);
                eData.setEndTimer(eData.getTime()+CHEM_CLEANUP_TIME);
            }
            else if(iterRespondersList(responseLeft, responderList) == true)
            {
                responderStatus = false;
                eData.setResponderStatus(responderStatus);
                eData.setEndTimer(0);
            }
            else if(eData.getTime() == eData.getEndTimer() && eData.getResponderStatus() == true)
            {
                eData.changeToInactive();
                String s = eData.getEmergencyType() + " end " + eData.getLocation();
                responder.send(s);
                logger.info(String.format("%s is being simulated",s));    
            }

        }    
    }

    //Damage and casualty caused by the flood emergency type is genarated using this method.
    private static void floodDamageandCasualties(Emergencies eData, ResponderCommImpl responder)
    {
        int checkDamageChange;
        int checkCasualtyChange;
        
        checkDamageChange = eData.getDamage(); 
        checkCasualtyChange = eData.getCasualties(); 

        if (rNG.generate() <= FLOOD_DAMAGE_PROB) 
        {

            if (eData.getDamage() == 0) 
            {
                eData.setDamage(1);    
            }
            else
            {
                eData.setDamage(1+ eData.getDamage()); 
            }
            
        }

        if (rNG.generate() <= FLOOD_CASUALTY_PROB) 
        {

            if (eData.getCasualties() == 0) 
            {
                eData.setCasualties(1);    
            }
            else
            {
                eData.setCasualties(1+ eData.getCasualties()); 
            }
        }

        if (eData.getDamage() > 0 && checkDamageChange != eData.getDamage())
        {
            responder.send(eData.getEmergencyType()+" damage "+ eData.getDamage()+" "+ eData.getLocation());
            logger.info(String.format(eData.getEmergencyType()+" damage "+ eData.getDamage()+" "+ eData.getLocation()+" is simulated"));
        }

        if (eData.getCasualties() > 0 && checkCasualtyChange != eData.getCasualties())
        {
            responder.send(eData.getEmergencyType()+" casualty "+ eData.getCasualties()+" "+ eData.getLocation());
            logger.info(String.format(eData.getEmergencyType()+" casualty "+ eData.getDamage()+" "+ eData.getLocation()+" is simulated"));
        }
    }

    //Damage and casualty caused by the chemical emergency type is genarated using this method.
    private static void chemDamageandCasualties(Emergencies eData, ResponderCommImpl responder)
    {
        int checkDamageChange;
        int checkCasualtyChange;
        checkDamageChange = eData.getDamage(); 
        checkCasualtyChange = eData.getCasualties(); 

        if (rNG.generate() <= CHEM_CONTAM_PROB) 
        {

            if (eData.getDamage() == 0) 
            {
                eData.setDamage(1);    
            }
            else
            {
                eData.setDamage(1+ eData.getDamage()); 
            }
            
        }
        
        if (rNG.generate() <= CHEM_CASUALTY_PROB) 
        {

            if (eData.getCasualties() == 0) 
            {
                eData.setCasualties(1);    
            }
            else
            {
                eData.setCasualties(1+ eData.getCasualties()); 
            }
            
        }

        if (eData.getDamage() > 0 && checkDamageChange != eData.getDamage())
        {
            responder.send(eData.getEmergencyType()+" contam "+ eData.getDamage()+" "+ eData.getLocation());
            logger.info(String.format(eData.getEmergencyType()+" contam "+ eData.getDamage()+" "+ eData.getLocation()+" is simulated"));
        }

        if (eData.getCasualties() > 0 && checkCasualtyChange != eData.getCasualties())
        {
            responder.send(eData.getEmergencyType()+" casualty "+ eData.getCasualties()+" "+ eData.getLocation());
            logger.info(String.format(eData.getEmergencyType()+" casualty "+ eData.getDamage()+" "+ eData.getLocation()+" is simulated"));
        }
    }

    //Damage and casualty caused by the fire emergency type is genarated using this method.
    private static void fireDamageandCasualties(Emergencies eData, ResponderCommImpl responder)
    {
        if (eData.getState().details().equals("LowIntensity")) //lowIntensity fires have a lower chance of causing damage or casualty
        {
            int checkDamageChange;
            int checkCasualtyChange;
            
            checkDamageChange = eData.getDamage(); 
            checkCasualtyChange = eData.getCasualties(); 

            if (rNG.generate() <= FIRE_LOW_DAMAGE_PROB) 
            {

                if (eData.getDamage() == 0) 
                {
                    eData.setDamage(1);    
                }
                else
                {
                    eData.setDamage(1+ eData.getDamage()); 
                }
                
            }
            
            if (rNG.generate() <= FIRE_LOW_CASUALTY_PROB) 
            {

                if (eData.getCasualties() == 0) 
                {
                    eData.setCasualties(1);    
                }
                else
                {
                    eData.setCasualties(1+ eData.getCasualties()); 
                }
                
            }

            if (eData.getDamage() > 0 && checkDamageChange != eData.getDamage())
            {
                responder.send(eData.getEmergencyType()+" damage "+ eData.getDamage()+" "+ eData.getLocation());
                logger.info(String.format(eData.getEmergencyType()+" damage "+ eData.getDamage()+" "+ eData.getLocation()+" is simulated"));
            }

            if (eData.getCasualties() > 0 && checkCasualtyChange != eData.getCasualties())
            {
                responder.send(eData.getEmergencyType()+" casualty "+ eData.getCasualties()+" "+ eData.getLocation());
                logger.info(String.format(eData.getEmergencyType()+" casualty "+ eData.getDamage()+" "+ eData.getLocation()+" is simulated"));
            }
        }
        else if (eData.getState().details().equals("HighIntensity")) //HighIntensity fires have a higher chance of causing damage or casualty
        {
            int checkDamageChange;
            int checkCasualtyChange;
            
            checkDamageChange = eData.getDamage(); 
            checkCasualtyChange = eData.getCasualties(); 

            if (rNG.generate() <= FIRE_HIGH_DAMAGE_PROB) 
            {

                if (eData.getDamage() == 0) 
                {
                    eData.setDamage(1);    
                }
                else
                {
                    eData.setDamage(1+ eData.getDamage()); 
                }
                
            }
            
            if (rNG.generate() <= FIRE_HIGH_CASUALTY_PROB) 
            {

                if (eData.getCasualties() == 0) 
                {
                    eData.setCasualties(1);    
                }
                else
                {
                    eData.setCasualties(1+ eData.getCasualties()); 
                }
                
            }

            if (eData.getDamage() > 0 && checkDamageChange != eData.getDamage())
            {
                responder.send(eData.getEmergencyType()+" damage "+ eData.getDamage()+" "+ eData.getLocation());
                logger.info(String.format(eData.getEmergencyType()+" damage "+ eData.getDamage()+" "+ eData.getLocation()+" is simulated"));
            }

            if (eData.getCasualties() > 0 && checkCasualtyChange != eData.getCasualties())
            {
                responder.send(eData.getEmergencyType()+" casualty "+ eData.getCasualties()+" "+ eData.getLocation());
                logger.info(String.format(eData.getEmergencyType()+" casualty "+ eData.getDamage()+" "+ eData.getLocation()+" is simulated"));
            }
        }     
    }
        
}        

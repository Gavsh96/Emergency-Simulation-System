package edu.curtin.emergencysim.states;

import edu.curtin.emergencysim.types.Emergencies;
@SuppressWarnings("PMD.CommentDefaultAccessModifier")
/*Represents the idle state of the fire emergencies
Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix*/

public class LowIntensity implements EmergencyState
{
    Emergencies emergency;
    public LowIntensity(Emergencies newEmergency)
    {
        emergency = newEmergency;
    }

    @Override
    public String details() 
    {
        return "LowIntensity";
    }

    @Override
    public void changeToInactiveState() 
    {
        emergency.setState(emergency.getInactive());  
    }

    @Override
    public void changeToHighIntensityState() 
    {
        emergency.setState(emergency.getHighIntensity());
        
    }

    @Override
    public void changeToActiveState() 
    {
        System.out.println("cannot move to active state");   
    }

    @Override
    public void changeToLowIntensityState() 
    {
        System.out.println("already in low intensity state");
    }
    
} 
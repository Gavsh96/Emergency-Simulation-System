package edu.curtin.emergencysim.states;

import edu.curtin.emergencysim.types.Emergencies;
@SuppressWarnings("PMD.CommentDefaultAccessModifier")
/*Represents the high intensity state of the fire emergencies
Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix*/

public class HighIntensity implements EmergencyState
{
    Emergencies emergency;
    public HighIntensity(Emergencies newEmergency)
    {
        emergency = newEmergency;
    }

    @Override
    public String details() 
    {
        return "HighIntensity";
    }

    @Override
    public void changeToInactiveState() 
    {
        System.out.println("Cannot change to inactive state from high intensity state");       
    }

    @Override
    public void changeToHighIntensityState() 
    {
        System.out.println("already in high intensity state");
    }

    @Override
    public void changeToActiveState() 
    {
        System.out.println("Cannot change to active state from high intensity state");
    }

    @Override
    public void changeToLowIntensityState() 
    {
        emergency.setState(emergency.getLowIntensity());   
    }
    
} 
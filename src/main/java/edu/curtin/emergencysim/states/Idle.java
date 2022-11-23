package edu.curtin.emergencysim.states;

import edu.curtin.emergencysim.types.Emergencies;
@SuppressWarnings("PMD.CommentDefaultAccessModifier")
/*Represents the idle state of the fire, flood and chemical emergencies
Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix*/

public class Idle implements EmergencyState
{
    Emergencies emergency;
    public Idle(Emergencies newEmergency)
    {
        emergency = newEmergency;
    }

    @Override
    public String details() 
    {
        return "Idle";
    }

    @Override
    public void changeToActiveState()
    {
        emergency.setState(emergency.getActive());    
    }

    @Override
    public void changeToInactiveState() 
    {
        System.out.println("Cannot change from idle to inactive state");       
    }

    @Override
    public void changeToHighIntensityState() 
    {
        System.out.println("Cannot change from idle to high intensity state");    
    }

    @Override
    public void changeToLowIntensityState() 
    {
        emergency.setState(emergency.getLowIntensity()); 
    }
    
} 

package edu.curtin.emergencysim.states;

import edu.curtin.emergencysim.types.Emergencies;
@SuppressWarnings("PMD.CommentDefaultAccessModifier")
/*Represents the idle state of the fire, flood and chemical emergencies
Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix*/

public class Inactive implements EmergencyState
{
    Emergencies emergency;
    public Inactive(Emergencies newEmergency)
    {
        emergency = newEmergency;
    }

    @Override
    public String details() 
    {
        return "Inactive";
    }

    @Override
    public void changeToInactiveState() 
    {
        System.out.println("already in inactive state");   
    }

    @Override
    public void changeToHighIntensityState() 
    {
        System.out.println("cannot move to high intensity state");
    }

    @Override
    public void changeToActiveState() 
    {
        System.out.println("cannot move to active state");
    }

    @Override
    public void changeToLowIntensityState() 
    {
        System.out.println("cannot move to low intensity state");    
    }

    
} 
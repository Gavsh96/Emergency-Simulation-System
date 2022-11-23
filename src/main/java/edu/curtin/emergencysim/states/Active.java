package edu.curtin.emergencysim.states;

import edu.curtin.emergencysim.types.Emergencies;
@SuppressWarnings("PMD.CommentDefaultAccessModifier")
/*Represents the active state of the flood and chemical emergencies
Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix*/

public class Active implements EmergencyState
{
    Emergencies emergency;
    public Active(Emergencies newEmergency)
    {
        emergency = newEmergency;
    }

    @Override
    public String details() 
    {
        return "Active";
    }

    @Override
    public void changeToInactiveState() 
    {
        emergency.setState(emergency.getInactive());       
    }

    @Override
    public void changeToHighIntensityState() 
    {
        System.out.println("Cannot change from active to high intensity state");    
    }

    @Override
    public void changeToActiveState()
    {
        System.out.println("Already in active state");    
    }

    @Override
    public void changeToLowIntensityState() 
    {
        System.out.println("Cannot change from active to high intensity state");
    }
    
} 
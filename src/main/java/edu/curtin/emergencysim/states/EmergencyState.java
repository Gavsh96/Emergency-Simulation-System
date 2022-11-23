package edu.curtin.emergencysim.states;
/*Interface used to implement state pattern*/
public interface EmergencyState
{
    public String details();
    public void changeToActiveState(); 
    public void changeToInactiveState();
    public void changeToLowIntensityState();
    public void changeToHighIntensityState();
      
}
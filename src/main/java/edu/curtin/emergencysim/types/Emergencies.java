package edu.curtin.emergencysim.types;

import edu.curtin.emergencysim.states.*;
/*Emergencies interface used to generate emergencies stated in the file*/

public interface Emergencies
{
    public void setState(EmergencyState active);
    
    public void setTime(int startTime);

    public int getStartTime();

    public void setEmergencyType(String type);

    public void setLocation(String location);

    public void update(int time);

    public int getTime();
    
    public String getEmergencyType();

    public String getLocation();

    public void setEndTimer(int endTimer);

    public int getEndTimer();

    public void setHighTimer(int highTimer);
    
    public int getHighTimer(); 

    public EmergencyState getState();

    public EmergencyState getIdle();

    public EmergencyState getActive();

    public EmergencyState getInactive();

    public EmergencyState getLowIntensity();

    public EmergencyState getHighIntensity();

    public void changeToActiveState();

    public void changeToInactive();
    
    public void changeToLowIntensity(); 

    public void changeToHighIntensity();   

    public void setResponderStatus(boolean status);

    public boolean getResponderStatus();

    public void setDamage(int damage);

    public int getDamage();

    public void setCasualties(int casualty);

    public int getCasualties();

}
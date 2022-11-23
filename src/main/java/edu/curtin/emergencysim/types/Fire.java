package edu.curtin.emergencysim.types;

import edu.curtin.emergencysim.observers.Subject;
import edu.curtin.emergencysim.states.*;

@SuppressWarnings("PMD.CommentDefaultAccessModifier")
/*Represents A fire emergency
Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix*/

public class Fire implements Emergencies 
{
    private int time;
    private int startTime;
    private int highTime;
    private int endTime;
    private String type;
    private String location;
    private EmergencyState idle;
    private EmergencyState lowIntensity;
    private EmergencyState highIntensity;
    private EmergencyState inactive;
    private EmergencyState stateOFEmergency;
    private boolean status;
    private int damage;
    private int casualties;
    Subject observerE;

    public Fire(Subject observerE)
    {
        this.observerE = observerE;
        idle = new Idle(this);
        lowIntensity = new LowIntensity(this);
        highIntensity = new HighIntensity(this);
        inactive = new Inactive(this);
        stateOFEmergency = idle;
        observerE.register(this);
    }

    @Override
    public void setState(EmergencyState newState)
    {
        stateOFEmergency = newState;
    }

    @Override
    public void setTime(int startTime) 
    {
        this.startTime = startTime;   
    }

    @Override
    public void setEmergencyType(String type) 
    {
        this.type = type;  
    }

    @Override
    public void setLocation(String location) 
    {
        this.location = location;
    }

    @Override
    public int getTime() 
    {
        return time;
    }

    @Override
    public String getEmergencyType() 
    
    {
        return type;
    }

    @Override
    public String getLocation() 
    {
        return location;
    }

    @Override
    public EmergencyState getIdle()
    {
        return idle;
    }

    @Override
    public EmergencyState getActive()
    {
        System.out.println("fire do not have an active state");
        return null;
    }
    
    @Override
    public EmergencyState getInactive()
    {
        return inactive;
    }

    @Override
    public EmergencyState getLowIntensity() 
    {
        return lowIntensity;
    }

    @Override
    public EmergencyState getHighIntensity() 
    {
        return highIntensity;
    }

    @Override
    public void changeToActiveState() 
    {
        System.out.println("Cannot go to the active state");
    }

    @Override
    public void changeToInactive() 
    {
        stateOFEmergency.changeToInactiveState();   
    }

    @Override
    public void changeToLowIntensity() 
    {
        stateOFEmergency.changeToLowIntensityState();    
    }

    @Override
    public void changeToHighIntensity() 
    {
        stateOFEmergency.changeToHighIntensityState();   
    }

    @Override
    public EmergencyState getState() 
    {
        return stateOFEmergency;
    }

    @Override
    public void update(int time) 
    {
        this.time = time;
    }

    @Override
    public int getStartTime() 
    {
        return startTime;    
    }

    @Override
    public void setEndTimer(int endTimer) 
    {
        this.endTime = endTimer;    
    }

    @Override
    public int getEndTimer() 
    {
        return endTime;
    }

    @Override
    public void setHighTimer(int highTimer) 
    {
        this.highTime = highTimer;
    }

    @Override
    public int getHighTimer() 
    {
        return highTime;
    }

    @Override
    public void setResponderStatus(boolean status) 
    {
        this.status = status;
        
    }

    @Override
    public boolean getResponderStatus() 
    {
        return status;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
        
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setCasualties(int casualties) {
        this.casualties = casualties;
        
    }

    @Override
    public int getCasualties() 
    {
        return casualties;
    }

}
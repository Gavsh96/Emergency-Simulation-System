package edu.curtin.emergencysim.types;

import edu.curtin.emergencysim.observers.Subject;
import edu.curtin.emergencysim.states.*;

@SuppressWarnings("PMD.CommentDefaultAccessModifier")
/*Represents A flood emergency
Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix*/

public class Flood implements Emergencies 
{
    private int time;
    private int startTime;
    private int endTime;
    private int damage;
    private int casualty;
    private String type;
    private String location;
    private EmergencyState idle;
    private EmergencyState active;
    private EmergencyState inactive;
    private EmergencyState stateOFEmergency;
    private boolean status;
    Subject observeE;

    public Flood(Subject observeE)
    {
        this.observeE = observeE;
        idle = new Idle(this);
        active = new Active(this);
        inactive = new Inactive(this);
        stateOFEmergency = idle;
        observeE.register(this);
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
        return active;
    }
    
    @Override
    public EmergencyState getInactive()
    {
        return inactive;
    }

    @Override
    public EmergencyState getLowIntensity() 
    {
        System.out.println("flood do not have an low intensity state");
        return null;
    }

    @Override
    public EmergencyState getHighIntensity() 
    {
        System.out.println("flood do not have an high intensity state");
        return null;
    }

    @Override
    public void changeToActiveState() 
    {
        stateOFEmergency.changeToActiveState();  
    }

    @Override
    public void changeToInactive() 
    {
        stateOFEmergency.changeToInactiveState();   
    }

    @Override
    public void changeToHighIntensity() 
    {
        System.out.println("do not have a high intensity state");    
    }

    @Override
    public void changeToLowIntensity() 
    {
        System.out.println("do not have a low intensity state");    
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
       System.out.println("do not have a high intensity state"); 
    }

    @Override
    public int getHighTimer() {
        System.out.println("do not have a high intensity state");
        return 0;
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
    public void setCasualties(int casualty) {
        this.casualty = casualty;
        
    }

    @Override
    public int getCasualties() {
        return casualty;
    }
    
}

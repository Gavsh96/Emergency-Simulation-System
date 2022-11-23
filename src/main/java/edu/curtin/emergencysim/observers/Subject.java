package edu.curtin.emergencysim.observers;

import edu.curtin.emergencysim.types.Emergencies;
/*Interface created to implement the observer pattern*/
public interface Subject
{
    public void register(Emergencies e);

    public void unregister(Emergencies e);

    public void notifyEmergencies();
}
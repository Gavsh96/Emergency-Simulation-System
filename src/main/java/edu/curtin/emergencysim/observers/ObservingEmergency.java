package edu.curtin.emergencysim.observers;

import java.util.ArrayList;
import java.util.List;
import edu.curtin.emergencysim.types.Emergencies;
/*Used to implement the observer pattern to update the time value in real time of every emergency genarated*/
public class ObservingEmergency implements Subject{

    private List<Emergencies> emer;

    private int time;

    public ObservingEmergency()
    {
        emer = new ArrayList<>();
    }
    
    @Override
    public void register(Emergencies newEmer) 
    {
        emer.add(newEmer);
    }

    @Override
    public void unregister(Emergencies deleteEmergencies)
    {
        int emergenciesIndex = emer.indexOf(deleteEmergencies);

        System.out.println("Emergencies " + (emergenciesIndex+1) + " deleted");

        emer.remove(emergenciesIndex);
    }

    @Override
    public void notifyEmergencies() //notifies and updates all the emergencies genarated
    {
        for(Emergencies em : emer)
        {
            em.update(time);
        }
    }

    public void setTime(int time)
    {
        this.time = time;
        notifyEmergencies();
    }

}

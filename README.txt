README
======
* Type "./gradlew run" to run. 
* Type "./gradlew check" to verify PMD rules. (On Windows, drop the "./" from start of course; i.e., "gradlew run" or "gradlew check".)

This is a Emergency Simulation System made using java 
which genarates and displays the emergenices based on 
the input text file and also recives updates from responders on the sate of the emergency.

Observer pattern was used to update the time values in all the emergencies created in real time.

State pattern was used to set states to fire flood and chemical emergencies.

When a fire emergency is created it is created with an idle state when the 
runtime is equal to the start time it switches to low intensity from idle 
and switches to high intensity if there are no responders. If responders are present 
and fire is at low intensity it changes to the inactive state after low to end time. 
If responders are present at a fire with high intensity state changes from high to low to inactive.

Flood and chemical emergencies have the states idle active and inactive when runtime is equal to the 
start time it switches from idle to active and if responders are present the state will change from 
active to inactive depending on the end time.


  
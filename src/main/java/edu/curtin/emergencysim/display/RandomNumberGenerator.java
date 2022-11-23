package edu.curtin.emergencysim.display;
/*Used genarate a random number between 0 & 1 to decide if a damage or a casualty occurs during an emergency*/
public class RandomNumberGenerator 
{
    public double generate()
    {
      double randomInt = Math.random();
      return randomInt;
    }
}
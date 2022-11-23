package edu.curtin.emergencysim.exceptions;
/*Used to provide exceptions for the command line arguments*/
public class ArgumentException extends Exception
{
    public ArgumentException(String msg) 
    { 
        super(msg); 
    }
    
    public ArgumentException(String msg, Throwable cause) 
    { 
        super(msg, cause); 
    }
}
package edu.curtin.emergencysim.exceptions;
/*Used to provide exceptions for the input file*/
public class FileReadException extends Exception
{
    public FileReadException(String msg) 
    { 
        super(msg); 
    }
    
    public FileReadException(String msg, Throwable cause) 
    { 
        super(msg, cause); 
    }
}
package edu.curtin.emergencysim.responders;
import java.util.*;
/*Had to suppress the CommentDefaultAccessModifier PMD warning that i could not figure out how to fix*/
@SuppressWarnings("PMD.CommentDefaultAccessModifier")
public interface ResponderComm
{
    List<String> poll();
    void send(String s);
}
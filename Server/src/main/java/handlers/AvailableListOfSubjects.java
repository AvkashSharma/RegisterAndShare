package handlers;

import requests.Request;
import requests.RequestType;

import java.util.ArrayList;
import java.util.List;

/**
 * Get available subjects Request to send to cleint
 */
public class AvailableListOfSubjects extends Request {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String clientName;
    List<String> AvailableListOfSubjects= new ArrayList<String>();
    
    public AvailableListOfSubjects(int reqNumber,String clientName,List<String>AvailableListOfSubjects){
        super(RequestType.AVAILABLE_LIST_OF_SUBJECTS,reqNumber);
        this.clientName=clientName;
        this.AvailableListOfSubjects=AvailableListOfSubjects;

    }
     public String getClientName(){
        return clientName;
    }
     public List<String> getAvailableListOfSubjects(){
        return AvailableListOfSubjects;
    }
    @Override
    public String toString() {
        return RequestType.AVAILABLE_LIST_OF_SUBJECTS + " "+ getClientName() + " " + getAvailableListOfSubjects();
    }

    public void print(){
        System.out.println(this.toString());
    }
}

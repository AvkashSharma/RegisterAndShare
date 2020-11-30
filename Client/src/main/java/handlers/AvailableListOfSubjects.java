package handlers;

import java.io.Serializable;
import requests.Request;
import requests.RequestType;
import java.util.ArrayList;
import java.util.List;
public class AvailableListOfSubjects extends Request implements Serializable {
    
    String clientName;
    List<String> AvailableListOfSubjects= new ArrayList<String>();
    
    public AvailableListOfSubjects(int reqNumber,String clientName,List<String>AvailableListOfSubjects){
        super(RequestType.AVAILABLE_LIST_OF_SUBJECTS);
        this.clientName=clientName;
        this.AvailableListOfSubjects=AvailableListOfSubjects;

    }
     public String getClientName(){
        return clientName;
    }
     public List<String> getAvailableListOfSubjects(){
        return getAvailableListOfSubjects();
    }
    @Override
    public String toString() {
        return RequestType.AVAILABLE_LIST_OF_SUBJECTS + " "+ getClientName() + " " + getAvailableListOfSubjects();
    }

    public void print(){
        System.out.println(this.toString());
    }
}
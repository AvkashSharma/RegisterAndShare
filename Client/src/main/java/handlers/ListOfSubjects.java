package handlers;

import java.io.Serializable;
import requests.Request;
import requests.RequestType;
import java.util.ArrayList;
import java.util.List;
public class ListOfSubjects extends Request implements Serializable {
    
    String clientName;
    List<String> ListOfSubjects= new ArrayList<String>();
    
    public ListOfSubjects(String clientName,List<String>ListOfSubjects){
        super(RequestType.LIST_OF_SUBJECTS);
        this.clientName=clientName;
        this.ListOfSubjects=ListOfSubjects;

    }
     public String getClientName(){
        return clientName;
    }
     public List<String> getListOfSubjects(){
        return getListOfSubjects();
    }
    @Override
    public String toString() {
        return RequestType.LIST_OF_SUBJECTS + " "+ getClientName() + " " + getListOfSubjects();
    }

    public void print(){
        System.out.println(this.toString());
    }
}

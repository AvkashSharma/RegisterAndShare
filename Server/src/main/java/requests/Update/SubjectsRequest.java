package requests.Update;
import java.io.Serializable;
import requests.Request;
import requests.RequestType;

/*
  - Request # 
  - Unique name
  - List of Subjects
  */

public class SubjectsRequest extends Request implements Serializable{

    
    String clientName;
    String [] listOfSubjects;

    public SubjectsRequest(String clientName, String[] listOfSubjects) {
        super(RequestType.SUBJECTS);
        this.clientName = clientName;
        this.listOfSubjects=listOfSubjects;
    }

    public String getClientName() {
        return clientName;
    }
    public String[] getListOfSubjects() {
        return listOfSubjects;
    }


    @Override
    public String toString() {
        return RequestType.SUBJECTS + " " + this.getRid()+" "+ getClientName() + " " +getListOfSubjects();
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}
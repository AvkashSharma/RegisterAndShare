package requests.Update;
import java.io.Serializable;
import requests.Request;
import requests.RequestType;
import java.util.ArrayList;
import java.util.List;
/*
- Request # 
- Unique name
- List of Subjects
  */

public class SubjectsRequest extends Request implements Serializable{

    
    String clientName;
    List <String>listOfSubjects=new ArrayList<String>();

    public SubjectsRequest(int reqNumber, String clientName, List<String>listOfSubjects) {
        super(RequestType.SUBJECTS, reqNumber);
        this.clientName = clientName;
        this.listOfSubjects=listOfSubjects;
    }

    public String getClientName() {
        return clientName;
    }
    public List<String> getListOfSubjects() {
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

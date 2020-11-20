package Requests.Update;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;



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

    @Override
    public String toString() {
        return RequestType.SUBJECTS + " " + this.getRid()+" "+ clientName + " " +listOfSubjects ;
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}

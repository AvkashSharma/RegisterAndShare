package Requests.Update;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;




public class SubjectsRejected extends Request implements Serializable {
     
    String clientName;
     String [] listOfSubjects;

    public SubjectsRejected(String clientName, String[] listOfSubjects){
      super(RequestType.SUBJECTS_UPDATED);
      this.clientName = clientName;
      this.listOfSubjects=listOfSubjects;
    }
    
    @Override
    public String toString(){
        return RequestType.SUBJECTS_REJECTED+ " " + this.getRid()+" "+ clientName+" "+listOfSubjects ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}

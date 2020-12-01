package requests.Update;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import requests.Request;
import requests.RequestType;

/*
- Request # 
- Unique name
- List of Subjects
  */

public class SubjectsRequest extends Request implements Serializable{

    
    String clientName;
    List <String>subjectsToSubscribe=new ArrayList<String>();
  

    public SubjectsRequest(int reqNumber, String clientName,List<String>subjectsToSubscribe) {
        super(RequestType.SUBJECTS, reqNumber);
        this.clientName = clientName;
        this.subjectsToSubscribe=subjectsToSubscribe;
    }

    public String getClientName() {
        return clientName;
    }

   
    public List<String> getSubjectsToSubscribe() {
        return subjectsToSubscribe;
    }


    @Override
    public String toString() {
        return RequestType.SUBJECTS + " " + this.getRid()+" "+ getClientName() +" "+getSubjectsToSubscribe();
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}

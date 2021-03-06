package requests.Update;
import requests.Request;
import requests.RequestType;
import java.util.ArrayList;
import java.util.List;
/*
- Request # 
- Unique name
- List of Subjects
  */

public class SubjectsRequest extends Request {

    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
        return RequestType.SUBJECTS + " " + this.getRid()+" "+ getClientName()+ " "+getSubjectsToSubscribe();
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}

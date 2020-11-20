package Requests.Publish;
import java.io.Serializable;

import Requests.Request;
import Requests.RequestType;



public class PublishRequest extends Request implements Serializable{

    
    String clientName;
    String  Subject;
  

    public PublishRequest(String clientName, String Subject) {
        super(RequestType.PUBLISH);
        this.clientName = clientName;
        this.Subject=Subject;  
    }

    public String getClientName() {
        return clientName;
    }

    public String getSubject(){
        return Subject;
    }
    

    @Override
    public String toString() {
        return RequestType.PUBLISH + " " + this.getRid()+" "+ clientName + " " ;
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}

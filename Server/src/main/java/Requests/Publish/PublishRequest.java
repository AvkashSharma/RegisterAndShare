package Requests.Publish;
import java.io.Serializable;

import Requests.Request;
import Requests.RequestType;

/*
  - Request # 
  - Unique name
  -  Subject
  - Text
  */

public class PublishRequest extends Request implements Serializable{

    
    String clientName;
    String  Subject;
    String Text;

    public PublishRequest(String clientName, String Subject, String Text) {
        super(RequestType.PUBLISH);
        this.clientName = clientName;
        this.Subject=Subject;  
        this.Text= Text;
    }

    public String getClientName() {
        return clientName;
    }

    public String getSubject(){
        return Subject;
    }
    public String getText(){
        return Text;
    }
    

    @Override
    public String toString() {
        return RequestType.PUBLISH + " " + this.getRid()+" "+ getClientName() + " " +getSubject()+" " + getText() ;
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}

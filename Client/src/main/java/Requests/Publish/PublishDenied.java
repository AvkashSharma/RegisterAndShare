package Requests.Publish;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;
public class PublishDenied extends Request implements Serializable {
    
    String Reason;

    public PublishDenied(String Reason){

        super(RequestType.PUBLISH_DENIED);
       
        this.Reason=Reason;

    }
    
     
     public String getReason(){
        return Reason;
    }
@Override
    public String toString() {
        return RequestType.PUBLISH_DENIED + " "+ this.getRid() + " " + Reason;
    }

    public void print(){
        System.out.println(this.toString());
    }
}

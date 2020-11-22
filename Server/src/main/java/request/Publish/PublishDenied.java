package requests.Publish;
import java.io.Serializable;
import requests.Request;
import requests.RequestType;
/*
  - Request # 
  - Reason
  */
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
        return RequestType.PUBLISH_DENIED + " "+ this.getRid() + " " + getReason();
    }

    public void print(){
        System.out.println(this.toString());
    }
}

package Requests.Registration;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;


/*
Register Request
- Request #
- Reason
*/
public class RegisteredDenied extends Request implements Serializable {
   
    
    String reason;

    public RegisteredDenied(String reason){
      super(RequestType.REGISTERED_DENIED);
      
      this.reason=reason;

    }
    public String getReason() {
        return reason;
    }
    @Override
    public String toString(){
        return RequestType.REGISTERED_DENIED+ " "  + this.getRid()+" "+getReason() ;
    }

    public void print(){
        System.out.println(this.toString());
    }

}


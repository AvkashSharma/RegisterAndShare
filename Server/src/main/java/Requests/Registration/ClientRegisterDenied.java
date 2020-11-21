package Requests.Registration;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;


/*
Register Request
- Request #
- Reason
*/
public class ClientRegisterDenied extends Request implements Serializable {
   
    
    String reason;

    public ClientRegisterDenied(String reason){
      super(RequestType.CLIENT_REGISTER_DENIED);
      
      this.reason=reason;

    }
    public String getReason() {
        return reason;
    }
    @Override
    public String toString(){
        return RequestType.CLIENT_REGISTER_DENIED+ " "  + this.getRid()+" "+getReason() ;
    }

    public void print(){
        System.out.println(this.toString());
    }

}


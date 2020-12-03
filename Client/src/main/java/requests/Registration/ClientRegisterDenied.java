package requests.Registration;
import requests.Request;
import requests.RequestType;


/*
Register Request
- Request #
- Reason
*/
public class ClientRegisterDenied extends Request {
   
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String reason;

    public ClientRegisterDenied(String reason, int rid){
        super(RequestType.CLIENT_REGISTER_DENIED);
        this.rid = rid;
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


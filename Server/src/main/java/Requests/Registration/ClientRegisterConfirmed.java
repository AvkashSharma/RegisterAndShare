package requests.Registration;
import java.io.Serializable;
import requests.Request;
import requests.RequestType;


/*
Register Request
- Request #

*/

public class ClientRegisterConfirmed extends Request implements Serializable {
    
 
    public ClientRegisterConfirmed(){
      super(RequestType.CLIENT_REGISTER_CONFIRMED);
     
    }
    
    @Override
    public String toString(){
        return RequestType.CLIENT_REGISTER_CONFIRMED+ " " + this.getRid()+ " " ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}

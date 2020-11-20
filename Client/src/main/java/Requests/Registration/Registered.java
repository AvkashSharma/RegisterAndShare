package Requests.Registration;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;


/*
Register Request
- Request #
*/

public class Registered extends Request implements Serializable {
    

    public Registered(){
      super(RequestType.REGISTERED);
    }
    
    @Override
    public String toString(){
        return RequestType.REGISTERED+ " " + this.getRid() ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}

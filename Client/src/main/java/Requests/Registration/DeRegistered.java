package Requests.Registration;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;




public class DeRegistered extends Request implements Serializable {
   
    String clientName;

    public DeRegistered(String clientName){
      super(RequestType.DE_REGISTERED);
      this.clientName = clientName;
      
    }
    
    @Override
    public String toString(){
        return RequestType.DE_REGISTERED+ " " +clientName ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}

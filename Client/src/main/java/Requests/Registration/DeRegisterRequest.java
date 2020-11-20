package Requests.Registration;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;




public class DeRegisterRequest extends Request implements Serializable {
   
    String clientName;

    public DeRegisterRequest(String clientName){
      super(RequestType.DE_REGISTER);
      this.clientName = clientName;
      

    }
    
    @Override
    public String toString(){
        return RequestType.DE_REGISTER+ " " + this.getRid()+" "+clientName ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}

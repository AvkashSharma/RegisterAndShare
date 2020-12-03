package requests.Registration;
import requests.Request;
import requests.RequestType;

/*
Register Request
- Request #
*/

public class ClientRegisterConfirmed extends Request  {
    
    /**
   *
   */
  private static final long serialVersionUID = 1L;

  public ClientRegisterConfirmed() {
      super(RequestType.CLIENT_REGISTER_CONFIRMED);
    }

    public ClientRegisterConfirmed(int rid){
        super(RequestType.CLIENT_REGISTER_CONFIRMED);
        this.rid = rid;
      }
    
    @Override
    public String toString(){
        return RequestType.CLIENT_REGISTER_CONFIRMED+ " " + this.getRid()+ " " ;
    }

    public void print(){
        System.out.println(this.toString());
    }

}

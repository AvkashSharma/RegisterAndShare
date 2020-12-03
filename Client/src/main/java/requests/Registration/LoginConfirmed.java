package requests.Registration;

import requests.Request;
import requests.RequestType;


public class LoginConfirmed extends Request  {
    
    /**
   *
   */
  private static final long serialVersionUID = 1L;

  public LoginConfirmed() {
      super(RequestType.LOGIN_CONFIRMED);
    }

    public LoginConfirmed(int rid){
        super(RequestType.LOGIN_CONFIRMED);
        this.rid = rid;
      }
    
    @Override
    public String toString(){
        return RequestType.LOGIN_CONFIRMED+ " " + this.getRid()+ " " ;
    }

    public void print(){
        System.out.println(this.toString());
    }

}

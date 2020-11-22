package requests.Update;
import java.io.Serializable;

import requests.Request;
import requests.RequestType;


/*
  - Request # 
  - Reason
  */
public class UpdateDenied extends Request implements Serializable {
   
    String reason;

    public UpdateDenied( String reason){
      super(RequestType.UPDATE_DENIED);

      this.reason=reason;

    }
    public String getReason() {
        return reason;
    }
    @Override
    public String toString(){
        return RequestType.UPDATE_DENIED+ " "  + this.getRid()+" "+getReason() ;
    }

    public void print(){
        System.out.println(this.toString());
    }

}


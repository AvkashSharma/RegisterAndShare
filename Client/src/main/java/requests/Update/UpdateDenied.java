package requests.Update;

import requests.Request;
import requests.RequestType;


/*
  - Request # 
  - Reason
  */
public class UpdateDenied extends Request {
   
    /**
   *
   */
  private static final long serialVersionUID = 1L;
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


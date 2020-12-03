package requests.Publish;
import requests.Request;
import requests.RequestType;
/*
  - Request # 
  - Reason
  */
public class PublishDenied extends Request  {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String Reason;

    public PublishDenied(int reqNumber,String Reason){

        super(RequestType.PUBLISH_DENIED,reqNumber);
    
        this.Reason=Reason;

    }
    
    public String getReason(){
        return Reason;
    }
@Override
    public String toString() {
        return RequestType.PUBLISH_DENIED + " "+ this.getRid() + " " + getReason();
    }

    public void print(){
        System.out.println(this.toString());
    }
}

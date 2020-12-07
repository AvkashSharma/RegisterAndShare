package requests.Registration;
import requests.Request;
import requests.RequestType;
/*
- Request # 
- Unique name
- Ip address
- Socket
  */

public class DisconnectRequest extends Request {
    private static final long serialVersionUID = 1L;
    String clientName;
    

    public DisconnectRequest(int reqNumber, String clientName) {
        super(RequestType.DISCONNECT, reqNumber);
        this.clientName = clientName;
      
    }

    public String getClientName() {
        return clientName;
    }


    @Override
    public String toString() {
        return RequestType.DISCONNECT + " " + this.getRid()+" "+ getClientName();
    }

    public void print(){
        System.out.println(this.toString());
    }
}

    


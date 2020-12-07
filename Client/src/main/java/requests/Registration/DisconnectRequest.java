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
    private String address;
    private int port;

    public DisconnectRequest(int reqNumber, String clientName,String address,int port) {
        super(RequestType.DISCONNECT, reqNumber);
        this.clientName = clientName;
        this.address=address;
        this.port=port;
    }

    public String getClientName() {
        return clientName;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }


    @Override
    public String toString() {
        return RequestType.DISCONNECT + " " + this.getRid()+" "+ getClientName() +" "+getAddress()+ " "+ getPort();
    }

    public void print(){
        System.out.println(this.toString());
    }
}

    


package requests.Registration;
import requests.Request;
import requests.RequestType;


/*
Register Request
- Request #
- Name
- Ip address
- Socket#
*/
public class ServerRegisterDenied extends Request {


    private static final long serialVersionUID = 1L;
    private String clientName;
    private String address;
    private int socket;

    public ServerRegisterDenied(int rqNumber, String clientName, String address, int socket){
        super(RequestType.SERVER_REGISTER_DENIED,rqNumber); 
        this.clientName=clientName;
        this.address = address;
        this.socket = socket; 
    }
    
    public String getClientName() {
        return clientName;
    }

    public String getAddress() {
        return address;
    }

    public int getSocket() {
        return socket;
    }
    
    @Override
    public String toString() {
        return RequestType.SERVER_REGISTER_DENIED + " " + this.getRid() + " " + getClientName() + " " + getAddress() + ":"
            + getSocket();
    }

    public void print(){
        System.out.println(this.toString());
    }

}


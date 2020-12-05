package requests.Registration;
import requests.Request;
import requests.RequestType;

/*
Register Request
- Request #
- Unique Name
- Ip
- Socket
*/

public class ServerRegisterConfirmed extends Request {
    
    /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String clientName;
  private String address;
  private int socket;
  
    
  public ServerRegisterConfirmed(int rqNumber, String clientName, String address, int socket){
      super(RequestType.SERVER_REGISTER_CONFIRMED,rqNumber);
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
        return RequestType.SERVER_REGISTER_CONFIRMED + " " + this.getRid() + " " + getClientName() + " " + getAddress() + ":"
                + getSocket();
    }
    

    public void print(){
        System.out.println(this.toString());
    }


}

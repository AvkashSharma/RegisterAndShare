package requests.Registration;
import requests.Request;
import requests.RequestType;

public class DisconnectClientServerToServer extends Request {
      

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String clientName;

    public DisconnectClientServerToServer(String clientName){
        super(RequestType.DISCONNECT_CLIENT_SERVER_TO_SERVER);
        this.clientName = clientName;
    }
    public String getClientName(){
        return clientName;
    }
    
    @Override
    public String toString(){
        return RequestType.DISCONNECT_CLIENT_SERVER_TO_SERVER+ " "+getClientName() ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}



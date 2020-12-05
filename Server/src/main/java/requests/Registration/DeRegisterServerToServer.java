package requests.Registration;
import requests.Request;
import requests.RequestType;


/*

- Unique Name
*/

public class DeRegisterServerToServer extends Request {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String clientName;

    public DeRegisterServerToServer(String clientName){
        super(RequestType.DE_REGISTER_SERVER_TO_SERVER);
        this.clientName = clientName;
    }
    public String getClientName(){
        return clientName;
    }
    
    @Override
    public String toString(){
        return RequestType.DE_REGISTER_SERVER_TO_SERVER+ " "+getClientName() ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}

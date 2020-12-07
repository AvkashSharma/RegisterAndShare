package requests.Registration;
import requests.Request;
import requests.RequestType;


public class DisconnectionConfirmed extends Request {

    /**
     * 
     */

    private static final long serialVersionUID = 1L;

    public DisconnectionConfirmed() {
        super(RequestType.DISCONNECTON_CONFIRMED);
    }

    @Override
    public String toString() {
        return RequestType.DISCONNECTON_CONFIRMED + " " + this.getRid() + " ";
    }

    public void print() {
        System.out.println(this.toString());
    }

}

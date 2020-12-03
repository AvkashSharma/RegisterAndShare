package requests.Registration;
import requests.Request;
import requests.RequestType;

/*
- Request #
- Unique Name
*/

public class DeRegisterRequest extends Request {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String clientName;

    public DeRegisterRequest(int rid, String clientName) {
        super(RequestType.DE_REGISTER, rid);
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public String toString() {
        return RequestType.DE_REGISTER + " " + this.getRid() + " " + getClientName();
    }

    public void print() {
        System.out.println(this.toString());
    }

}

package requests.Registration;
import java.io.Serializable;
import requests.Request;
import requests.RequestType;


/*
- Request #
- Unique Name
*/

public class DeRegisterRequest extends Request implements Serializable {

    String clientName;

    public DeRegisterRequest(int rid, String clientName) {
        super(RequestType.DE_REGISTER);
        this.clientName = clientName;
        this.rid = rid;
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

package requests.Registration;

import requests.Request;
import requests.RequestType;

public class DeRegisterConfirmed extends Request  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DeRegisterConfirmed() {
        super(RequestType.DE_REGISTER_CONFIRMED);
    }

    @Override
    public String toString() {
        return RequestType.DE_REGISTER_CONFIRMED + " " + this.getRid() + " ";
    }

    public void print() {
        System.out.println(this.toString());
    }

}

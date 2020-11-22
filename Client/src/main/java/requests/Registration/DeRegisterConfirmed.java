package requests.Registration;

import java.io.Serializable;

import requests.Request;
import requests.RequestType;

public class DeRegisterConfirmed extends Request implements Serializable {

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

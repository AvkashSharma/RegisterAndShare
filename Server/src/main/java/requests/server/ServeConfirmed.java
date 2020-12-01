package requests.server;

import java.io.Serializable;

public class ServeConfirmed implements Serializable{
    String confirmation = "";

    public  ServeConfirmed(String confirm) {
        this.confirmation = confirm;
    }

    @Override
    public String toString() {
        return "SERVE CONFIRMED - "+this.confirmation;
    }

    public void print() {
        System.out.println(this.toString());
    }

}
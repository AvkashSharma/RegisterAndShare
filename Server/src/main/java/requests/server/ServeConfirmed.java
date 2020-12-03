package requests.server;

import java.io.Serializable;

public class ServeConfirmed implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
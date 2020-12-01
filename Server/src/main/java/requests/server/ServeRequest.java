package requests.server;

import java.io.Serializable;

public class ServeRequest implements Serializable {
    private Boolean serve = true;

    public ServeRequest(Boolean serve) {
        this.serve = serve;
    }

    public Boolean getServe() {
        return serve;
    }

    public void setServe(Boolean serve) {
        this.serve = serve;
    }

    @Override
    public String toString() {
        return "SERVE REQUEST ";
    }

    public void print() {
        System.out.println(this.toString());
    }
}
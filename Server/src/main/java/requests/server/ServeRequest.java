package requests.server;

public class ServeRequest {
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
}

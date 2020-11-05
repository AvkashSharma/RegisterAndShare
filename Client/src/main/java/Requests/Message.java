package Requests;

public class Message {

    protected RequestType requestType;

    public Message(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }


}

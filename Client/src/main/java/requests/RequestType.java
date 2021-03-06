package requests;

/* This enum contains all the message request types*/

public enum RequestType {
    REGISTER("REGISTER"),
    CLIENT_REGISTER_CONFIRMED("REGISTERED"),
    CLIENT_REGISTER_DENIED("REGISTER-DENIED"),
    DE_REGISTER("DE-REGISTER"),
    DE_REGISTER_CONFIRMED("DE-REGISTER_CONFIRMED"),
    DISCONNECTON_CONFIRMED("DISCONNECTION-CONFIRMED"),
    DISCONNECT("DISCONNECT"),
    UPDATE("UPDATE"),
    UPDATE_CONFIRMED("UPDATE-CONFIRMED"),
    UPDATE_DENIED("UPDATE-DENIED"),
    SUBJECTS("SUBJECTS"),
    AVAILABLE_LIST_OF_SUBJECTS("AVAILABLE-LIST-OF-SUBJECTS"),
    SUBJECTS_UPDATED("SUBJECTS-UPDATED"),
    SUBJECTS_REJECTED("SUBJECTS-REJECTED"),
    PUBLISH("PUBLISH"),
    MESSAGE("MESSAGE"),
    PUBLISH_DENIED("PUBLISH-DENIED"),
    CHANGE_SERVER("CHANGE-SERVER"),
    UPDATE_SERVER("UPDATE-SERVER");

    private final String requestType;

    private RequestType(String requestType){
        this.requestType = requestType;
    }

    public boolean equalsName(String otherRequestType){
        return requestType.equals(otherRequestType);
    }

    public String toString(){
        return this.requestType;
    } 
    
}

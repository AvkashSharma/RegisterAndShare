package Requests;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Request implements Serializable {

    protected RequestType requestType;
    protected int rid;

    public Request(RequestType requestType) {
        this.requestType = requestType;
    }
    
    public Request(RequestType requestType, int reqNumber) {
        this.requestType = requestType;
        this.rid = reqNumber;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRid(int rid){
        this.rid = rid;
    }
    
    public int getRid(){
        return rid;
    }
    public void writeObject(ObjectOutputStream out) throws IOException  { 
        out.defaultWriteObject();  
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException  {    
        in.defaultReadObject();  }
}

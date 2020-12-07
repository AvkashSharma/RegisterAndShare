package requests;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;


public class Request implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
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

    public void startTimer(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            int tp = 10000;
            
            @Override
            public void run() {
                System.out.println("Timer Started: " + tp);

                if(tp <= 0){
                    System.out.println(tp);
                    timer.cancel();
                    done();
                }
                tp = tp - 1000;
                
            }
            
        }, 1000,1000);
    }

    public void done(){
        System.out.println("Done");
    }


    // public static void main(String[] args) {
    //     Request req = new Request(RequestType.REGISTER);

    //     req.startTimer();
    // }
    
}


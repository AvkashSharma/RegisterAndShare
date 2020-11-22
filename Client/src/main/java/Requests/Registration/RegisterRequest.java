package requests.Registration;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import javax.sound.sampled.SourceDataLine;

import requests.Request;
import requests.RequestType;


/*
Register Request
- Request #
- Unique name
- IP Address 
- Socket #
*/

public class RegisterRequest extends Request implements Serializable{

    InetSocketAddress clientSocketAddress;
    String clientName;

    public RegisterRequest(int rqNumber, String clientName, InetSocketAddress clientSocketAddress) {
        super(RequestType.REGISTER,rqNumber);
        this.clientName = clientName;
        this.clientSocketAddress = clientSocketAddress;
    }

    public String getClientName() {
        return clientName;
    }
    
    public InetSocketAddress getClientSocketAddress(){
        return clientSocketAddress;
    }

    @Override
    public String toString() {
        return RequestType.REGISTER + " " + this.getRid()+" "+ getClientName() + " " + getClientSocketAddress();
    }

    public void print(){
        System.out.println(this.toString());
    }


    // TEST CODE
    // public static void main(String[] args) throws UnknownHostException {
    //     RegisterRequest rm = new RegisterRequest("rrr", new InetSocketAddress(InetAddress.getLocalHost(), 6000));

    //     System.out.println(rm.clientSocketAddress.getPort());
    //     System.out.println("request ID " + rm.getRid());

    //     rm.setRid(1);
    //     System.out.println("request ID " + rm.getRid());
    //     System.out.println(rm.rid);

    // }

}

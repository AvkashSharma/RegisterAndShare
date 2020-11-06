package Requests;

import java.net.InetSocketAddress;

/*
Register Request
- Request #
- Unique name
- IP Address 
- Socket #
*/

public class RegisterMessage extends Message {

    InetSocketAddress clientSocketAddress;
    String clientName;

    public RegisterMessage(String clientName, InetSocketAddress clientSocketAddress) {
        super(RequestType.REGISTER);
        this.clientName = clientName;
        this.clientSocketAddress = clientSocketAddress;
    }

    public String getClientName() {
        return clientName;
    }

    public String toString() {
        return RequestType.REGISTER + " " + clientName + " " + clientSocketAddress;
    }

    // TEST CODE
    // public static void main(String[] args) throws UnknownHostException {
    //     RegisterMessage rm = new RegisterMessage("rrr", new InetSocketAddress(InetAddress.getLocalHost(), 6000));

    //     System.out.println(rm.clientSocketAddress.getPort());

    // }



}

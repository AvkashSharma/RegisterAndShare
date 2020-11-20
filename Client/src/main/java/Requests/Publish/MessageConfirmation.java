package Requests.Publish;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;
public class MessageConfirmation extends Request implements Serializable {
    
    String clientName;
    String Subject;
    String Text;
    public MessageConfirmation(String clientName,String Subject,String Text){

        super(RequestType.MESSAGE);
        this.clientName=clientName;
        this.Subject=Subject;
        this.Text=Text;

    }
    public String getClientName(){
        return clientName;
    }
     public String getSubject(){
        return Subject;
    }
     public String getText(){
        return Text;
    }
@Override
    public String toString() {
        return RequestType.MESSAGE + " "+ clientName + " " + Subject+ " "+ Text;
    }

    public void print(){
        System.out.println(this.toString());
    }
}

package requests.Publish;
import requests.Request;
import requests.RequestType;

/*
  - Request # 
  - Unique name
  -  Subject
  - Text
  */

public class MessageConfirmation extends Request  {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
        return RequestType.MESSAGE + " "+ getClientName() + " " + getSubject()+ " "+ getText();
    }

    public void print(){
        System.out.println(this.toString());
    }
}

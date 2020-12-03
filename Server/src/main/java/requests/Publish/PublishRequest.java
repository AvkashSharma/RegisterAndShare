package requests.Publish;
import requests.Request;
import requests.RequestType;

/*
  - Request # 
  - Unique name
  -  Subject
  - Text
  */

public class PublishRequest extends Request {

    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String clientName;
    String  Subject;
    String Text;

    public PublishRequest(int reqNumber,String clientName, String Subject, String Text) {
        super(RequestType.PUBLISH,reqNumber);
        this.clientName = clientName;
        this.Subject=Subject;  
        this.Text= Text;
    }

    public String getClientName() {
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
        return RequestType.PUBLISH + " " + this.getRid()+" "+ getClientName() + " " +getSubject()+" " + getText() ;
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}

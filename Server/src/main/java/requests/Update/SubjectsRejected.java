package requests.Update;
import requests.Request;
import requests.RequestType;

/*
  - Request # 
  - Unique name
 -  List of subjects
  */


public class SubjectsRejected extends Request {
     
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String clientName;
    String [] listOfSubjects;
    String  list;

    public SubjectsRejected(int rid, String clientName, String listOfSubjects){
        super(RequestType.SUBJECTS_REJECTED, rid);
        this.clientName = clientName;
        this.list =listOfSubjects;
      }

    public SubjectsRejected(String clientName, String[] listOfSubjects){
      super(RequestType.SUBJECTS_REJECTED);
      this.clientName = clientName;
      this.listOfSubjects=listOfSubjects;
    }
    public String getClientName() {
        return clientName;
    }
    
    public String[] getListOfSubjects(){
        return listOfSubjects;
    }
    public String getList(){
        return list;
    }

    @Override
    public String toString(){
        return RequestType.SUBJECTS_REJECTED+ " " + this.getRid()+" "+ getClientName()+" "+getList() ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}

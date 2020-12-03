package requests.Update;
import requests.Request;
import requests.RequestType;

/*
  - Request # 
  - Unique name
  -  List of subjects
  */



public class SubjectsUpdated extends Request {
     
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String clientName;
     String [] listOfSubjects;

    public SubjectsUpdated(String clientName, String[] listOfSubjects){
      super(RequestType.SUBJECTS_UPDATED);
      this.clientName = clientName;
      this.listOfSubjects=listOfSubjects;
    }
    public String getClientName() {
        return clientName;
    }
    
    public String[] getListOfSubjects(){
        return listOfSubjects;
    }
    @Override
    public String toString(){
        return RequestType.SUBJECTS_UPDATED+ " " + this.getRid()+" "+ getClientName()+" "+getListOfSubjects() ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}

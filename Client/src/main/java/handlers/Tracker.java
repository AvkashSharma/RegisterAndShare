package handlers;


import client.ClientData;
import requests.Request;
import requests.Publish.PublishDenied;
import requests.Publish.PublishRequest;
import requests.Registration.ClientRegisterConfirmed;
import requests.Registration.ClientRegisterDenied;
import requests.Registration.DeRegisterRequest;
import requests.Registration.RegisterRequest;
import requests.Update.SubjectsRejected;
import requests.Update.SubjectsRequest;
import requests.Update.SubjectsUpdated;
import requests.Update.UpdateConfirmed;
import requests.Update.UpdateDenied;
import requests.Update.UpdateRequest;

public class Tracker {


  /**
   * This method adds request sent by the client 
   * to the HashMap and start the timer
   * @param requestd
   */
  public static void handleSentRequest(Object request){

    // Handle Register Request
    if(request instanceof RegisterRequest){
      RegisterRequest req = (RegisterRequest)request;

      int Rid = req.getRid();
      System.out.println("REGISTER REQUEST RID:                          " + Rid);
      req.startTimer();
      ClientData.requestMap.put(Rid, request);
      System.out.println("SIZE OF HASH MAP:                              " + ClientData.requestMap.size());
      
    }

    // Handle De-Register Request
    else if(request instanceof DeRegisterRequest){
      // DeRegisterRequest req = (DeRegisterRequest)request;
      // int Rid = req.getRid();
      // System.out.println("DE-REGISTER REQUEST RID:                      " + Rid);
      // ClientData.requestMap.put(Rid, request);
      // System.out.println("SIZE OF HASH MAP: " + ClientData.requestMap.size());
      
    }

    // Handle User Update Request
    else if(request instanceof UpdateRequest){
      UpdateRequest req = (UpdateRequest)request;
      int Rid = req.getRid();
      System.out.println("UPDATE REQUEST RID:                      " + Rid);
      req.startTimer();
      ClientData.requestMap.put(Rid, request);
      System.out.println("SIZE OF HASH MAP: " + ClientData.requestMap.size());
    }

    // Handle Subject Update Request
    else if(request instanceof SubjectsRequest){
      SubjectsRequest req = (SubjectsRequest) request;
      int Rid = req.getRid();
      System.out.println("SUBJECT REQUEST RID:                      " + Rid);
      req.startTimer();
      ClientData.requestMap.put(Rid, request);
      System.out.println("SIZE OF HASH MAP: " + ClientData.requestMap.size());
    }

    // Handle Publish Request
    else if(request instanceof PublishRequest){
      PublishRequest req = (PublishRequest) request;
      int Rid = req.getRid();
      System.out.println("PUBLISH REQUEST RID:                      " + Rid);
      req.startTimer();
      ClientData.requestMap.put(Rid, request);
      System.out.println("SIZE OF HASH MAP: " + ClientData.requestMap.size());
    }
  }

  /**
   * This method keeps track of response received by the client. Stops the timer and
   * removes the Object from the HashMap 
   * @param response
   */

  public static void handleReceivedResponse(Object response){

    System.out.print("RECEIVED RESPONSE:                        ");
    System.out.println(response.toString());

    // Handle Register confirmation Received
    if(response instanceof ClientRegisterConfirmed){
      ClientRegisterConfirmed res = (ClientRegisterConfirmed) response;
      
      // Get the Rid
      int Rid = res.getRid();
      System.out.println("REGISTER CONFIRMATION RID:                          " + Rid);
      // if the Rid exists in the map remove it 
      if(ClientData.requestMap.containsKey(Rid)){
        System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));

        // Need to get the same object from the Map to stop the timer
        Request reqToStop = (Request)ClientData.requestMap.get(Rid);
        reqToStop.stopTimer();

        // print the size of the map
        System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        ClientData.requestMap.remove(Rid);
        
        // print the size of the map
        System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      }
    }

    // Handle Client registration Denied 
    else if(response instanceof ClientRegisterDenied){
      ClientRegisterDenied res = (ClientRegisterDenied) response;
      int Rid = res.getRid();
      System.out.println("REGISTER DENIED RID:                          " + Rid);

      if(ClientData.requestMap.containsKey(Rid)){
        System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));

        // Need to get the same object from the Map to stop the timer
        Request reqToStop = (Request)ClientData.requestMap.get(Rid);
        reqToStop.stopTimer();

        // print the size of the map
        System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        ClientData.requestMap.remove(Rid);
         // print the size of the map
        System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      } 
    }

    // Handle User Update Confirmed
    else if(response instanceof UpdateConfirmed){
      UpdateConfirmed res = (UpdateConfirmed) response;
      int Rid = res.getRid();
      System.out.println("UPDATE CONFIRMED RID:                          " + Rid);

      // if the Rid exists in the map remove it 
      if(ClientData.requestMap.containsKey(Rid)){
        System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));

        // Need to get the same object from the Map to stop the timer
        Request reqToStop = (Request)ClientData.requestMap.get(Rid);
        reqToStop.stopTimer();

        // print the size of the map
        System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        ClientData.requestMap.remove(Rid);
         // print the size of the map
        System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      }
    }

    // Handle User Update Denied
    else if(response instanceof UpdateDenied){
      UpdateDenied res = (UpdateDenied) response;
      int Rid = res.getRid();
      System.out.println("UPDATE DENIED RID:                          " + Rid);

      // if Rid exists in the map remove it
      if(ClientData.requestMap.containsKey(Rid)){
        System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));

        // Need to get the same object from the Map to stop the timer
        Request reqToStop = (Request)ClientData.requestMap.get(Rid);
        reqToStop.stopTimer();

        // print the size of the map
        System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        ClientData.requestMap.remove(Rid);
         // print the size of the map
        System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      }
    }

    // Handle Subjects Updated
    else if(response instanceof SubjectsUpdated){
      SubjectsUpdated res = (SubjectsUpdated) response;
      int Rid = res.getRid();
      System.out.println("SUBJECTS UPDATED RID:                          " + Rid);

      // if Rid exists in the map remove it
      if(ClientData.requestMap.containsKey(Rid)){
        System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));


        // Need to get the same object from the Map to stop the timer
        Request reqToStop = (Request)ClientData.requestMap.get(Rid);
        reqToStop.stopTimer();
        
        // print the size of the map
        System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        ClientData.requestMap.remove(Rid);
         // print the size of the map
        System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      }
    }

    // Handle Subjects Rejected
    else if(response instanceof SubjectsRejected){
      SubjectsRejected res = (SubjectsRejected) response;
      int Rid = res.getRid();
      System.out.println("SUBJECTS UPDATED RID:                          " + Rid);

      // if Rid exists in the map remove it
      if(ClientData.requestMap.containsKey(Rid)){
        // System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));
        // print the size of the map
        // System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        ClientData.requestMap.remove(Rid);
         // print the size of the map
        // System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      }
    }


    // Handle Publish Denied
    else if(response instanceof PublishDenied){
      PublishDenied res = (PublishDenied) response;
      int Rid = res.getRid();
      System.out.println("SUBJECTS UPDATED RID:                          " + Rid);

      // if Rid exists in the map remove it
      if(ClientData.requestMap.containsKey(Rid)){
        // System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));
        // print the size of the map
        // System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        // Need to get the same object from the Map to stop the timer
        Request reqToStop = (Request)ClientData.requestMap.get(Rid);
        reqToStop.stopTimer();
        ClientData.requestMap.remove(Rid);
         // print the size of the map
        // System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      }
    }

    else {
      // System.out.println("THIS RESPONSE IS NOT HANDLED BY REQUEST TRACKER");
    }

  }
}

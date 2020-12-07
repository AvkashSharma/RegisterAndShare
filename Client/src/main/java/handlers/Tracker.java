package handlers;

import client.ClientData;
import requests.Registration.ClientRegisterConfirmed;
import requests.Registration.ClientRegisterDenied;
import requests.Registration.DeRegisterConfirmed;
import requests.Registration.DeRegisterRequest;
import requests.Registration.RegisterRequest;
import requests.Update.UpdateConfirmed;

public class Tracker {

  public static void handleSentRequest(Object request){
    
    // Register Request
    if(request instanceof RegisterRequest){
      RegisterRequest req = (RegisterRequest)request;

      int Rid = req.getRid();
      System.out.println("REGISTER REQUEST RID:                          " + Rid);
      ClientData.requestMap.put(Rid, request);
      System.out.println("SIZE OF HASH MAP:                              " + ClientData.requestMap.size());
      //req.startTimer();
    }

    // DeRegister Request
    if(request instanceof DeRegisterRequest){
      DeRegisterRequest req = (DeRegisterRequest)request;

      int Rid = req.getRid();
      System.out.println("DE-REGISTER REQUEST RID:                      " + Rid);
      ClientData.requestMap.put(Rid, request);
      System.out.println("SIZE OF HASH MAP: " + ClientData.requestMap.size());
      //req.startTimer();
    }
  }


  public static void handleReceivedResponse(Object response){

    System.out.print("RECEIVED RESPONSE:                        ");
    System.out.println(response.toString());


    // Handle Register confirmation Received
    if(response instanceof ClientRegisterConfirmed){
      ClientRegisterConfirmed res = (ClientRegisterConfirmed) response;
      // Get the Rid
      int Rid = res.getRid();
      System.out.println("CONFIRMATION RID:                          " + Rid);
      // if the Rid exists in the map remove it 
      if(ClientData.requestMap.containsKey(Rid)){
        System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));
        // print the size of the map
        System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        ClientData.requestMap.remove(Rid);
         // print the size of the map
        System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      }
    }

    // Handle Client registration Denied 
    if(response instanceof ClientRegisterDenied){
      ClientRegisterDenied res = (ClientRegisterDenied)response;
      int Rid = res.getRid();
      System.out.println("REGISTER DENIED RID:                          " + Rid);

      // if the Rid exists in the map remove it 
      if(ClientData.requestMap.containsKey(Rid)){
        System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));
        // print the size of the map
        System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        ClientData.requestMap.remove(Rid);
         // print the size of the map
        System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      }
    }


    // Handle Client Update Confirmed
    if(response instanceof UpdateConfirmed){
      UpdateConfirmed res = (UpdateConfirmed) response;
      int Rid = res.getRid();
      System.out.println("UPDATE CONFIRMED RID:                          " + Rid);

      // if the Rid exists in the map remove it 
      if(ClientData.requestMap.containsKey(Rid)){
        System.out.println("--------------------------FOUND A REQUEST ID GOING TO REMOVE FROM THE LIST");
        System.out.println(ClientData.requestMap.get(Rid));
        // print the size of the map
        System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
        ClientData.requestMap.remove(Rid);
         // print the size of the map
        System.out.println("SIZE OF HASH MAP AFTER: " + ClientData.requestMap.size());
      }
    }


  }

}

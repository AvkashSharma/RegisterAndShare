package handlers;

import java.net.DatagramPacket;
import java.net.UnknownHostException;

import requests.Request;
import server.ServerData;

public class Tracker {

    public static void receivedRequest(Object request, DatagramPacket packet) {
        if (request instanceof Request) {
            try {
                Request req = (Request) request;
                String ip = packet.getAddress().getLocalHost().getHostAddress().toString();
                int port = packet.getPort();
                int rid = req.getRid();
                String hashId =  rid+"-"+ip+":"+port;
                // System.out.println("REQUEST RID:                          " + hashId);
                req.startTimer();
                ServerData.requestMap.put(hashId, request);

                // System.out.println("SIZE OF HASH MAP:                              " + ServerData.requestMap.size());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stop(int rid, DatagramPacket packet) {
        try {
            String ip = packet.getAddress().getLocalHost().getHostAddress().toString();
            int port = packet.getPort();
            String hashId = rid+"-"+ip+":"+port;

            Request req = (Request) ServerData.requestMap.get(hashId);
            req.stopTimer();
            // System.out.println("SIZE OF HASH MAP BEFORE: " + ServerData.requestMap.size());
            ServerData.requestMap.remove(hashId);
            // System.out.println("SIZE OF HASH MAP AFTER: " + ServerData.requestMap.size());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
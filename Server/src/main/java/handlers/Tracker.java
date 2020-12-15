package handlers;

import java.net.DatagramPacket;
import java.net.UnknownHostException;

import requests.Request;
import server.ServerData;

/**
 * Tracker used to track incoming requests
 * <p>
 * Whenever there is a client request, it adds to the pending request list
 * <p>
 * Whenever it completes the request, it removes from pending request list
 */
public class Tracker {

    /**
     * Add Request to tracker list
     * 
     * @param request
     * @param packet
     */
    public static void receivedRequest(Object request, DatagramPacket packet) {
        if (request instanceof Request) {
            try {
                Request req = (Request) request;
                String ip = packet.getAddress().getLocalHost().getHostAddress().toString();
                int port = packet.getPort();
                int rid = req.getRid();
                String hashId = rid + "-" + ip + ":" + port;

                // start the request timer
                req.startTimer();
                // add request to pending request
                ServerData.requestMap.put(hashId, request);

                // System.out.println("SIZE OF HASH MAP: " + ServerData.requestMap.size());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Remove request from the Tracker list
     * 
     * @param rid
     * @param packet
     */
    public static void stop(int rid, DatagramPacket packet) {
        try {
            String ip = packet.getAddress().getLocalHost().getHostAddress().toString();
            int port = packet.getPort();
            String hashId = rid + "-" + ip + ":" + port;

            Request req = (Request) ServerData.requestMap.get(hashId);
            // stop the request timer
            req.stopTimer();
            // remove request from pending request
            ServerData.requestMap.remove(hashId);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
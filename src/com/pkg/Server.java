package com.pkg;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.SocketHandler;

public class Server {

    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;


    // constructor with port
    public Server(int port) throws UnirestException {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Over")) {
                try {
                    line = in.readUTF();
                    //System.out.println(line);

                    String sym = line;
                    String url = "https://investors-exchange-iex-trading.p.rapidapi.com/stock/" + sym + "/quote";
                    String host = "investors-exchange-iex-trading.p.rapidapi.com";
                    String key = "9839253669msh25359a5b42b8a1fp17319bjsneea1b13b4a62";

                    HttpResponse<JsonNode> response = Unirest.get(url)
                            .header("X-RapidAPI-Host", host)
                            .header("X-RapidAPI-Key", key)
                            .asJson();

                    if (response.getStatus() == 200) {
                        //ova ke bide output na server
                        System.out.println("Symbol: " + response.getBody().getObject().get("symbol"));
                        System.out.println("Company Name: " + response.getBody().getObject().get("companyName"));
                        System.out.println("Sector: " + response.getBody().getObject().get("sector"));
                        System.out.println("Price: " + response.getBody().getObject().get("delayedPrice"));

                        //This one returns NULL
                        //System.out.println("Real time price: " + response.getBody().getObject().get("iexRealtimePrice"));
                    } else {
                        System.out.println("STATUS OFFLINE");
                    }

                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) throws UnirestException, Exception {

        Server server = new Server(5000);
//        ServerSocket s = new ServerSocket(8080);
//        Socket x = s.accept();
//
//
//
//        BufferedReader bf = new BufferedReader(new InputStreamReader(x.getInputStream()));
//        //DataOutputStream to = new DataOutputStream(x.getOutputStream());
//        PrintWriter pr = new PrintWriter(x.getOutputStream());
//        pr.println("Client connected");
//        pr.flush();
//        pr.println("Input symbol of the wanted stock: " +'\n');
//        pr.flush();
//        String sym = bf.readLine();
//
//        String url = "https://investors-exchange-iex-trading.p.rapidapi.com/stock/" + sym + "/quote";
//        String host = "investors-exchange-iex-trading.p.rapidapi.com";
//        String key = "9839253669msh25359a5b42b8a1fp17319bjsneea1b13b4a62";
//
//        HttpResponse<JsonNode> response = Unirest.get(url)
//                .header("X-RapidAPI-Host", host)
//                .header("X-RapidAPI-Key", key)
//
//                .asJson();
//
//        if (response.getStatus() == 200) {
//            //System.out.println(response.getBody());
//
//            //ova ke bide output na client
//            pr.println(response.getBody().getObject().get("delayedPrice").toString());
//            pr.flush();
//        }else{
//            System.out.println("STATUS OFFLINE");
//        }
    }
}

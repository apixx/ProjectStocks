package com.pkg;

import java.io.*;
import java.net.*;

public class Client {

    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    public Client (String address, int port)
    {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            System.out.println("Input a symbol for the stock that you want: ");
            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.equals("Over"))
        {
            try
            {
                line = input.readLine();
                out.writeUTF(line);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])throws Exception{

        Client client = new Client("127.0.0.1", 5000);
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//
//        Socket x = new Socket("127.0.0.1", 8080);
//
//        //DataOutputStream to = new DataOutputStream(x.getOutputStream());
//
//        PrintWriter pr = new PrintWriter(x.getOutputStream());
//        BufferedReader bf = new BufferedReader(new InputStreamReader(x.getInputStream()));
//
//        String message, message2, sym;
//        //message = bf.readLine();
//        //System.out.println(message);
//
//
//        pr.println("->");
//        pr.flush();
//        //message2 = input.readLine();
//        //to.writeBytes(message2);
//        pr.println(message2);
//        sym = message2;
//        pr.flush();
//
//        message2 = bf.readLine();
//        System.out.println(message2);
//        for(int i=0; i<symbol; i++){
////            System.out.print("->");
////            to.writeBytes(input.readLine()+'\n');
////        }
////        System.out.println(from.readLine());
////        for(int i=0; i<symbol; i++){
////            //System.out.print
////            message = from.readLine();
////            System.out.print(message + " ");
////        }

//        x.close();

    }
}

package Java_File_Transfer_Application;

import java.io.*;
import java.net.*;

public class ServerSide{
    public static void main(String [] args) {
        try {
            ServerSocket ss = new ServerSocket(8080);
            boolean stop = false;
            while(!stop){

                System.out.println("Waiting for clients ... ");
                Socket socket = ss.accept(); 
                System.out.println("Client connected ... ");
                //pass socket instance to another thread
                ClientThread clientThread = new ClientThread(socket);
                clientThread.start();
            }
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }   
}
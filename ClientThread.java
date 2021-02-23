package Java_File_Transfer_Application;

import java.net.*;
import java.io.*;

public class ClientThread extends Thread{
    private Socket socket = null;
    private BufferedReader reader;
    private BufferedInputStream filereader;
    private BufferedOutputStream out;


    public ClientThread(Socket socket){
        this.socket = socket;
    }

    public void closeConnection(){
        try {
            if (out != null){
                out.close();
            }
            if (reader != null){
                reader.close();
            }
            if (filereader != null){
                filereader.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run(){
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedOutputStream(socket.getOutputStream());

            String FileName = reader.readLine();
            System.out.println(socket.getInetAddress().getHostAddress() + "Has requested file : " + FileName);
            String filePath = "Java_File_Transfer_Application/files_to_send/";
            File file = new File(filePath + FileName);
            
            //before proceeding, check if file exists
            if (!file.exists()){
                byte code = (byte)0;
                out.write(code);
                closeConnection();
            }else{
                //if file exists
                out.write((byte)1);
                //create a buffered input file stream
                filereader = new BufferedInputStream(new FileInputStream(file));
                //read the file in chunks of 1 kb
                byte [] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = filereader.read(buffer)) != -1){
                    out.write(buffer, 0 , bytesRead);
                    out.flush();
                }
                //close the connection
                closeConnection();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
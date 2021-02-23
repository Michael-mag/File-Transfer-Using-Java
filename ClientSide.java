package Java_File_Transfer_Application;
import java.io.*;
import java.net.*;

public class ClientSide {
    public static void main(String [] args)
    {
        try
        {
            InputStreamReader in = new InputStreamReader(System.in);     
            BufferedReader reader  = new BufferedReader(in);
            
            String ipAddress = "";
            String fileName = "";
            
            boolean isValid = false;
            
            
            while(!isValid)
            {
               System.out.print("Please enter a valid Server Ip address: ");
               //read the entered ip address
               ipAddress = reader.readLine();
               ValidateIP validator = new ValidateIP();
               isValid = validator.isValid(ipAddress);
            }
            
            System.out.print("Please enter a file name: ");
            fileName = reader.readLine();

            
            Socket socket = new Socket(ipAddress, 8080);
            InputStream inputByte = socket.getInputStream();
            BufferedInputStream input = new BufferedInputStream(inputByte);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            //send desired filename
            out.println(fileName);
            int code = input.read();
            if (code == 1) 
            {   
                BufferedOutputStream outputFile = new BufferedOutputStream(new FileOutputStream("/Users/michael/Desktop/" + fileName));                
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer)) != -1) {
                    System.out.print("."); //acts as a download indicator
                    outputFile.write(buffer, 0, bytesRead);
                    outputFile.flush();
                }
                System.out.println();
                System.out.println("File: " + fileName + " was successfully downloaded!");
                outputFile.close();
            }
            else
            {
                System.out.println("File is not present on the server!");
            }
            
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}

package Java_File_Transfer_Application;
import java.net.*;

public class ValidateIP{
    public boolean isValid(String ipAddress)
    {
        String[] numbers = ipAddress.split("\\.");
        if (numbers.length != 4)
        {
            return false;
        }
        
        for(String str: numbers)
        {
            int i = Integer.parseInt(str);
            if((i<0) || (i>255)) 
            {
                return false;
            }
        }
        return true;
    }
}
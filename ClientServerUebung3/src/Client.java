import java.io.IOException;
import java.util.Scanner;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client
{
	
	public static void main(String args[])
	{
		
		Client client = new Client();
		
	}
	
	public Client()
	{
		
		
		Socket socket;
	    try
	    {
	           socket = new Socket("127.0.0.1", 3141);
	           System.out.println("Auf Server verbunden");
	           communicate(socket);
	    }
	    catch (IOException e)
	    {
	        System.out.println(e);
	    }
	    
	    
	    
	    
	}
	
	public void communicate(Socket socket)
	{
		while(true)
		{
			OutputStream out = null;
			
			try
			{
				out = socket.getOutputStream();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			} 
			
	        System.out.println("Bitte geben Sie die URL ein");
	        
	        Scanner s = new Scanner(System.in);
	        String text = s.next();
	        
	        PrintStream ps = new PrintStream(out, true); 
	        
	        ps.println(text);
		}
	}

}
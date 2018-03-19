import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server implements Runnable
{
	
	public static void main(String args[])
	{
		
		Server server = new Server();
		server.start();
		
	}
	
	//------Ressources------
	//ServerSocket for accepting connections
	private ServerSocket serverSocket;
		
	//Socket Array for connection to devices
	private Socket server[] = new Socket[1];
	
	
	
	//------Constructor------
	public Server()
	{
		
	    try
	    {
	    	//Setting Port to ServerSocket
			serverSocket = new ServerSocket(3141);
	    }
	    catch (IOException e)
	    {
	        System.out.println(e);
	    }
	    
	   // listening();
	}
	
	
	//method to start the server
	public void start()
	{
		//starting thread to accept incoming connections
		accept();
		
		//starting thread to remove half open or lost connections
		//removeClient();
	}
	
	
	public void accept()
	{
		
		//creating Thread for accepting incoming connections
		new Thread()
	    {
	    	public void run()
	    	{
		        while(true)
		        {
		        	//Accepting communication on new Socket
		        	addClient();
		        }
	    	}
	    	
	    //starting new Thread
	    }.start();
	}
	
	
	
	//Method to add Client to a new Socket to server[]
		public void addClient()
		{
			for(int i = 0; i<server.length; i++)
			{
				if(server[i]==null)
				{
					try
					{
						//check variable for free space in server[]
						boolean check = false;
						
						//going through all the server[] sockets
						for(int j = 0;j<server.length;j++)
						{
							//if free space for a socket in server[] is found
							if(server[j] == null)
							{
								check = true;
								
								//accept the new connection
								server[j] = serverSocket.accept();
								communicate(server[j]);
							}
						}
						
						//if no free space for a socket in server[] is found
						if(check == false)
						{
							//add one free space to the server[] array
							arrayPlus();
							
							//add a socket and connection to the new free element in the server[] array
							server[server.length-1] = serverSocket.accept();
							communicate(server[server.length-1]);
						}
						
					}
					
					
					
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			
			
		}
	
	
		
		public void communicate(Socket cSocket) throws IOException
		{
			
		while(true)
			{
				System.out.println("Warten auf Text");
				BufferedReader in = null;
				String line = null;;
				//DataInputStream in = null;
				
				
				try
				{
					in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
					line = in.readLine();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
				
				System.out.println(calculating(line));
				
				
				DataOutputStream out = new DataOutputStream(cSocket.getOutputStream());
				
				
			}	
		}
		
		
		
		public int calculating(String s)
		{
			
			System.out.println(s);
			
			String[] parts = s.split("\\s+");
			
			System.out.println(parts[0]);
			
			
			int res = 0;
			
			int n1 = Integer.parseInt(parts[0]);
			int n2 = Integer.parseInt(parts[2]);
			
			
			switch(parts[1])
			{
			case "+":
				res = n1 + n2;
				break;
			case "-":
				res = n1 - n2;
				break;
			case "/":
				res = n1 / n2;
				break;
			case "*":
				res = n1 * n2;
				break;
			default:
				System.out.println("Fehler");
				break;
			}
			
			return res;
			
			
		}
		
		
		
	
	/*public void listening()
	{
		
		
		while (true)
		{
			
		    try
		    {
		       clientSocket = socket.accept();
		       System.out.println("Client connected");
		       
		       if(threads[threads.length-1]!=null)
			   {
					addOneToArray();
			   }
		       
		       
		       threads[threads.length-1] = new Thread(new Server());
		       threads[threads.length-1].start();
		       
		    }
		    
		    catch (IOException e)
		    {
		       System.out.println(e);
		    }
		    
		    
		}
	    
	    
	}*/
	
	
	/*
		
	public void run()
	{
		Socket cSocket = clientSocket;
		while(true)
		{
			System.out.println("Warten auf Text");
			BufferedReader rein = null;
			
			try
			{
				rein = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			} 
			
			System.out.println(rein);
		}
		
	}
*/
		
		//Method to remove Sockets with half open Connections 
		public void removeClient()
		{
			//creating Thread for checking connections
			new Thread()
		    {
		    	public void run()
		    	{
			        while(true)
			        {
			        	//checking sockets on half open connections or lost connections
			        	for(int i = 0; i<server.length;i++)
			        	{
			        		if(!(server[i].isConnected()))
			        		{
			        			try
			        			{
			        				//try to close connection
									server[i].close();
								}
			        			catch (IOException e)
			        			{
			        				//failed to close connection
									e.printStackTrace();
								}
			        			
			        			//setting server[i] to null to open server[i] for other connections
			        			server[i] = null;
			        		}
			        	}
			        	try
			        	{
			        		//try to sleep for milliseconds
							Thread.sleep(1000);
						}
			        	catch (InterruptedException e)
			        	{
			        		//failed to sleep for milliseconds
							e.printStackTrace();
						}
			        }
		    	}
		    	
		    //starting new Thread
		    }.start();
			
		}
		
	
	
	//method to resize dynamic SocketArray (+1)
		public void arrayPlus()
		{
			//making a array with length of server[] +1
			Socket tmp[] = new Socket[(server.length)+1];
			
			//TODO copying elements of server[] to tmp[]
			tmp = server.clone();
			
			//referring server[] to tmp[]
			server = tmp;
		}


		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	
	
	/*
	public void addOneToArray()
	{
		int length = threads.length;
		
		Thread tmp[] = new Thread[length+1];
		
		for(int i = 0; i < length; i++)
		{
			tmp[i] = threads[i];
		}
		
		threads = tmp;
	}

*/



}

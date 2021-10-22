

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class PrivateMessageThread extends Thread
{
	Socket socket;
	ChatServer server;
	public PrivateMessageThread(Socket socket, ChatServer server) 
    {
        this.socket = socket;
        this.server = server;
    }
	public void run()
	{
		try
		{
			InputStream input = socket.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(input));
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
			
		}
		catch(Exception e)
		{
			System.out.println("Unknown error occurred: " + e.getMessage());
            e.printStackTrace();
		}
	}
	
}

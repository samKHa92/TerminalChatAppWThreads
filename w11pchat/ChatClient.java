

import java.net.*;
import java.time.LocalTime;
import java.util.Scanner;
import java.io.*;

public class ChatClient 
{
	private String hostname;
	private int port;
	private String userName;
	private String[] Interests;
	private LocalTime jointime;
	private LocalTime time = LocalTime.now();
	
	public static LocalTime time1 = LocalTime.now();
	
	public ChatClient(String hostname, int port)
	{
		this.hostname = hostname;
		this.port = port;
	}
	
	public static void main(String[] args) 
	{
		if (args.length < 1)
		{
			ChatClient client = new ChatClient("localhost", 3000);
			client.execute();
		}
		else
		{
			String hostname;
			int port;
			while (true)
			{
				String input = args[0];
				try 
				{
					hostname = input.split(":")[0];
					port = Integer.parseInt(input.split(":")[1]);
					break;
				}
				catch (Exception e) 
				{
					System.out.println("Please insert data in a correct format.");
					System.out.println("\nPlease write <hostname>:<portnumber> to join a server.\n");
				}
			}
			ChatClient client = new ChatClient(hostname, port);
			client.execute();
		}
	}

	public void execute()
	{
		try 
		{
			Socket socket = new Socket(hostname, port);
			System.out.println("Connected to the chat server");
			new InputThreads(socket, this).start();
			new OutputThreads(socket, this).start();

		}

		catch (Exception e) 
		{
			System.out.println("Unknown error occurred: " + e.getMessage());
		}


	}
	
	

	public LocalTime getJointime() 
	{
		return jointime;
	}

	public void setJointime(LocalTime jointime)
	{
		this.jointime = jointime;
	}

	public String[] getInterests()
	{
		return Interests;
	}

	public void setInterests(String[] interests) 
	{
		Interests = interests;
	}
	
	
	String getUserName()
	{
		return this.userName;
	}
	
	static String [] returnModifiedStringArray(String param)
	{
		return param.split(":");
	}
	
	static String concatStringArray(String [] param)
	{
		String ret = "";
		for(String element : param)
			ret+=element;
		return ret;
	}

	void setUserName(String userName) 
	{
		this.userName = userName;
	}

}

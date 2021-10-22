

import java.io.*;
import java.net.*;
import java.time.*;

public class OutputThreads extends Thread 
{

	private Socket socket;
	private ChatClient client;
	private LocalTime time = LocalTime.now();
	
	private PrintWriter pw;
	
	public OutputThreads(Socket socket, ChatClient client) 
	{
		this.socket = socket;
		this.client = client;

		try 
		{
			OutputStream output = socket.getOutputStream();
			pw = new PrintWriter(output, true);
		} 
		catch (Exception e) 
		{
			System.out.println("Unknown error occured: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void run() 
	{
		Console console = System.console();
		String userName = console.readLine("\nEnter your name: ");
		client.setUserName(userName);
		client.setJointime(LocalTime.now());
		pw.println(userName);
		pw.println(LocalTime.now());
		ThreadsForUsers.outputRules(userName);
		System.out.println("NOTE: please answer following questiong until we get you connected to the chat.");
//		String answerForSurvey;
//		answerForSurvey = console.readLine();
//		pw.println(answerForSurvey);
//		if(answerForSurvey.equals("yes") || answerForSurvey.equals("YES"))
//		{
			System.out.println("\nWhat's your favorite football team?\n");
			String ans1 = console.readLine();
			pw.println(ans1);
			
			System.out.println("\nWhat's your favorite car?\n");
			String ans2 = console.readLine();
			pw.println(ans2);
			
			System.out.println("\nWhat's your favorite book?\n");
			String ans3 = console.readLine();
			pw.println(ans3);
			
			String [] answers = new String[] {ans1, ans2, ans3};
			client.setInterests(answers);
		//}
		String received;
		while (true)
		{
			received = console.readLine();
			if (received.charAt(0) == '@')
        	{
        		String reciever = received.split("\\s+")[0].substring(1);
        		String author = client.getUserName();
        		String text = received.substring(reciever.length()+2);
        		String time = LocalTime.now().toString();
        		PrivateMessage prmessage = new PrivateMessage(author, reciever, text, time);
        		PrivateMessage.addprMessages(prmessage);
        		
        	}
			pw.println(received);
			if(received.equals("LOGOUT"))
			{
				break;
			}
		} 
		try
		{
			socket.close();
		} 
		catch (Exception ex) 
		{
			System.out.println("You logged out... See you next time.");
		}
	}
}

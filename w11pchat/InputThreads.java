

import java.io.*;
import java.net.*;
import java.time.*;

public class InputThreads extends Thread
{
	 private Socket socket;
	 private ChatClient client;
	 private LocalTime time = LocalTime.now();
	
	 private BufferedReader bf;
	 
	 public InputThreads(Socket socket, ChatClient client) 
	 {
		 this.socket = socket;
	     this.client = client;
	 
	     try 
	     {
	    	 InputStream input = socket.getInputStream();
	    	 bf = new BufferedReader(new InputStreamReader(input));
	     } 
	     catch (Exception e)
	     {	
	    	 System.out.println("Unknown error occurred: " + e.getMessage());
	         e.printStackTrace();
	     }
	 }
	 
	 public void run() 
	 {	
		 String ansToSurvey = null;
		 
//		 try 
//		 {
//			 ansToSurvey = bf.readLine();
//		 } 
//		 catch (IOException e) 
//		 {
//			 
//		 }
//		 
//		 if(ansToSurvey.equals("yes") || ansToSurvey.equals("YES"))
			 survey();
		 
		 System.out.println();
		 
		 while (true) 
		 {
			 try 
			 {
				 String response = bf.readLine();
	             System.out.println("\n"+ response);
	         } 
	         catch (Exception e)
	         {
	             System.out.println("Unknown error occured: " + e.getMessage());
	             e.printStackTrace();
	             break;
	         }
		 }
	 }
	 
	 public void survey()
	 {
		 String[] answers = new String[3];
		 try 
		 {
			String trash = bf.readLine();
		 } 
		 catch (IOException e) 
		 {
			e.printStackTrace();
		 }
		 
		 try 
		 {
			 answers[0] = bf.readLine();
		 } 
		 catch (IOException e) 
		 {
			 
		 }
		 
		 
		 try 
		 {
			 answers[1] = bf.readLine();
		 } 
		 catch (IOException e) 
		 {
			 
		 }
		 
		 
		 try 
		 {
			 answers[2] = bf.readLine();
		 } 
		 catch (IOException e) 
		 {
			 
		 }
		 
		 client.setInterests(answers);
		 
		 //ClientType newClientToBeAdded = new ClientType(client.getUserName(),answers);
		 
		 
	 }
}

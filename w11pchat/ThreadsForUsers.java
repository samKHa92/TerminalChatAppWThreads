

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ThreadsForUsers extends Thread
{
	private ChatServer server;
	private Socket socket;
	private LocalTime time = LocalTime.now();
	private static LocalTime time1 = LocalTime.now();
	
	private PrintWriter pw;
	
	public static ChatServer server1;
	public static Socket socket1;
    public ThreadsForUsers(Socket socket, ChatServer server) 
    {
        this.socket = socket;
        this.server = server;
        server1 = this.server;
        socket1=this.socket;
    }
    
    private static Set<PrivateMessage> privateMessages = new HashSet<>();
    
    public void run() 
    {
        try 
        {
            InputStream input = socket.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(input));
            OutputStream os = socket.getOutputStream();
            pw = new PrintWriter(os, true);
            String newUserName = bf.readLine();
            String newJoinTime = bf.readLine();
            String answers[] = new String[3];
            //String trash = bf.readLine();
            answers[0] = bf.readLine();
            answers[1] = bf.readLine();
            answers[2] = bf.readLine();
            outputActives();
            pw.println("\n"+newUserName+", now you are online and can text other group members.");
            server.addNewUser(newUserName,answers,newJoinTime);
            String messageToAll = "*** " + newUserName + " has joined the chat room. ***\n";
            server.display(messageToAll, this);
            String textMessage;
 
            while (true)
            {
            	textMessage = bf.readLine();
            	if (textMessage.charAt(0) == '@')
            	{
            		PrivateMessage toRemove = null;
            		boolean match = false;
            		for(PrivateMessage element : PrivateMessage.getPrivateMessages())
            			if(element.reciever.equals(newUserName))
            			{
            				pw.println(element.time+"\t["+element.author+"] "+element.text+"(Private message)");
            				toRemove = element;
            				match = true;
            				break;
            			}
            		if(match)
            		{
            			privateMessages.remove(toRemove);
            			PrivateMessage.setPrivateMessages(privateMessages);
            		}
            		else
            			pw.println("No such a participant.");
            	}
            	
            	else if(textMessage.equals("LOGOUT"))
            	{
            		server.removeUser(newUserName, this);
                    socket.close();
                    messageToAll = newUserName + " logged out.";
                    server.display(messageToAll, this);
            		break;
            	}
            	else if (textMessage.equals("WHOIS"))
            	{
            		pw.println("\n"+newUserName + ", you asked to display currently active users:");
            		outputActives();
            	}
            	
            	else if (textMessage.equals("RULES"))
            	{
            		pw.println("\n"+newUserName + ", you asked to display rules:");
            		nonStaticOutputRules(newUserName);
            	}
            	else if (textMessage.equals("SAMETASTE"))
            	{
            		pw.println("\n"+newUserName + ", you asked to display all members with the same taste as yours:");
            		outputMembersWithSameTaste(newUserName);
            	}
            	else if (textMessage.equals("PINGU"))
            	{
            		pw.println("\n"+newUserName + ", you asked to display a random pinguin fact:");
            		outputRandomPenguinFact();
            	}
            	else if (textMessage.equals("MYTASTE"))
            	{
            		pw.println("\n"+newUserName + ", you asked to display your own taste:");
            		outputOwnTaste(newUserName);
            	}
            	
            	else
            	{
            		messageToAll =this.time+"\t\t[" + newUserName + "]: " + textMessage;
            		server.display(messageToAll, this);
            	}
 
            } 
        } 
        
        catch (Exception e)
        {
            System.out.println("Unknown error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


	private void outputOwnTaste(String newUserName) 
	{
		for(ClientType element : this.server.getClients().stream().filter(p -> p.username.equals(newUserName)).collect(Collectors.toSet()))
		{
			for(String element1 : element.interests)
			{
				pw.println(element1);
			}
		}
	}


	private void outputRandomPenguinFact() 
    {
		String[]facts = new String[]
		{
				"Penguins are black and white",
				"The word 'penguin' is the most used word on Artemis platform",
				"Penguin is on a logo of Linux",
				"Penguins are lovely",
				"Penguins are not mammals, or maybe they are... idk",
				"80% of penguins are lonely and depressive"
		};
		Random rn = new Random();
		int i = rn.nextInt(6)+0;
		pw.println(facts[i]+"\n");
	}


	public void outputMembersWithSameTaste(String userName2)
    {
    	Set <ClientType> curClient = server.getClients().stream().filter(p -> p.username.equals(userName2)).collect(Collectors.toSet());
    	String [] currentUserInterests = curClient.iterator().next().interests;
    	for(ClientType element : server.getClients().stream().filter(p -> !p.username.equals(userName2)).collect(Collectors.toSet()))
    	{
    		String[] values = element.interests;
    		for(int i = 0; i < 3; i++)
    			if(values[i].equals(currentUserInterests[i]))
    			{
    				if(i == 0)
    					pw.println(element.username+" - Same favorite football team: " + values[i]+".");
    				if(i == 1)
    					pw.println(element.username+" - Same favorite car: " + values[i]+".");
    				if(i == 2)
    					pw.println(element.username+" - Same favorite book: " + values[i]+".");
    			}
    	}
    }
    
    public void Message(String message)
    {
        pw.println(message);
    }
    
    public static void outputRules(String userNameParameter)
    {
    	System.out.println("\nHello " + userNameParameter+"! It is pleasure to host you in our chat. Please be aware of our rules and secret commands:\n");
    	System.out.println("1) Please be kind to other members.\n");
    	System.out.println("2) Use 'WHOIS' command to see who is active at the concrete moment.\n");
    	System.out.println("3) Use 'LOGOUT' command to leave the chat.\n");
    	System.out.println("4) Use 'PINGU' command to see a interesting random fact about pinguins.\n");
    	System.out.println("5) Use 'SAMETASTE' command to see which members have the taste like you.\n");
    	System.out.println("6) Use 'MYTASTE' command to check your own taste.\n");
    	System.out.println("7) Use 'RULES' command to see rules once again.\n");
    	System.out.println("8) Use @<membername> <text> command to send private message to participant.\n");
    }
    
    public void nonStaticOutputRules(String userNameParameter)
    {
    	pw.println("\nHello " + userNameParameter+"! It is pleasure to host you in our chat. Please be aware of our rules and secret commands:\n");
    	pw.println("1) Please be kind to other members.");
    	pw.println("2) Use 'WHOIS' command to see who is active at the concrete moment.");
    	pw.println("3) Use 'LOGOUT' command to leave the chat.");
    	pw.println("4) Use 'PINGU' command to see a interesting random fact about pinguins");
    	pw.println("5) Use 'SAMETASTE' command to see which members have the taste like you.");
    	pw.println("6) Use 'MYTASTE' command to check your own taste.");
    	pw.println("7) Use 'RULES' command to see rules once again.");
    	pw.println("8) Use @<membername> <text> command to send private message to participant.");
    }

    public void outputActives() 
    {
        if (server.nonEmpty()) 
        {
            pw.println("\nActive users:\n");
            for(ClientType element : server.getClients())
            	pw.println(element.username +", active since - "+element.joinedTime+"\n");
        }
        else 
            pw.println("\nNo active users\n");
    }
    public static void addPrivateMessage(PrivateMessage prmessage)
    {
    	privateMessages.add(prmessage);
    	ChatServer.setPrivateMessages(privateMessages);
    }
    
}

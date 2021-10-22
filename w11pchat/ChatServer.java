

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.net.*;
import java.time.LocalTime;

public class ChatServer 
{
	private int port;
    private Set<ClientType> clients = new HashSet<>();
    private Set<ThreadsForUsers> threadsForUsers = new HashSet<>();
    private LocalTime time = LocalTime.now();
    
	private static LocalTime time1 = LocalTime.now();
	private static Set<PrivateMessage> privateMessages = new HashSet<>();
 
    public ChatServer(int port) 
    {
        this.port = port;
    }
    
    public static void main(String[] args) 
    {
    	int port = 0;
    	if (args.length < 1)
    		port = 3000;
    	else
    		port = Integer.parseInt(args[0]);
        ChatServer server = new ChatServer(port);
        server.execute();
    }
 
    public void execute() 
    {
        try (ServerSocket serverSocket = new ServerSocket(port)) 
        {
 
            System.out.println("\nServer is waiting on port " + port + ".");
 
            while (true) 
            {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");
 
                ThreadsForUsers newUser = new ThreadsForUsers(socket, this);
                threadsForUsers.add(newUser);
                newUser.start();
 
            }
 
        } 
        
        catch (Exception e) 
        {
            System.out.println("Unknown error occured: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    public  void display(String message, ThreadsForUsers UsertoShow) 
    {
    	for (ThreadsForUsers element : threadsForUsers) 
    		if (element != UsertoShow) 
    			element.Message(message);
    }
 

    public  void addNewUser(String userName, String[] ans, String time)
    {
    	ClientType temp = new ClientType(userName,ans,time);
    	clients.add(temp);
    }
    

    public  void removeUser(String userName, ThreadsForUsers threadUser) 
    {
    	Set<ClientType> tempClients = this.clients;
    	this.clients = tempClients.stream().filter(p -> !p.username.equals(userName)).collect(Collectors.toSet());
    	threadsForUsers.remove(threadUser);
    	System.out.println("\nThe user " + userName + " logged out");
    }

    boolean nonEmpty() 
    {
        return !this.clients.isEmpty();
    }
    
    public Set<ClientType> getClients()
    {
    	return this.clients;
    }
    
    public  Set<String> getUserNames() 
    {
    	Set<String> usernames = new HashSet<>();
    	for(ClientType element : this.clients)
    		usernames.add(element.username);
    	return usernames;
    }
    
    public  Set<String> getUserJoinTimes() 
    {
    	Set<String> usernames = new HashSet<>();
    	for(ClientType element : this.clients)
    		usernames.add(element.joinedTime);
    	return usernames;
    }
    
    public void addPrivateMessage(PrivateMessage prmessage)
    {
    	this.privateMessages.add(prmessage);
    }

	public static Set<PrivateMessage> getPrivateMessages() 
	{
		return privateMessages;
	}

	public static void setPrivateMessages(Set<PrivateMessage> privateMessages) 
	{
		privateMessages = privateMessages;
	}
 
    
    
	
}

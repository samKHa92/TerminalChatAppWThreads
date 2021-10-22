
import java.util.HashSet;
import java.util.Set;

public class PrivateMessage
{
	String author;
	String reciever;
	String text;
	String time;
	
	public PrivateMessage (String author, String reciever, String text, String time)
	{
		this.author = author;
		this.reciever = reciever;
		this.text = text;
		this.time = time;
	}
	
	public static Set<PrivateMessage> privateMessages1 = new HashSet<>();
	
	public static void addprMessages (PrivateMessage mes)
	{
		privateMessages1.add(mes);
	}

	public static Set<PrivateMessage> getPrivateMessages()
	{
		return privateMessages1;
	}

	public static void setPrivateMessages(Set<PrivateMessage> privateMessages1) 
	{
		PrivateMessage.privateMessages1 = privateMessages1;
	}
	
	
}

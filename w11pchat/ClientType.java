

import java.util.*;
import java.time.*;

public class ClientType
{
	public String username;
	public String joinedTime;
	public String[] interests;
	
	public ClientType(String username, String [] interests, String joinedTime)
	{
		this.username = username;
		this.interests = interests;
		this.joinedTime = joinedTime;
	}

}

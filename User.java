import java.util.ArrayList;

public class User {
	private int id;
	private String name;
	private String password;
	private ArrayList<Flight> userflight;
	
	public User(int id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.userflight = new ArrayList<Flight>();
	}
	
	public User() {
		this.id = -1;
		this.name = "name";
		this.password = "1234";
	}
	public boolean hasFlight(String flightNo)
	{
		for(Flight fl:userflight)
			if(fl.getFlightNo().equals(flightNo))
				return true;
		return false;
	}
	public boolean match(int mid,String mpassword)
	{
		return (id==mid) && (password.equals(mpassword));
	}
	public void addFlight(Flight fl)
	{
		userflight.add(fl);
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
		
}


public class Flight {
	
	private String flightNo;
	private String departure;
	private String arrival;
	private String date;
	private String time;
	
	private int capacity;
	private int seats[];
	
	public Flight(String flightNo, String departure, String arrival, String date, String time, int capacity) {

		this.flightNo = flightNo;
		this.departure = departure;
		this.arrival = arrival;
		this.date = date;
		this.time = time;
		this.capacity = capacity;
		seats=new int[capacity];
	}
	
	public Flight()
	{
		this.flightNo = "";
		this.departure = "";
		this.arrival = "";
		this.date = "";
		this.time = "";
		this.capacity = 0;
	}
	public boolean isCheckedIn(int userID)
	{
		for(int i=0;i<capacity;i++)
			if(seats[i]==userID)
				return true;
		return false;
	}
	public int whichCheckedIn(int userID)
	{
		for(int i=0;i<capacity;i++)
			if(seats[i]==userID)
				return i;
		return -1;
	}
	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int[] getSeats() {
		return seats;
	}

	public void setSeats(int[] seats) {
		this.seats = seats;
	}
	
	public void reservation(int seatNo, int userID)
	{
		seats[seatNo]=userID;
	}

	@Override
	public String toString() {
		return flightNo + "\t" + departure + "\t" + arrival + "\t" + date
				+ "\t" + time;
	}


}

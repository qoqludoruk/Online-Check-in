import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JFrame implements ActionListener{

	private JTextField txtUser,txtFlight;
	private JPasswordField txtPass;
	private JButton btnCreate;
	public static JLabel lblflightsInfo, lblUser;
	private JPanel pnlNorth,pnlSouth;

	public static ArrayList<Flight> flightList;
	public static ArrayList<User> userList;
	public static User foundUser;
	public static String foundFlightNo;
	
	public Main() {

		flightList=new ArrayList<Flight>();
		userList=new ArrayList<User>();
		
		setLayout(new BorderLayout());
		
		btnCreate=new JButton("Check in");
		btnCreate.addActionListener(this);

		lblUser=new JLabel("Enter user and flight info: ");
		txtUser=new JTextField("User ID");

		txtUser.setColumns(7);
		txtUser.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	txtUser.setText("");
            }
        });
		txtPass=new JPasswordField("Password");
		txtPass.setColumns(7);
		txtPass.setEchoChar((char)0);
		txtPass.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	txtPass.setText("");
            	txtPass.setEchoChar('*');
            }
        });
		txtFlight=new JTextField("Flight No");
		txtFlight.setColumns(7);
		txtFlight.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	txtFlight.setText("");
            }
        });
		
		
		pnlNorth=new JPanel();
		pnlNorth.setLayout(new FlowLayout());
		pnlNorth.add(lblUser);
		pnlNorth.add(txtUser);
		pnlNorth.add(txtPass);
		pnlNorth.add(txtFlight);
		
		pnlNorth.add(btnCreate);		
		add(pnlNorth,BorderLayout.NORTH);
		
		pnlSouth=new JPanel();
		pnlSouth.setLayout(new FlowLayout());
		lblflightsInfo=new JLabel();
		
		pnlSouth.add(lblflightsInfo);

		add(pnlSouth,BorderLayout.SOUTH);
	
		setSize(800,200);
		setVisible(true);
		setTitle("Flight Reservation");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	
	public static void main(String[] args) throws IOException {
		
		new Main();
		
		try
		{
			readFlights();
			readUsers();
			readUserFlights();
		}
		catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
		};
		

	}
	public static Flight findFlight(String flightNo)
	{
		for(Flight fl:flightList)
			if(flightNo.equals(fl.getFlightNo()))
				return fl;
		return null;
	}
	public static void readFlights() throws IOException
	{
		FileReader fr=new FileReader(new File("flight.txt"));
		BufferedReader br=new BufferedReader(fr); 
		String line;
		while((line=br.readLine())!=null)
		{
			
			String []fl=line.split(";");
			String flightNo=fl[0];
			String departure=fl[1];
			String arrival=fl[2];
			String date=fl[3];
			String time=fl[4];
			int capacity=Integer.parseInt(fl[5]);
			flightList.add(new Flight(flightNo,departure,arrival,date,time,capacity));
							
		}
		fr.close();
		//formatlı yazdır
		String flightsInfo="<html>Flight Info<br>";
		flightsInfo+="FlightNo\tDeparture\tArrival\tDate\tTime<br>";
		for(Flight fl: flightList)
			flightsInfo+=fl+"<br>";
		flightsInfo+="</html>";
		lblflightsInfo.setText(flightsInfo);
		
	}
	public static void readUsers() throws IOException
	{
		FileReader fr=new FileReader(new File("user.txt"));
		BufferedReader br=new BufferedReader(fr); 
		String line;
		while((line=br.readLine())!=null)
		{			
			String []fl=line.split(";");
			int id=Integer.parseInt(fl[0]);
			String name=fl[1];
			String password=fl[2];
			
			userList.add(new User(id,name,password));
							
		}
		fr.close();		
	}
	public static void readUserFlights() throws IOException
	{
		FileReader fr=new FileReader(new File("userflight.txt"));
		BufferedReader br=new BufferedReader(fr); 
		String line;
		while((line=br.readLine())!=null)
		{			
			String []fl=line.split(";");
			int id=Integer.parseInt(fl[0]);
			String flightNo=fl[1];
			User foundUser=null;
			Flight foundFlight=null;
			for(User us:userList)
				if(us.getId()==id)
					foundUser=us;

			for(Flight flight:flightList)
				if(flight.getFlightNo().equals(flightNo))
					foundFlight=flight;
			if(foundFlight!=null && foundUser!=null)
				foundUser.addFlight(foundFlight);
												
		}
		fr.close();		
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource().equals(btnCreate))
		{//Create button pressed
			boolean userFound=false;
			//User user=null;
			int id;
			try
			{
				id=Integer.parseInt(txtUser.getText());
			}
			catch(NumberFormatException exp)
			{
				id=-1;
			}
			String pass=txtPass.getText();
			foundFlightNo=txtFlight.getText();
			
			for(User us:userList)
			{
				if(us.match(id, pass))
				{
					userFound=true;
					foundUser=us;
					break;
				}
			}
			if(userFound)
			{
				if(foundUser.hasFlight(foundFlightNo))
				{
					Flight fl=findFlight(foundFlightNo);
					if(fl.isCheckedIn(id))
					{
						int selectedSeatNo=fl.whichCheckedIn(id);
						int row=selectedSeatNo/6+1;
						String []seatLetters= {"A","B","C","D","E","F"};
						String seatLet=seatLetters[selectedSeatNo%6]; 
						JOptionPane.showMessageDialog(null, "You already checked in to this flight! Seat: "+row+seatLet, "Warning", JOptionPane.WARNING_MESSAGE);
					}
					else
					{
						Reservation reserve=new Reservation(this);
						this.setVisible(false);
					}
					
				}
				else
					JOptionPane.showMessageDialog(null, "User does not have this flight", "Warning", JOptionPane.WARNING_MESSAGE);
				
			}
			else
			{
				JOptionPane.showMessageDialog(null, "User or password not found", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}

}

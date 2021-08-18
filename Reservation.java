import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Reservation extends JFrame implements ActionListener
{
	private Main mainWindow;
	private JPanel pnlNorth,pnlSouth;
	public static JLabel lblSeats[], userInfo, lblSeatLetters[];
	public static JButton checkin;
	public static int selectedSeatNo=-1;
	public static Flight foundFlight;
	public Reservation(Main mWindow) {
		mainWindow=mWindow;
		// TODO Auto-generated constructor stub
		
		setVisible(true);
		setTitle("Reservation");
		
		setLayout(new BorderLayout());
		pnlNorth=new JPanel();
		pnlNorth.setLayout(new GridLayout(2,1));
		userInfo=new JLabel("<html>Hello "+mainWindow.foundUser.getName()+"<br>Flight No: "+mainWindow.foundFlightNo+"</html>");
		pnlNorth.add(userInfo);
		checkin=new JButton("Check in");
		checkin.addActionListener(this);
		pnlNorth.add(checkin);
		
		add(pnlNorth,BorderLayout.NORTH);
		
		pnlSouth=new JPanel();
		
		int seatCapacity=0;
		for(Flight fl:mainWindow.flightList)
			if(fl.getFlightNo().equals(mainWindow.foundFlightNo))
				seatCapacity=fl.getCapacity();
		setSize(180,4*seatCapacity);//750
		lblSeats=new JLabel[seatCapacity];//Seat label array is created
		pnlSouth.setLayout(new GridLayout(1+seatCapacity/6, 7, 5,5) );
		
		String []seatLetters= {"  "," A"," B"," C"," D"," E"," F"};
		lblSeatLetters=new JLabel[seatLetters.length];
		for(int l=0;l<seatLetters.length;l++)
		{
			lblSeatLetters[l]=new JLabel(seatLetters[l]);
			pnlSouth.add(lblSeatLetters[l]);
		}
		foundFlight=mainWindow.findFlight(mainWindow.foundFlightNo);
		for(int i=0;i<lblSeats.length;i++)
		{//Seats labels created and added to the panel
			if(i%6==0)
			{
				pnlSouth.add(new JLabel(""+(i/6+1)));
			}
			lblSeats[i]=new JLabel();
			lblSeats[i].setOpaque(true);
			if(foundFlight.getSeats()[i]==0)
				lblSeats[i].setBackground(Color.WHITE);
			else
				lblSeats[i].setBackground(Color.GRAY);
			
			lblSeats[i].addMouseListener(new MyMouseListener(i));
			
			pnlSouth.add(lblSeats[i]);
		}
		add(pnlSouth,BorderLayout.CENTER);
		setResizable(false);
	    addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                mainWindow.setVisible(true);
            }
        });

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Check in"))
		{
			if(selectedSeatNo==-1)
				JOptionPane.showMessageDialog(null, "You didnt select a seat!", "Warning", JOptionPane.WARNING_MESSAGE);
			else
			{
				foundFlight.reservation(selectedSeatNo, mainWindow.foundUser.getId());
				//hangi koltuk seçildi 1B
				int row=selectedSeatNo/6+1;
				String []seatLetters= {"A","B","C","D","E","F"};
				String seatLet=seatLetters[selectedSeatNo%6];
				JOptionPane.showMessageDialog(null, "You succesfully checked in! Seat: "+row+seatLet, "Info", JOptionPane.INFORMATION_MESSAGE);
				selectedSeatNo=-1;
				mainWindow.setVisible(true);
				this.setVisible(false);
			}
		}
		
		//mainWindow.userList
	}
	
}
class MyMouseListener implements MouseListener {
    private int index;

    public MyMouseListener(int index) {
        this.index = index;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	
    	for(int i=0;i<Reservation.lblSeats.length;i++)
    	{
    		if(Reservation.foundFlight.getSeats()[i]!=0)
    			Reservation.lblSeats[i].setBackground(Color.GRAY);
    		    			
    	}
    	 if(Reservation.lblSeats[index].getBackground()==Color.WHITE)
    	 {
    		 if(Reservation.selectedSeatNo!=-1)
    			 Reservation.lblSeats[Reservation.selectedSeatNo].setBackground(Color.WHITE);
    		 Reservation.selectedSeatNo=index;
    		 Reservation.lblSeats[index].setBackground(Color.BLUE);
    	 }
    	 
    }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
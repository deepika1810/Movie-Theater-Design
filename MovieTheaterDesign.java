/* Name: Deepika Mulchandani
Contact details: deepikak@vt.edu/+1(540)449-7621 */
/*imports*/

import java.io.*;
import java.util.*;


public class MovieTheaterDesign 
{
	private static StringBuilder assignment = new StringBuilder();
    private static int available_seats = 200;
	private static HashMap<Character,SeatRow> seats = new HashMap<Character,SeatRow>(); 
	
	public static void main(String[] args) throws IOException 
	{
		/*Calling the createTheater method which creates a movie theater that has the seating arrangement of 10 rows x 20 seats.
		  Reading input from test file and calling the 'seatAssignment' method with the reservation identifier and the number of seats requested for each request in the input file.
		  Writing back the contents of the 'assignment' string to the output file after all the requests of the input file have been processed.
		*/
		createTheater();
		try 
		{
			Scanner in = new Scanner(new File(args[0]));	
			while(in.hasNextLine()) 
			{
				if(in.hasNext()==false) 
					break;
				String str = in.next();
				int i = in.nextInt();
				seatAssignment(str,i);
			}
			in.close();
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(args[1])));
			out.write(assignment.toString());
			out.close();
		}
		catch(Exception e) 
		{
	        System.out.println("Either File not found or invalid input format");
	    }
	}
	
	public static void createTheater()
	{
		/*Creating the theater. The theater stores the information in the form of a hashmap. The hashmap has the row identifiers(A, B, C, etc.) as the keys 
		 and objects of the class 'SeatRow' described above as the values. */
		for(int i=0;i<10;i++)
		{
			int val = 65+i;
			seats.put((char)val, new SeatRow());
		}
	}
	
	
	public static void seatAssignment(String request, int num_seats) 
	{
		/*This method gets the requisition ID and the number of seats. It then checks various conditions and does the seat assignment. It appends the requisition number 
		 and the seat numbers assigned for that request in the 'assignment' string. */
		assignment.append(request);
		
		/* If the request is for 0 seats, print out a request can't be processed message */
		if(num_seats == 0)
		{
			assignment.append(" Request should be for greater than 0 seats.\n");
		}
		/* If for any request the number of seats requested are greater than available seats in the theater, print out a request can't be processed message */
		else if(num_seats>available_seats)
		{
			assignment.append(" Request cannot be processed as there are not enough seats.\n");
		}
		/* Process the request by calculating the seats to be assigned. */
		else 
		{	
				available_seats = available_seats - num_seats;
				char row_key = 'J'; /*Start with the top row */
				
				/* Since the seats are assigned from the middle, there are two components(the left side of the row and the right side of the row) 
				 that need to be checked for seat availability to assign contiguous seats. If the seats remaining on the left side for the particular row 
				 can accommodate the request and the seats remaining on the left side is greater than those on the right side, then the request is fulfilled by 
				 the seats on the left. 
				 Otherwise, the right side of the row is checked, and seats are assigned from the right side of the row. 
				 If both these conditions fail, then the row is changed(decreased by 1) and the same conditions are checked.
				 Both the left and right side start with 10 seats each.
				 Special case is when the number of seats is between 11 and 20. In this case, we check if a row has not been used, then we assign the entire row
				 if the requests are for 20 seats or the middle seats if the request if for 11 seats and so on.
				 Another special case is when the number of seats is greater than 20. In this case, we try to assign all 20 seats of a row if it is empty and the 
				 remaining seats are assigned normally. 
				 If contiguous seats cannot be assigned, we call the 'breakAssignment' function which assigns the seats by breaking the number of seats into partitions.
	             */
				
				while(row_key != '@' && num_seats > 0) 
					if(seats.get(row_key).getSeatsRem_Left() >= num_seats && seats.get(row_key).getSeatsRem_Left() >= seats.get(row_key).getSeatsRem_Right())
					{
						leftSeatAssignment(row_key,num_seats);
						num_seats = 0;
			
					}
					else if(seats.get(row_key).getSeatsRem_Right() >= num_seats)
					{
						rightSeatAssignment(row_key,num_seats);
						num_seats = 0;			
						
					}
					else if(num_seats >= 11 && num_seats <= 20)
					{
						if(seats.get(row_key).getRowStatus() == false)
						{
							int partition1 = num_seats/2;
							int partition2 = num_seats - partition1; 
							leftSeatAssignment(row_key,partition1);
							assignment.append(",");
							rightSeatAssignment(row_key,partition2);
							num_seats = 0;
						}
						row_key -= 1;
						if(row_key == '@' && num_seats > 0)
						{ 
							breakSeatAssignment(num_seats);
							num_seats = 0;
						}
						
					}
					else if(num_seats > 20)
					{
						if(seats.get(row_key).getRowStatus() == false)
						{
							leftSeatAssignment(row_key,10);
							assignment.append(",");
							rightSeatAssignment(row_key,10);
							num_seats = num_seats - 20;
							if(num_seats > 0)
								assignment.append(",");
						}
						row_key -= 1;
						if(row_key == '@' && num_seats > 0)
							row_key = 'J';
					}
					else 
					{
						row_key -= 1;
						if(row_key == '@')
						{
							breakSeatAssignment(num_seats);
							num_seats = 0;
						}
						
					}
			assignment.append("\n");
		}		
	}
	
	/* This function assigns the contiguous seats on the left side of a row. This function is called by the 'seatAssignment' and 'breakAssignment'
	 function when a request can be processed by the left side of a row. */
	public static void leftSeatAssignment(char row_key,int num_seats)
	{
		seats.get(row_key).updateSeatsRem_Left(num_seats);
		while(num_seats > 0)	
		{
			assignment.append(" "+row_key+(seats.get(row_key).getSeatNum_Left()));
			if(num_seats != 1)
				assignment.append(",");
			num_seats--;
		}
	}
	
	/* This function assigns the contiguous seats on the right side of a row. This function is called by the 'seatAssignment' and 'breakAssignment'
	 function when a request can be processed by the right side of a row. */
	public static void rightSeatAssignment(char row_key,int num_seats)
	{
		while(num_seats > 0)
		{
			assignment.append(" "+row_key+seats.get(row_key).getSeatNum_Right());
			if(num_seats != 1)
				assignment.append(",");
			seats.get(row_key).updateSeatsRem_Right();
			num_seats--;
		}
	}	
	
	/* This function does the breaking in assignment of the request. It assigns the seats on the left side in different rows and if more seats have to be assigned 
	 then it assigns seats on the right side the same way.*/
	public static void breakSeatAssignment(int num_seats)
	{
		char row_key = 'J';
		int partition = 0;
		while(row_key != '@' && num_seats > 0)
		{
			partition = seats.get(row_key).getSeatsRem_Left();
			if(partition > num_seats)
				partition = num_seats;
			if(partition > 0)
			{
				leftSeatAssignment(row_key,partition);
				num_seats = num_seats - partition;
				if(num_seats > 0)
					assignment.append(",");
			}	
			row_key -= 1;
		}
		row_key = 'J';
		while(row_key != '@' && num_seats > 0)
		{
			partition = seats.get(row_key).getSeatsRem_Right();
			if(partition > num_seats)
				partition = num_seats;
			if(partition > 0)
			{
				rightSeatAssignment(row_key,partition); 
				num_seats = num_seats - partition;
				if(num_seats > 0)
					assignment.append(",");
			}
			row_key -= 1;
		}	
	}
	
}




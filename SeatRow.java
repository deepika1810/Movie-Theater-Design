/* Name: Deepika Mulchandani
Contact details: deepikak@vt.edu/+1(540)449-7621 */

/*This is the data structure specification for the seats in a theater row.*/
public class SeatRow
	{
		/*Variables for the data structure.
		Each row of the theater is an entry in the hashmap in java. 
		The row ID ,i.e, A,B,...,J are the keys for the hashmap. 
		The value for each key is a corresponding object of this class.
		The variables are described below.
		1. has_row_been_used: Is the row empty?
		2. seats_rem_left: The number of seats remaining in the left part 
		for that row, initialized to 10 for each row.
		3. seats_rem_right: The number of seats remaining in the right part 
		for that row, initialized to 10 for each row.
		4. seat_num_right: The offset to return the seat number to be assigned.
		5. seat_num_left: The offset to return the seat number to be assigned.
		 */
		public boolean has_row_been_used = false;
		public int seats_rem_left = 10;
		public int seats_rem_right = 10;
		public int seat_num_right = 1;
		public int seat_num_left = 0;
		
		/* Method to return the answer to the query: is this row empty? */
		public boolean getRowStatus()
		{
			return has_row_been_used;
		}
		/* Method to return the number of seats that can be assigned
		 in the left side of the particular row.  */
		public int getSeatsRem_Left() 
		{
			return seats_rem_left;
		}
		/* Method to return the number of seats that can be assigned
		 in the right side of the particular row.  */
		public int getSeatsRem_Right() 
		{
			return seats_rem_right;
		}
		/* Method to change the status if the row has been used,i.e, it is
		not completely empty.*/
		public void updateRowStatus() 
		{
			has_row_been_used = true;
		}
		/* Method to update the remaining seats for a row after an assignment
		in the left side of the row. */
		public void updateSeatsRem_Left(int num_seats)
		{
			if(seats_rem_left == 10)
				updateRowStatus();
			seat_num_left = 0;
			seats_rem_left -= num_seats; 
		}
		/* Method to update the remaining seats for a row after an assignment 
		in the right side of the row. */
		public void updateSeatsRem_Right()
		{
			seats_rem_right--;
			seat_num_right +=2;
		}
		/* Method to return the seat number to be assigned in the right side.*/
		public int getSeatNum_Right()
		{
			return seats_rem_right+seat_num_right;
		}
		/* Method to return the seat number to be assigned in the left side.*/
		public int getSeatNum_Left()
		{
			seat_num_left++;
			return seats_rem_left+seat_num_left;
		}
	}


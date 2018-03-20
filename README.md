# Movie-Theater-Design

——Movie Theater Seating Homework.——
Once in the github repository, click on 'Clone or Download' and download the zip. 

This submission contains .java and .class files for the homework along with 11 test files. It also contains the corresponding output files for the 11 test cases. The ‘SeatRow.java’ file has the data structure design used for the movie theater rows. The ‘MovieTheaterDesign.java’ file has the implementation for seat assignment. The instructions for executing the code via command line are given below.

In the terminal, change the present working directory to the downloaded folder from the submission.
Although the .class files are included, the compilation can be done using the steps described below:
> javac SeatRow.java
> javac MovieTheaterDesign.java

After compilation, the program can be run as follows:
> java MovieTheaterDesign Tests/Test1.txt Test_Outputs/Test1output.txt

Here, the ‘Tests/Test1.txt’ is the command line argument for the input file which is in the folder and the ‘Test_Outputs/Test1output.txt’ is the argument for the output file where the output for the test case has to be written. The ‘Test1output.txt’ file will be rewritten whenever the program is run. Also, if no output file exists then specifying the name in the command line argument will generate a new file with the name specified. The input test file has to be present in the folder. If any other test file needs to be run, then the input file has to be present in the folder and its name or path needs to be specified as the first argument.

——Implementation Details (Design specification and justification)——
1. Design: 
The design has to maximize both customer satisfaction and theater utilization. Customers usually want to have contiguous seats and seats in the middle of the row. Theaters on the other hand usually associate different costs with different rows, such that rows away from the screen have the highest cost. Hence, theaters usually try to fill the seats in the top rows. 
Keeping these factors in mind, the seat assignment here is done such that the seats are assigned starting from the middle of the row to the corner and starting with the row furthest from the screen. So, if the first request is for 2 seats, then starting from the furthest row i.e J, the middle seats get assigned, i.e., J9,J10. If the next request is for 4 seats, then J11,J12,J13,J14 are the seats assigned. 
Also, to maximize theater utilization, for each request the check starts from row J. Thus if the seat J1 is the only empty seat in row J, and a request comes in for a single seat then this seat gets assigned. This means that even if the seats assigned for the previous request were C9 and C10, even then the next request will assign J1 instead of C8. This will maximize theater utilization and also increase the profit.
The special case where the seats requested are between 11 to 20, the assignment is to give the entire unused row if the seats requested are 20 or to give the middle seats of the unused row if the request is for 11 or 15 etc. This happens provided there are unused rows, otherwise non-contiguous allocation is done.
If the seats requested are greater than 20, then the assignment is to give 20 seats(entire row) of the unused rows and then give contiguous seats in other rows if possible otherwise  do non-contiguous allocation. 
Non-contiguous seat allocation is when the seats are broken up and assigned starting from the topmost row, i.e, J. The break is such that the number of available seats in J is the first partition. All the seats are tried to be allocated in the left side of the theater. If they still can’t be allocated then the same process is repeated on the right side of the theater.This ensures maximum theater utilization.  
The message ‘Request cannot be processed since there are not enough available seats’ is given for a request(e.g only 2 seats are remaining in the theater but request is for 3). The code however ensures that a later request with value 1 or 2 will be processed.
The message ‘Request should be for seats greater than 0’ is given if 0 seats are requested.

2. Data Structure used: 
 The data structure used is a java hashmap. The key for the hashmap is the row of the theater ,i.e., A,B,C,…,J in our case. The corresponding value is the object of class ’SeatRow’. For each row which is a key in the hashmap, an object of this class is created. The class has variables and getter and setter methods for the variables. The variables keep track of whether a row is empty or has some seats assigned, the number of seats remaining in the left side of the row, the number of seats remaining in the right side of the row. It has methods which check the number of remaining seats, update the number of seats after an assignment is made for a requisition, and gets the appropriate seat numbers for a requisition.

—-Test File Details:—-
1. Test1- General test to check the code.
2. Test2- Test to check requests with seats greater than 20. This will test the special case.
3. Test3- Test with 200 requests each for 1 seat, to check the assignment happens in the correct order. 
4. Test4- Test to check that messages are displayed for the cases where request cannot be processed if enough seats are not available or if request is for 0 seats. 
5. Test5-  Test to check the ‘breakAssignment’ method ,i.e, non-contiguous seat assignment.
6. Test6- Test to check that seat in the topmost available row is assigned for a later request. 
7. Test7- Test to check assignment of contiguous seats for the seats remaining for cases with >20 seats requests.
8. Test8- Test to check that if the rows get filled by many >20 seats requests, then the order of filling the other requests still starts at the top. 
9. Test9- 400 requests with 0 and 1 seat requests to ensure that large number requests are also handled.
10. Test10-  400 requests to ensure the requests are handled correctly.
11. Test 11- Test to check the assignment of seats between 11 and 20. 


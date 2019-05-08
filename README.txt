Readme.txt
Github Link: https://github.com/mohammedsapin/RUStaying
Login information for Firebase: 
email: rusoftwaregroup11@gmail.com
password: software11


1. Open the file android studio, and using an emulator, run the application. 
2. An emulator should appear on your screen after the gradle is finished building (you can see in “sync” console near the bottom to track the progress), and after several seconds, our main page should pop up.
3. GUEST SIDE USEAGE:
4. From here, click on register to create your account, and then back at the main page, log in with the information you just created to access your account. 
   1. To login with an already created user information:
      1. Username: final@mail.com
      2. Password: 123456
1. For users who haven't created an account, they can still view our room prices for potential bookings or view general information about the hotel, but can’t access the main features (explained below) without checking in first after creating an account.
2. After logging in and checking in, you can access all features of our application! 
   1. Book a room:
      1. Allows guests to book a room
   1. Check in:
      1. Allows guests to check in, unlocking other features such as feedback form, inbox, and service requests.
   1. Inbox:
      1. Alerts user with a message when the admin marks their request as “in progress” or “completed”
   1. Key card:
      1. After a user checks in the have the ability to go to the Key Card page where they can hit a button labeled generate. After they hit this button, a key code is randomly generated that can then be used by the guest to enter their room. There will be a keypad on the door to their room for them to enter the code. After the guest checks out. The keyCode will be wiped.  
   1. Feedback:
      1. Form where guests can input feedback from their stay, rating our services and providing additional comments.
   1. Logout:
      1. Log out of our application
   1. Account:
      1. View guest’s account information
   1. Services:
      1. View list of offered services below:
   1. Request Bellboy
      1. Make bellboy request
   1. Request Travel and Valet
      1. Make travel and valet request
   1. Request Maintenance
      1. Make maintenance request
   1. Request Cleaning Service (synonymous with room service)
      1. Make room service request
   1. Request Food Service
      1. Make food service request
1. ADMIN SIDE USAGE
2. Use preset logins (can also be found as presets in our database):
   1. Manager Admin:
      1. Username: admin@mail.com
      2. Password: admin12345
   1. Bellboy Admin:
      1. Username: bellboyadmin@mail.com
      2. Password: 1234567
   1. Valet Admin:
      1. Username: valetadmin@mail.com
      2. Password: 1234567
   1. Maintenance Admin:
      1. Username: maintenanceadmin@mail.com
      2. Password: 1234567
   1. Room Service Admin:
      1. Username: roomservicesadmin@mail.com
      2. Password: 1234567
   1. Food Service Admin:
      1. Username: foodadmin@mail.com
      2. Password: 1234567
1. From the manager Admin side, the manager has access to the following:
   1. View Rooms:
      1. View rooms that are still available for booking (manager cannot book rooms)
   1. View Guests: 
      1. Lists all guests current checked in with their name and emails
   1. View Services:
      1. Page listing all 5 types of requests. Clicking on one will lead the admin to view each in progress or incomplete request per type.
   1. View Hotel Data:
      1. Page allowing admin to view a graph detailing the information compiled from all guest’s feedback forms, put into a graph.
   1. Logout:
      1. Log out of application
1. From the request admin side (Bellboy, Valet, Maintenance, Room Service, Food):
   1. View “    “ Requests:
      1. View all incomplete or in progress requests of that type
   1. Logout:
      1. Log out of application
Java Files


Main Files:


MainAcitivty
* Start up activity and gives user options to login, register, view rooms or view hotel information


HomeActivity
* Main page to navigate through the features offered by the app. Checks the guest information and gives them functionality based on if they have a reservation and if they are checked in


Reservation Activity
	Files
	ReservationActivity
	File to create a reservation and set check in and check out dates and request room types in order to view available rooms
	ResInfo
	Object to manage all the data related to a single reservation. Object created to simplify the data sent through intents and bundles
	Room
	Room object to manage the fields such as roomType and reservation dates of a single room. Object created for each room to better update the database information 
	newViewRooms
	File that receives the reservation information and pulls room data from the database. It filters available rooms based on the check in and check out dates 
	newViewRoomsAdapter
	Helper file for using CardView and RecyclerView to print out all the available rooms to the UI
	preLoginReservationActivity
	File to allow guests to view rooms before logging in. The guest is not allowed to book a room here
	AdminViewRooms
	File to filter available rooms for the admin but does not allow the functionality to book it
	AdminRoomAdapter
	Helper file for using CardView and RecyclerView to print out all the available rooms
	



Check In Activity
	Files
	CheckInActivity
	Pulls the reservation information from the database and allows guest to check in and check out
	



Admin Side
	Files
	AdminActivity
	Main page for admin after they login to their account from the login page. From here admin can access guest information, room information, services, and hotel data.
	AdminServiceList
	Page admin sees when they click view services it’s a list of the different service types. Admin can click a button to view services.
	ViewGuests
	Page that admin sees when clicking view guests, it is filtered by guests that are currently check in to the hotel.
	ViewGuestsAdapter
	Adapter used to create the recycler view that displays Guests registered from the database in individual cards called cardview. Each card refreshes in real time everytime the database is changed. So when a guest checks in or checks out the view is updated.
	BellboyAdmin
	Main page that Bellboy service admin sees when they login. This page has a button that that leads them to BellboyServices (below), and a button to logout.
	BellboyServices
	Displays all incomplete or in progress bellboy requests from the database. The admin can click a request and set its status to either “in progress” or “complete”. Completed requests will not appear here anymore, but will still be in the database.
	ViewBellboyAdapter
	Adapter used to create the recycler view that displays services from the database in individual cards called cardview. Each card can be interacted with by the admin to set the status and the display refreshes in realtime everytime the database is changed.
	ValetAdmin
	Main page that Valet service admin sees when they login. This page has a button that that leads them to ValetServices (below), and a button to logout.
	ValetServices
	Displays all incomplete or in progress valet and travel requests from the database. The admin can click a request and set its status to either “in progress” or “complete”. Completed requests will not appear here anymore, but will still be in the database.
	ValetServicesAdapter
	Adapter used to create the recycler view that displays services from the database in individual cards called cardview. Each card can be interacted with by the admin to set the status and the display refreshes in realtime everytime the database is changed.
	MaintenanceAdmin
	Main page that Valet service admin sees when they login. This page has a button that that leads them to MaintenanceServices (below), and a button to logout.
	MaintenanceServices
	Displays all incomplete or in progress maintenance requests from the database. The admin can click a request and set its status to either “in progress” or “complete”. Completed requests will not appear here anymore, but will still be in the database.
	MaintenanceAdapter
	Adapter used to create the recycler view that displays services from the database in individual cards called cardview. Each card can be interacted with by the admin to set the status and the display refreshes in realtime everytime the database is changed.
	RoomServiceAdmin
	Main page that room service admin sees when they login. This page has a button that that leads them to RoomServices (below), and a button to logout.
	RoomServices
	Displays all incomplete or in progress room service requests from the database. The admin can click a request and set its status to either “in progress” or “complete”. Completed requests will not appear here anymore, but will still be in the database.
	RoomServicesAdapter
	Adapter used to create the recycler view that displays services from the database in individual cards called cardview. Each card can be interacted with by the admin to set the status and the display refreshes in realtime everytime the database is changed.
	FoodAdmin
	Main page that food service admin sees when they login. This page has a button that that leads them to FoodServices (below), and a button to logout.
	FoodServices
	Displays all incomplete or in progress food requests from the database. The admin can click a request and set its status to either “in progress” or “complete”. Completed requests will not appear here anymore, but will still be in the database.
	ViewFoodAdapter
	Adapter used to create the recycler view that displays services from the database in individual cards called cardview. Each card can be interacted with by the admin to set the status and the display refreshes in realtime everytime the database is changed.
	



Service Requests (From guests)
	Files
	ServicesActivity
	Page that lists all possible request types that the guest can make. However, users who have not checked in cannot access these request pages.
	BellboyActivity
	Page where guest can make a bellboy request. Accepts a date, time, number of luggage, and a location from where the guest would like the bellboy to pick up their times.
	ValetTravelActivity
	Page where guest can make a valet or travel request. Accepts a date, time, number of people traveling, and a starting and ending location.
	MaintenanceActivity
	Page where guest can make a maintenance request. Accepts a date, time, and allows user to select which type of issue they are having, as well as a box to input additional comments.
	RoomServiceActivity
	Page where guest can make a room service request. Accepts a date, time, and allows user to select cleaning and restocking materials, as well as a box to input additional comments.
	FoodServiceActivity
	Page where guest can make a food request. Accepts a date, time, and allows user to select items from a menu from where they can see their total order price, and make additional accommodations for their order. 
	Service 
	Object with all attributes from each service page. Contains 5 sub classes that use overloading to store different requests with different parameters for each type of request type.
	



Feedback Pages
	Files
	Feedback
	Feedback object that holds answers from users 
	FeedbackActivity
	File that allows guests to input feedback about their experience
	



Accessing/Editing Info Pages
	Files
	EditInfoActivity
	Allows guest to change personal info like their name and email
	ProfileActivity
	Displays the guest’s personal info
	CreditActivity
	If user has not already filled out credit card details, they can do so here
	CreditInfoAcitvity
	If user wants to replace the previous credit card saved in db, they can do so
	ViewProfile
	Object Class containing getters and setters for the private personal info.
	



Key Code
	Files
	KeyCardActivity
	Allows user to generate keyCode only if they are checked in. Once they click the generate key button they will be given a unique randomly generated key code and the button will be disabled. Then once the guest checks out, their key code will be removed.
	Integration Tests


Branches were created when each subgroup pulled from master to start writing their code. The premise of each branch was so that each group was able to test their code on a past version of the master branch. If and only if their code was working correctly without any bugs, they could merge their code to the master. Usually our team would meet up on a given day to test this new master branch after pushing. One branch was pushed at a time, problems would be resolved before merging another branch.


An integration test that we tested for is our Check-in feature. Guests are required to check-in to the hotel before having access to other features such as bellboy, valet and travel, maintenance, cleaning, inbox, feedback,etc. Under a guest account, I tried to access each of those services/features without checking-in and as designed, the app did not allow it. Only after checking-in did the app allow access to those features.


Another integration test that we ran checked for the status for each service request. When the admin updates the status for a request, the status update needs to display on the Guest inbox page as well. We tested this by changing statuses of different requests on the admin side and the app successfully displayed the right updates on the Guest side as well. This is an example of how the admin and guest accounts are integrated.


After all of the integrating, we also checked that the app didn’t crash for any reason. 








Unit Tests


Testing for individual pieces of code were done by the subgroups. Scripts were not used to test code. Instead, these individual groups would test their written code directly to check for unexpected behavior. 


Some examples of unit testing on code are:
1. Reservation testing- Checking to see if obtained values from Calendar tool is correct and checking if it was correctly uploaded to database right after. Also checking to see if search filters worked, pictures of room were shown, and booking was successful.
2. Feedback- Checking to see if integers obtained from ratingBars were correct and within parameters even when seemingly erroneous input was submitted
3. Services- Since each of the services were similar in function, all of them were tested under similar circumstances. Checking to see if different XML tools were received correctly and uploaded to database in the order they came in to keep track.
4. KeyCode-Checking to see if users code was being generated after check in and then unchanged until check out
5. Pricing-Checking to see if pricing algorithms were reflected in the recycle view display
6. Accessing info pages- Checking to see if large volumes of data was sent and stored correctly, making sure what info is hidden and secure. 
7. Admin side- Checking to see if requests were fulfilled correctly or if they were still in the “In process” state. Checking to see if all service requests can be viewed and the data can be visualized. 




For our database we use firebase to store data. To access the database you need to go to firebase.google.com. The login information is:


email: rusoftwaregroup11@gmail.com
password: software11
 
After logging in you should see our app name RUStaying, from there on the left hand side you can access database → realtime database and here you will see a big JSON file with all of our data stored through test inputs. Data can only be created through the app when a user registers, requests a service, or leaves feedback. You do not need any files as everything has already been created on the firebase account. All the data was created through testing the app the only thing we created manually was the Room Object in the database. The database is real time so while running the app you can see it update when you register or make any requests.
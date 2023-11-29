# Project Report
##  Tully Library Database Reconstruction

**Team**: if else if else if else if else if else

**Team Members**: Darrel Tran, Matthew Peng, Arjay Reyes

## Project Contribution
### Proposal

Darrel 
1. State diagram
2. Wrote some operations
3. Contributed to the description

Arjay
1. Setup [Github repository](https://github.com/ArjayReyes/CS-151-Tully), folders, and added team members to repository.
2. Class and Sequence diagrams
3. Wrote initial proposal problem, assumptions, and operations.

Matthew
1. Dealt with the use case diagram(and that's it)
### Presentation
Darrel 
1. Created barebones UI
    1. Made frame not resizable so that it’s easier to make the project/screens prettier through some “manual” or hard-coded manipulations of the components
    2. Each page used layouts including (not in any particular order):
        1. BorderLayout
        2. GridLayout
        3. FlowLayout
        4. GridBagLayout
    3. Most pages have JPanels to store the components and allow for easier component manipulation
        1. Some panels have panels within panels
    5. Button functionalities so that each button leads to a new screen
        1. The transition to each new page hides the current one and creates the new screen
    6. Return button for each page so that users can go back to the previous screen
    7. Each page aside from the account, inventory, and waitlist screen displays the “Tully Library” text
    8. Each username/password textfield initially isn’t editable and is grayed out with default text
        1. Once user clicks the textfield, they can type whatever they want and the default text disappears
    9. Main screen where users can choose on whether they want to signup or login
    10. Signup Screen
        1. Allow users to enter username and password in text fields
        2. Checkbox to display password
        3. Submit button that doesn’t do anything except go to a blank screen
        4. Hovering over the password text field or the checkbox tells the user what they need to do/put for those components
    11. Login Screen
        1. Allow users to enter their username and password
        2. Submit button that doesn’t do anything except go to Library screen
    12. Library Screen
        1. A scroll box with temporary buttons with dummy information that don’t do anything
        2. Account button to access the user’s account
        3. “Available Books” text above the scroll box
    13. Account Screen
        1. Welcome text displaying dummy name for the user
        2. Book fee text displaying dummy fee
        3. Pay fee button that doesn’t do anything
        4. Inventory button that leads to the book inventory screen
        5. Waitlist button that leads to the waitlisted books screen
    14. Inventory Screen
        1. Scroll box with dummy buttons that don’t do anything
        2. “Borrowed Books” text above the scroll box
    15. Waitlisted Books Screen
        1. Scroll box with dummy buttons that don’t do anything
        2. “Waitlisted Books” text above the scroll box

Arjay
1. Created the Book class
    1. Utilized in both Library and User classes.
    2. Contained information such as title, author, ISBN, and returnDate at the time.

Matthew
1. Created the User class
    - The User class has the account information along with the list of borrowed and waitlisted books
    - It has the basic getters, setters, and toStrings along with functions that deal with overdue books and fee calculations based on currentDate and returnDate of the book
2. Created and implemented the custom exceptions
    - There is one abstract class "GeneralException" and 2 subclasses "PasswordLoginException" and "FeeException"
    - The password exceptions deal with creating and logging into an account
    - the fee exceptions deal with certain prohibited actions when the user has an active fee
3. Made the Sign up and Login buttons work initially
### Project Code & Report

Darrel - UI/Frontend
1. Added my specific contributions to the report for the proposal, presentation, code
2. Added some missing operations to the report including: Return Books, Add Book, Remove Book, Remove User, and Modify the Date
3. Completed the Steps To Run Code section
4. Created Working UI/Frontend (Continuation of the UI from the Presentation)
    1. If a part of the UI is not listed here, then it was not changed from when it was added in the presentation version
    2. Main screen 
        1. Added Admin button to access “admin functionalities”
            1. Leads to new page where you can access buttons for screens for adding a book, removing a book, or removing a user
        2. Button to modify the date
            1. Leads to a new screen
    3. Date Modify Screen
        1. Allows user to reset the date to the current day or increment the date by an x number of days from the current date
        2. Text is displayed to show the current date or the changed date
    4. Admin Screen
        1. Buttons you can click that lead to a new screen for adding a book, removing a book, or removing a user
    5. Add Book Screen
        1. Allows the user to add a completely new book by giving its title, author, and ISBN to text fields
        2. Submit button that adds the new book to file if the given information passes all checks
    6. Remove Book Screen
        1. Allows the user to remove a book by giving its ISBN
        2. Submit button that removes the book if the given information passes all checks
    7. Remove User Screen
        1. Allows the user to remove a user by giving their ID
        2. Submit button that removes the user if the given information passes all checks
    8. Signup Screen
        1. Submit button creates a new user if the given username/password passes all checks
            1. If information does not pass a specific check, a popup tells the user what they need
        2. Leads to a success screen 
    9. Signup Success Screen
        1. Displays “Successfully signed up!” text & user's ID
        2. Allows user to go back to the main screen
    10. Login Screen
        1. Submit button now checks the given username/password and opens the library screen if all necessary checks are passed
            1. If information does not pass a specific check, a popup tells the user what the error is 
    11. Library Screen
        1. A scroll box with buttons that show the book’s title and ISBN on the actual button
            1. When clicked, the button displays a new popup screen 
            2. Popup screen displays the book’s title, author, ISBN, and availability (if it’s in use or not)
                1. Buttons to allow the user to borrow or waitlist the book or close the popup
    12. Account Screen
        1. Welcome text displaying the user’s username & their ID
        2. Book fee text displaying the user’s fee for overdue books
        3. Pay fee button that sets the user’s fee back to 0 as long as they returned the books they owe/are overdue
    13. Inventory Screen
        1. Scroll box with buttons that show the book’s author and ISBN on the button
            1. When button is clicked, it shows the book’s due date, title, author, ISBN, and availability
            2. Return button to return the book, extend button to extend the book, and close button to close the popup
        2. “Borrowed Books” text above the scroll box
    14. Waitlisted Books Screen
        1. Scroll box with buttons that show the book’s author and ISBN on the button
            1. When button is clicked, it shows the book’s title, author, ISBN, and availability
            2. Borrow button to borrow the waitlisted book, and close button to close the popup

Arjay - File I/O/Backend
1. File I/O
    1. Worked with a team to help the library application read and write .txt files for book and user databases.
        1. Appended new users and books to databases.
        2. Can delete users and books if ID is provided.
    2. File I/O within GUI:
        1. Sign-up: Users created must be appended to userDatabase.txt.
        2. Available Books: 
            1. Borrow - if the user borrows a book, update userDatabase.txt and add a returnDate in bookDatabase.txt.
            2. Waitlist - if the user borrows a book, update userDatabase.txt.
        3. Account Screen:
            1. For user’s borrowed books list, users can return, which removes the book from the user, and extend the due date which extends the returnDate by 7 days; assume that the library is forgiving of due dates in this case or that the user has an excuse for overdue books.
            2. For user’s waitlisted books list, any books available and subsequently borrowed are updated into the file.
        4. Admin:
            1. Add book - appends book to the bookDatabase.txt
            2. Remove book - removes book from the bookDatabse.txt; no white space.
            3. Remove user - removes user from userDatabase.txt; no white space.
    3. Worked closely with Darrel to fulfill functionality requirements for file I/O, and with Matthew for custom exception handling.
2. Code reevaluation
    1. After brainstorming with the team, we had to add additional parameters for our User and Book classes, in particular, we had to create an additional ArrayList class for our Users that stores any ‘waitlisted’ books that the user wants.
    2. Other miscellaneous and minor changes made were:
        1. Booleans for Book class that check if it is borrowed or waitlisted.
        2. Using LocalDate instead of the deprecated Date library for Book.
3. Project Diagram Update
    1. Updated Sequence and Class diagrams to showcase updated classes/parameters used.

Matthew
1. The User class
   - Created and implemented most of the user class
   - Key functions to include that weren't the getters, setters, and toString printers:
   - isOverdue/hasOverdue
       - These functions check for overdue book(s), where one is specific and one is generalized for all of the user's books
       - It will first check if you even have the book with hasBook, and if not, returns false.
       - It then calls daysDifference to get how many days currentDate is past the book's returnDate. If it's negative, it's not overdue and visa versa.
   - hasBook
       - This function will verify if a given book is in the user's booksBorrowed via enhanced for loop.
       - The .equals calculation is done by Arjay in the Book class.
   - calculateFees/feeCostOfBook
       - These functions will get calculate the fees that the user may have, and is called every time the user page pops up.
       - calculateFees is a for loop that adds up the individual fees from each book with feeCostOfBooks and returns it
       - The function also utilizes daysDifference to not only check if a book is overdue, but also determine how big the fee is.
       - The calculation per book is .50 * # days overdue, capping at a fee of 10.
       - The reason why these two functions are separate is because I realized the pay fee button is pretty useless when returning a book clears the fee, so I made it display the fee price of returning a book with the JOptionPane.
   - daysDifference
       - This function is utilized for fee calculations and overdue checking.
       - Since I can't find a function that can convert a date into all days, i improvised and made it get the difference of the years * 365 + the differnce in their days of the week.
       - Ex tested calculations that work: cur = 2023/11/28, return 2023/11/24, days missed is 2023-2023 + 4 = 4 days
       - cur = 2024/1/1, return = 2023/12/31 is 2024-2023*365 + (-364) = 1 day
2. The 3 Exception classes
   -
3. Small modification with the UIManager's design
   - Converted the UIManager's design pattern into a singleton type. where only one instance of UIManager should exist.
   - This is important because the universal information of the currentDate is needed to be consistent for the User class and checking for overdues.
   - Changed it so borrowing a book is based on currentDate(one we can control for testing purposes) and not TODAY(constant date) so you don't borrow a book and immediately have to pay a fee for it.
   - Made extending a book not add a year worth of time and instead another week.
## Problem

Tully Library has contracted our startup company, 5X-if-else, to create an intuitive library management system by the end of Q4 2023. The library management system must incorporate object-oriented programming concepts, Java’s Graphical User Interface (GUI) capabilities, and exception handling.

Previous works:
* Not applicable.

Assumptions/Operating Environment/Intended Usage:
* Assumed that the library has “been in operation” with a premade database of books and users.
* Users can waitlist a book if it is already borrowed with a separate page in their Account listing waitlisted books instead of having to scroll down and find the books themselves.
* Date can be changed as a way to simulate time passing through the menu screen.
* Admin button that can add/remove books and remove users using their ID.
    * We opted for this rudimentary method due to time constraints and to meet project requirements.

## Diagrams
[Class Diagram](https://github.com/ArjayReyes/CS-151-Tully/blob/e362095d23865772a32fa4cb01d7b782a59aaf04/diagrams/updatedClassDiagram.PNG)

[State Diagram](https://github.com/ArjayReyes/CS-151-Tully/blob/de13f3f0b1aea7fe0c0e84bd7a50f48e9c0fd51f/diagrams/stateDiagram.png)

[Sequence Diagram](https://github.com/ArjayReyes/CS-151-Tully/blob/669660edf699e102c16bd14dfc1a1aebce3457ad/diagrams/updatedSequenceDiagram.PNG)

[Use Case Diagram](https://github.com/ArjayReyes/CS-151-Tully/blob/3a59ab931e5298c9be705fcf000b86f617b55675/diagrams/the%20case%20of%20use.png)

## Functionality

In order to meet with the requirements listed by Tully Library, our program must implement the JavaFX/Java Swing class in order to create an interactive and simple interface for users to access the database. The database itself must also be run using object oriented concepts such as, File I/O, polymorphism, and exception handling. Emphasis on exception handling; used to deal with invalid user inputs, program errors, and stackoverflow. Extensive testing will be necessary to ensure all operations are functioning and exceptions are all handled.

## Operations

Borrow book - The Library class will have a UI that allows the user to click on a “borrow” button next to the book that appears on the visual list of books in the UI.

Sign In - Users can sign in with a username & password using a fill-in form.

Sign Out - Users can sign out using a button in the UI.

Pay Fees - The user can check what fees they have through their account UI and pay their fees.

Extend - Users can extend the time they’re borrowing their book(s) through buttons on their account.

Create Account - Users can create an account in the Library UI with a username & password; username has to be unique.

Waitlist - Users can be put on a waitlist to borrow a specific book that is already borrowed by someone else.

Show books - show users the books they’ve borrowed and ones that are overdue.

Return Books - Return a book and remove it from a user’s account

Add Book - Add a book to the database given its title, author, and ISBN

Remove Book - Remove a book from the database given its ISBN

Remove User - Remove a user from the database given their ID

Modify the Date - Allows users to reset the date or increment it by a certain number of days

## Solution

After working on difficult hurdles, our startup 5X-if-else delivered a suitable application for Tully Library. While it has its rough edges, an interface where users can borrow and interact with the library’s database was provided which edits two database files containing user and books. Some exception handling was provided primarily for our login and signup functionality for new users. Overall, while there were obstacles our team worked to the best of our abilities to deliver an application that meets the project's requirements.

### Steps To Run Code
1. First/Main Screen
    1. Click “Sign Up” to access the Sign Up Screen and create a new account
    2. Click “Login” to access the Login Screen and log into your account
    3. Click “Admin” to access admin tools that include adding a book, removing a book, and removing a user
    4. Click “Modify The Date” to access the Modify The Date Screen and change the current date of the Library
2. Sign Up Screen
    1. Follow the “First/Main Screen” instructions above
    2. Enter your username and password in the directed boxes
    3. The username can be anything, but can’t be blank
    4. The password must have/be:
        1. Not blank
        2. 10 characters
        3. 1 special character
        4. 1 uppercase and lowercase character
        5. 1 number
    5. If you forget the password rules, you can hover over the password box to see them
    6. To show the password, press the checkbox to the right of the password box
    7. Once you have correctly entered a username and password, press the “Submit” button and you should be directed to a success screen 
        1. In the success screen, press “Home” to go back to the First/Main Screen
    8. Press “Return” to go back to the First/Main Screen
3. Login Screen
    1. Follow the “First/Main Screen” instructions above
    2. Enter your username and password in their respective boxes
        1. The boxes cannot be blank
    3. Press “Submit” once you have entered the correct username and password
        1. If the username and password match, you will be directed to the Library Screen
    4. Press “Return” to go back to the First/Main Screen
4. Library Screen
    1. Sign up from the “Sign Up Screen” instructions from above if you have not done so already
    2. Login using the instructions under “Login Screen” from above
    3. Once you have logged in you should see a screen with a scroll box that has a list of all the available books
        1. Press the box/button inside the scroll box to view the complete information of the book
        2. Inside this information screen, near the bottom, you can press “Borrow” to borrow a book and “Waitlist” to put a book on your waitlist if it has already been borrowed by someone else
        3. Press “Close” or press the x in the top right of the screen to close the information screen	
    4. Press “Return” (not on the book information screen) on the bottom right to go back to the Login Screen
    5. Press “Account” in the top right to view your account
5. Account Screen
    1. Follow the instructions from “Library Screen” above
    2. If you have any overdue books, you will see their fees near the top, right above the “Welcome, user_name” text
        1. Press “Pay Fees” to remove your fees
        2. In order to pay your fees, you must return or extend the due date of any overdue books first
    3. Press “Inventory of Books” to view books that you have borrowed
    4. Press “Waitlisted Books” to view books that you have put on your waitlist
    5. Press “Return” to go back to the Library Screen
6. Book Inventory Screen
    1. Follow the instructions from “Account Screen” above
    2. Press one of the books/buttons/boxes in the scroll box to view it’s information screen
        1. Press “Return” to return the book
        2. Press “Extend” to extend the book’s due date
        3. Press “Close” or press the x in the top right of the screen to close the information screen	
    3. Press “Return” (not on the book information screen) to go back to the Account Screen
7. Book Waitlist Screen
    1. Follow the instructions from “Account Screen” above
    2. Press one of the books/buttons/boxes in the scroll box to view it’s information screen
        1. Press “Borrow” to borrow a waitlisted book if it’s available
        2. Press “Close” or press the x in the top right of the screen to close the information screen	
    3. Press “Return” to go back to the Account Screen
8. Admin Tools Screen
    1. Follow the “First/Main Screen” instructions above
    2. Once you are in the admin screen:
        1. Press “Add Book” to go to the Add Book Screen
        2. Press “Remove Book” to go to the Remove Book Screen
        3. Press “Remove User” to go to the Remove User Screen
    3. Press “Return” to go back to the First/Main Screen
9. Add Book Screen
    1. Follow the “Admin Tools Screen” instructions above
    2. Enter the book’s title, author, and ISBN in their respective boxes
        1. The ISBN must include only numbers
    3. Once you have correctly entered the book’s information, press “Submit” to add a new book to the database
    4. Press “Return” to return to the Admin Tools Screen
10. Remove Book Screen
    1. Follow the “Admin Tools Screen” instructions above
    2. Enter the ISBN of the book you want to remove in the box 
        1. The ISBN must include only numbers
        2. The book must already exist within the database
    3. Once you have correctly entered the book’s ISBN, press “Submit” to remove the book from the database 
    4. Press “Return” to return to the Admin Tools Screen 
11. Remove User Screen
    1. Follow the “Admin Tools Screen” instructions above
    2. Enter the ID of the user you want to remove in the box 
        1. The ID must include only numbers
        2. The user must already exist within the database
    3. Once you have correctly entered the user’s ID, press “Submit” to remove the user from the database 
    4. Press “Return” to return to the Admin Tools Screen
12. Modifying The Date Screen
    1. Follow the “First/Main Screen” instructions above
    2. Once you have entered the Modifying The Date Screen, you have three possible actions:
        1. To return to the first/main screen, press “Return”
        2. To reset the date to the current day, press “Reset Date”
        3. To modify the date, press the up or down arrow on the small box to the right of the “Modify the current date by x days from now: ” text
            1. If you modify the date using the arrows, the text next to “Current Date: ” will automatically update
            2. If you want to manually enter a number of days, you must press enter to update the date and text
        4. Note that you can not decrement the date below 0
            1. i.e you cannot change the date to be before today

### Screenshots

#### Main Screen
![](https://github.com/ArjayReyes/CS-151-Tully/blob/94e0b7dc82e8b829969c1e5af310df8215b41ed3/screenshots/Main%20Screen.PNG)
#### Sign Up Screen: Password Requirements
![](screenshots/Signup-1.PNG)
#### Sign Up Screen: PasswordField
![](screenshots/Signup-2.PNG)
#### Sign Up Screen: PasswordField Disabled
![](screenshots/Signup-3.PNG)
#### Sign Up Screen: Successful Sign-Up, ID Displayed
![](screenshots/Signup-4.PNG)
#### Login Screen
![](screenshots/Login-1.PNG)
#### Login Screen
![](screenshots/Login-2.PNG)
#### Login Screen: Exception Displaied
![](screenshots/Login-3.PNG)
#### Library Screen: List of Books
![](screenshots/Library-1.PNG)
#### Library Screen: Book Information
![](screenshots/Library-2.PNG)
#### Library Screen: Book Successfully Borrowed
![](screenshots/Library-3.PNG)
#### Account Screen
![](screenshots/Account-1.PNG)
#### Account Screen: Overdue Fees
![](screenshots/Account-2.PNG)
#### Fee Exception: Cannot Borrow Books with Overdue Fees
![](screenshots/FeeException-1.png)
#### Fee Exception: Cannot Waitlist Books with Overdue Fees
![](screenshots/FeeException-2.png)
#### Fee Exception: Cannot Extend Books with Overdue Fees
![](screenshots/FeeException-3.png)
#### Fee Exception: Returning Book with Fee
![](screenshots/FeeException-4.png)
#### Account Screen: Overdue Book Returned
![](screenshots/Account-3.PNG)
#### Account Screen: No more fees!
![](screenshots/Account-4.PNG)
#### Account Screen: Waitlisted Books
![](screenshots/WaitlistedBooks.PNG)
#### Account Screen: Borrowed Books
![](screenshots/BorrowedBooks.PNG)
#### Date Screen: Default
![](screenshots/Date-1.PNG)
#### Date Screen: Modified
![](screenshots/Date-2.PNG)
#### Admin Screen
![](screenshots/Admin.PNG)
#### Add Book
![](screenshots/AddBook-1.PNG)
#### Add Book Success
![](screenshots/AddBook-2.PNG)
#### Remove Book
![](screenshots/RemoveBook-1.PNG)
#### RemoveBook Success
![](screenshots/RemoveBook-2.PNG)
#### Remove User
![](screenshots/RemoveUser-1.PNG)
#### Remove User Success
![](screenshots/RemoveUser-2.PNG)


## References
* No references used or needed.

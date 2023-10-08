# Project Proposal
## Tully Library Database Reconstruction

**Team**: if else if else if else if else if else

**Team Members**: Arjay Reyes, Matthew Peng, Darrel Tran

**Problem**: Tully Library has contracted our startup company, 5X-if-else, to create an intuitive library management system by the end of Q4 2023. The library management system must incorporate object-oriented programming concepts, Java’s Graphical User Interface (GUI) capabilities, and exception handling.

**Assumptions**: 
Limit users' ability to manipulate the database. 
* Use exceptions to handle invalid books or users having too many books.
* Users have basic “computer sense” to navigate a simple interface (can read/understand directions, click buttons, know what a username/password is, etc).

**Description**: Three classes will be necessary which are Library, Book, and User classes. The Library class will have a user interface that allows the user to create an account with a username and password using the User class and borrow books from a list of books using the Book class that the user can clearly navigate through; book class will have a status that determines if it is in the library or checked out by another user. The user can sign in to access their account or log out. The Book class will have all attributes of a normal book (author, title, etc). The User class will have the list of books they borrowed, any fees they owe, and their username and password. A graphical user interface will be used for ease-of-access and a file will register all users and book data. This file must also be accessed after writing said data for repeated use. Additional documentation will be attached using JavaDocs besides this repository.

**Functionality**: In order to meet with the requirements listed by Tully Library, our program must implement the JavaFX/Java Swing class in order to create an interactive and simple interface for users to access the database. The database itself must also be run using object oriented concepts such as, File I/O, polymorphism, and exception handling. Emphasis on exception handling; used to deal with invalid user inputs, program errors, and stackoverflow. Extensive testing will be necessary to ensure all operations are functioning and exceptions are all handled. 

**Operations**: 
* Borrow book - The Library class will have a UI that allows the user to click on a “borrow” button next to the book that appears on the visual list of books in the UI.
* Sign In - Users can sign in with a username & password using a fill-in form.
* Sign Out - Users can sign out using a button in the UI.
* Pay Fees - The user can check what fees they have through their account UI and pay their fees.
* Extend - Users can extend the time they’re borrowing their book(s) through buttons on their account.
* Create Account - Users can create an account in the Library UI with a username & password; username has to be unique.
* Waitlist - Users can be put on a waitlist to borrow a specific book that is already borrowed by someone else.
* Show books - show users the books they’ve borrowed and ones that are overdue.

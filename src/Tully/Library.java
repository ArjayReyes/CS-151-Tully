package Tully;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Library
{
    private ArrayList<Book> books; // maybe change these to Collection class? ALSO HAVE TO WAIT FILE IO PARSING THING
    private final UIManager UI = new UIManager(); // creates UI
    // Users temporarily have to be stored in the UIManager class, but should not be WHEN USERS ARE STORED TO A TXT FILE
    private User currentUser;
    private ArrayList<User> users;
    public Library() throws FileNotFoundException, ParseException {
        books = new ArrayList<Book>();
    }

    public Library(ArrayList<Book> books, ArrayList<User> users) throws FileNotFoundException, ParseException {
        this.books = books;
    }

    public static void checkWaitlists(ArrayList<Book> books, ArrayList<User> users) {
        for (Book book : books) {
            for (User user : users) {
                ArrayList<Book> userWaitlist = user.getBooksWaitlisted();
                for (Book value : userWaitlist) {
                    if (book.equals(value)) {
                        book.setIsWaitlisted(true);
                    }
                }
            }
        }
    }

    // Unused check. Made this just in case the setReturnDate fails.
    public static void checkAvailability(ArrayList<Book> books, ArrayList<User> users) {
        for (Book book : books) {
            for (User user : users) {
                ArrayList<Book> userBooks = user.getBooksBorrowed();
                for (Book value : userBooks) {
                    if (book.equals(value)) {
                        book.setBorrowed(true);
                    }
                }
            }
        }
    }

    // Method reads book info from bookDatabase.txt to an ArrayList<Tully.Book>
    // Reads: title, author, ISBN, returnDate
    // Does NOT update waitListed
    // TODO: create a separate method that reads ArrayList<Tully.User> and updates books
    public static void loadBookDatabase(ArrayList<Book> books) throws FileNotFoundException {
        File bookFile = new File("bookDatabase.txt");
        Scanner sc = new Scanner(bookFile);
        while (sc.hasNext()) {
            Book book = new Book();
            String title = sc.nextLine();
            book.setTitle(title);
            String author = sc.nextLine();
            book.setAuthor(author);
            String ISBN = sc.nextLine();
            book.setISBN(ISBN);
            String returnDate = sc.nextLine();
            if (!(returnDate.isBlank())) {
                book.setReturnDate(LocalDate.parse(returnDate));
            }
            books.add(book);
            System.out.println(book);
        }
    }

    // This method adds user info from userDatabase.txt to an ArrayList<Tully.User>
    // Reads: username, password, info, ArrayList<Books> for borrowed and waitlisted books
    public static void loadUserDatabase(ArrayList<User> users) throws FileNotFoundException {
        File userFile = new File("userDatabase.txt");
        Scanner sc = new Scanner(userFile);

        while (sc.hasNext()) {
            User user = new User();
            String credentials = sc.nextLine();
            String[] userInfo = credentials.split(",");
            user.setName(userInfo[0]);
            user.setPassword(userInfo[1]);
            user.setLibraryID(Integer.parseInt(userInfo[2]));

            // next line for borrowedBooks
            String borrowedList = sc.nextLine();
            ArrayList<Book> booksBorrowed = new ArrayList<Book>();
            user.setBooksWaitlisted(booksBorrowed);
            if (!(borrowedList.isBlank())) {
                String[] borrowedBooksInfo = borrowedList.split(";");
                String[] bookInfo;
                for (String borrowedBooks : borrowedBooksInfo) {
                    // This splits book information and add to user's borrowed books.
                    bookInfo = borrowedBooks.split(",");
                    // utilizes returnDate constructor
                    Book book = new Book(bookInfo[0], bookInfo[1], bookInfo[2], LocalDate.parse(bookInfo[3]));
                    booksBorrowed.add(book);
                }
                user.setBooksBorrowed(booksBorrowed);
            }
            user.setBooksWaitlisted(booksBorrowed);

            String waitlistList = sc.nextLine();
            ArrayList<Book> booksWaitlisted = new ArrayList<Book>();
            user.setBooksWaitlisted(booksWaitlisted);
            if (!(waitlistList.isBlank())) {
                String[] waitlistedBooksInfo = waitlistList.split(";");
                String[] bookInfo;
                for (String waitlistedBooks : waitlistedBooksInfo) {
                    bookInfo = waitlistedBooks.split(",");
                    // utilizes non-returnDate constructor and is set as waitlisted
                    Book book = new Book(bookInfo[0], bookInfo[1], bookInfo[2]);
                    book.setIsWaitlisted(true);
                    booksWaitlisted.add(book);
                }
                user.setBooksWaitlisted(booksWaitlisted);
            }
            users.add(user);
        }
    }

    public ArrayList<Book> getBooks()
    {
        return books;
    }

    public void setBooks(ArrayList<Book> books)
    {
        this.books = books;
    }

}

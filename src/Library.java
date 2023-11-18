import java.util.*;

public class Library
{
    private ArrayList<Book> books; // maybe change these to Collection class? ALSO HAVE TO WAIT FILE IO PARSING THING
    private final UIManager UI = new UIManager(); // creates UI
    // Users temporarily have to be stored in the UIManager class, but should not be WHEN USERS ARE STORED TO A TXT FILE
    private User currentUser;
    private ArrayList<User> users;
    public Library()
    {
        books = new ArrayList<Book>();
    }

    public Library(ArrayList<Book> books, ArrayList<User> users)
    {
        this.books = books;
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

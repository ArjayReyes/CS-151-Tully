import java.util.Date;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    // state represents if a Book has a return date (true) or not (false)
    private boolean state = false;
    private Date returnDate;

    // constructor for book with no return date
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    // constructor for book with returnDate
    public Book(String title, String author, String ISBN, Date returnDate) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.returnDate = returnDate;
        this.state = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isCheckedOut() {
        return state;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    // Use this setter in Library to create a returnDate for book
    // sets returnDate AND state
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        this.state = true;
    }

    public String toString() {
        return  "Title: " + title +
                "Author: " + author +
                "ISBN: " + ISBN +
                "State: " + state +
                "Return Date: " + returnDate;
    }
}

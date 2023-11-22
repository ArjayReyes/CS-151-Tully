import java.util.Date;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isBorrowed = false;
    private Date returnDate;

    // constructor for book with no return date; default is January 1st, 2000
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.returnDate = (new Date(2025, 1, 1));
    }

    // constructor for book with returnDate
    public Book(String title, String author, String ISBN, Date returnDate) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.returnDate = returnDate;
        this.isBorrowed = true;
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

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    // Use this setter in Library to create a returnDate for book
    // sets returnDate AND isBorrowed
    // maybe set returnDate to a week (7 days) when book is checked out.
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        this.isBorrowed = true;
    }



    public String toString() {
        return  "Title: " + title +
                "Author: " + author +
                "ISBN: " + ISBN +
                "State: " + isBorrowed +
                "Return Date: " + returnDate;
    }
}

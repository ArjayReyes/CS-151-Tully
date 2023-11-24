
import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isBorrowed = false;
    private boolean isWaitlisted = false;
    private LocalDate returnDate;   // to set a date use LocalDate.of(Year,Month,Day)

    // constructor for book with no return date; default is January 1st, 2000
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.returnDate = LocalDate.of(2025,1,1);
    }

    // constructor for book with returnDate
    public Book(String title, String author, String ISBN, LocalDate returnDate) {
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

    public boolean getIsBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public boolean getIsWaitlisted() {
        return isWaitlisted;
    }

    public void setIsWaitlisted(boolean isWaitlisted) {
        this.isWaitlisted = isWaitlisted;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    // Use this setter in Library to create a returnDate for book
    // sets returnDate AND isBorrowed
    // maybe set returnDate to a week (7 days) when book is checked out
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.isBorrowed = true;
    }

    // https://stackoverflow.com/questions/8180430/how-to-override-equals-method-in-java
    // Checks if title, author, and ISBN are the same as book object.
    // book2.equals(book1)
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(obj.getClass() != this.getClass()) {
            return false;
        }

        final Book that = (Book) obj;
        if (!Objects.equals(this.title, that.title)) {
            return false;
        }
        if (!Objects.equals(this.author, that.author)) {
            return false;
        }
        if (!Objects.equals(this.ISBN, that.ISBN)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  "Title: " + title +
                "\nAuthor: " + author +
                "\nISBN: " + ISBN +
                "\nBook being used?: " + isBorrowed +
                "\nReturn Date: " + returnDate;
    }
}

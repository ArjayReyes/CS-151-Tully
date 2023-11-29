import java.time.LocalDate;
import java.util.ArrayList;
//RAAAAAAAAAAAAAAAAAAh
public class User {
	private String name;
	private String password;
	private int libraryID;
	private ArrayList<Book> booksBorrowed;//change to be generalized as books
	private ArrayList<Book> booksWaitlisted;
	private UIManager UI = UIManager.getInstance();

	private double fees;

	public User() {
		this.name = "Joe Biden";
		this.password = "sleepy";
		this.libraryID = 1919191919;
		this.booksBorrowed = new ArrayList<Book>();
		this.booksWaitlisted = new ArrayList<Book>();
		fees = 0;
	}

	public User(String n, String p, int id, ArrayList<Book> ar, ArrayList<Book> ar2, double fees) {
		this.name = n;
		this.password = p;
		this.libraryID = id;
		this.booksBorrowed = ar;
		this.booksWaitlisted = ar2;
		this.fees = fees;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getPassword() {
		return password;
	}

	public void setLibraryID(int libraryID) {
		this.libraryID = libraryID;
	}
	public int getId() {
		return libraryID;
	}

	public ArrayList<Book> getBooksBorrowed() {
		return booksBorrowed;
	}

	public void setBooksBorrowed(ArrayList<Book> booksBorrowed) {
		this.booksBorrowed = booksBorrowed;
	}

	public ArrayList<Book> getBooksWaitlisted() {
		return booksWaitlisted;
	}

	public void setBooksWaitlisted(ArrayList<Book> booksWaitlisted) {
		this.booksWaitlisted = booksWaitlisted;
	}
	//this returns the fee cost of a book
	public double feeCostOfBook(Book b) {
		double feeRate = .5;
		double feeCap = 10;
		double tempFees = 0;
		int daysLate = daysDifference(b.getReturnDate());
    	System.out.println("late "+ daysLate);
        if (daysLate > 0) {
        	tempFees = daysLate*feeRate; if(tempFees>feeCap)tempFees = feeCap;
        }
        return tempFees;
	}
	public void calculateFees() {
		double userFees = 0;
        for (int i = 0; i < booksBorrowed.size(); i++) {
        	//thy math
        	double tempFees = feeCostOfBook(booksBorrowed.get(i));
            userFees += tempFees;
            System.out.println("fees temp "+tempFees);
            System.out.println(UI.getDate());
        }
        setFees(userFees);
	}
	// a little different
	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	// Added an equals method within Tully.Book class.
	public boolean hasBook(Book book) {//add a method in book to compare the contents
		for(Book b : booksBorrowed) {
			if (b.equals(book)) return true;
		}
		return false;
	}
	//returns how many days are past the return date
	private int daysDifference(LocalDate d) {
		return (UI.getDate().getYear()-d.getYear())*365 + (UI.getDate().getDayOfYear()-d.getDayOfYear());
	}
	//make it so it checks the date from Tully.Library and compares to the return Date of Tully.Book.
	public boolean isOverdue(Book book) {
		if (!hasBook(book)) {
			System.out.println("You don't even have this book silly");
			return false;
		}
		if (daysDifference(book.getReturnDate()) > 0) {
			System.out.println("looks overdue");
			return true;
		}
		//System.out.println("nop");
		return false;

	}
	//a method that stops people from extending or borrowing any more books
	public boolean hasOverdue() {
		for (Book b : booksBorrowed) {
			if (isOverdue(b))return true;
		}
		return false;
	}

	public String showBooksBorrowed() {
		String str = "Here are the books " + name + " with ID " + libraryID + " borrowed:\n";
		for(Book s : booksBorrowed) {
			str += s + "\n";
		}
		return str;
	}

	public String showBooksWaitlisted() {
		String str = "Here are the books "+ name + " with ID "+ libraryID+ " waitlisted:\n";
		for(Book s : booksWaitlisted) {
			str += s + "\n";
		}
		return str;
	}

	@Override
	public String toString() {
		return "\nUser: "+name+
				"\nLibrary ID: "+libraryID+
				"\nBooks Borrowed: "+booksBorrowed+
				"\nBooks waitlisted: "+booksWaitlisted;
	}

}

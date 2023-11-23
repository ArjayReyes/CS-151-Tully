import java.util.ArrayList;
//RAAAAAAAAAAAAAAAAAAh
public class User {
	private String name;
	private String password;
	private int libraryID;
	private ArrayList<Book> booksBorrowed;//change to be generalized as books
	private ArrayList<Book> booksWaitlisted;

	public User() {
		this.name = "Joe Biden";
		this.password = "sleepy";
		this.libraryID = 1919191919;
		this.booksBorrowed = new ArrayList<Book>();
		this.booksWaitlisted = new ArrayList<Book>();
	}

	public User(String n, String p, int id, ArrayList<Book> ar, ArrayList<Book> ar2) {
		this.name = n;
		this.password = p;
		this.libraryID = id;
		this.booksBorrowed = ar;
		this.booksWaitlisted = ar2;
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

	// Added an equals method within Book class.
	public boolean hasBook(String book) {//add a method in book to compare the contents
		if (booksBorrowed.contains(book)) {
			//System.out.println("yea");
			return true;
		}
		//System.out.println("nop");
		return false;
	}

	//make it so it checks the date from Library and compares to the return Date of Book.
	public boolean isOverdue(String book) {
		//first statement checks if you even have the book
//		if (hasBook(book)) {
//			System.out.println("You don't even have this book silly");
//			return false;
//		}
		if (booksBorrowed.contains(book)) {
			//System.out.println("yea");
			return true;
		}
		//System.out.println("nop");
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

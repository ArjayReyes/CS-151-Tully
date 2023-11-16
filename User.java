

import java.util.ArrayList;
//RAAAAAAAAAAAAAAAAAAh
public class User {
	private String name;
	private String pass;
	private int libraryID;
	private ArrayList<Book> booksBorrowed;//change to be generalized as books
	
	public User() {
		name = "Joe Biden";
		pass = "sleepy";
		libraryID = 1919191919;
		booksBorrowed = new ArrayList<Book>();
	}
	public User(String n, String p, int id, ArrayList<Book> ar) {
		name = n;
		pass = p;
		libraryID = id;
		booksBorrowed = ar;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return pass;
	}
	public int getId() {
		return libraryID;
	}
	public ArrayList<Book> getBooks() {
		return booksBorrowed;
	}
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
	public String showBooks() {
		String str = "Here are the books "+ name + " with Id "+libraryID+ " borrowed:\n";
		for(Book s : booksBorrowed) {
			str += s + "\n";
		}
		return str;
	}
	
	@Override
	public String toString() {
		return "\nUser: "+name+
				"\nLibrary Id: "+libraryID+
				"\nBooks Borrowed: "+booksBorrowed;
	}

}
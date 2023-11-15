package q1;

import java.util.ArrayList;

public class User {
	private String name;
	private int libraryID;
	private ArrayList<String> booksBorrowed;//change to be generalized as books
	
	public User() {
		name = "Joe Biden";
		libraryID = 1919191919;
		booksBorrowed = new ArrayList<String>();
	}
	public User(String n, int id, ArrayList<String> ar) {
		name = n;
		libraryID = id;
		booksBorrowed = ar;
	}
	public boolean hasBook(String book) {//add a method in book to compare the contents
		if (booksBorrowed.contains(book)) {
			System.out.println("yea");
			return true;
		}
		System.out.println("nop");
		return false;
	}
	//make it so it checks the date from Library and compares to the return Date of Book.
	//Can't do rn cuz no book class
	public boolean isOverdue(String book) {
		//first statement checks if you even have the book
//		if (!booksBorrowed.contains(book)) {
//			System.out.println("You don't even have this book silly");
//			return false;
//		}
		if (booksBorrowed.contains(book)) {
			System.out.println("yea");
			return true;
		}
		System.out.println("nop");
		return false;
		
	}
	public String showBooks() {
		String str = "Here are the books "+ name + " with Id "+libraryID+ " borrowed:\n";
		for(String s : booksBorrowed) {
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
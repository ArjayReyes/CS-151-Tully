package Tully;

import java.io.*;
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
    public Library() {
        books = new ArrayList<Book>();
    }

    public Library(ArrayList<Book> books, ArrayList<User> users) {
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

    // Add book method
    public static void addBook(String title, String author, String ISBN, ArrayList<Book> books) {
        try {
            File f = new File("bookDatabase.txt");
            if (f.exists() && !f.isDirectory()) {
                BufferedWriter out = new BufferedWriter(new FileWriter("bookDatabase.txt", true));
                out.write(title + "\n" + author + "\n" + ISBN + "\n");
                out.newLine();
                out.close();
            } else {
                BufferedWriter out = new BufferedWriter(new FileWriter("bookDatabase.txt"));
                out.write(title + "\n" + author + "\n" + ISBN + "\n");
                out.newLine();
                out.close();
            }
            Book book = new Book(title, author, ISBN);
            books.add(book);
        } catch (Exception ex) {
            System.out.println("Problem reading file.");
        }
    }

    // Delete book method.
    public static void deleteBook(Book book, ArrayList<User> users, ArrayList<Book> books) {
        try {
            BufferedReader file = new BufferedReader(new FileReader("bookDatabase.txt"));
            StringBuilder input = new StringBuilder();
            String line;

            while ((line = file.readLine()) != null) {
                String trimLine = line.trim();
                if (trimLine.contains(book.getTitle())) {
                    file.readLine();
                    file.readLine();
                    file.readLine();
                    for (int i=0; i <users.size(); i++) {
                        ArrayList<Book> borrowedBooks = users.get(i).getBooksBorrowed();
                        for (int j = 0; i < borrowedBooks.size(); j++) {
                            if (borrowedBooks.get(j).equals(book)) {
                                borrowedBooks.remove(j);
                            }
                        }
                        ArrayList<Book> waitlistedBooks = users.get(i).getBooksWaitlisted();
                        for (int k = 0; i < waitlistedBooks.size(); k++) {
                            if (waitlistedBooks.get(k).equals(book)) {
                                waitlistedBooks.remove(k);
                            }
                        }
                    }
                    continue;
                }
                input.append(line);
                input.append('\n');
            }
            file.close();

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("bookDatabase.txt");
            fileOut.write(input.toString().getBytes());
            fileOut.close();
            loadBookDatabase(books);

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    // Sets all borrowed books as available and resets to default date, requires user object.
    public static void deleteUser(User user, ArrayList<User> users, ArrayList<Book> books) {
        try {
            BufferedReader file = new BufferedReader(new FileReader("userDatabase.txt"));
            StringBuilder input = new StringBuilder();
            String line;

            while ((line = file.readLine()) != null) {
                String trimLine = line.trim();
                if (trimLine.contains(user.getName() + "," + user.getPassword() + "," + user.getId())) {
                    file.readLine();
                    file.readLine();
                    ArrayList<Book> removedBooks = new ArrayList<Book>(user.getBooksBorrowed());
                    for (Book book : removedBooks) {
                        book.setBorrowed(false);
                        book.setReturnDate(LocalDate.of(2025,1,1));
                        updateBookDate(book);
                    }
                    loadBookDatabase(books);
                    continue;
                }
                input.append(line);
                input.append('\n');
            }
            file.close();

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("userDatabase.txt");
            fileOut.write(input.toString().getBytes());
            fileOut.close();
            loadUserDatabase(users);

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    // Method updates user books in userDatabase.txt
    // update user directly before calling this method!
    // can be looped for entire database if needed
    // code from: https://stackoverflow.com/questions/20039980/java-replace-line-in-text-file
    public static void updateUserBooks(User user) {
        try {
            BufferedReader file = new BufferedReader(new FileReader("userDatabase.txt"));
            StringBuilder input = new StringBuilder();
            String line;

            while ((line = file.readLine()) != null) {
                input.append(line);
                if(line.contains(user.getName() + "," + user.getPassword() + "," + user.getId())) {
                    file.readLine();
                    ArrayList<Book> updatedBooks = new ArrayList<Book>(user.getBooksBorrowed());
                    StringBuilder line2 = new StringBuilder();

                    // enhanced for loop for books borrowed
                    for (int i = 0; i < updatedBooks.size(); i++) {
                        Book book = updatedBooks.get(i);
                        line2.append(book.getTitle()).append(',').append(book.getAuthor()).append(',').append(book.getISBN());
                        if (!(book.getReturnDate() == LocalDate.of(2025, 1, 1))) {
                            line2.append(',').append(book.getReturnDate().toString());
                        }
                        if(!(i == updatedBooks.size()-1)) {
                            line2.append(';');
                        }
                    }
                    input.append('\n').append(line2);

                    file.readLine();
                    ArrayList<Book> waitlistBooks = new ArrayList<Book>(user.getBooksWaitlisted());
                    StringBuilder line3 = new StringBuilder();

                    // enhanced for loop for books waitlisted by user
                    for (int i = 0; i < waitlistBooks.size(); i++) {
                        Book book = waitlistBooks.get(i);
                        line3.append(book.getTitle()).append(',').append(book.getAuthor()).append(',').append(book.getISBN());
                        if(!(i == waitlistBooks.size()-1)) {
                            line3.append(';');
                        }
                    }
                    input.append('\n').append(line3);
                }
                input.append('\n');
            }
            file.close();

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("userDatabase.txt");
            fileOut.write(input.toString().getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    // Method to update book return dates.
    // Can be looped through entire database if needed.
    // code from: https://stackoverflow.com/questions/20039980/java-replace-line-in-text-file
    public static void updateBookDate(Book book) {
        try {
            BufferedReader file = new BufferedReader(new FileReader("bookDatabase.txt"));
            StringBuilder input = new StringBuilder();
            String line;
            while ((line = file.readLine()) != null) {
                input.append(line);
                if (line.contains(book.getTitle())) {
                    line = file.readLine();
                    input.append('\n').append(line);
                    line = file.readLine();
                    input.append('\n').append(line);
                    file.readLine();
                    input.append('\n');
                    if (!(book.getReturnDate().equals(LocalDate.of(2025,1,1)))) {
                        input.append(book.getReturnDate().toString());
                    }
                }
                input.append('\n');
            }
            file.close();

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("bookDatabase.txt");
            fileOut.write(input.toString().getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    // Method reads book info from bookDatabase.txt to an ArrayList<Tully.Book>
    // Reads: title, author, ISBN, returnDate
    // Waitlist is updated through checkWaitlist
    public static void loadBookDatabase(ArrayList<Book> books) {
        try {
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
                    book.setBorrowed(true);
                }
                books.add(book);
            }
        } catch (Exception e) {
            System.out.println("No file found!");
        }
    }

    // This method adds user info from userDatabase.txt to an ArrayList<User>
    // Reads: username, password, info, ArrayList<Books> for borrowed and waitlisted books
    public static void loadUserDatabase(ArrayList<User> users) {
        try {
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
        } catch (Exception e) {
            System.out.println("No file found!");
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

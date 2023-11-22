import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.*;

public class UIManager implements MouseListener
{
    private JFrame frame;
    JFrame displayBookFrame;
    private JButton signUpButton;
    private JButton loginButton;
    private JButton returnButton;
    private JButton submitButton;
    private JButton accountButton;
    private JButton payFeesButton;
    private JButton bookInventoryButton;
    private JButton waitListListButton; // show list of waitlisted books
    private JButton closeBookInfoButton;
    private JButton borrowBookButton;
    private JButton waitListBookButton;
    private JTextField usernameSignUp;
    private JPasswordField passwordSignUp;
    private JTextField usernameLogin;
    private JPasswordField passwordLogin;
    private JCheckBox passwordCheckBox;
    private JPanel north;
    private JPanel west;
    private JPanel center;
    private JPanel east;
    private JPanel south;
    private JPanel signUpPanel;
    private JPanel loginPanel;
    private JPanel booksPanel;
    private JPanel inventoryPanel;
    private JPanel waitListPanel;
    private JPanel accountPanel;
    private JPanel feePanel;
    private JPanel bookInfoButtonPanel;
    private JScrollPane scrollPane;
    private JScrollPane inventoryOfBooks;
    private JScrollPane waitListedBooks;
    private String currentScreen;
    private JLabel welcomeText;
    private JLabel waitlistText;
    private JLabel inventoryText;
    private JLabel availableBooksText;
    private JLabel welcomeUserText;
    private JLabel usernameText;
    private JLabel passwordText;
    private JLabel signUpSuccessText;
    private JLabel currentFees;
    private JLabel bookNameText;
    private JLabel bookAuthorText;
    private JLabel bookISBNText;
    private JLabel bookAvailabilityText;
    private final Color RED = new Color(216, 80, 77);
    private final Color DEFAULT_COLOR = new JButton().getBackground();
    private final int DEFAULT_TIMEOUT_TIME = ToolTipManager.sharedInstance().getDismissDelay();
    private ArrayList<User> users;
    private User currentUser;
    private ArrayList<String> listOfErrors;

    public static void main(String[] args)
    {
        UIManager test = new UIManager();
    }

    public UIManager()
    {
        users = new ArrayList<User>();

        // probably have to create Library instance here maybe

        initialize();
    }

    public String getCurrentScreen()
    {
        return currentScreen;
    }

    public ArrayList<User> getUsers()
    {
        return users;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        checkUserFieldsClickable(e);

        if (e.getSource() == signUpButton)
        {
            createSignUp();
        }
        else if (e.getSource() == loginButton)
        {
            createLogin();
        }

        if (e.getSource() == returnButton)
        {
            hideCurrentScreen();

            if (currentScreen.equals("signUp") || currentScreen.equals("login") || currentScreen.equals("signUpSuccess"))
            {
                createMain();
            }
            else if (currentScreen.equals("bookScreen"))
            {
                createLogin();
            }
            else if (currentScreen.equals("accountScreen"))
            {
                createBookScreen();
            }
            else if (currentScreen.equals("inventoryScreen") || currentScreen.equals("waitlistedBooksScreen"))
            {
                createAccountScreen();
            }
        }

        if (e.getSource() == submitButton)
        {
            /*
            if (!currentScreen.equals("signUp") && !currentScreen.equals("login"))
            {
                hideCurrentScreen();
            }
             */

            if (currentScreen.equals("signUp"))
            {
                if (checkSignUpFields())
                {
                    hideCurrentScreen();

                    createSignUpSuccess();
                }
                else
                {
                    createErrorPopup();
                }
            }
            else if (currentScreen.equals("login"))
            {
                if (checkLoginFields())
                {
                    hideCurrentScreen();

                    createBookScreen();
                }
                else
                {
                    createErrorPopup();
                }
            }
        }

        if (e.getSource() == accountButton)
        {
            hideCurrentScreen();

            createAccountScreen();
        }

        if (e.getSource() == payFeesButton)
        {
            // TODO: Pay the fee
        }

        if (e.getSource() == bookInventoryButton)
        {
            hideCurrentScreen();

            createInventoryScreen();
        }
        else if (e.getSource() == waitListListButton)
        {
            hideCurrentScreen();

            createWaitlistScreen();
        }

        // no book has ever been selected before
        if (displayBookFrame != null && e.getSource() != borrowBookButton && e.getSource() != waitListBookButton)
        {
            // if a book has already been viewed, close the old screen
            // to make room for the new book info screen
            displayBookFrame.setVisible(false);
        }

        // TODO: MIGHT change to use array instead of panel?
        if (currentScreen.equals("bookScreen")) // small optimization; no unnecessary loops when something clicked
        {
            createBookInfoScreen(booksPanel, e);
        }
        else if (currentScreen.equals("inventoryScreen"))
        {
            createBookInfoScreen(inventoryPanel, e);
        }
        else if (currentScreen.equals("waitlistedBooksScreen"))
        {
            createBookInfoScreen(waitListPanel, e);
        }

        System.out.println(currentScreen);
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        checkMouseHover(e);

        frame.validate();
        frame.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        checkMouseStoppedHovering(e);

        frame.validate();
        frame.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    // removes html tags from a JButton's text
    public String getJButtonTextWithoutHTML(JButton button)
    {
        StringBuilder returnMe = new StringBuilder();
        // removes <html> & </html> tags & adds to the StringBuilder
        returnMe.append(button.getText(), 6, button.getText().length() - 7);

        // remove any <br>'s and replace the space it took by a single space
        for (int i = 0; i < returnMe.length() - 4; i++)
        {
            if (returnMe.substring(i, i + 4).equals("<br>"))
            {
                returnMe.replace(i, i + 4, " ");
            }
        }

        return returnMe.toString();
    }

    // displays the book's information after a button representing the book is pressed
    public void createBookInfoScreen(JPanel someBookPanel, MouseEvent e)
    {
        JButton tempMouse = (JButton) e.getSource();

        for (int i = 0; i < someBookPanel.getComponentCount(); i++)
        {
            JButton temp = (JButton) someBookPanel.getComponent(i);

            // matches the same book buttons
            // if it matches, create the info screen
            if (tempMouse != null && tempMouse == someBookPanel.getComponent(i))
            {
                displayBookFrame = new JFrame(); // lazy way to "reset" the frame without hiding all the components
                displayBookFrame.setLayout(new GridLayout(5, 1));
                displayBookFrame.setSize(900, 500);
                displayBookFrame.setTitle(getJButtonTextWithoutHTML(temp));
                displayBookFrame.getContentPane().setBackground(new Color(121, 156, 185));
                displayBookFrame.setResizable(false);
                displayBookFrame.setLocationRelativeTo(null);
                displayBookFrame.setVisible(true);
                displayBookFrame.add(bookNameText);
                displayBookFrame.add(bookAuthorText);
                displayBookFrame.add(bookISBNText);
                displayBookFrame.add(bookAvailabilityText);
                displayBookFrame.add(bookInfoButtonPanel);
                bookInfoButtonPanel.add(borrowBookButton);
                bookInfoButtonPanel.add(waitListBookButton);
                bookInfoButtonPanel.add(closeBookInfoButton);

                closeBookInfoButton.setVisible(true);
                borrowBookButton.setVisible(true);
                waitListBookButton.setVisible(true);

                // TODO: Set actual text
                bookAuthorText.setVisible(true);
                bookAuthorText.setText("Author: ");
                // small indent
                bookAuthorText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

                bookISBNText.setVisible(true);
                bookISBNText.setText("ISBN: ");
                bookISBNText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

                bookNameText.setVisible(true);
                bookNameText.setText("Title: ");
                bookNameText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

                bookAvailabilityText.setVisible(true);
                bookAvailabilityText.setText("Availability: ");
                bookAvailabilityText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

                borrowBookButton.setBackground(DEFAULT_COLOR);
                waitListBookButton.setBackground(DEFAULT_COLOR);
                closeBookInfoButton.setBackground(DEFAULT_COLOR);

                break;
            }
        }
    }

    // initialize all instance variables
    // creating a new object
    // and setting their intial attributes
    public void initialize()
    {
        frame = new JFrame();
        north = new JPanel();
        center = new JPanel();
        south = new JPanel();
        west = new JPanel();
        east = new JPanel();
        signUpButton = new JButton();
        loginButton = new JButton();
        usernameSignUp = new JTextField();
        passwordSignUp = new JPasswordField();
        usernameLogin = new JTextField();
        passwordLogin = new JPasswordField();
        returnButton = new JButton();
        passwordCheckBox = new JCheckBox();
        signUpPanel = new JPanel();
        loginPanel = new JPanel();
        submitButton = new JButton();
        booksPanel = new JPanel();
        scrollPane = new JScrollPane(booksPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inventoryPanel = new JPanel();
        inventoryOfBooks = new JScrollPane(inventoryPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        waitListPanel = new JPanel();
        waitListedBooks = new JScrollPane(waitListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        accountButton = new JButton();
        bookInventoryButton = new JButton();
        waitListListButton = new JButton();
        payFeesButton = new JButton();
        accountPanel = new JPanel();
        feePanel = new JPanel();
        closeBookInfoButton = new JButton();
        borrowBookButton = new JButton();
        waitListBookButton = new JButton();
        bookInfoButtonPanel = new JPanel();

        frame.setSize(1000, 650);
        frame.setTitle("Tully Library");
        // have to include getContentPane()
        // because it's the frame's "overlay?"
        frame.getContentPane().setBackground(new Color(121, 156, 185));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        north.setBackground(frame.getContentPane().getBackground());
        center.setBackground(frame.getContentPane().getBackground());
        south.setBackground(frame.getContentPane().getBackground());
        west.setBackground(frame.getContentPane().getBackground());
        east.setBackground(frame.getContentPane().getBackground());
        loginPanel.setBackground(frame.getContentPane().getBackground());
        signUpPanel.setBackground(frame.getContentPane().getBackground());
        booksPanel.setBackground(frame.getContentPane().getBackground());
        inventoryPanel.setBackground(frame.getContentPane().getBackground());
        waitListPanel.setBackground(frame.getContentPane().getBackground());
        accountPanel.setBackground(frame.getContentPane().getBackground());
        feePanel.setBackground(frame.getContentPane().getBackground());
        bookInfoButtonPanel.setBackground(frame.getContentPane().getBackground());

        welcomeText = new JLabel();
        welcomeText.setText("Tully Library");
        welcomeText.setFont(new Font("Arial", Font.BOLD, 60));
        welcomeText.setPreferredSize(new Dimension(650, 100));
        welcomeText.setOpaque(false);

        waitlistText = new JLabel();
        waitlistText.setText("Waitlisted Books");
        waitlistText.setFont(new Font("Cambria", Font.BOLD, 40));
        waitlistText.setPreferredSize(new Dimension(625, 75));
        waitlistText.setOpaque(false);

        inventoryText = new JLabel();
        inventoryText.setText("Borrowed Books");
        inventoryText.setFont(new Font("Cambria", Font.BOLD, 40));
        inventoryText.setPreferredSize(new Dimension(625, 75));
        inventoryText.setOpaque(false);

        availableBooksText = new JLabel();
        availableBooksText.setText("Available Books");
        availableBooksText.setFont(new Font("Cambria", Font.BOLD, 40));
        availableBooksText.setPreferredSize(new Dimension(625, 75));
        availableBooksText.setOpaque(false);

        welcomeUserText = new JLabel();
        welcomeUserText.setText("Welcome, " + "TEMP_USER" + "!");
        welcomeUserText.setFont(new Font("Arial", Font.BOLD, 40));
        welcomeUserText.setPreferredSize(new Dimension(950, 75));
        welcomeUserText.setOpaque(false);
        welcomeUserText.setVisible(true);
        welcomeUserText.setHorizontalTextPosition(SwingConstants.LEFT);
        welcomeUserText.setName("welcomeText"); // ID

        usernameText = new JLabel();
        usernameText.setText("Username:");
        usernameText.setFont(new Font("Arial", Font.BOLD, 40));
        usernameText.setPreferredSize(new Dimension(225, 100));
        usernameText.setOpaque(false);
        usernameText.setVisible(true);

        passwordText = new JLabel();
        passwordText.setText("Password:");
        passwordText.setFont(new Font("Arial", Font.BOLD, 40));
        passwordText.setPreferredSize(new Dimension(225, 50));
        passwordText.setOpaque(false);
        passwordText.setVisible(true);

        signUpSuccessText = new JLabel();
        signUpSuccessText.setText("signUpSuccess temp text");
        signUpSuccessText.setFont(new Font("Arial", Font.BOLD, 40));
        signUpSuccessText.setPreferredSize(new Dimension(600, 500));
        signUpSuccessText.setOpaque(false);
        signUpSuccessText.setVisible(true);

        bookNameText = new JLabel();
        bookNameText.setFont(new Font("Arial", Font.BOLD, 20));
        bookNameText.setPreferredSize(new Dimension(850, 50));
        bookNameText.setOpaque(false);
        bookNameText.setVisible(true);

        bookAuthorText = new JLabel();
        bookAuthorText.setFont(new Font("Arial", Font.BOLD, 20));
        bookAuthorText.setPreferredSize(new Dimension(850, 50));
        bookAuthorText.setOpaque(false);
        bookAuthorText.setVisible(true);

        bookISBNText = new JLabel();
        bookISBNText.setFont(new Font("Arial", Font.BOLD, 20));
        bookISBNText.setPreferredSize(new Dimension(850, 50));
        bookISBNText.setOpaque(false);
        bookISBNText.setVisible(true);

        bookAvailabilityText = new JLabel();
        bookAvailabilityText.setFont(new Font("Arial", Font.BOLD, 20));
        bookAvailabilityText.setPreferredSize(new Dimension(850, 50));
        bookAvailabilityText.setOpaque(false);
        bookAvailabilityText.setVisible(true);

        currentFees = new JLabel();
        currentFees.setFont(new Font("Arial", Font.BOLD, 40));
        currentFees.setPreferredSize(new Dimension(950, 75));
        currentFees.setOpaque(false);
        currentFees.setVisible(false);
        currentFees.setHorizontalTextPosition(SwingConstants.LEFT);

        signUpButton.setText("Sign Up");
        signUpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        signUpButton.setPreferredSize(new Dimension(200, 100));
        signUpButton.setFont(new Font("Arial", Font.BOLD, 40));
        signUpButton.setVisible(true);
        // CALL THIS ONLY ONCE ON EACH COMPONENT IF ADDING SAME THING
        // for some reason can cause errors when multiple of the same listener is added to same object
        signUpButton.addMouseListener(this);

        loginButton.setText("Login");
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        loginButton.setPreferredSize(new Dimension(200, 100));
        loginButton.setFont(new Font("Arial", Font.BOLD, 40));
        loginButton.setVisible(true);
        loginButton.addMouseListener(this);

        returnButton.setText("Return");
        returnButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        returnButton.setPreferredSize(new Dimension(200, 100));
        returnButton.setFont(new Font("Arial", Font.BOLD, 40));
        returnButton.setVisible(false);
        returnButton.addMouseListener(this);

        submitButton.setText("Submit");
        submitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        submitButton.setPreferredSize(new Dimension(200, 100));
        submitButton.setFont(new Font("Arial", Font.BOLD, 40));
        submitButton.setVisible(false);
        submitButton.addMouseListener(this);

        accountButton.setText("Account");
        accountButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        accountButton.setPreferredSize(new Dimension(200, 100));
        accountButton.setFont(new Font("Arial", Font.BOLD, 40));
        accountButton.setVisible(false);
        accountButton.addMouseListener(this);

        payFeesButton.setText("Pay Fees");
        payFeesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        payFeesButton.setPreferredSize(new Dimension(200, 50));
        payFeesButton.setFont(new Font("Arial", Font.BOLD, 40));
        payFeesButton.setVisible(false);
        payFeesButton.addMouseListener(this);

        waitListListButton.setText("Waitlisted Books");
        waitListListButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        waitListListButton.setPreferredSize(new Dimension(950, 100));
        waitListListButton.setFont(new Font("Arial", Font.BOLD, 40));
        waitListListButton.setVisible(false);
        waitListListButton.addMouseListener(this);

        bookInventoryButton.setText("Inventory of Books");
        bookInventoryButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        bookInventoryButton.setPreferredSize(new Dimension(950, 100));
        bookInventoryButton.setFont(new Font("Arial", Font.BOLD, 40));
        bookInventoryButton.setVisible(false);
        bookInventoryButton.addMouseListener(this);

        closeBookInfoButton.setText("Close");
        closeBookInfoButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        closeBookInfoButton.setPreferredSize(new Dimension(150, 50));
        closeBookInfoButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeBookInfoButton.setVisible(false);
        closeBookInfoButton.addMouseListener(this);

        borrowBookButton.setText("Borrow");
        borrowBookButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        borrowBookButton.setPreferredSize(new Dimension(150, 50));
        borrowBookButton.setFont(new Font("Arial", Font.BOLD, 20));
        borrowBookButton.setVisible(false);
        borrowBookButton.addMouseListener(this);

        waitListBookButton.setText("Waitlist");
        waitListBookButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        waitListBookButton.setPreferredSize(new Dimension(150, 50));
        waitListBookButton.setFont(new Font("Arial", Font.BOLD, 20));
        waitListBookButton.setVisible(false);
        waitListBookButton.addMouseListener(this);

        usernameSignUp.setFont(new Font("Arial", Font.BOLD, 40));
        usernameSignUp.setPreferredSize(new Dimension(600, 100));
        usernameSignUp.setText("Enter your username here");
        usernameSignUp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        usernameSignUp.setVisible(true);
        usernameSignUp.addMouseListener(this);
        usernameSignUp.setForeground(Color.GRAY);
        usernameSignUp.setEditable(false);

        passwordSignUp.setFont(new Font("Arial", Font.BOLD, 40));
        passwordSignUp.setPreferredSize(new Dimension(600, 100));
        passwordSignUp.setText("Enter your password here");
        passwordSignUp.setEchoChar((char) 0);
        passwordSignUp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        passwordSignUp.setVisible(true);
        passwordSignUp.addMouseListener(this);
        passwordSignUp.setForeground(Color.GRAY);
        passwordSignUp.setEditable(false);

        usernameLogin.setFont(new Font("Arial", Font.BOLD, 40));
        usernameLogin.setPreferredSize(new Dimension(600, 100));
        usernameLogin.setText("Enter your username here");
        usernameLogin.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        usernameLogin.setVisible(true);
        usernameLogin.addMouseListener(this);
        usernameLogin.setForeground(Color.GRAY);
        usernameLogin.setEditable(false);

        passwordLogin.setFont(new Font("Arial", Font.BOLD, 40));
        passwordLogin.setPreferredSize(new Dimension(600, 100));
        passwordLogin.setText("Enter your password here");
        passwordLogin.setEchoChar((char) 0);
        passwordLogin.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        passwordLogin.setVisible(true);
        passwordLogin.addMouseListener(this);
        passwordLogin.setForeground(Color.GRAY);
        passwordLogin.setEditable(false);

        passwordCheckBox.setVisible(true);
        passwordCheckBox.setBackground(new Color(73, 84, 114));
        passwordCheckBox.addMouseListener(this);

        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        scrollPane.setPreferredSize(new Dimension(500, 435));
        // changes vertical scroll bar scrolling speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        inventoryOfBooks.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        inventoryOfBooks.setPreferredSize(new Dimension(500, 435));
        inventoryOfBooks.getVerticalScrollBar().setUnitIncrement(10);

        waitListedBooks.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        waitListedBooks.setPreferredSize(new Dimension(500, 435));
        waitListedBooks.getVerticalScrollBar().setUnitIncrement(10);

        frame.getContentPane().setLayout(new BorderLayout());

        frame.add(west, BorderLayout.WEST);
        frame.add(north, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(south, BorderLayout.SOUTH);
        frame.add(east, BorderLayout.EAST);

        createMain();

        frame.setLocationRelativeTo(null); // centers the window
        frame.setVisible(true);
        frame.validate();
        frame.repaint();
    }

    // creates the main screen
    // the screen that users see first when first opening the application
    public void createMain()
    {
        currentScreen = "main";

        north.add(welcomeText);
        center.add(signUpButton);
        center.add(loginButton);

        welcomeText.setVisible(true);
        signUpButton.setVisible(true);
        loginButton.setVisible(true);

        // makes it so the components "flow" like text
        // the flow can be customized to be focused on the left, center, etc
        // FlowLayout is also the default layout
        center.setLayout(new FlowLayout(FlowLayout.CENTER));
        north.setLayout(new FlowLayout(FlowLayout.CENTER));
        west.setLayout(new FlowLayout(FlowLayout.CENTER));
        east.setLayout(new FlowLayout(FlowLayout.CENTER));
        south.setLayout(new FlowLayout(FlowLayout.CENTER));

        // pushes north & center certain distances
        // helps make screen look a little nicer
        north.setBorder(BorderFactory.createEmptyBorder(0, 300, 150, 0));
        center.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
    }

    // creates the signup screen
    public void createSignUp()
    {
        currentScreen = "signUp";

        usernameSignUp.setText("Enter your username here");
        usernameSignUp.setForeground(Color.GRAY);
        usernameSignUp.setEditable(false);

        passwordSignUp.setText("Enter your password here");
        passwordSignUp.setEchoChar((char) 0);
        passwordSignUp.setForeground(Color.GRAY);
        passwordSignUp.setEditable(false);

        signUpButton.setVisible(false);
        loginButton.setVisible(false);
        signUpPanel.setVisible(true);
        returnButton.setVisible(true);
        submitButton.setVisible(true);
        passwordCheckBox.setVisible(true);
        usernameText.setVisible(true);
        passwordText.setVisible(true);
        west.setVisible(true);
        east.setVisible(true);
        south.setVisible(true);
        north.setVisible(true);

        west.add(usernameText); // .add does not duplicate the same component
        west.add(passwordText);
        signUpPanel.add(usernameSignUp);
        signUpPanel.add(passwordSignUp);
        center.add(signUpPanel);
        south.add(returnButton);
        south.add(submitButton);
        east.add(passwordCheckBox);

        // 3 because layout automatically positions text, but screen is large without much on it
        // so text is far apart
        // so 3 makes text more "tight"
        // GridLayout also takes into consideration every component, visible or not
        // in this case, splits layout into 3 rows
        // and 1 column
        west.setLayout(new GridLayout(3, 1));
        center.setLayout(new FlowLayout(FlowLayout.LEFT));
        signUpPanel.setLayout(new GridLayout(2, 1));
        south.setLayout(new FlowLayout(FlowLayout.CENTER));

        // beautifying the screen a bit
        north.setBorder(BorderFactory.createEmptyBorder(0, 300, 100, 0));
        center.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        west.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        south.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        east.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 60));

        signUpButton.setBackground(DEFAULT_COLOR);
        loginButton.setBackground(DEFAULT_COLOR);
        submitButton.setBackground(DEFAULT_COLOR);
        returnButton.setBackground(DEFAULT_COLOR);
    }

    // creates success screen upon successfully signing up
    public void createSignUpSuccess()
    {
        currentScreen = "signUpSuccess";

        returnButton.setText("Home");

        // html text
        // &emsp = 4 empty spaces
        signUpSuccessText.setText("<html>" + "&emsp&emsp&emsp&emsp&emsp&emsp&emsp Successfully signed up!" + "</html>");
        signUpSuccessText.setPreferredSize(new Dimension(1000, 400));

        center.add(signUpSuccessText);

        signUpSuccessText.setVisible(true);
        returnButton.setVisible(true);
        west.setVisible(false);
        center.setLayout(new FlowLayout(FlowLayout.LEFT));

        north.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 0));
    }

    // creates a popup that tells the user what they still need
    // for login or signup usernames or passwords
    public void createErrorPopup()
    {
        StringBuilder errors = new StringBuilder();

        errors.append("<html>");

        // checks error keys to know which errors to display
        for (int i = 0; i < listOfErrors.size(); i++)
        {
            switch(listOfErrors.get(i))
            {
                case "emptyUser":
                    errors.append("Username cannot be empty!");
                    break;
                case "emptyPass":
                    errors.append("Password cannot be empty!");
                    break;
                case "duplicate":
                    errors.append("Username already exists!");
                    break;
                case "10characters":
                    errors.append("Password has to have at least 10 characters!");
                    break;
                case "uppercase":
                    errors.append("Password has to have at least one uppercase character!");
                    break;
                case "lowercase":
                    errors.append("Password has to have at least one lowercase character!");
                    break;
                case "special":
                    errors.append("Password has to have at least one special character!");
                    break;
                case "number":
                    errors.append("Password has to have at least one number!");
                    break;
                case "incorrectLogin":
                    errors.append("Incorrect username or password!");
                    break;
            }

            if (i != errors.length() - 1)
            {
                // line break
                errors.append(" <br> ");
            }
        }

        errors.append("</html>");

        // display the error
        JOptionPane.showMessageDialog(frame, errors.toString());
    }

    // checks the username & password of the signup for any errors
    public boolean checkSignUpFields()
    {
        //Here we can make sure that username and password exist, and that the password fits the requirements.
        String usertxt = usernameSignUp.getText();
        String pass = String.valueOf(passwordSignUp.getPassword()); //.getText() is depreciated
        // check if foreground is grey since a possible username or password could be "Enter your username here"
        boolean checkUsername = usertxt.equals("Enter your username here") && usernameSignUp.getForeground() == Color.GRAY;
        boolean checkPassword = pass.equals("Enter your password here") && passwordSignUp.getForeground() == Color.GRAY;
        listOfErrors = new ArrayList<String>();

        //one field is empty
        // or both fields are empty
        if ((checkUsername || usertxt.isEmpty()) && (checkPassword || pass.isEmpty())) {
            System.out.println("bruh");
            listOfErrors.add("emptyUser");
            listOfErrors.add("emptyPass");
            return false;
        }
        else if (checkUsername || usertxt.isEmpty()) {
            System.out.println("bruh");
            listOfErrors.add("emptyUser");
            return false;
        }
        else if (checkPassword || pass.isEmpty()) {
            System.out.println("bruh");
            listOfErrors.add("emptyPass");
            return false;
        }
        //duplicate username
        for(User use : users) {
            if(use.getName().equals(usertxt))
            {
                System.out.println("dupe");
                listOfErrors.add("duplicate");
                return false;
            }
        }

        Pattern letterUp = Pattern.compile("[A-Z]");
        Pattern letterDown = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasLetterUp = letterUp.matcher(pass);
        Matcher hasLetterDown = letterDown.matcher(pass);
        Matcher hasDigit = digit.matcher(pass);
        Matcher hasSpecial = special.matcher(pass);

        try {
            if (pass.length() < 10)
                throw new Exception("At least 10 characters needed.");
        }catch(Exception ex) {
            System.out.println("At least 10 characters needed.");
            listOfErrors.add("10characters");
        }
        try {
            if (!hasLetterUp.find())
                throw new Exception("At least one upper case letter");
        }catch(Exception ex) {
            System.out.println("At least one upper case letter");
            listOfErrors.add("uppercase");
        }
        try {
            if (!hasLetterDown.find())
                throw new Exception("At least one lower case letter");
        }catch(Exception ex) {
            System.out.println("At least one lower case letter");
            listOfErrors.add("lowercase");
        }
        try {
            if (!hasSpecial.find())
                throw new Exception("At least one special letter");
        }catch(Exception ex) {
            System.out.println("At least one special letter");
            listOfErrors.add("special");
        }
        try {
            if (!hasDigit.find())
                throw new Exception("At least one number");
        }catch(Exception ex) {
            System.out.println("At least one number");
            listOfErrors.add("number");
        }
        //if all is good, create the User, add it to users
        String randoId = "";
        Random r = new Random();
        for(int i = 0; i< 8;i++) {
            randoId += r.nextInt(10);
        }

        // empty = no errors found
        if (listOfErrors.isEmpty())
        {
            User u = new User(usertxt, pass, Integer.parseInt(randoId), new ArrayList<Book>());
            users.add(u);
            return true;
        }
        else
        {
            return false;
        }
    }

    // creates the actual library
    // displays available books and allows user to access their account
    public void createBookScreen()
    {
        currentScreen = "bookScreen";

        north.setBorder(BorderFactory.createEmptyBorder(0, 320, 0, 0));
        center.setBorder(null);
        west.setBorder(null);
        east.setBorder(null);
        south.setBorder(null);

        north.setVisible(true);
        welcomeText.setVisible(true);
        west.setVisible(false);
        accountButton.setVisible(true);
        east.setVisible(true);

        loginPanel.setVisible(false);
        submitButton.setVisible(false);
        scrollPane.setVisible(true);

        booksPanel.setBorder(LineBorder.createBlackLineBorder());
        booksPanel.setVisible(true);

        // Spent 3 hours trying to understand GridBag
        // still makes no sense
        // plugging in somewhat random values for the constraints works
        // puts scroll pane on the bottom middle of the screen
        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.gridx = 1; // lower numbers = left, higher numbers = right?
        panelConstraints.gridy = 1; // lower numbers = top, larger numbers = bottom?
        frame.setLayout(new GridBagLayout());
        frame.add(scrollPane, panelConstraints);

        // puts text right above the scrollPane
        availableBooksText.setVisible(true);
        north.add(availableBooksText);

        // puts the welcome text right above the "Available Books" text
        panelConstraints.gridx = 1;
        panelConstraints.gridy = 0;
        frame.add(north, panelConstraints); // replaces existing north
        north.setPreferredSize(new Dimension(650, 175));

        // TODO: ADD ACTUAL BOOKS
        // TODO: ADD WAIT LIST WHEN PRESS BOOK IF ALREADY BORROWED
        // probably want to get rid of this for loop at some point
        for (int i = 0; i < 25; i++)
        {
            JButton temp = new JButton();
            temp.setText("<html>Book " + i + "<br>Author: Bob</html>");
            temp.setFont(new Font("Calibri", Font.BOLD, 25));
            temp.setHorizontalAlignment(SwingConstants.LEFT);
            temp.setPreferredSize(new Dimension(450, 100));
            temp.addMouseListener(this);
            temp.setName(i + "");
            booksPanel.add(temp);
        }

        booksPanel.setLayout(new GridLayout(booksPanel.getComponentCount(), 1));

        // puts account & return button to the right
        east.add(accountButton); // Account button panel
        east.setLayout(new GridBagLayout());
        accountButton.setVisible(true);
        south.add(returnButton); // Return button panel
        south.setLayout(new GridBagLayout());
        returnButton.setVisible(true);
        panelConstraints.gridx = 2;
        panelConstraints.gridy = -1;
        frame.add(east, panelConstraints);
        panelConstraints.gridx = 2;
        panelConstraints.gridy = 1;
        panelConstraints.anchor = GridBagConstraints.SOUTH; // sticks component to the south
        frame.add(south, panelConstraints);
    }

    // creates the account screen
    // shows the user's fees & inventory of books or waitlisted books
    public void createAccountScreen()
    {
        currentScreen = "accountScreen";

        north.setPreferredSize(new Dimension(1000, 300));

        welcomeUserText.setText("Welcome, " + currentUser.getName()+ "!");
        welcomeUserText.setFont(new Font("Arial", Font.BOLD, 40));
        welcomeUserText.setPreferredSize(new Dimension(950, 75));
        welcomeUserText.setOpaque(false);
        welcomeUserText.setVisible(true);
        welcomeUserText.setHorizontalTextPosition(SwingConstants.LEFT);

        // TODO: ADD ACTUAL FEES
        currentFees.setVisible(true);
        currentFees.setText("Fees: " + "999" + "$");

        north.setVisible(true);
        payFeesButton.setVisible(true);
        waitListListButton.setVisible(true);
        bookInventoryButton.setVisible(true);
        feePanel.setVisible(true);
        accountPanel.setVisible(true);
        center.setVisible(true);
        welcomeText.setVisible(false);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.add(north, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(south, BorderLayout.SOUTH);
        frame.add(east, BorderLayout.EAST);
        frame.add(west, BorderLayout.WEST);

        center.setBorder(BorderFactory.createEmptyBorder(0, 9, 0, 0));
        north.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        north.add(welcomeUserText);
        feePanel.add(currentFees);
        feePanel.add(payFeesButton);
        north.add(feePanel);
        accountPanel.add(bookInventoryButton);
        accountPanel.add(waitListListButton);
        center.add(accountPanel);

        accountPanel.setLayout(new GridLayout(2, 1));
        feePanel.setLayout(new GridLayout(2, 1));
        north.setLayout(new FlowLayout(FlowLayout.CENTER));

        returnButton.setBackground(DEFAULT_COLOR);
    }

    // create the user's inventory screen of all the books they have borrowed
    public void createInventoryScreen()
    {
        currentScreen = "inventoryScreen";

        returnButton.setVisible(true);
        inventoryOfBooks.setVisible(true);

        north.setVisible(true);
        south.setVisible(true);

        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.gridx = 1;
        panelConstraints.gridy = 1;
        frame.setLayout(new GridBagLayout());
        frame.add(inventoryOfBooks, panelConstraints);

        inventoryText.setVisible(true);
        north.add(inventoryText);

        panelConstraints.gridx = 1;
        panelConstraints.gridy = 0;
        frame.add(north, panelConstraints); // replaces existing north
        north.setPreferredSize(new Dimension(650, 75));

        north.setBorder(BorderFactory.createEmptyBorder(0, 325, 0, 0));

        // TODO: ADD ACTUAL BOOKS
        // probably want to get rid of this for loop at some point
        for (int i = 0; i < 10; i++)
        {
            JButton temp = new JButton();
            temp.setText("<html>Book " + i + "<br>Author: Joe</html>");
            temp.setFont(new Font("Calibri", Font.BOLD, 25));
            temp.setHorizontalAlignment(SwingConstants.LEFT);
            temp.setPreferredSize(new Dimension(450, 100));
            temp.addMouseListener(this);
            inventoryPanel.add(temp);
        }

        inventoryPanel.setLayout(new GridLayout(inventoryPanel.getComponentCount(), 1));

        south.add(returnButton);  // Return button panel
        south.setLayout(new GridBagLayout());
        returnButton.setVisible(true);
        panelConstraints.gridx = 2;
        panelConstraints.gridy = 1;
        panelConstraints.anchor = GridBagConstraints.SOUTH; // sticks component to the south
        frame.add(south, panelConstraints);
    }

    // creates the user's waitlist screen of all the books they want to borrow
    public void createWaitlistScreen()
    {
        currentScreen = "waitlistedBooksScreen";

        returnButton.setVisible(true);
        waitListedBooks.setVisible(true);

        north.setBorder(null);
        center.setBorder(null);
        west.setBorder(null);
        east.setBorder(null);
        south.setBorder(null);

        north.setVisible(true);
        south.setVisible(true);

        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.gridx = 1;
        panelConstraints.gridy = 1;
        frame.setLayout(new GridBagLayout());
        frame.add(waitListedBooks, panelConstraints);

        waitlistText.setVisible(true);
        north.add(waitlistText);

        panelConstraints.gridx = 1;
        panelConstraints.gridy = 0;
        frame.add(north, panelConstraints); // replaces existing north
        north.setPreferredSize(new Dimension(650, 75));

        north.setBorder(BorderFactory.createEmptyBorder(0, 325, 0, 0));

        // TODO: ADD ACTUAL BOOKS
        // probably want to get rid of this for loop at some point
        for (int i = 0; i < 5; i++)
        {
            JButton temp = new JButton();
            temp.setText("<html>Book " + i + "<br>Author: Jill</html>");
            temp.setFont(new Font("Calibri", Font.BOLD, 25));
            temp.setHorizontalAlignment(SwingConstants.LEFT);
            temp.setPreferredSize(new Dimension(450, 100));
            temp.addMouseListener(this);
            waitListPanel.add(temp);
        }

        waitListPanel.setLayout(new GridLayout(waitListPanel.getComponentCount(), 1));

        south.add(returnButton);  // Return button panel
        south.setLayout(new GridBagLayout());
        returnButton.setVisible(true);
        panelConstraints.gridx = 2;
        panelConstraints.gridy = 1;
        panelConstraints.anchor = GridBagConstraints.SOUTH; // sticks component to the south
        frame.add(south, panelConstraints);
    }

    // checks login username & password for any errors
    public boolean checkLoginFields()
    {
        //verify if this person's login credentials are correct.
        String usertxt = usernameLogin.getText();
        String pass = passwordLogin.getText();
        boolean checkUsername = usertxt.equals("Enter your username here") && usernameSignUp.getForeground() == Color.GRAY;
        boolean checkPassword = pass.equals("Enter your password here") && passwordSignUp.getForeground() == Color.GRAY;
        listOfErrors = new ArrayList<String>();

        //one field is empty
        // or both fields are empty
        if ((checkUsername || usertxt.isEmpty()) && (checkPassword || pass.isEmpty())) {
            System.out.println("bruh");
            listOfErrors.add("emptyUser");
            listOfErrors.add("emptyPass");
            return false;
        }
        else if (checkUsername || usertxt.isEmpty()) {
            System.out.println("bruh");
            listOfErrors.add("emptyUser");
            return false;
        }
        else if (checkPassword || pass.isEmpty()) {
            System.out.println("bruh");
            listOfErrors.add("emptyPass");
            return false;
        }
        for(User use : users) {
            if(use.getName().equals(usertxt) && use.getPassword().equals(pass))
                currentUser = use;
        }
        if (currentUser == null)
        {
            System.out.println("failed to login in");
            listOfErrors.add("incorrectLogin");
            return false;
        }

        return true;
    }

    // creates the login screen
    public void createLogin()
    {
        currentScreen = "login";

        currentUser = null; //resets the current user

        usernameLogin.setText("Enter your username here");
        usernameLogin.setForeground(Color.GRAY);
        usernameLogin.setEditable(false);

        passwordLogin.setText("Enter your password here");
        passwordLogin.setEchoChar((char) 0);
        passwordLogin.setForeground(Color.GRAY);
        passwordLogin.setEditable(false);

        loginPanel.setBackground(frame.getContentPane().getBackground());

        west.add(usernameText);
        west.add(passwordText);
        loginPanel.add(usernameLogin);
        loginPanel.add(passwordLogin);
        center.add(loginPanel);
        south.add(returnButton);
        south.add(submitButton);

        returnButton.setVisible(true);
        submitButton.setVisible(true);
        loginPanel.setVisible(true);
        signUpButton.setVisible(false);
        loginButton.setVisible(false);
        usernameLogin.setVisible(true);
        passwordLogin.setVisible(true);
        usernameText.setVisible(true);
        passwordText.setVisible(true);
        west.setVisible(true);
        center.setVisible(true);

        frame.getContentPane().setLayout(new BorderLayout());
        north.setLayout(new FlowLayout(FlowLayout.CENTER)); // FlowLayout = default layout
        south.setLayout(new FlowLayout(FlowLayout.CENTER));
        east.setLayout(new FlowLayout(FlowLayout.LEFT));
        west.setLayout(new GridLayout(3, 1));
        center.setLayout(new FlowLayout(FlowLayout.LEFT));
        loginPanel.setLayout(new GridLayout(2, 1));

        north.setBorder(BorderFactory.createEmptyBorder(0, 300, 100, 0));
        center.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        west.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        south.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        signUpButton.setBackground(DEFAULT_COLOR);
        loginButton.setBackground(DEFAULT_COLOR);
        submitButton.setBackground(DEFAULT_COLOR);
        returnButton.setBackground(DEFAULT_COLOR);

        frame.add(west, BorderLayout.WEST);
        frame.add(north, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(south, BorderLayout.SOUTH);
        frame.add(east, BorderLayout.EAST);
    }

    // hides the current screen right before transitioning to another screen
    // the transition is not part of this
    // but all the "create" methods
    public void hideCurrentScreen()
    {
        if (currentScreen.equals("signUp"))
        {
            west.setBorder(null);
            east.setBorder(null);
            south.setBorder(null);

            signUpPanel.setVisible(false);
            returnButton.setVisible(false);
            passwordCheckBox.setVisible(false);
            submitButton.setVisible(false);
            usernameText.setVisible(false);
            passwordText.setVisible(false);

            signUpButton.setBackground(DEFAULT_COLOR);
            loginButton.setBackground(DEFAULT_COLOR);

            passwordCheckBox.setBackground(new Color(73, 84, 114));
            ToolTipManager.sharedInstance().setDismissDelay(DEFAULT_TIMEOUT_TIME);
        }
        else if (currentScreen.equals("login"))
        {
            west.setBorder(null);
            south.setBorder(null);

            loginPanel.setVisible(false);
            returnButton.setVisible(false);
            submitButton.setVisible(false);
            usernameLogin.setVisible(false);
            passwordLogin.setVisible(false);
            usernameText.setVisible(false);
            passwordText.setVisible(false);

            signUpButton.setBackground(DEFAULT_COLOR);
            loginButton.setBackground(DEFAULT_COLOR);
        }
        else if (currentScreen.equals("signUpSuccess"))
        {
            returnButton.setText("Return");

            signUpSuccessText.setVisible(false);
            returnButton.setVisible(false);
            west.setVisible(true);

            passwordCheckBox.setBackground(new Color(73, 84, 114));
            ToolTipManager.sharedInstance().setDismissDelay(DEFAULT_TIMEOUT_TIME);
        }
        else if (currentScreen.equals("bookScreen"))
        {
            north.setPreferredSize(null);

            accountButton.setVisible(false);
            scrollPane.setVisible(false);
            returnButton.setVisible(true);
            availableBooksText.setVisible(false);
        }
        else if (currentScreen.equals("accountScreen"))
        {
            payFeesButton.setVisible(false);
            bookInventoryButton.setVisible(false);
            waitListListButton.setVisible(false);
            center.setVisible(false);
            west.setVisible(false);
            east.setVisible(false);
            feePanel.setVisible(false);
            welcomeUserText.setVisible(false);
            accountPanel.setVisible(false);
        }
        else if (currentScreen.equals("inventoryScreen"))
        {
            inventoryOfBooks.setVisible(false);
            inventoryText.setVisible(false);
        }
        else if (currentScreen.equals("waitlistedBooksScreen"))
        {
            waitListedBooks.setVisible(false);
            waitlistText.setVisible(false);
        }
    }

    // changes the username & password fields based on when they were clicked
    // ex: username & password not clicked, color = gray and says "Enter your password/username here"
    // when its clicked, field becomes empty and color changes to black
    // also checks if the passwordCheckBox was clicked
    // to display the password or not
    public void checkUserFieldsClickable(EventObject e)
    {
        // check currentScreen since invisible stuff can still get clicked somehow
        // if not clicked already clicked
        if (e.getSource() == usernameSignUp && usernameSignUp.getForeground() == Color.GRAY)
        {
            usernameSignUp.setText("");
            usernameSignUp.setForeground(Color.BLACK);
            usernameSignUp.setEditable(true);
        }
        // if already clicked
        else if (usernameSignUp.getText().isEmpty())
        {
            usernameSignUp.setText("Enter your username here");
            usernameSignUp.setForeground(Color.GRAY);
            usernameSignUp.setEditable(false);
        }

        // if already clicked but hide the password
        if (e.getSource() == passwordSignUp && passwordSignUp.getForeground() == Color.GRAY && !passwordCheckBox.isSelected())
        {
            passwordSignUp.setForeground(Color.BLACK);
            passwordSignUp.setEchoChar('*');
            passwordSignUp.setText("");
            passwordSignUp.setEditable(true);
        }
        // if already clicked but show the password
        else if (e.getSource() == passwordSignUp && passwordSignUp.getForeground() == Color.GRAY && passwordCheckBox.isSelected())
        {
            passwordSignUp.setForeground(Color.BLACK);
            passwordSignUp.setEchoChar((char) 0);
            passwordSignUp.setText("");
            passwordSignUp.setEditable(true);
        }
        // if not clicked already clicked
        else if (String.valueOf(passwordSignUp.getPassword()).isEmpty())
        {
            passwordSignUp.setForeground(Color.GRAY);
            passwordSignUp.setEchoChar((char) 0);
            passwordSignUp.setText("Enter your password here");
            passwordSignUp.setEditable(false);
        }

        // if checkbox not already pressed
        // when pressed, change text to normal
        if (e.getSource() == passwordCheckBox && !passwordCheckBox.isSelected()
                && passwordSignUp.getForeground() == Color.BLACK)
        {
            passwordSignUp.setEchoChar((char) 0);
            passwordSignUp.setText(String.valueOf(passwordSignUp.getPassword()));
        }
        // if checkbox already pressed
        // when pressed, change text to *
        if (e.getSource() == passwordCheckBox && passwordCheckBox.isSelected()
                && passwordSignUp.getForeground() == Color.BLACK)
        {
            passwordSignUp.setEchoChar('*');
            passwordSignUp.setText(String.valueOf(passwordSignUp.getPassword()));
        }

        // if not clicked already clicked
        if (e.getSource() == usernameLogin && usernameLogin.getForeground() == Color.GRAY)
        {
            usernameLogin.setForeground(Color.BLACK);
            usernameLogin.setText("");
            usernameLogin.setEditable(true);
        }
        // if already clicked
        else if (usernameLogin.getText().isEmpty())
        {
            usernameLogin.setForeground(Color.GRAY);
            usernameLogin.setText("Enter your username here");
            usernameLogin.setEditable(false);
        }

        // if not clicked already clicked
        if (e.getSource() == passwordLogin && passwordLogin.getForeground() == Color.GRAY)
        {
            passwordLogin.setForeground(Color.BLACK);
            passwordLogin.setEchoChar('*');
            passwordLogin.setText("");
            passwordLogin.setEditable(true);
        }
        // if already clicked
        else if (String.valueOf(passwordLogin.getPassword()).isEmpty())
        {
            passwordLogin.setForeground(Color.GRAY);
            passwordLogin.setEchoChar((char) 0);
            passwordLogin.setText("Enter your password here");
            passwordLogin.setEditable(false);
        }
    }

    // checks if mouse is hovering over a button
    // if it is, change the color to red
    public void checkMouseHover(MouseEvent e)
    {
        if (e.getSource() == signUpButton)
        {
            signUpButton.setBackground(RED);
        }

        if (e.getSource() == loginButton)
        {
            loginButton.setBackground(RED);
        }

        if (e.getSource() == returnButton)
        {
            returnButton.setBackground(RED);
        }

        if (e.getSource() == submitButton)
        {
            submitButton.setBackground(RED);
        }

        if (e.getSource() == accountButton)
        {
            accountButton.setBackground(RED);
        }

        if (e.getSource() == payFeesButton)
        {
            payFeesButton.setBackground(RED);
        }

        if (e.getSource() == bookInventoryButton)
        {
            bookInventoryButton.setBackground(RED);
        }

        if (e.getSource() == waitListListButton)
        {
            waitListListButton.setBackground(RED);
        }

        if (e.getSource() == borrowBookButton)
        {
            borrowBookButton.setBackground(RED);
        }

        if (e.getSource() == closeBookInfoButton)
        {
            closeBookInfoButton.setBackground(RED);
        }

        if (e.getSource() == waitListBookButton)
        {
            waitListBookButton.setBackground(RED);
        }

        // show tooltip/hint for password
        if (e.getSource() == passwordCheckBox)
        {
            passwordCheckBox.setBackground(new Color(35, 48, 84));
            passwordCheckBox.setToolTipText("Click me to show the password!");
            // 10 seconds
            // increase time before tooltip disappears
            ToolTipManager.sharedInstance().setDismissDelay(10000);
        }

        // show tooltip/requirements for password signup
        if (e.getSource() == passwordSignUp)
        {
            passwordSignUp.setToolTipText("<html>" +
                    "Password cannot be empty.<br>" +
                    "Password must have at least 10 characters.<br>" +
                    "Password must have a special character.<br>" +
                    "Password must have an uppercase character.<br>" +
                    "Password must have a lowercase character.<br>" +
                    "Password must have a number." +
                    "</html>");
            // 30 seconds
            ToolTipManager.sharedInstance().setDismissDelay(30000);
        }
    }

    // checks if mouse is not hovering over a button
    // if not, change color to the default color
    public void checkMouseStoppedHovering(MouseEvent e)
    {
        if (e.getSource() == signUpButton)
        {
            signUpButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == loginButton)
        {
            loginButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == returnButton)
        {
            returnButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == submitButton)
        {
            submitButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == accountButton)
        {
            accountButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == payFeesButton)
        {
            payFeesButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == bookInventoryButton)
        {
            bookInventoryButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == waitListListButton)
        {
            waitListListButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == borrowBookButton)
        {
            borrowBookButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == closeBookInfoButton)
        {
            closeBookInfoButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == waitListBookButton)
        {
            waitListBookButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == passwordCheckBox)
        {
            passwordCheckBox.setBackground(new Color(73, 84, 114));
            ToolTipManager.sharedInstance().setDismissDelay(DEFAULT_TIMEOUT_TIME);
        }
    }
}

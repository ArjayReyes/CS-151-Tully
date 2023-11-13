import javax.print.event.PrintJobAttributeListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.html.CSS;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLOutput;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public class Library implements MouseListener
{
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private JFrame frame;
    private JButton signUpButton;
    private JButton loginButton;
    private JButton returnButton;
    private JButton submitButton;
    private JButton accountButton;
    private JButton payFeesButton;
    private JButton bookInventoryButton;
    private JButton waitListListButton; // show list of waitlisted books
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
    private JScrollPane scrollPane;
    private JScrollPane inventoryOfBooks;
    private JScrollPane waitListedBooks;
    private String currentScreen;
    private final Color RED = new Color(216, 80, 77);
    private final Color DEFAULT_COLOR = new JButton().getBackground();
    private final int DEFAULT_TIMEOUT_TIME = ToolTipManager.sharedInstance().getDismissDelay();

    public Library()
    {
        books = null;
        users = null;
        currentScreen = "main";

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

        initialize();
    }

    public Library(ArrayList<Book> books, ArrayList<User> users)
    {
        this.books = books;
        this.users = users;
        currentScreen = "main";

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

        initialize();
    }

    public ArrayList<Book> getBooks()
    {
        return books;
    }

    public void setBooks(ArrayList<Book> books)
    {
        this.books = books;
    }

    public ArrayList<User> getUsers()
    {
        return users;
    }

    public void setUsers(ArrayList<User> users)
    {
        this.users = users;
    }

    public String getCurrentScreen()
    {
        return currentScreen;
    }

    public void setCurrentScreen(String currentScreen)
    {
        this.currentScreen = currentScreen;
    }

    // initializes all necessary components
    public void initialize()
    {
        JLabel welcomeText = new JLabel();
        welcomeText.setText("Tully Library");
        welcomeText.setFont(new Font("Arial", Font.BOLD, 60));
        welcomeText.setPreferredSize(new Dimension(650, 100));
        welcomeText.setOpaque(false);

        frame.setSize(1000, 650);
        frame.setTitle("Tully Library");
        // have to include getContentPane()
        // because it's the frame's "overlay?"
        frame.getContentPane().setBackground(new Color(121, 156, 185));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // new Color(121, 156, 185)
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

        feePanel.setName("feePanel");

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

        inventoryOfBooks.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        inventoryOfBooks.setPreferredSize(new Dimension(500, 435));

        waitListedBooks.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        waitListedBooks.setPreferredSize(new Dimension(500, 435));

        frame.getContentPane().setLayout(new BorderLayout());

        north.add(welcomeText);
        center.add(signUpButton);
        center.add(loginButton);

        frame.add(west, BorderLayout.WEST);
        frame.add(north, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(south, BorderLayout.SOUTH);
        frame.add(east, BorderLayout.EAST);

        north.setBorder(BorderFactory.createEmptyBorder(0, 300, 150, 0));
        center.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        frame.setLocationRelativeTo(null); // centers the window
        frame.setVisible(true);
        frame.validate();
        frame.repaint();
    }

    public void showLibrary()
    {

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        /*************************************** TEXT/PASSWORD FIELD FUNCTIONALITIES ***************************************/
        // check currentScreen since invisible stuff can still get clicked somehow
        if (e.getSource() == usernameSignUp && usernameSignUp.getForeground() == Color.GRAY)
        {
            usernameSignUp.setText("");
            usernameSignUp.setForeground(Color.BLACK);
            usernameSignUp.setEditable(true);
        }
        else if (usernameSignUp.getText().isEmpty())
        {
            usernameSignUp.setText("Enter your username here");
            usernameSignUp.setForeground(Color.GRAY);
            usernameSignUp.setEditable(false);
        }

        if (e.getSource() == passwordSignUp && passwordSignUp.getForeground() == Color.GRAY && !passwordCheckBox.isSelected())
        {
            passwordSignUp.setForeground(Color.BLACK);
            passwordSignUp.setEchoChar('*');
            passwordSignUp.setText("");
            passwordSignUp.setEditable(true);
        }
        else if (e.getSource() == passwordSignUp && passwordSignUp.getForeground() == Color.GRAY && passwordCheckBox.isSelected())
        {
            passwordSignUp.setForeground(Color.BLACK);
            passwordSignUp.setEchoChar((char) 0);
            passwordSignUp.setText("");
            passwordSignUp.setEditable(true);
        }
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

        if (e.getSource() == usernameLogin && usernameLogin.getForeground() == Color.GRAY)
        {
            usernameLogin.setForeground(Color.BLACK);
            usernameLogin.setText("");
            usernameLogin.setEditable(true);
        }
        else if (usernameLogin.getText().isEmpty())
        {
            usernameLogin.setForeground(Color.GRAY);
            usernameLogin.setText("Enter your username here");
            usernameLogin.setEditable(false);
        }

        if (e.getSource() == passwordLogin && passwordLogin.getForeground() == Color.GRAY)
        {
            passwordLogin.setForeground(Color.BLACK);
            passwordLogin.setEchoChar('*');
            passwordLogin.setText("");
            passwordLogin.setEditable(true);
        }
        else if (String.valueOf(passwordLogin.getPassword()).isEmpty())
        {
            passwordLogin.setForeground(Color.GRAY);
            passwordLogin.setEchoChar((char) 0);
            passwordLogin.setText("Enter your password here");
            passwordLogin.setEditable(false);
        }

        /*************************************** BUTTON FUNCTIONALITIES ***************************************/
        if (e.getSource() == signUpButton)
        {
            currentScreen = "signUp";

            JLabel usernameText = new JLabel();
            usernameText.setText("Username:");
            usernameText.setFont(new Font("Arial", Font.BOLD, 40));
            usernameText.setPreferredSize(new Dimension(225, 100));
            usernameText.setOpaque(false);
            usernameText.setVisible(true);

            JLabel passwordText = new JLabel();
            passwordText.setText("Password:");
            passwordText.setFont(new Font("Arial", Font.BOLD, 40));
            passwordText.setPreferredSize(new Dimension(225, 50));
            passwordText.setOpaque(false);
            passwordText.setVisible(true);

            usernameSignUp.setText("Enter your username here");
            usernameSignUp.setForeground(Color.GRAY);
            usernameSignUp.setEditable(false);

            passwordSignUp.setText("Enter your password here");
            passwordSignUp.setEchoChar((char) 0);
            passwordSignUp.setForeground(Color.GRAY);
            passwordSignUp.setEditable(false);

            signUpPanel.setVisible(true);
            signUpPanel.setBackground(frame.getContentPane().getBackground());

            west.add(usernameText); // .add does not duplicate the same component
            west.add(passwordText);
            signUpButton.setVisible(false);
            loginButton.setVisible(false);
            signUpPanel.add(usernameSignUp);
            signUpPanel.add(passwordSignUp);
            center.add(signUpPanel);
            south.add(returnButton);
            south.add(submitButton);
            east.add(passwordCheckBox);

            returnButton.setVisible(true);
            submitButton.setVisible(true);
            passwordCheckBox.setVisible(true);

            // 3 because layout automatically positions text, but screen is large without much on it
            // so text is far apart
            // so 3 makes text more "tight"
            // GridLayout also takes into consideration every component, visible or not
            west.setLayout(new GridLayout(3, 1));
            center.setLayout(new FlowLayout(FlowLayout.LEFT));
            signUpPanel.setLayout(new GridLayout(2, 1));
            south.setLayout(new FlowLayout(FlowLayout.CENTER));

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

        if (e.getSource() == loginButton)
        {
            currentScreen = "login";

            JLabel usernameText = new JLabel();
            usernameText.setText("Username:");
            usernameText.setFont(new Font("Arial", Font.BOLD, 40));
            usernameText.setPreferredSize(new Dimension(225, 100));
            usernameText.setOpaque(false);
            usernameText.setVisible(true);

            JLabel passwordText = new JLabel();
            passwordText.setText("Password:");
            passwordText.setFont(new Font("Arial", Font.BOLD, 40));
            passwordText.setPreferredSize(new Dimension(225, 50));
            passwordText.setOpaque(false);
            passwordText.setVisible(true);

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
            signUpButton.setVisible(false);
            loginButton.setVisible(false);
            loginPanel.add(usernameLogin);
            loginPanel.add(passwordLogin);
            center.add(loginPanel);
            south.add(returnButton);
            south.add(submitButton);

            returnButton.setVisible(true);
            submitButton.setVisible(true);
            loginPanel.setVisible(true);

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
        }

        if (e.getSource() == returnButton)
        {
            if (currentScreen.equals("signUp") || currentScreen.equals("login") || currentScreen.equals("signUpSuccess"))
            {
                // remove local variables of username and password textFields
                for (int i = 0; i < west.getComponentCount(); i++)
                {
                    JLabel temp;

                    if (west.getComponent(i) instanceof JLabel)
                    {
                        temp = (JLabel) west.getComponent(i);

                        if (temp.getText().equals("Username:") || (temp.getText().equals("Password:")))
                        {
                            west.remove(west.getComponent(i));

                            // since removing something shifts "loop" to the right
                            --i;
                        }
                    }
                }
            }

            if (currentScreen.equals("signUp"))
            {
                north.setBorder(BorderFactory.createEmptyBorder(0, 300, 100, 0));
                center.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
                west.setBorder(null);
                east.setBorder(null);
                south.setBorder(null);

                signUpPanel.setVisible(false);
                returnButton.setVisible(false);
                passwordCheckBox.setVisible(false);
                submitButton.setVisible(false);

                signUpButton.setVisible(true);
                loginButton.setVisible(true);
                center.setLayout(null);

                currentScreen = "main";

                signUpButton.setBackground(DEFAULT_COLOR);
                loginButton.setBackground(DEFAULT_COLOR);
            }
            else if (currentScreen.equals("login"))
            {
                north.setBorder(BorderFactory.createEmptyBorder(0, 300, 100, 0));
                center.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
                west.setBorder(null);
                south.setBorder(null);

                loginPanel.setVisible(false);
                returnButton.setVisible(false);
                submitButton.setVisible(false);

                signUpButton.setVisible(true);
                loginButton.setVisible(true);
                center.setLayout(null);

                currentScreen = "main";

                signUpButton.setBackground(DEFAULT_COLOR);
                loginButton.setBackground(DEFAULT_COLOR);
            }
            else if (currentScreen.equals("signUpSuccess"))
            {
                returnButton.setVisible(false);
                loginButton.setVisible(true);
                signUpButton.setVisible(true);
                returnButton.setText("Return");

                north.setBorder(BorderFactory.createEmptyBorder(0, 300, 100, 0));
                center.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

                currentScreen = "main";

                signUpButton.setBackground(DEFAULT_COLOR);
                loginButton.setBackground(DEFAULT_COLOR);
            }
            else if (currentScreen.equals("bookScreen"))
            {
                // TODO: RETURN TO MAIN SCREEN
            }
            else if (currentScreen.equals("accountScreen"))
            {
                // TODO: RETURN TO LIBRARY BOOK SELECTION SCREEN
            }
            else if (currentScreen.equals("inventoryScreen"))
            {
                // TODO: RETURN TO ACCOUNT SCREEN
            }
            else if (currentScreen.equals("waitlistedBooksScreen"))
            {
                // TODO: RETURN TO ACCOUNT SCREEN
            }
        }

        if (e.getSource() == submitButton)
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

                center.add(signUpButton);
                center.add(loginButton);
                returnButton.setVisible(true);
                returnButton.setText("Home");
                center.setLayout(null);

                // TODO: actually check the username for duplicates and check is password is empty
                currentScreen = "signUpSuccess";
            }
            else if (currentScreen.equals("login"))
            {
                currentScreen = "bookScreen";

                north.setBorder(BorderFactory.createEmptyBorder(0, 320, 0, 0));
                center.setBorder(null);
                west.setBorder(null);
                east.setBorder(null);
                south.setBorder(null);

                loginPanel.setVisible(false);
                returnButton.setVisible(false);
                passwordCheckBox.setVisible(false);
                submitButton.setVisible(false);
                scrollPane.setVisible(true);

                booksPanel.setBorder(LineBorder.createBlackLineBorder());
                booksPanel.setVisible(true);

                GridBagConstraints panelConstraints = new GridBagConstraints(); // GRIDBAG MAKES NO SENSE
                panelConstraints.gridx = 1; // lower numbers = left side, higher numbers = right side?
                panelConstraints.gridy = 1;
                frame.setLayout(new GridBagLayout());
                frame.add(scrollPane, panelConstraints);

                // TODO: REMINDER TO POSSIBLY REMOVE THIS LATER
                JLabel availableBooksText = new JLabel();
                availableBooksText.setText("Available Books");
                availableBooksText.setFont(new Font("Cambria", Font.BOLD, 40));
                availableBooksText.setPreferredSize(new Dimension(625, 75));
                availableBooksText.setOpaque(false);
                north.add(availableBooksText);

                panelConstraints.gridx = 1;
                panelConstraints.gridy = 0;
                frame.add(north, panelConstraints); // replaces existing north
                north.setPreferredSize(new Dimension(650, 175)); // TODO: CHANGE NORTH SIZE WHEN GO BACK TO MAIN SCREENS

                // enlarges icon
                ImageIcon imageIcon = (ImageIcon) UIManager.getIcon("OptionPane.warningIcon"); // load the image to a imageIcon
                Image image = imageIcon.getImage(); // transform it
                Image newImage = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                imageIcon = new ImageIcon(newImage);  // transform it back

                panelConstraints.ipadx = 0;

                // TODO: ADD ACTUAL BOOKS
                // TODO: ADD WAIT LIST WHEN PRESS BOOK IF ALREADY BORROWED
                // probably want to get rid of this for loop at some point
                for (int i = 0; i < 25; i++)
                {
                    JButton temp = new JButton();
                    temp.setText("<html>Book " + i + "<br>Author: Bob</html>");
                    temp.setFont(new Font("Calibri", Font.BOLD, 25));
                    temp.setHorizontalAlignment(SwingConstants.LEFT);
                    temp.setIcon(imageIcon);
                    temp.setPreferredSize(new Dimension(450, 100));
                    booksPanel.add(temp);
                }

                booksPanel.setLayout(new GridLayout(booksPanel.getComponentCount(), 1));

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

            for (int i = 0; i < west.getComponentCount(); i++)
            {
                JLabel temp;

                if (west.getComponent(i) instanceof JLabel)
                {
                    temp = (JLabel) west.getComponent(i);

                    if (temp.getText().equals("Username:") || (temp.getText().equals("Password:")))
                    {
                        west.remove(west.getComponent(i));

                        // since removing something shifts "loop" to the right
                        --i;
                    }
                }
            }

            returnButton.setBackground(DEFAULT_COLOR);
        }

        if (e.getSource() == accountButton)
        {
            currentScreen = "accountScreen";

            // TODO: ADD BACK LOGO/WELCOME TEXT AND AVAILABLE BOOKS TEXT
            for (int i = 0; i < north.getComponentCount(); i++)
            {
                JLabel temp;

                if (north.getComponent(i) instanceof JLabel)
                {
                    temp = (JLabel) north.getComponent(i);

                    if (temp.getText().equals("Tully Library") || (temp.getText().equals("Available Books")))
                    {
                        north.remove(north.getComponent(i));

                        // since removing something shifts "loop" to the right
                        --i;
                    }
                }
            }

            north.setPreferredSize(new Dimension(1000, 300));

            // TODO: ADD USERNAME
            JLabel welcomeUserText = new JLabel();
            welcomeUserText.setText("Welcome, " + "USERNAME_HERE" + "!");
            welcomeUserText.setFont(new Font("Arial", Font.BOLD, 40));
            welcomeUserText.setPreferredSize(new Dimension(950, 75));
            welcomeUserText.setOpaque(false);
            welcomeUserText.setVisible(true);
            welcomeUserText.setHorizontalTextPosition(SwingConstants.LEFT);
            welcomeUserText.setName("welcomeText"); // ID

            // TODO: ADD ACTUAL FEES
            JLabel currentFees = new JLabel();
            currentFees.setText("Fees: " + "999" + "$");
            currentFees.setFont(new Font("Arial", Font.BOLD, 40));
            currentFees.setPreferredSize(new Dimension(950, 75));
            currentFees.setOpaque(false);
            currentFees.setVisible(true);
            currentFees.setHorizontalTextPosition(SwingConstants.LEFT);

            accountButton.setVisible(false);
            booksPanel.setVisible(false);
            scrollPane.setVisible(false);
            north.setVisible(true);
            payFeesButton.setVisible(true);
            waitListListButton.setVisible(true);
            bookInventoryButton.setVisible(true);

            frame.getContentPane().setLayout(new BorderLayout());
            frame.add(west, BorderLayout.WEST);
            frame.add(north, BorderLayout.NORTH);
            frame.add(center, BorderLayout.CENTER);
            frame.add(south, BorderLayout.SOUTH);
            frame.add(east, BorderLayout.EAST);

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

            north.setBackground(null);
            center.setBackground(null);
            west.setBorder(null);
            east.setBorder(null);
            south.setBorder(null);

            returnButton.setBackground(DEFAULT_COLOR);
        }

        // TODO: PAY FEES & RESET FEE'S TEXT TO 0
        if (e.getSource() == payFeesButton)
        {

        }

        if (e.getSource() == bookInventoryButton)
        {
            currentScreen = "inventoryScreen";

            for (int i = 0; i < north.getComponentCount(); i++)
            {
                JLabel tempLabel;
                JPanel tempFrame;

                if (north.getComponent(i) instanceof JLabel)
                {
                    tempLabel = (JLabel) north.getComponent(i);

                    if (tempLabel.getName().equals("welcomeText"))
                    {
                        north.remove(north.getComponent(i));

                        // since removing something shifts "loop" to the right
                        --i;
                    }
                }
                else if (north.getComponent(i) instanceof JPanel)
                {
                    tempFrame = (JPanel) north.getComponent(i);

                    System.out.println(tempFrame.getName());

                    if (tempFrame.getName().equals("feePanel"))
                    {
                        north.remove(north.getComponent(i));

                        // since removing something shifts "loop" to the right
                        --i;
                    }
                }
            }

            payFeesButton.setVisible(false);
            bookInventoryButton.setVisible(false);
            waitListListButton.setVisible(false);
            returnButton.setVisible(true);

            north.setBorder(null);
            center.setBorder(null);
            west.setBorder(null);
            east.setBorder(null);
            south.setBorder(null);

            // TODO: SET FRAMES VISIBLE AGAIN WHEN GO BACK
            north.setVisible(true);
            center.setVisible(false);
            west.setVisible(false);
            east.setVisible(false);
            south.setVisible(true);

            GridBagConstraints panelConstraints = new GridBagConstraints();
            panelConstraints.gridx = 1;
            panelConstraints.gridy = 1;
            frame.setLayout(new GridBagLayout());
            frame.add(inventoryOfBooks, panelConstraints);

            // TODO: REMINDER TO POSSIBLY REMOVE THIS LATER
            JLabel inventoryText = new JLabel();
            inventoryText.setText("Borrowed Books");
            inventoryText.setFont(new Font("Cambria", Font.BOLD, 40));
            inventoryText.setPreferredSize(new Dimension(625, 75));
            inventoryText.setOpaque(false);
            north.add(inventoryText);

            panelConstraints.gridx = 1;
            panelConstraints.gridy = 0;
            frame.add(north, panelConstraints); // replaces existing north
            north.setPreferredSize(new Dimension(650, 75));

            north.setBorder(BorderFactory.createEmptyBorder(0, 325, 0, 0));

            // enlarges icon
            ImageIcon imageIcon = (ImageIcon) UIManager.getIcon("OptionPane.warningIcon"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            Image newImage = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            imageIcon = new ImageIcon(newImage);  // transform it back

            panelConstraints.ipadx = 0;

            // TODO: ADD ACTUAL BOOKS
            // probably want to get rid of this for loop at some point
            for (int i = 0; i < 10; i++)
            {
                JButton temp = new JButton();
                temp.setText("<html>Book " + i + "<br>Author: Joe</html>");
                temp.setFont(new Font("Calibri", Font.BOLD, 25));
                temp.setHorizontalAlignment(SwingConstants.LEFT);
                temp.setIcon(imageIcon);
                temp.setPreferredSize(new Dimension(450, 100));
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

        if (e.getSource() == waitListListButton)
        {
            currentScreen = "waitlistedBooksScreen";

            for (int i = 0; i < north.getComponentCount(); i++)
            {
                JLabel tempLabel;
                JPanel tempFrame;

                if (north.getComponent(i) instanceof JLabel)
                {
                    tempLabel = (JLabel) north.getComponent(i);

                    if (tempLabel.getName().equals("welcomeText"))
                    {
                        north.remove(north.getComponent(i));

                        // since removing something shifts "loop" to the right
                        --i;
                    }
                }
                else if (north.getComponent(i) instanceof JPanel)
                {
                    tempFrame = (JPanel) north.getComponent(i);

                    System.out.println(tempFrame.getName());

                    if (tempFrame.getName().equals("feePanel"))
                    {
                        north.remove(north.getComponent(i));

                        // since removing something shifts "loop" to the right
                        --i;
                    }
                }
            }

            payFeesButton.setVisible(false);
            bookInventoryButton.setVisible(false);
            waitListListButton.setVisible(false);
            returnButton.setVisible(true);

            north.setBorder(null);
            center.setBorder(null);
            west.setBorder(null);
            east.setBorder(null);
            south.setBorder(null);

            // TODO: SET FRAMES VISIBLE AGAIN WHEN GO BACK
            north.setVisible(true);
            center.setVisible(false);
            west.setVisible(false);
            east.setVisible(false);
            south.setVisible(true);

            GridBagConstraints panelConstraints = new GridBagConstraints();
            panelConstraints.gridx = 1;
            panelConstraints.gridy = 1;
            frame.setLayout(new GridBagLayout());
            frame.add(waitListedBooks, panelConstraints);

            // TODO: REMINDER TO POSSIBLY REMOVE THIS LATER
            JLabel waitlistText = new JLabel();
            waitlistText.setText("Waitlisted Books");
            waitlistText.setFont(new Font("Cambria", Font.BOLD, 40));
            waitlistText.setPreferredSize(new Dimension(625, 75));
            waitlistText.setOpaque(false);
            north.add(waitlistText);

            panelConstraints.gridx = 1;
            panelConstraints.gridy = 0;
            frame.add(north, panelConstraints); // replaces existing north
            north.setPreferredSize(new Dimension(650, 75));

            north.setBorder(BorderFactory.createEmptyBorder(0, 325, 0, 0));

            // enlarges icon
            ImageIcon imageIcon = (ImageIcon) UIManager.getIcon("OptionPane.warningIcon"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            Image newImage = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            imageIcon = new ImageIcon(newImage);  // transform it back

            panelConstraints.ipadx = 0;

            // TODO: ADD ACTUAL BOOKS
            // probably want to get rid of this for loop at some point
            for (int i = 0; i < 5; i++)
            {
                JButton temp = new JButton();
                temp.setText("<html>Book " + i + "<br>Author: Jill</html>");
                temp.setFont(new Font("Calibri", Font.BOLD, 25));
                temp.setHorizontalAlignment(SwingConstants.LEFT);
                temp.setIcon(imageIcon);
                temp.setPreferredSize(new Dimension(450, 100));
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

        // repaints everything
        frame.validate();
        frame.repaint();
        System.out.println(currentScreen);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
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

        if (e.getSource() == passwordCheckBox)
        {
            passwordCheckBox.setBackground(new Color(35, 48, 84));
            passwordCheckBox.setToolTipText("Click me to show the password!");
            ToolTipManager.sharedInstance().setDismissDelay(10000); // 10 seconds
        }

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
            ToolTipManager.sharedInstance().setDismissDelay(30000); // 30 seconds
        }

        frame.validate();
        frame.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e)
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

        if (e.getSource() == passwordCheckBox)
        {
            passwordCheckBox.setBackground(new Color(73, 84, 114));
            ToolTipManager.sharedInstance().setDismissDelay(DEFAULT_TIMEOUT_TIME);
        }

        frame.validate();
        frame.repaint();
    }

    public static void main(String[] args)
    {
        Library test = new Library();
    }
}

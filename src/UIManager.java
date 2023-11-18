import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.*;

public class UIManager implements MouseListener
{
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
    private JLabel welcomeText;
    private JLabel waitlistText;
    private JLabel inventoryText;
    private JLabel availableBooksText;
    private JLabel welcomeUserText;
    private JLabel usernameText;
    private JLabel passwordText;
    private JLabel signUpSuccessText;
    private JLabel currentFees;
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

    public void createMain()
    {
        currentScreen = "main";

        north.add(welcomeText);
        center.add(signUpButton);
        center.add(loginButton);

        welcomeText.setVisible(true);
        signUpButton.setVisible(true);
        loginButton.setVisible(true);

        center.setLayout(new FlowLayout(FlowLayout.CENTER));
        north.setLayout(new FlowLayout(FlowLayout.CENTER));
        west.setLayout(new FlowLayout(FlowLayout.CENTER));
        east.setLayout(new FlowLayout(FlowLayout.CENTER));
        south.setLayout(new FlowLayout(FlowLayout.CENTER));

        north.setBorder(BorderFactory.createEmptyBorder(0, 300, 150, 0));
        center.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
    }

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

    public void createSignUpSuccess()
    {
        currentScreen = "signUpSuccess";

        returnButton.setText("Home");

        signUpSuccessText.setText("<html>" + "&emsp&emsp&emsp&emsp&emsp&emsp&emsp Successfully signed up!" + "</html>");
        signUpSuccessText.setPreferredSize(new Dimension(1000, 400));

        center.add(signUpSuccessText);

        signUpSuccessText.setVisible(true);
        returnButton.setVisible(true);
        west.setVisible(false);
        center.setLayout(new FlowLayout(FlowLayout.LEFT));

        north.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 0));
    }

    public void createErrorPopup()
    {
        StringBuilder errors = new StringBuilder();

        errors.append("<html>");

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
                errors.append(" <br> ");
            }
        }

        errors.append("</html>");

        JOptionPane.showMessageDialog(frame, errors.toString());
    }

    public boolean checkSignUpFields()
    {
        //Here we can make sure that username and password exist, and that the password fits the requirements.
        String usertxt = usernameSignUp.getText();
        String pass = String.valueOf(passwordSignUp.getPassword()); //.getText() is depreciated
        listOfErrors = new ArrayList<String>();

        //one field is empty
        // or both fields are empty
        if ((usertxt.equals("Enter your username here") || usertxt.isEmpty()) && (pass.equals("Enter your password here") || pass.isEmpty())) {
            System.out.println("bruh");
            listOfErrors.add("emptyUser");
            listOfErrors.add("emptyPass");
            return false;
        }
        else if (usertxt.equals("Enter your username here") || usertxt.isEmpty()) {
            System.out.println("bruh");
            listOfErrors.add("emptyUser");
            return false;
        }
        else if (pass.equals("Enter your password here") || pass.isEmpty()) {
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

        GridBagConstraints panelConstraints = new GridBagConstraints(); // GRIDBAG MAKES NO SENSE
        panelConstraints.gridx = 1; // lower numbers = left side, higher numbers = right side?
        panelConstraints.gridy = 1;
        frame.setLayout(new GridBagLayout());
        frame.add(scrollPane, panelConstraints);

        availableBooksText.setVisible(true);
        north.add(availableBooksText);

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

    public void createAccountScreen()
    {
        currentScreen = "accountScreen";

        north.setPreferredSize(new Dimension(1000, 300));

        System.out.println(users.size());

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

    public boolean checkLoginFields()
    {
        //verify if this person's login credentials are correct.
        String usertxt = usernameLogin.getText();
        String pass = passwordLogin.getText();
        listOfErrors = new ArrayList<String>();

        //one field is empty
        // or both fields are empty
        if ((usertxt.equals("Enter your username here") || usertxt.isEmpty()) && (pass.equals("Enter your password here") || pass.isEmpty())) {
            System.out.println("bruh");
            listOfErrors.add("emptyUser");
            listOfErrors.add("emptyPass");
            return false;
        }
        else if (usertxt.equals("Enter your username here") || usertxt.isEmpty()) {
            System.out.println("bruh");
            listOfErrors.add("emptyUser");
            return false;
        }
        else if (pass.equals("Enter your password here") || pass.isEmpty()) {
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

    public void checkUserFieldsClickable(EventObject e)
    {
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
    }

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
    }

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

        if (e.getSource() == passwordCheckBox)
        {
            passwordCheckBox.setBackground(new Color(73, 84, 114));
            ToolTipManager.sharedInstance().setDismissDelay(DEFAULT_TIMEOUT_TIME);
        }
    }
}

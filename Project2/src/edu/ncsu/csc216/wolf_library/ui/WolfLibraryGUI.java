package edu.ncsu.csc216.wolf_library.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.*;

import edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem;
import edu.ncsu.csc216.wolf_library.lending_system.LendingManager;
import edu.ncsu.csc216.wolf_library.util.Constants;

/**
 * A user interface for a book lending system.
 * 
 * @author Jo Perry
 */
public class WolfLibraryGUI extends JFrame implements ActionListener {
    /** ID for serialization */
    private static final long serialVersionUID = 1L;
    
    // Parameters for component sizes and spacings 
    private static final int FRAME_WIDTH = 500; 
    private static final int FRAME_HEIGHT = 600; 
    private static final int NAME_WIDTH = 25;
    private static final int INVENTORY_LENGTH = 450; 
    private static final int QUEUE_LENGTH = 300;
    private static final int CHECKED_OUT_LENGTH = 120;
    private static final int VERTICAL_SPACER = 5;
    private static final int TOP_PAD = 5;
    private static final int LEFT_PAD = 10;
    private static final int RIGHT_PAD = 10;
    private static final int BOTTOM_PAD = 10;
    
    // Panel and window titles
    private static final String INVENTORY_TITLE = "Library Inventory";
    private static final String RESERVEQ_TITLE = "My Reserve Queue";
    private static final String CHECKED_OUT_TITLE = "My Checked Out Books";
    private static final String WINDOW_TITLE = "Wolf Library System";

    // Button and text field strings
    private static final String BROWSE = "Browse";
    private static final String LOGOUT = "Logout";
    private static final String ADMIN = "Admin";
    private static final String QUEUE = "Show My Books";
    private static final String MOVE = "Move Selected Book Up";
    private static final String RETURN = "Return Selected Book";
    private static final String REMOVE = "Remove Selected Book";
    private static final String LOGIN = "Login";
    private static final String QUIT = "Quit";
    private static final String ADD = "Reserve Selected Book";
    private static final String ADD_PATRON = "Add New Patron Account";
    private static final String CANCEL = "Cancel Account";
    private static final String WELCOME = "Welcome";
    private static final String PATRON = "Patron";
    private static final String NONE = "None";
    private static final String ERROR = "Error";

    // Buttons and combo box
    private JButton btnBrowse = new JButton(BROWSE);
    private JButton btnShowQueues = new JButton(QUEUE);
    private JButton btnAddToReserves = new JButton(ADD);
    private JButton btnMove = new JButton(MOVE);
    private JButton btnRemove = new JButton(REMOVE);
    private JButton btnReturn = new JButton(RETURN);   
    private JButton btnLogin = new JButton(LOGIN);
    private JButton btnQuit = new JButton(QUIT);
    private JButton btnLogout = new JButton(LOGOUT);
    private JButton btnAddNewPatron = new JButton(ADD_PATRON);
    private JButton btnCancelAcct = new JButton(CANCEL);

    // Labels and separator
    private JLabel lblAddedToQueue = new JLabel(" "); 
    private JLabel lblUserName = new JLabel("Username: ");
    private JLabel lblPassword = new JLabel("Password: ");
    private JLabel lblWelcome = new JLabel("");

    // Text fields;
    private JTextField txtUserName = new JTextField(NAME_WIDTH);
    private JPasswordField pwdPassword = new JPasswordField(NAME_WIDTH);

    // Default list models for the scrollable lists
    private DefaultListModel<String> dlmInventory = new DefaultListModel<String>();
    private DefaultListModel<String> dlmReserveQueue = new DefaultListModel<String>();
    private DefaultListModel<String> dlmCheckedOut = new DefaultListModel<String>();  
    
    // Scrollable lists
    private JList<String> lstInventory = new JList<String>(dlmInventory);
    private JList<String> lstReserveQueue = new JList<String>(dlmReserveQueue);
    private JList<String> lstCheckedOut = new JList<String>(dlmCheckedOut);   
    private JScrollPane scpInventory = new JScrollPane(lstInventory);
    private JScrollPane scpReserveQueue = new JScrollPane(lstReserveQueue);
    private JScrollPane scpCheckedOut = new JScrollPane(lstCheckedOut);

    // Organizational and alignment boxes and panels
    private JPanel pnlCenter = new JPanel(); 
    private JPanel pnlUserButtons = new JPanel(new FlowLayout());
    private JPanel pnlPatronButtons = new JPanel(new FlowLayout());
    private JPanel pnlAdminButtons = new JPanel(new FlowLayout());
    private JPanel pnlButtons = new JPanel();
    private JPanel pnlNoButtons = new JPanel();
    private JPanel pnlInventoryTop = new JPanel();
    private JPanel pnlAddButton = new JPanel(new FlowLayout());
    private JPanel pnlAddMessage = new JPanel(new FlowLayout());
    private JPanel pnlReturnButton = new JPanel(new FlowLayout());
    private JPanel pnlQueueInstr = new JPanel(new FlowLayout());
    private JPanel pnlQueue = new JPanel(new BorderLayout());
    private JPanel pnlSeparator = new JPanel();
    private JPanel pnlLogin = new JPanel();
    private JPanel pnlLoginInfo = new JPanel(new FlowLayout());
    private JPanel pnlUserName = new JPanel(new FlowLayout());
    private JPanel pnlUserPassword = new JPanel(new FlowLayout());
    private JPanel pnlWelcome = new JPanel();
    private Box boxInventory = Box.createVerticalBox();   
    private Box boxQueue = Box.createVerticalBox();
    private CardLayout mainCardLayout = new CardLayout(1, 1);
    private CardLayout buttonCardLayout = new CardLayout(1, 1);

    // Main window
    private Container mainWindow = getContentPane();

    // Backend model
    private transient LendingManager library;


    /**
     * Constructor for WolfLibraryGUI.  Creates the LendingManager model.
     * The GUI is initialized and set visible.
     * @param filename name of file that initializes the inventory
     * @throws IllegalArgumentException if there is an error reading the file.
     */
    public WolfLibraryGUI(String filename) {
        try {
            if (filename == null) {
                String userPickFilename = null;
                JFileChooser fc = new JFileChooser(".");
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnVal = fc.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    userPickFilename = fc.getSelectedFile().getName();
                }
                library = new LibraryLendingSystem(userPickFilename);
            } else {
                library = new LibraryLendingSystem(filename);
            }
            populatePatronAccounts();
            initializeUI();
            this.setVisible(true);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles all actions for the various buttons for the GUI.
     * @param ae the button click
     */
    public void actionPerformed(ActionEvent ae){
        // Browse titles button
        if (ae.getSource().equals(btnBrowse)) {
            mainCardLayout.show(pnlCenter, BROWSE);
            lblAddedToQueue.setText(" ");
            refreshInventory();
        }
        // Show my reserve queue button
        if (ae.getSource().equals(btnShowQueues)) {
            mainCardLayout.show(pnlCenter, QUEUE);
            this.refreshQueues();
        }
        // Add to my reserve queue button
        if (ae.getSource().equals(btnAddToReserves)){
            int k = lstInventory.getSelectedIndex();
            if (k >= 0) {
                library.reserveItem(k);
                lblAddedToQueue.setText("Reserved: " + (String) dlmInventory.get(k));                   
                refreshQueues();
                refreshInventory();
            }
        }
        // Move up in reserve queue button
        if (ae.getSource().equals(btnMove)){
            int k = lstReserveQueue.getSelectedIndex();
            if (k >= 0) {
                library.reserveMoveAheadOne(k);
                refreshQueues();
            }
        }
        // Remove selected item button
        if (ae.getSource().equals(btnRemove)){
            int k = lstReserveQueue.getSelectedIndex();
            if (k >= 0) 
                library.removeSelectedFromReserves(k);
            refreshQueues();
        }
        // Return selected item button  
        if (ae.getSource().equals(btnReturn)){
            int k = lstCheckedOut.getSelectedIndex();
            if (k >= 0){
                library.returnItem(k);
                refreshQueues();
                refreshInventory();
            }               
        }
        
        // General User functions
        // Quit button
        if (ae.getSource().equals(btnQuit)) {
            stopExecution();
        }
        // Logout
        if (ae.getSource().equals(btnLogout)) {
            mainCardLayout.show(pnlCenter, ADMIN);
            library.logout();
            enableDisable();
        }
        // Login
        if (ae.getSource().equals(btnLogin)) {
            try {
                library.login(txtUserName.getText(), new String(pwdPassword.getPassword()));
                enableDisable();
                txtUserName.setText("");
                pwdPassword.setText("");
                mainCardLayout.show(pnlCenter, WELCOME);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
            }

        }
        
        // Administrative functions
        // Add new patron
        if (ae.getSource().equals(btnAddNewPatron)) {
            addNewPatron();
        }
        // Cancel Account
        if (ae.getSource().equals(btnCancelAcct)) {
            try {
                library.cancelAccount(cancelInfo());    
            }
            catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Private Method - populates the patron accounts with two dummy patrons. It is
     *    useful for testing.
     */
    private void populatePatronAccounts() {
        library.login("admin", "admin");
        library.addNewPatron("patron1@ncsu.edu", "pw1", 3);
        library.addNewPatron("patron2@ncsu.edu", "pw2", 2);
        library.logout();
        library.login("patron1@ncsu.edu", "pw1");
        library.reserveItem(0);
        library.reserveItem(1);
        library.reserveItem(2);
        library.reserveItem(3);
        library.logout();   
    }

    /**
     * Private Method - creates the user interface.
     */
    private void initializeUI() {
        //  Initialize the main frame parameters.
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(WINDOW_TITLE);

        // Set the list contents and behaviors.
        loadModel(lstInventory, dlmInventory, library.showInventory());
        lstInventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstReserveQueue.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstCheckedOut.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Include all visual components.
        setBordersAndPanels();
        mainWindow.add(pnlButtons, BorderLayout.NORTH);
        mainWindow.add(pnlCenter, BorderLayout.CENTER);
        mainWindow.add(pnlUserButtons, BorderLayout.SOUTH);

        // Enable buttons to respond to events.
        addListeners();

        enableDisable();
        addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                stopExecution();
            }
        });         
    }

    /**
     * Private method -- Adds action listeners to all the buttons.
     */
    private void addListeners() {
        btnQuit.addActionListener(this);
        btnBrowse.addActionListener(this);
        btnShowQueues.addActionListener(this);
        btnAddToReserves.addActionListener(this);
        btnMove.addActionListener(this);
        btnReturn.addActionListener(this);
        btnRemove.addActionListener(this);
        btnLogout.addActionListener(this);
        btnLogin.addActionListener(this);
        btnCancelAcct.addActionListener(this);
        btnAddNewPatron.addActionListener(this);
    }

    /**
     * Private Method - adds most of the components to the interface and formats them appropriately.
     */
    private void setBordersAndPanels() { 
        // Set up user options to Logout and Quit
        pnlUserButtons.add(btnLogout);
        pnlUserButtons.add(btnQuit);
        
        // Set up panel of patron buttons   
        pnlPatronButtons.add(btnBrowse);
        pnlPatronButtons.add(btnShowQueues);

        // Set up panel of admin buttons
        pnlAdminButtons.add(btnAddNewPatron);
        pnlAdminButtons.add(btnCancelAcct);
        
        pnlButtons.setLayout(buttonCardLayout);
        pnlButtons.add(pnlPatronButtons, PATRON);
        pnlButtons.add(pnlAdminButtons, ADMIN);
        pnlButtons.add(pnlNoButtons, NONE);

        // Set the borders for scrolling lists
        Font fontTitles = new Font(mainWindow.getFont().getName(), Font.BOLD, mainWindow.getFont().getSize());      
        scpInventory.setBorder(BorderFactory.createTitledBorder(scpInventory.getBorder(), INVENTORY_TITLE,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, fontTitles));
        scpCheckedOut.setBorder(BorderFactory.createTitledBorder(scpCheckedOut.getBorder(), CHECKED_OUT_TITLE,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, fontTitles));
        scpReserveQueue.setBorder(BorderFactory.createTitledBorder(scpReserveQueue.getBorder(), RESERVEQ_TITLE,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, fontTitles));
        scpInventory.setPreferredSize(new Dimension(FRAME_WIDTH - LEFT_PAD - RIGHT_PAD, INVENTORY_LENGTH));
        scpCheckedOut.setPreferredSize(new Dimension(FRAME_WIDTH - LEFT_PAD - RIGHT_PAD, CHECKED_OUT_LENGTH));
        scpReserveQueue.setPreferredSize(new Dimension(FRAME_WIDTH - LEFT_PAD - RIGHT_PAD, QUEUE_LENGTH));

        // Set up the browsing, queue, and admin panels.
        setUpBrowsingPanel();
        setUpQueuePanel();
        setUpLoginPanel();
        setUpWelcomePanel();

        // Add browsing, queue, login, and welcome panels to the UI.
        pnlCenter.setLayout(mainCardLayout);
        pnlCenter.add(pnlLogin, ADMIN);
        pnlCenter.add(boxInventory, BROWSE);
        pnlCenter.add(boxQueue, QUEUE); 
        pnlCenter.add(pnlWelcome, WELCOME);
    }
    
    /**
     * Private method -- Sets up a welcome panel for after a user logs in.
     */
    private void setUpWelcomePanel() {
        pnlWelcome.setLayout(new BoxLayout(pnlWelcome, getDefaultCloseOperation()));
        lblWelcome.setFont(new Font("Lucida Grande", Font.ITALIC, 18));
        pnlWelcome.add(lblWelcome);
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblWelcome.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    }

    /**
     * Private method -- Sets up login panel.
     */
    private void setUpLoginPanel() {
        // The top of the Admin panel has a subpanel for login information.
        pnlLoginInfo.setLayout(new BoxLayout(pnlLoginInfo, BoxLayout.PAGE_AXIS));
        // The login info panel has two subpanels, one for username and the other for password.
        pnlUserName.setLayout(new FlowLayout());
        pnlUserPassword.setLayout(new FlowLayout());
        pnlUserName.add(lblUserName);
        pnlUserName.add(txtUserName);
        pnlUserPassword.add(lblPassword);
        pnlUserPassword.add(pwdPassword);
        pnlLoginInfo.add(Box.createVerticalStrut(40));
        pnlLoginInfo.add(pnlUserName);
        pnlLoginInfo.add(pnlUserPassword);
        pnlLoginInfo.add(btnLogin);
        pnlLoginInfo.setBorder(BorderFactory.createCompoundBorder( 
                (EmptyBorder) BorderFactory.createEmptyBorder(TOP_PAD, LEFT_PAD, BOTTOM_PAD, RIGHT_PAD),
                BorderFactory.createLineBorder(Color.red)));
        pnlLogin.setLayout(new BorderLayout());
        pnlLogin.add(pnlLoginInfo, BorderLayout.NORTH);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Private method -- Sets up queue face.
     */
    private void setUpQueuePanel() {
        // Add components for the queue and home list.
        boxQueue.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(TOP_PAD, LEFT_PAD, BOTTOM_PAD, RIGHT_PAD));    
        boxQueue.add(scpCheckedOut);
        boxQueue.add(Box.createVerticalStrut(VERTICAL_SPACER));
        boxQueue.add(pnlReturnButton);  
        boxQueue.add(pnlSeparator);
        boxQueue.add(Box.createVerticalStrut(VERTICAL_SPACER));
        boxQueue.add(scpReserveQueue);
        boxQueue.add(pnlQueueInstr);        
        pnlQueue.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(TOP_PAD, LEFT_PAD, BOTTOM_PAD, RIGHT_PAD));        
        pnlSeparator.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));       
        pnlQueueInstr.add(btnMove);
        pnlQueueInstr.add(btnRemove);
    }

    /**
     * Private method -- Sets up browsing face.
     */
    private void setUpBrowsingPanel() {
        // Add the browsing components.
        boxInventory.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(TOP_PAD, LEFT_PAD, BOTTOM_PAD, RIGHT_PAD));
        pnlAddButton.add(btnAddToReserves);
        pnlAddMessage.add(lblAddedToQueue);
        pnlReturnButton.add(btnReturn);     
        boxInventory.add(pnlInventoryTop);
        boxInventory.add(scpInventory);
        boxInventory.add(Box.createVerticalStrut(VERTICAL_SPACER));
        boxInventory.add(pnlAddButton);
        boxInventory.add(Box.createVerticalStrut(VERTICAL_SPACER));
        boxInventory.add(pnlAddMessage);
        boxInventory.add(Box.createVerticalStrut(VERTICAL_SPACER * 2));     
    }

    /**
     * Private method -- adds a new patron to the AccountManager through the 
     * LibraryManager.
     */
    private void addNewPatron() {
        String[] info = newPatronInfo();
        if (info != null) {
            try {
                library.addNewPatron(info[0], info[1], Integer.parseInt(info[2])); 
            }
            catch (Exception e) {
                //Note that the message displayed in the JOptionPane is the message passed
                //through the exception object.
                JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** 
     * Private method -- opens a dialog box for entry of new patron username and password.
     * @return An array of 2 strings, the first is the username and the second is the password.
     *    The strings are null if the user cancels out.
     */
    private String[] newPatronInfo( ) {
        String[] info = null;
        JPanel pnlCustInfo = new JPanel();
        JPanel pnlPatronName = new JPanel(new FlowLayout());
        JPanel pnlCustPassword = new JPanel(new FlowLayout());
        JPanel pnlPatronLimit = new JPanel(new FlowLayout());
        JTextField txtPatronName = new JTextField(NAME_WIDTH);
        JPasswordField pwdCustPassword = new JPasswordField(NAME_WIDTH);
        String[] limits = {"1", "2", "3", "4", "5"};
        JComboBox<String> cboLimit = new JComboBox<String>(limits);

        pnlCustInfo.setLayout(new BoxLayout(pnlCustInfo, BoxLayout.PAGE_AXIS));
        pnlPatronName.add(new JLabel("Username: "));
        pnlPatronName.add(txtPatronName);
        pnlCustPassword.add(new JLabel("Password: "));
        pnlCustPassword.add(pwdCustPassword);
        pnlPatronLimit.add(new JLabel("Book Limit: "));
        pnlPatronLimit.add(cboLimit);
        pnlCustInfo.add(pnlPatronName);
        pnlCustInfo.add(pnlCustPassword);
        pnlCustInfo.add(pnlPatronLimit);
        int result = JOptionPane.showConfirmDialog(null, pnlCustInfo, 
                "Enter the new patron's username and password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            info = new String[3];
            info[0] = txtPatronName.getText();
            info[1] = new String(pwdCustPassword.getPassword());
            info[2] = (String) cboLimit.getSelectedItem();
        }
        return info;
    }

    /**
     * Private method -- opens a dialog box for entry of account cancellation info.
     * @return username of account to cancel, or null if the user cancels out.
     */
    private String cancelInfo() {
        String acctResult = null;
        JPanel pnlCancel = new JPanel(new FlowLayout());
        pnlCancel.add(new JLabel("Username: "));
        JTextField txtCancelUserName = new JTextField(NAME_WIDTH);
        pnlCancel.add(txtCancelUserName);
        int result = JOptionPane.showConfirmDialog(null, pnlCancel, 
                "Enter the username for the account to be cancelled.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
            acctResult = txtCancelUserName.getText().trim();
        return acctResult;
    }

    /**
     * Private Method -- enables and disables administrative actions.
     */
    private void enableDisable() {
        String userId = library.getCurrentUserId();
        lblWelcome.setText(WELCOME + " " + userId);
        
        
        boolean adminLoggedIn = userId.equals(Constants.ADMIN);
        boolean patronLoggedIn = !userId.equals(Constants.ADMIN) && !userId.equals("");
        
        if (adminLoggedIn) {
            buttonCardLayout.show(pnlButtons, ADMIN);
        } else if (patronLoggedIn) {
            buttonCardLayout.show(pnlButtons, PATRON);
        } else {
            buttonCardLayout.show(pnlButtons, NONE);
        }
        btnQuit.setEnabled(adminLoggedIn);
        btnAddNewPatron.setEnabled(adminLoggedIn);
        btnCancelAcct.setEnabled(adminLoggedIn);
        btnQuit.setEnabled(adminLoggedIn);
        btnLogin.setEnabled(!adminLoggedIn && !patronLoggedIn);
        btnLogout.setEnabled(adminLoggedIn || patronLoggedIn);

        btnBrowse.setEnabled(patronLoggedIn);
        btnShowQueues.setEnabled(patronLoggedIn);
        btnAddToReserves.setEnabled(patronLoggedIn);
        btnMove.setEnabled(patronLoggedIn);
        btnRemove.setEnabled(patronLoggedIn);
        btnReturn.setEnabled(patronLoggedIn);
    }

    /**
     * Private Method - loads a list model from a string, using newline separators.
     * @param j the JList to refresh
     * @param m the default list model associated with j
     * @param info the String whose tokens initialize the default list model
     */
    private void loadModel(JList<String> j, DefaultListModel<String> m, String info) {
        Scanner s = new Scanner(info);
        while (s.hasNext()) {
            m.addElement(s.nextLine());
        }
        j.ensureIndexIsVisible(0);
        s.close();
    }

    /**
     * Private Method - refreshes lists on the QUEUE card.
     */   
    private void refreshQueues() {
        dlmCheckedOut.clear();
        dlmReserveQueue.clear();
        try { // Can load these lists only if a patron is logged in
            loadModel(lstCheckedOut, dlmCheckedOut, library.traverseCheckedOut());
            loadModel(lstReserveQueue, dlmReserveQueue, library.traverseReserveQueue());
        } catch (IllegalStateException e) {
            // Patron is not logged in
        }
    }

    /**
     * Private Method - refreshes list on the BROWSE card.
     */
    private void refreshInventory() {
        dlmInventory.clear();
        loadModel(lstInventory, dlmInventory, library.showInventory()); 
    }

    /**
     * Private Method - exits the program.
     */
    private static void stopExecution() {
        System.exit(0);
    }

    /**
     * Starts the program.  
     * @param args command line args
     */
    public static void main(String[] args) {
        try {
            if (args.length > 0)
                new WolfLibraryGUI(args[0]);
            else
                new WolfLibraryGUI(null);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Incorrect Inventory File Specified");
            stopExecution();
        }
    }

}

//**********  End of WolfLibraryGUI.java  **********/*
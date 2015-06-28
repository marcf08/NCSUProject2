/**
 * 
 */
package edu.ncsu.csc216.wolf_library.patron;

/**
 * The library account system defines the methods and users of the Wolf Library
 * implementation.
 * 
 * @author Marcus
 *
 */
public class LibraryAccountSystem implements AccountManager {
    /**
     * The account system uses a patron database to verify users.
     */
    private PatronDB patronList;
    /**
     * The admin user is the nested inner class in the account system. The user
     * has the responsibilities pertaining to managing the library system.
     */
    static Admin adminUser;
    /**
     * The current patron is the patron currently logged into the system.
     */
    private Patron currentPatron;
    /**
     * The admin logged in method checks to see whether the admin account is
     * logged in.
     */
    private boolean adminLoggedIn;
    /**
     * The patron logged in method checks to see whether a patron account is
     * logged in.
     */
    private boolean patronLoggedIn;

    /**
     * The constructor instantiates the library account system.
     */
    public LibraryAccountSystem() {
        // Instantiate the administrator statically
        adminUser = new Admin();
        patronList = new PatronDB();

    }

    /**
     * The admin static class creates the admin account that can add or remove
     * patron accounts.
     * 
     * @author Marcus
     *
     */
    class Admin extends User {
        public Admin() {
            // Per the specifications, the user has the
            // username and password set to admin
            super("admin", "admin");
        }
    }

    /**
     * The login method accepts login credentials from the dialog box. It then
     * sets the current patron to the one whose credentials were verified.
     */
    public void login(String id, String password) {
        if (patronLoggedIn) {
            throw new IllegalStateException();
        }
        if (adminLoggedIn) {
            throw new IllegalStateException();
        }
        try {
            if (id.equals("admin") && adminUser.verifyPassword("admin")) {
                adminLoggedIn = true;
            } else {
                currentPatron = patronList.verifyPatron(id, password);
                 this.patronLoggedIn = true;
            }
        } catch (IllegalArgumentException e) {
            //TODO: Handle?
        }
        
    }

    /**
     * The logout method clears the current patron and sets the logged in
     * boolean to false.
     */
    public void logout() {
        if (adminLoggedIn) {
            this.adminLoggedIn = false;
        } else {
            currentPatron = null;
            this.patronLoggedIn = false;
        }
        

    }

    /**
     * The get current method patron returns the patron currently logged in or
     * null (if no patron is currently logged in.)
     * @return the patron currently logged in
     */
    public Patron getCurrentPatron() {
        return this.currentPatron;
    }

    /**
     * The admin logged in method checks to see if the admin is 
     * currently logged in.
     * @return true if the admin is logged on and false otherwise
     */
    public boolean isAdminLoggedIn() {
        return this.adminLoggedIn;
    }

    /**
     * The is patron logged in method checks to see if a patron is currently
     * logged in.
     * @return true if the patron is logged in and false otherwise
     */
    public boolean isPatronLoggedIn() {
        return this.patronLoggedIn;
    }

    /**
     * The add new patron method allows the administrator to 
     * add a new patron to the database.
     * @param id the new user's id
     * @param password the new user's password
     * @param num the maximum number of books this patron can check out
     */
    public void addNewPatron(String id, String password, int num) {
        if (!adminLoggedIn) {
            throw new IllegalStateException();
        }
        try {
            this.patronList.addNewPatron(id, password, num);
        } catch (Exception e) {
            //Uses the most general exception since the new patron can
            //throw multiple types of exceptions
            //TODO: How to handle this (redisplay dialog box?)
        }
        

    }

    /**
     * The cancel account method removes a patron if the particular
     * id supplied matches a patron in the list.
     */
    public void cancelAccount(String id) {
        if (!adminLoggedIn) {
            throw new IllegalStateException();
        }
        try {
            patronList.cancelAccount(id);
        } catch (IllegalArgumentException e) {
            //TODO: Handle?
        }
        

    }

    /**
     * The list accounts method lists the user accounts for the 
     * library database.
     * @return a list of the accounts
     */
    public String listAcounts() {
        return patronList.toString();
        }
    }


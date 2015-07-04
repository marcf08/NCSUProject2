package edu.ncsu.csc216.wolf_library.lending_system;


import edu.ncsu.csc216.wolf_library.inventory.BookDB;
import edu.ncsu.csc216.wolf_library.patron.AccountManager;
import edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem;
import edu.ncsu.csc216.wolf_library.patron.Patron;
import edu.ncsu.csc216.wolf_library.util.Constants;

/**
 * The library lending system defines the methods used in the back end for the
 * library system.
 * 
 * @author Marcus
 *
 */
public class LibraryLendingSystem implements LendingManager {
    /**
     * The accounts are the patron accounts associated with the system.
     */
    private AccountManager accounts;
    /**
     * The book inventory is the inventory of the books used for the library
     * system
     */
    private BookDB bookInventory;

    /**
     * The constructor instantiates the library lending system given an input
     * String for a file.
     * @param input the file's input
     */
    public LibraryLendingSystem(String input) {
        bookInventory = new BookDB(input);
        accounts = new LibraryAccountSystem();
    }

    /**
     * The show inventory method shows all books in the inventory
     * 
     * @return the string of books in the inventory
     */
    public String showInventory() {
        return bookInventory.traverse();
    }

    /**
     * The login method sets the user for the current context to a given value.
     * 
     * @param id
     *            the user's id
     * @param password
     *            the user's password
     */
    public void login(String id, String password) {
        accounts.login(id, password);
    }

    /**
     * The logout method logs the current user out of the system.
     */
    public void logout() {
        accounts.logout();
    }

    /**
     * The add new patron method adds a new account to the patron database as
     * long as the administrator account is logged in.
     * 
     * @param id
     *            the new user's id
     * @param password
     *            the new user's password
     * @param num
     *            the amount of books the user can check out
     */
    public void addNewPatron(String id, String password, int num) {
        if (!accounts.isAdminLoggedIn()) {
            throw new IllegalStateException(Constants.EXP_ACCESS_DENIED);
        }
        accounts.addNewPatron(id, password, num);
    }

    /**
     * The get current user id returns the currently logged in user's id.
     * 
     * @return the patron's user id who is currently logged in
     */
    public String getCurrentUserId() {
        if (accounts.isAdminLoggedIn()) {
            return Constants.ADMIN;
        } else if (accounts.getCurrentPatron() == null) {
            return ""; //Return empty string per specifications
        } else {
            return accounts.getCurrentPatron().getId();
        }
    }

    /**
     * The cancel account method cancels a user's account.
     * 
     * @param id
     *            the user's id to cancel
     */
    public void cancelAccount(String id) {
        if (!accounts.isAdminLoggedIn()) {
            throw new IllegalStateException(Constants.EXP_ACCESS_DENIED);
        }
        accounts.cancelAccount(id);
    }

    /**
     * The reserve item reserves the selected item for the reserve queue.
     * 
     * @param position
     *            the position of the item to reserve
     */
    public void reserveItem(int position) {
        if (!accounts.isPatronLoggedIn()) {
            throw new IllegalStateException(Constants.EXP_LLS_PATRON_NOT_LOGGED_IN);
        }
        
        accounts.getCurrentPatron().reserve(bookInventory.findItemAt(position));
    }

    /**
     * The reserve move ahead one method moves the item in the reserve queue
     * ahead by one.
     * 
     * @param position
     *            the position of the book in the reserve queue to move up one
     */
    public void reserveMoveAheadOne(int position) {
        if (!accounts.isPatronLoggedIn()) {
            throw new IllegalStateException(Constants.EXP_LLS_PATRON_NOT_LOGGED_IN);
        }
        if (position < 0) {
            throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
        }
        Patron current = accounts.getCurrentPatron();
        current.moveAheadOneInReserves(position);
    }

    /**
     * The remove selected from reserves method removes a book at the given
     * position from the reserve queue.
     * 
     * @param position
     *            the position in the reserve queue to remove
     */
    public void removeSelectedFromReserves(int position) {
        if (!accounts.isPatronLoggedIn()) {
            throw new IllegalStateException(Constants.EXP_LLS_PATRON_NOT_LOGGED_IN);
        }
        if (position < 0) {
            throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
        }
        Patron current = accounts.getCurrentPatron();
        current.unReserve(position);

    }

    /**
     * The traverse reserve queue method shows a string of the books in the
     * reserve queue.
     * 
     * @return the books in the reserve queue
     */
    public String traverseReserveQueue() {
        if (!accounts.isPatronLoggedIn()) {
            throw new IllegalStateException();
        }
        return accounts.getCurrentPatron().traverseReserveQueue();
    }

    /**
     * The traverse checked out queue shows a string of the books in the checked
     * out queue.
     * @return the patron's checked out queue
     */
    public String traverseCheckedOut() {
        if (!accounts.isPatronLoggedIn()) {
            throw new IllegalStateException();
        }
        return accounts.getCurrentPatron().traverseCheckedOut();
    }

    /**
     * The return item method returns a book to the inventory.
     * 
     * @param position the position of the item in the list of checked out items
     */
    public void returnItem(int position) {
        if (!accounts.isPatronLoggedIn()) {
            throw new IllegalStateException(Constants.EXP_LLS_PATRON_NOT_LOGGED_IN);
        }
        if (position < 0) {
            throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
        }
        accounts.getCurrentPatron().returnBook(position);
    }

}
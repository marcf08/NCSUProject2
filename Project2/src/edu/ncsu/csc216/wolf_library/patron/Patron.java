/**
 * 
 */
package edu.ncsu.csc216.wolf_library.patron;

import edu.ncsu.csc216.wolf_library.inventory.Book;
import edu.ncsu.csc216.wolf_library.util.MultiPurposeList;

/**
 * The patron class creates a patron using the base functionality of the User
 * class.
 * 
 * @author Marcus
 *
 */
public class Patron extends User {
    /**
     * The max checked out field designates the maximum number of books a patron
     * can check out.
     */
    private int maxCheckedOut;
    /**
     * The num checked out field designates the current number of books a patron
     * can check out.
     */
    private int numCheckedOut;
    /**
     * The checked out queue is a multi-purpose list of the books the user
     * current has checked out.
     */
    private MultiPurposeList<Book> checkedOut;

    /**
     * The checked out queue is a multi-purpose list of the books the user
     * has reserved. Books that can't be checked out will get stored here.  
     */
    private MultiPurposeList<Book> reserveQueue;
    
    /**
     * The patron constructor constructs a patron given an id and password. It
     * calls the parent constructor from the abstract parent class.
     */
    public Patron(String id, String password, int maxCheckedOut) {
        if (maxCheckedOut < 1) {
            throw new IllegalArgumentException();
        }
        if (id.trim() == null) {
            throw new IllegalArgumentException();
        }
        if (password.trim() == null) {
            throw new IllegalArgumentException();
        }
        super(id, password);
        this.maxCheckedOut = maxCheckedOut;
    }

    
   /**
    * The traverse reserve queue method returns a 
    */
    
    
}

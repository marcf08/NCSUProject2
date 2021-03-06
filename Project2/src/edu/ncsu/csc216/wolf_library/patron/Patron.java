package edu.ncsu.csc216.wolf_library.patron;

import edu.ncsu.csc216.wolf_library.inventory.Book;
import edu.ncsu.csc216.wolf_library.util.Constants;
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
    private int nowCheckedOut;
    /**
     * The checked out queue is a multi-purpose list of the books the user
     * current has checked out.
     */
    private MultiPurposeList<Book> checkedOut;

    /**
     * The checked out queue is a multi-purpose list of the books the user has
     * reserved. Books that can't be checked out will get stored here.
     */
    private MultiPurposeList<Book> reserveQueue;

    /**
     * The patron constructor constructs a patron given an id and password. It
     * calls the parent constructor from the abstract parent class.
     * @param id the patron's id
     * @param password the patron's password
     * @param maxCheckedOut the maximum number of books the patron can check out
     */
    public Patron(String id, String password, int maxCheckedOut) {
        super(id, password);
        if (maxCheckedOut < 1) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_MAX);
        }
        if (getId().equals(Constants.ADMIN)) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_ADMIN);
        }
        this.maxCheckedOut = maxCheckedOut;
        nowCheckedOut = 0;
        checkedOut = new MultiPurposeList<Book>();
        reserveQueue = new MultiPurposeList<Book>();
    }

    /**
     * The traverse reserve queue method returns a string of the books in the
     * reserve queue in the order in which they were reserved.
     * 
     * @return books the string of reserved books
     */
    public String traverseReserveQueue() {
        String books = "";
        for (int i = 0; i < reserveQueue.size(); i++) {
            if (reserveQueue.lookAtItemN(i) != null) {
                books = books + reserveQueue.lookAtItemN(i).getInfo() + "\n";
            }
        }
        return books.trim();
    }

    /**
     * The traverse checked out method return a string of the books in the
     * checked out queue in the order in which they were reserved.
     * 
     * @return books the string of checked out books
     */
    public String traverseCheckedOut() {
        String books = "";
        for (int i = 0; i < checkedOut.size(); i++) {
            if (checkedOut.lookAtItemN(i) != null) {
                books = books + checkedOut.lookAtItemN(i).getInfo() + "\n";
            }
        }
        return books.trim();
    }

    /**
     * The move ahead one in reserves method moves the book in the given
     * position ahead one position in the reserve queue.
     * 
     * @param pos
     *            the position of the book to advance ahead one position
     */
    public void moveAheadOneInReserves(int pos) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (pos >= reserveQueue.size()) {
            throw new IndexOutOfBoundsException();
        }
        if (pos == 0) {
            return; // Do nothing, bail out of the method
        }
        // If no exceptions were thrown and the position was not 0, move the
        // book ahead
        reserveQueue.moveAheadOne(pos);
    }

    /**
     * The unreserve method removes the book in the given position from the
     * reserve queue.
     * 
     * @param pos
     *            the position of the book to unreserve
     */
    public void unReserve(int pos) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (pos >= reserveQueue.size()) {
            throw new IndexOutOfBoundsException();
        }
        // If no exceptions were thrown, remove the book
        reserveQueue.remove(pos);
    }

    /**
     * The reserve method places the book at the end of the reserve queue.
     * 
     * @param book
     *            a book to add to the end of the reserve queue
     */
    public void reserve(Book book) {
        if (book == null) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_NULL_BOOK);
        }
        reserveQueue.addToRear(book);
        if (nowCheckedOut < maxCheckedOut) {
            removeFirstAvailable();
        }
    }


    /**
     * The close account method closes the user's account and returns all
     * checked out books to the library.
     */
    public void closeAccount() {
        // Cycle through the queues in order to ensure we get all books.
        // The reason for this is that the reserve queue automatically
        // checks out books
        if (!checkedOut.isEmpty()) {
            for (int i = 0; i < checkedOut.size(); i++) {
                returnBook(i);
                i--; //If this is not here the size will change--in effect the loop shrinks as the size does
            }
        }

        // Remove reserve books for good measure
        if (!reserveQueue.isEmpty()) {
            for (int i = 0; i < reserveQueue.size(); i++) {
                reserveQueue.remove(i);
                i--; //If this is not here the size will change--in effect the loop shrinks as the size does
            }
        }
    }

    /**
     * The return book returns a book in the given position from the checked out
     * list. It returns the book to the library inventory.
     * 
     * @param pos
     *            the position in the checked out list to return
     */
    public void returnBook(int pos) {
        if (pos < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (pos >= checkedOut.size()) {
            throw new IndexOutOfBoundsException();
        }
        // Remove the book from the list
        Book toReturn = (Book) checkedOut.remove(pos);
        // Check it back in
        toReturn.backToInventory();
        nowCheckedOut--;
        
        if (nowCheckedOut < maxCheckedOut) {
            //Autocheck out
            removeFirstAvailable();
        }
    }

    /**
     * The remove first available method removes the first book available from
     * the reserve queue to the checked out queue.
     * 
     * @return firstAvail the first available book
     */
    private Book removeFirstAvailable() {
        Book firstAvail = null;
        int index = 0;
        // Find first available
        for (int i = 0; i < reserveQueue.size(); i++) {
            if (((Book) reserveQueue.lookAtItemN(i)).isAvailable()) {
                firstAvail = (Book) reserveQueue.lookAtItemN(i);
                index = i;
                break;
                // Done, found it, break out of the method
            }
        }
        if (firstAvail != null) {
            checkedOut.addToRear(firstAvail);
            reserveQueue.remove(index);
            firstAvail.removeOneCopyFromInventory();
            nowCheckedOut++;
        }
        return firstAvail;
    }

}

/**
 * 
 */
package edu.ncsu.csc216.wolf_library.inventory;

import java.util.Scanner;

import edu.ncsu.csc216.wolf_library.util.Constants;

/**
 * This class defines the methods of a book object for the Wolf Library project.
 * It contains the information about the book as well as methods for accessing
 * the book and keeping track of the copies currently in stock.
 * 
 * @author Marcus
 *
 */
public class Book implements Comparable<Book> {
    /**
     * The info string gives information about the book, including the author
     * and title.
     */
    private String info;
    /**
     * The num available integer keeps track of the number of books available.
     */
    private int numAvailable;

    /**
     * The constructor constructs a book object given info from a file. It
     * accepts input from a line of text in an input file. The input should come
     * in the form <number-in-stock><whitespace><book-info>. The book object
     * parses this string into the parts it needs to construct the book for use
     * by the Wolf Library program.
     */
    public Book(String info) {
        Scanner fromFile = new Scanner(info); // Create a scanner to read the
                                              // line from a file
        String numAvailable = fromFile.next(); // Get the number available in
                                               // String form

        // The scanner has now advanced past the numbers available (if it
        // exists)

        // Now build the rest of the title/info String
        String titleAuthor = "";
        while (fromFile.hasNext()) {
            titleAuthor = titleAuthor + fromFile.next() + " ";

     
        }
        
      
        

        fromFile.close(); // Done with the scanner at this point

        // Attempt to parse the copies available as an int
        try {
            if (Integer.parseInt(numAvailable) < 0) { // If it's negative, set
                                                      // it to zero
                this.numAvailable = 0;
            } else {
                this.numAvailable = Integer.parseInt(numAvailable);
            }
        } catch (IllegalArgumentException e) {
            
        }

        // Invalid book title represented by the null String
        if (titleAuthor.equals("")) {
            throw new IllegalArgumentException();
        }

        // Finally, set this to the instance variable
        this.info = titleAuthor.trim();
        
    }

    /**
     * The get info method is a simple getting for the information about a book.
     * 
     * @return info the title and author information for the selected book
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * The to string method returns a string representation of the book based on
     * whether or not it's available. If copies exist in stock, it returns the
     * book. Otherwise, it returns the listing prepended with an asterisk.
     * 
     * @return libraryListing a book listing from the inventory
     */

    public String toString() {
        String libraryListing = "";

        // Start with a String and build it per the requirements

        if (this.numAvailable == 0) {
            libraryListing = Constants.CURRENTLY_UNAVAILABLE + this.info;
        } else {
            libraryListing = this.info;
        }
        return libraryListing;
    }

    /**
     * The is available method is a simple boolean test for whether or not any
     * copies of a book are available.
     * 
     * @return true if there are copies available and false otherwise
     */
    public boolean isAvailable() {
        return (this.numAvailable != 0);
    }

    /**
     * The back to inventory method puts a copy of the book back into the
     * inventory stock. It simply increments the number available.
     */
    public void backToInventory() {
        this.numAvailable++;
    }

    /**
     * The remove one copy from inventory method removes a copy of the book from
     * the inventory stock. It simply decrements the number available.
     */
    public void removeOneCopyFromInventory() {
        if (numAvailable == 0) {
            throw new IllegalStateException(Constants.EXP_BOOK_UNAVAILABLE);
        }
        this.numAvailable--;
    }

    /**
     * The compare to method compares one book to another for sorting purposes.
     * It takes two books and returns an integer values based on which one is
     * larger than the other. The compare to method ignores books with
     * information prefixed by "a", "an", or "the." It calls another method to
     * strip the books of those prefixes for the purposes of comparison.
     * 
     * @param other
     *            another book to compare to for sorting purposes
     * @return an integer value obtained via lexicographical comparison
     */
    public int compareTo(Book other) {
        if (other == null) {
            throw new NullPointerException(Constants.EXP_CANNOT_COMPARE);
        }
        // First, get the books in the proper format for comparison
        String book = correctBooksForComparison(this);
        String otherBook = correctBooksForComparison(other);
        // Return the integer value obtained via the comparison method
        return book.compareTo(otherBook);
    }

    /**
     * The comparison method compares books omitting case. It also compares them
     * if they begin with "a", "an", or "the". Doing this requires a little work
     * that is best left out of the compare to method.
     * 
     * @param book
     *            a book to correct for comparison purposes
     * @return info the corrected information for book comparison purposes
     */
    private String correctBooksForComparison(Book book) {
        String correctedInfo = ""; // This String is the corrected info that the
                                   // method will return

        String first = book.getInfo(); // First, get the info as it is

        String second = first.toLowerCase(); // Second, put the book in lower
                                             // case
        // This ignores case

        // We'll also need a scanner to rebuild the title without the
        // offending prefixes
        Scanner bookInfo = new Scanner(second);

        // Deal with "a"
        if (second.startsWith("a")) {
            bookInfo.next(); // Advance the scanner past the first token
            while (bookInfo.hasNext()) {
                correctedInfo = correctedInfo + bookInfo.next() + " "; // Build the
                                                                 // amended
                                                                 // title
            }

        }

        // Deal with "an"
        else if (second.startsWith("an")) {
            bookInfo.next(); // Advance the scanner past the first token
            while (bookInfo.hasNext()) {
                correctedInfo = correctedInfo + bookInfo.next() + " "; // Build the
                                                                 // amended
                                                                 // title
            }

        }

        // Deal with "the"
        else if (second.startsWith("the")) {
            bookInfo.next(); // Advance the scanner past the first token
            while (bookInfo.hasNext()) {
                correctedInfo = correctedInfo + bookInfo.next() + " "; // Build the
                                                                 // amended
                                                                 // title
            }
        }

        // Might as well return the info even if it doesn't need any changes
        else {
            correctedInfo = second;
        }

        bookInfo.close(); // Close the scanner for good measure

        // Return the corrected title and author information
        return correctedInfo;
    }

}

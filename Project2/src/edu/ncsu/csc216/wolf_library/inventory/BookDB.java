package edu.ncsu.csc216.wolf_library.inventory;




import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import edu.ncsu.csc216.wolf_library.util.*;


/**
 * The book db class represents an internal book data base used by the Wolf
 * Library system. It consists of a multi-purpose list used to keep track of the
 * books in the library.
 * 
 * @author Marcus
 *
 */
public class BookDB {
    /**
     * The primary data member is a multi-purpose list developed in the
     * wolf_library.util package.
     */
    private MultiPurposeList<Book> books;

    /**
     * The constructor creates the database using a string of input obtained
     * from the input file.
     * 
     * @param input
     *            information taken from an input file and formatted properly
     */
    public BookDB(String input) {
        books = new MultiPurposeList<Book>();
        readFromFile(input);
    }

    /**
     * The read from file method accepts a file name as a string and uses this
     * to access the text file of books.
     * 
     * @param fileName
     *            the name of an input file
     */
    private void readFromFile(String fileName) {
        // Set up the input file
        File inputFile = new File(fileName);

        // Ensure the user has read access
        if (!inputFile.canRead()) {
            throw new IllegalArgumentException();
        }

        // Set up the scanner to read from the input file
        Scanner readInput = null;
        try {
            readInput = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error reading from input.");
        }
        while (readInput.hasNextLine()) {
            String lineFromFile = readInput.nextLine();
            insertInOrder(new Book(lineFromFile));

        }

    }

    /**
     * The insert in order method examines the books from the input file. It
     * compares the last book in the list in order to see where the new book
     * should go.
     * 
     * @param book
     *            a book to insert into the inventory
     */
    private void insertInOrder(Book book) {
       if (books.isEmpty()) {
           books.addToRear(book);
       } else {
           Book last = books.lookAtItem(books.size() - 1);
           System.out.println("LAST: " + last.toString());
           int compare = last.compareTo(book);
           System.out.println("Compare: " + compare);
           if (compare < 0) {
               this.books.addItem(books.size(), book);
               
           } else {
               
               books.addToRear(book);
              
           }
           
           
       }
            
        
        
        
        
        
        
        
        
    }

    /**
     * The traverse method returns a string of the books in the database in the
     * proper order.
     * 
     * @return bookListing the listing of books in the database
     */
    // TODO: REMOVE COUNT
    public String traverse() {
        String bookListing = "";
        int i = 0;
        while (i < books.size()) {
            bookListing = bookListing + books.lookAtItem(i) + "\n";
            i++;
            
        }
        return bookListing + "\n" + books.size();
    }

}
